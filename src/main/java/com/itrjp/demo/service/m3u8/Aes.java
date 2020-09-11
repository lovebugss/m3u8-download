package com.itrjp.demo.service.m3u8;

import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;

/**
 * @author : renjp
 * @date : 2020-09-10 22:12
 **/
public class Aes {
    public final static String ENCODING = "utf-8";

    /**
     * 密钥key
     */
    private String key;
    /**
     * 向量iv
     */
    private String iv;

    public Aes(String key, String iv) {
        this.key = key;
        this.iv = iv;
    }

    public Aes(String key) {
        this.key = key;
    }

    byte[] decrypt(InputStream inputStream) {
        if (this.iv != null) {
            return decrypt(inputStream, key, iv);
        } else {
            return decrypt(inputStream, key);
        }
    }

    /**
     * @param inputStream
     * @param key
     * @return
     */
    private static byte[] decrypt(InputStream inputStream, String key) {
        try {
            byte[] raw = key.getBytes(ENCODING);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(inputStream);
            try {
                return cipher.doFinal(encrypted1);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * @param inputStream
     * @param key
     * @param iv
     * @return
     */
    private static byte[] decrypt(InputStream inputStream, String key, String iv) {
        try {
            byte[] raw = key.getBytes(ENCODING);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] ivv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ips = new IvParameterSpec(decodeHex(iv));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ips);

            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(inputStream);
            try {
                return cipher.doFinal(encrypted1);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static byte[] decodeHex(String input) {
        char[] data = input.toCharArray();
        int len = data.length;
        if ((len & 0x01) != 0) {

        }
//        val out = ByteArray(len shr 1)
        byte[] out = new byte[len];
        try {
            int i = 0;
            int j = 0;
            while (j < len) {
                int f = toDigit(data[j], j) << 4;
                j++;
                f = f | toDigit(data[j], j);
                j++;
                out[i] = (byte) (f & 0xFF);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    private static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character $ch at index $index");
        }
        return digit;
    }

    @Override
    public String toString() {
        return "Aes{" +
                "key='" + key + '\'' +
                ", iv='" + iv + '\'' +
                '}';
    }
}
