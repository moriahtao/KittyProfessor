package com.cs5500.team209;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import jplag.ExitException;
import jplag.Program;
import jplag.options.CommandLineOptions;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by mengtao on 3/23/18.
 */
public class Parser {
    final static Logger logger = Logger.getLogger(Parser.class);
    final static AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion("us-east-1").build();
    final static TransferManager tx = new TransferManager(s3);
    public static double parse(String reportFilePath, String language, String user1,
                               String user2, String assignmentName)
            throws IOException {

        try {
            String[] input = new String[]{"-l", language, "-r", reportFilePath, "-s", "exercise1"};
            CommandLineOptions options = new CommandLineOptions(input, null);
            Program program = new Program(options);

            logger.info("initialize ok");
            program.run();
        }
        catch(ExitException ex) {
            logger.info("Error: "+ex.getReport());
        }

        String path = reportFilePath+"/match0-link.html";

        Document scorePage = Jsoup.parse(new File(path), "utf-8");
        String percentage = scorePage.select("h1").text();

        String matchTop = "<html> <body><h2>Similarity "+percentage+"</h2><br>" +
                "<h3>Between "+user1+" "+user2+" for "+ assignmentName +" </h3></body> </html>";
        PrintWriter writer = new PrintWriter(reportFilePath+"/match-top-custom.html", "UTF-8");
        writer.println(matchTop);
        writer.close();


        PrintWriter writer1 = new PrintWriter(reportFilePath+"/match0.html", "UTF-8");
        writer1.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n" +
                "<HTML><HEAD><TITLE>Matches for target & src</TITLE>\n" +
                "<META http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n" +
                "\n" +
                "</HEAD>\n" +
                "<FRAMESET ROWS=\"130,*\">\n" +
                "<FRAMESET COLS=\"60%,40%\">" +
                "  <FRAME SRC=\"match-top-custom.html\" NAME=\"link\" FRAMEBORDER=0>\n" +
                "  <FRAME SRC=\"match0-top.html\" NAME=\"top\" FRAMEBORDER=0 style=\"visibility:hidden;\">\n" +
                "\n" +
                " </FRAMESET>\n" +
                " <FRAMESET COLS=\"50%,50%\">\n" +
                "  <FRAME SRC=\"match0-0.html\" NAME=\"0\">\n" +
                "  <FRAME SRC=\"match0-1.html\" NAME=\"1\">\n" +
                " </FRAMESET>\n" +
                "</FRAMESET>\n" +
                "</HTML>\n");
        writer1.close();

        String style = "<style> pre{ font-family: Verdana!important; font-size: 18px; } </style>";

        try {
            Files.write(Paths.get(reportFilePath+"/match0-0.html"),
                    style.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(reportFilePath+"/match0-1.html"),
                    style.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            logger.error("Error writing to file");
        }




        MultipleFileUpload upload = tx.uploadDirectory("kittyprofessor-report",
                reportFilePath.split("/")[reportFilePath.split("/").length -1],
                new File(reportFilePath),
                true);

        return Double.parseDouble(percentage.split("%")[0]);

    }
}
