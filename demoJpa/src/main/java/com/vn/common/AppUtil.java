package com.vn.common;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.io.File;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

public class AppUtil {
    public static SimpleDateFormat sdf_yyyyMMddHHmmssSSS = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static Random RANDOM = new Random();
    static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public AppUtil() {
    }

    public static synchronized String generateTransId() {
        String val1 = sdf_yyyyMMddHHmmssSSS.format(new Date());
        String val2 = String.format("%03d", RANDOM.nextInt(999));
        return val1 + val2;
    }

    public static synchronized String generateAirtimeTransId(String prefix) {
        String data = prefix + generateTransId();
        return data;
    }

    public static synchronized String nvl(String input, String defaultValue) {
        return input != null ? input : defaultValue;
    }

    public static synchronized String decryptPropertyValue(String encryptedPropertyValue) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("jasypt");
        String decryptedPropertyValue = encryptor.decrypt(encryptedPropertyValue);
        return decryptedPropertyValue;
    }

    public static synchronized String decryptPropertyValue(String encryptedPropertyValue, String password) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(password);
        String decryptedPropertyValue = encryptor.decrypt(encryptedPropertyValue);
        return decryptedPropertyValue;
    }

    public static synchronized String generateFileLocation(String fileLocation, String prefix) {
        String strDate = sdf.format(new Date());
        String[] str = strDate.split("/");
        String day = str[0];
        String month = str[1];
        String year = str[2];
        String relativePath = String.format("%s/%s/%s/%s", prefix, year, month, day);
        String fileDir = String.format("%s/%s", fileLocation, relativePath);
        File f = new File(fileDir);
        boolean success = true;
        if (!f.exists()) {
            success = f.mkdirs();
        }

        return success ? relativePath : null;
    }

    public static synchronized String convertUnicode(String str){
        String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}
