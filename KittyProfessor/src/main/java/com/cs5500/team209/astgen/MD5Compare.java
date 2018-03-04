package com.cs5500.team209.astgen;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    public boolean compare(String content1, String content2) throws NoSuchAlgorithmException {

        MessageDigest md_1 = MessageDigest.getInstance("MD5");
        MessageDigest md_2 = MessageDigest.getInstance("MD5");

        try {
            InputStream is_1 = new DigestInputStream(new ByteArrayInputStream(content1.getBytes()), md_1);
            InputStream is_2 = new DigestInputStream(new ByteArrayInputStream(content2.getBytes()), md_2);
        }
        catch (Exception e){
            return false;
        }
        byte[] digest_1 = md_1.digest();
        byte[] digest_2 = md_2.digest();
        return digest_1.equals(digest_2);
    }
}
