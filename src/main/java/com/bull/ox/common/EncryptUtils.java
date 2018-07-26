package com.bull.ox.common;

import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.security.SecureRandom;

public class EncryptUtils {

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 10;

    public static SecureRandom secureRandom = new SecureRandom();

    // 加密密码
    public static String encryptPassword(String plainPassword) {
        byte[] salt = generateSalt();
        return encryptPassword(plainPassword,salt);
    }

    public static String encryptPassword(String plainPassword,byte[] salt) {
        Sha1Hash sha1Hash = new Sha1Hash(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return sha1Hash.toHex();
    }

    // 生成Salt
    public static byte[] generateSalt() {
        return generateSalt(SALT_SIZE);
    }

    public static byte[] generateSalt(int byteSize) {
        byte[] bytes = new byte[byteSize];
        secureRandom.nextBytes(bytes);
        return bytes;
    }
}
