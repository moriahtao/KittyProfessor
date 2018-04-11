package com.cs5500.team209;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	/**
	 * load the security configs of KittyProfessor
	 */
	@Test
	public void contextLoads() {
	}

	private static boolean isRedirected( Map<String, List<String>> header ) {
		for( String hv : header.get( null )) {
			if(   hv.contains( " 301 " )
					|| hv.contains( " 302 " )) return true;
		}
		return false;
	}

	@Test
	public void run() throws IOException, GitAPIException {
		String link =
				"https://github.com/balarajv/CodeMirror";
		Git.cloneRepository()
				.setURI(link)
				.setDirectory(Paths.get("data1/").toFile())
				.call();

		String            fileName = "aws";
		/*URL url  = new URL( link );
		HttpURLConnection http = (HttpURLConnection)url.openConnection();
		Map< String, List< String >> header = http.getHeaderFields();
		while( isRedirected( header )) {
			link = header.get( "Location" ).get( 0 );
			url    = new URL( link );
			http   = (HttpURLConnection)url.openConnection();
			header = http.getHeaderFields();
		}
		InputStream input  = http.getInputStream();
		byte[]       buffer = new byte[4096];
		int          n      = -1;
		OutputStream output = new FileOutputStream( new File( fileName ));
		while ((n = input.read(buffer)) != -1) {
			output.write( buffer, 0, n );
		}
		output.close();*/
	}

}

