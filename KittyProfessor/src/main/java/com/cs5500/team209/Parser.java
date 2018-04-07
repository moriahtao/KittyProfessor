package com.cs5500.team209;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jplag.ExitException;
import jplag.Program;
import jplag.options.CommandLineOptions;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;

/**
 * Created by mengtao on 3/23/18.
 */
public class Parser {
    final static Logger logger = Logger.getLogger(Parser.class);
    final static AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion("us-east-1").build();
    public static double parse(String reportFilePath) throws IOException {

        try {
            String[] input = new String[]{"-l", "c/c++", "-r", "result", "exercise1"};
            CommandLineOptions options = new CommandLineOptions(input, null);
            Program program = new Program(options);

            logger.info("initialize ok");
            program.run();
        }
        catch(ExitException ex) {
            logger.info("Error: "+ex.getReport());
            System.exit(1);
        }


        String path = "result/match0-link.html";
        String student1Path = "result/match0-0.html";
        String student2Path = "result/match0-1.html";

        Document scorePage = Jsoup.parse(new File(path), "utf-8");
        String percentage = scorePage.select("h1").text();

        Document student1CodePage = Jsoup.parse(new File(student1Path), "utf-8");
        String student1Code = student1CodePage.select("pre").html();

        Document student2CodePage = Jsoup.parse(new File(student2Path), "utf-8");
        String student2Code = student2CodePage.select("pre").html();
        File file = new File(reportFilePath);

        logger.info("This is info" + percentage);

        String newdata =
                "<html>" +
                        "<head>"+
        "<title>Results</title>"+
        "<link rel=\"stylesheet\" href=\"/css/app.css\">"+
        "</head>"  +
                "<div class=\"row expanded\">\n" +
                "    <div class=\"columns\">\n" +
                "        <div class=\"main--content\">\n" +
                "            <h2 class=\"inner--heading\">\n" +
                "                <nav aria-label=\"You are here:\" role=\"navigation\">\n" +
                "                    <ul class=\"breadcrumbs innerpage--breadcrumbs\">\n" +
                "                        <li><a href=\"#\">Courses</a></li>\n" +
                "                        <li><a href=\"#\">CS5500 | Managing Software Development</a></li>\n" +
                "                        <li><a href=\"#\">Assignemnts</a></li>\n" +
                "                        <li><a href=\"#\">Homework1 | Working with git</a></li>\n" +
                "                        <li><a href=\"#\">Student Report</a></li>\n" +
                "                        <li>\n" +
                "                            <span class=\"show-for-sr\">Current: </span> Complete Report\n" +
                "                        </li>\n" +
                "                    </ul>\n" +
                "                </nav>\n" +
                "            </h2>\n" +
                "            <div class=\"row expanded\">\n" +
                "                <div class=\"columns large-4\">\n" +
                "                    <div class=\"similarity-percent\">\n" +
                "                        <h1><span>"+ percentage +"</span></h1>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"row\">\n" +
                "                <div class=\"columns large-5 generate--report\">\n" +
                "                    <a href=\"\"><button class=\" button btn--primary right\">Generate Report</button></a>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "\n" +
                "            <div class=\"row expanded comparison--report\">\n" +
                "                <div class=\"columns\">\n" +
                "                    <div class=\"student-code\">\n" +
                "\n<pre>"+ student1Code+ "</pre>"+
                "                    </div>\n" +
                "                </div>\n" +
                "                <div class=\"columns \">\n" +
                "                    <div class=\"student-code\">\n" +
                "\n<pre>"+student2Code+"</pre>"+
                "                    </div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"row\">\n" +
                "                <div class=\"columns large-5 generate--report\">\n" +
                "                    <a href=\"mailto:student@example.com?Subject=Hello%20again\"><button class=\" button btn--primary right\">Email Report</button></a>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "<footer>\n" +
                "    <div class=\"row expanded footer\">\n" +
                "        <div class=\"columns\">\n" +
                "            <div class=\"footer--links\">\n" +
                "                <a href=\"#\">\n" +
                "                    Feedback\n" +
                "                </a>\n" +
                "                <a href=\"\">\n" +
                "                    Contact us\n" +
                "                </a>\n" +
                "            </div>\n" +
                "\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</footer>\n" +
                "\n" +
                "<script src=\"/node_modules/jquery/dist/jquery.js\"></script>\n" +
                "<script src=\"/node_modules/what-input/dist/what-input.js\"></script>\n" +
                "<script src=\"/node_modules/foundation-sites/dist/js/foundation.js\"></script>\n" +
                "<script src=\"/js/app.js\"></script>\n" +
                "</body>\n" +
                "</html>";

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        try{
            writer.write( newdata );

        } catch (IOException e) {
            logger.warn(e);
        } finally {
            writer.close();
        }
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("text/html");
        s3.putObject(new PutObjectRequest("kittyprofessor",
                file.getName(), file).withMetadata(metadata));
        return Double.parseDouble(percentage.split("%")[0]);

    }
}
