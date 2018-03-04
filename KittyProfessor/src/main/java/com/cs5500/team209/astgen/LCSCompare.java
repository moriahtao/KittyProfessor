package com.cs5500.team209.astgen;

/**
 * Compares two files using Lowest Common SubSequence approach
 * Part of it is taken from internet.
 * @author bala
 */
public class LCSCompare {

    /**
     * Constructor for LCS
     */
    public LCSCompare() {

    }

    /**
     * Compares and returns the LCS distances between two strings.
     * @return LCS value
     */
    public  int compare(String content1, String content2) {

        return lcs(content1.toCharArray(), content2.toCharArray(),
                content1.length(), content2.length());
    }

    /**
     * Longest common subsequence recursive caller
     * @param X charArray of string1
     * @param Y charArray of string2
     * @param m length of string1
     * @param n length of string2
     * @return LCA value
     */
    private int lcs( char[] X, char[] Y, int m, int n )
    {
        if (m == 0 || n == 0)
            return 0;
        if (X[m-1] == Y[n-1])
            return 1 + lcs(X, Y, m-1, n-1);
        else
            return max(lcs(X, Y, m, n-1), lcs(X, Y, m-1, n));
    }

    /**
     * returns max of two numbers
     * @param a first integer
     * @param b second integer
     * @return integer
     */
    private int max(int a, int b) {
        return (a > b)? a : b;
    }
}
