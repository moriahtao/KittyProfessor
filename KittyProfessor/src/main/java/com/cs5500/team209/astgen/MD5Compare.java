package com.cs5500.team209.astgen;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Compares two strings using MD5 compression.
 */
public class MD5Compare {

    /**
     * Compares two strings using MD5
     * @param content1 string 1
     * @param content2 String 2
     * @return true if contents are same, false otherwise.
     * @throws NoSuchAlgorithmException
     */
    public boolean compare(String content1, String content2) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        byte[] bytesOfMessage1 = content1.getBytes("UTF-8");
        byte[] bytesOfMessage2 = content2.getBytes("UTF-8");


        MessageDigest md_1 = MessageDigest.getInstance("MD5");
        MessageDigest md_2 = MessageDigest.getInstance("MD5");

        byte[] digest_1 = md_1.digest(bytesOfMessage1);
        byte[] digest_2 = md_2.digest(bytesOfMessage2);
        return Arrays.equals(digest_1, digest_2);
    }
}
