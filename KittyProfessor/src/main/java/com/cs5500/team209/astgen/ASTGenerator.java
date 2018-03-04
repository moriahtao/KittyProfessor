package com.cs5500.team209.astgen;

import com.cs5500.team209.antlr.Python3Lexer;
import com.cs5500.team209.antlr.Python3Parser;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;


/**
 * Generates AST, can be viewed using JFRAME.
 * @author bala
 */
public class ASTGenerator {

    /**
     * generates AST and displays in a JFrame
     * @param path path for the file
     * @throws IOException If the file is not available.
     */
    public void generateAST(String path) throws IOException {

        CharStream charStream = CharStreams.fromFileName(path);
        Python3Lexer lexer = new Python3Lexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Python3Parser parser = new Python3Parser(tokens);
        ParseTree tree = parser.file_input();
        explore( parser.file_input(), 0);

        JFrame frame = new JFrame("Antlr AST");
        JPanel panel = new JPanel();
        TreeViewer viewr = new TreeViewer(Arrays.asList(
                parser.getRuleNames()),tree);
        viewr.setScale(1.5);//scale a little
        panel.add(viewr);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setVisible(true);
    }

    /**
     * Loops through the nodes
     * @param ctx RuleContext for the parser
     * @param indentation Usually 0, initial indentation
     */
    private static void explore(RuleContext ctx, int indentation) {
        String ruleName = Python3Parser.ruleNames[ctx.getRuleIndex()];
        for (int i=0;i<indentation;i++) {
            System.out.print("  ");
        }
        System.out.println(ruleName);
        for (int i=0;i<ctx.getChildCount();i++) {
            ParseTree element = ctx.getChild(i);
            if (element instanceof RuleContext) {
                explore((RuleContext)element, indentation + 1);
            }
        }
    }
}
