package com.cs5500.team209;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import jplag.ExitException;
import jplag.Program;
import jplag.options.CommandLineOptions;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by mengtao on 3/23/18.
 */
public class Parser {
    final static Logger logger = Logger.getLogger(Parser.class);
    final static AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion("us-east-1").build();
    final static TransferManager tx = new TransferManager(s3);
    public static double parse(String reportFilePath, String language) throws IOException {

        try {
            String[] input = new String[]{"-l", language, "-r", reportFilePath, "-s", "exercise1"};
            CommandLineOptions options = new CommandLineOptions(input, null);
            Program program = new Program(options);

            logger.info("initialize ok");
            program.run();
        }
        catch(ExitException ex) {
            logger.info("Error: "+ex.getReport());
            System.exit(1);
        }

        String path = reportFilePath+"/match0-link.html";

        Document scorePage = Jsoup.parse(new File(path), "utf-8");
        String percentage = scorePage.select("h1").text();

        MultipleFileUpload upload = tx.uploadDirectory("kittyprofessor-report",
                reportFilePath.split("/")[reportFilePath.split("/").length -1],
                new File(reportFilePath),
                true);

        return Double.parseDouble(percentage.split("%")[0]);

    }
}
