package com.cs5500.team209.astgen;

import org.junit.Test;

import java.io.IOException;

/**
 * Tests AST generator for an external file
 */
public class ASTGeneratorTest {

    @Test(expected = IOException.class)
    public void generateASTtest() throws IOException {
        ASTGenerator astGenerator = new ASTGenerator();
        astGenerator.generateAST("unknown/location", false);
    }

    @Test
    public void generateASTtestcorrect() throws IOException {
        ASTGenerator astGenerator = new ASTGenerator();
        astGenerator.generateAST("SampleFiles/sample1.py", false);

    }
}
