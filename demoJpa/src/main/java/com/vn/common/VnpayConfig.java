package com.vn.common;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

public class VnpayConfig {
    private static final Logger logger = LoggerFactory.getLogger(VnpayConfig.class);

    public static String vnp_PayUrl = "http://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static String vnp_Returnurl = "http://localhost:8284/duan2_war/vnpay-transaction-result";
    public static String vnp_TmnCode = "QFFJOYEW";
    public static String vnp_HashSecret = "AJQGVLDDCBGRLZJARAEQDOFYJPKHSWDU";
    public static String vnp_apiUrl = "http://sandbox.vnpayment.vn/merchant_webapi/merchant.html";

    public static String md5(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            // converting byte array to Hexadecimal String
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (UnsupportedEncodingException ex) {
            digest = "";
        } catch (NoSuchAlgorithmException ex) {
            digest = "";
        }
        return digest;
    }

    public static String Sha256(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            // converting byte array to Hexadecimal String
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (UnsupportedEncodingException ex) {
            digest = "";
        } catch (NoSuchAlgorithmException ex) {
            digest = "";
        }
        return digest;
    }

    // Util for VNPAY
    public static String hashAllFields(Map fields) throws UnsupportedEncodingException {
        // create a list and sort it
        List fieldNames = new ArrayList(fields.keySet());
        Collections.sort(fieldNames);
        // create a buffer for the md5 input and add the secure secret first
        StringBuilder sb = new StringBuilder();
        sb.append(vnp_HashSecret);
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fieldValue = URLDecoder.decode(fieldValue, StandardCharsets.UTF_8.toString()).trim();
                fieldName = URLDecoder.decode(fieldName, StandardCharsets.UTF_8.toString()).trim();
                sb.append(fieldName);
                sb.append("=");
                sb.append(URLDecoder.decode(fieldValue, "UTF-8"));
            }
            if (itr.hasNext()) {
                sb.append("&");
            }
        }
        logger.info("sb {}", sb.toString());
        return Sha256(sb.toString());
    }

    public static String hashAllFieldsMD5(Map fields) throws UnsupportedEncodingException {
        // create a list and sort it
        List fieldNames = new ArrayList(fields.keySet());
        Collections.sort(fieldNames);
        // create a buffer for the md5 input and add the secure secret first
        StringBuilder sb = new StringBuilder();
        sb.append(vnp_HashSecret);
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fieldValue = URLDecoder.decode(fieldValue, StandardCharsets.UTF_8.toString()).trim();
                fieldName = URLDecoder.decode(fieldName, StandardCharsets.UTF_8.toString()).trim();
                sb.append(fieldName);
                sb.append("=");
                sb.append(URLDecoder.decode(fieldValue, "UTF-8"));
            }
            if (itr.hasNext()) {
                sb.append("&");
            }
        }
        logger.info("sb {}", sb.toString());
        return md5(sb.toString());
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ipAdress;
        try {
            ipAdress = request.getHeader("X-FORWARDED-FOR");
            if (ipAdress == null) {
                ipAdress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAdress = "Invalid IP:" + e.getMessage();
        }
        return ipAdress;
    }

    public static String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static Map<String, String> convertBodyToMap(String body) {
        Map<String, String> response = new HashMap<>();
        if (!Strings.isNullOrEmpty(body)) {
            if (body.startsWith("{")) {
                body = body.substring(1);
            }
            if (body.endsWith("}")) {
                body = body.substring(0, body.length() - 1);
            }
            String[] stringArr = body.trim().split("&");
            for (String string : stringArr) {
                if (!Strings.isNullOrEmpty(string)) {
                    string = string.trim();
                    String[] strArr = string.split("=");
                    String key = strArr[0];
                    String value = strArr[1].replace("+", " ");
                    response.put(key, value);
                }
            }
        }
        return response;
    }
}
