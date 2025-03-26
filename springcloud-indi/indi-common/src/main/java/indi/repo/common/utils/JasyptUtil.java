package indi.repo.common.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * @author ChenHQ
 * @date 2025/3/26 16:54
 */
public class JasyptUtil {

    public static String encrypt(String plainText, String password) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(password);
        return encryptor.encrypt(plainText);
    }

    public static void main(String[] args) {
        String plainText = "ailand@VFR_";
        String password = "1qazCDE3!@#";
        System.out.println("ENC(" + encrypt(plainText, password) + ")");
    }
}