package com.cs5500.team209.astgen;

import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Test cases for comparaing two strings
 */
public class CompareTest {

    /**
     * Testing lowest common sub sequence
     */
    @Test
    public void compareLCSTest() {
        LCSCompare lcsCompare = new LCSCompare();
        String code1 = "def sum(a, b, c):";
        String code2 = "def sum(a, b):";
        Assert.assertEquals(lcsCompare.compare(code1, code2), 14);
    }

    /**
     * Testing using MD5
     */
    @Test
    public void compareMD5Test() throws NoSuchAlgorithmException,
            UnsupportedEncodingException {
        MD5Compare md5Compare = new MD5Compare();
        Assert.assertTrue(md5Compare.compare("abc", "abc"));
        Assert.assertFalse(md5Compare.compare("abc", "def sum(a, b):"));

    }
}
