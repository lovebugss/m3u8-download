package com.itrjp.demo.service.m3u8;

import com.itrjp.demo.service.m3u8.bean.Inf;
import com.itrjp.demo.service.m3u8.bean.Key;
import com.itrjp.demo.service.m3u8.bean.M3u8;
import com.itrjp.demo.util.DownloadUtil;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 下载mu38文件
 * 1. 下载索引文件
 * 2. 解析文件
 * 3. 判断是否加密
 * 3.1 下载加密文件
 * 3.2 读取密钥
 * 4. 下载视频文件
 *
 * @author : renjp
 * @date : 2020-09-10 21:36
 **/
public class M3U8Service {
    private static Pattern VERSION_COMPILE = Pattern.compile("#EXT-X-VERSION:(\\d)");
    private static Pattern KEY_COMPILE = Pattern.compile("#EXT-X-KEY:METHOD=(.*),URI=\"(.*)\",IV=(.*)");

    public static void main(String[] args) throws IOException {
        String url = "http://renjp-s3-test.s3.amazonaws.com/videos/gnzw/output.m3u8";
        String prefix = extractUrlPrefix(url);
        // 1. 下载索引文件
        String indexFile = downloadIndex(url);
        // 2. 解析文件
        M3u8 m3u8 = parserIndexFile(indexFile);
//        // 判断是否加密
        Key key = m3u8.getKey();
        Aes aes = generateAesUtil(prefix, key);
//        // 下载文件
        List<Inf> infList = m3u8.getInfList();
        List<CompletableFuture<String>> infFuture = infList.stream()
                .map(inf ->
                        CompletableFuture.supplyAsync(() -> downloadFile(inf, prefix, aes))
                )
                .collect(Collectors.toList());
        infFuture.stream()
                .map(CompletableFuture::join)
                .forEach(System.out::println);
    }

    private static Aes generateAesUtil(String prefix, Key key) throws IOException {
        System.out.printf("generateAesUtil() param : %s, %s%n", prefix, key);
        if (key == null) {
            return null;
        }
        String uri = key.getUri();
        if (!uri.contains("http")) {
            String[] split = uri.split("/");
            uri = prefix + split[split.length - 1];
        }
        String s = DownloadUtil.downloadFile(uri);
        String iv = key.getIv();
        return new Aes(s, iv);
    }

    private static String extractUrlPrefix(String url) {
        String[] split = url.split("/");
        return url.replace(split[split.length - 1], "");
    }

    /**
     * 下载文件视频
     *
     * @param inf
     * @param prefix url前缀
     * @param aes    url前缀
     * @return
     */
    private static String downloadFile(Inf inf, String prefix, Aes aes) {
        String format = String.format("inf: %s, prefix: %s, key: %s", inf, prefix, aes);
        String url = prefix + inf.getPath();
        String[] split = url.split("/");

        String path = "tmp/" + split[split.length - 2];
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        path = path + "/" + split[split.length - 1];
        System.out.println("path: " + path);
        try {
            InputStream download = DownloadUtil.download(url);
            byte[] decrypt = aes.decrypt(download);
            try (FileOutputStream fileOutputStream = new FileOutputStream(new File(path))) {
                fileOutputStream.write(decrypt);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return format;
    }

    /**
     * 解析index 文件
     *
     * @param indexFile
     * @return
     */
    private static M3u8 parserIndexFile(String indexFile) {
        System.out.println("parserIndexFile() param: " + indexFile);
        if (indexFile == null || !indexFile.startsWith("#EXTM3U")) {
            throw new RuntimeException("无效的M3uU8格式");
        }
        M3u8 m3u8 = new M3u8();
        Matcher versionMatcher = VERSION_COMPILE.matcher(indexFile);
        if (versionMatcher.find()) {
            m3u8.setVersion(Integer.parseInt(versionMatcher.group(1)));
        }
        Matcher keyMatcher = KEY_COMPILE.matcher(indexFile);
        if (keyMatcher.find()) {
            m3u8.setKey(new Key(keyMatcher.group(1), keyMatcher.group(2), keyMatcher.group(3)));
        }
        String[] split = indexFile.replaceAll("(#EXTM3U)|(?:#EXT-.*)|(#EXTINF:)", "#").split("#");
        if (split.length > 0) {
            m3u8.setInfList(Arrays.stream(split)
                    .filter(StringUtils::hasText)
                    .map(s -> {
                        String[] split1 = s.split(",\\s");
                        return new Inf(split1[0], split1[1].trim());
                    })
                    .collect(Collectors.toList()));
        }
        return m3u8;
    }

    /**
     * 下载索引文件
     *
     * @param url
     * @return
     */
    private static String downloadIndex(String url) throws IOException {

        return DownloadUtil.downloadFile(url);
    }
}

