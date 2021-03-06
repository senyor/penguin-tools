/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.penguinsoftware.tools.filedownloader;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileDownloader {

    public static void main(String[] args) throws IOException, InterruptedException {

        if (args.length < 2) {
            System.out.println("Please provide file name template (e.g. c:/Temp/mypicture%d.jpg) and at least one url to download (e.g. http://files.com/image123.jpg)");
            return;
        }

        List<String> argsList = Arrays.asList(args);

        String fileNameTemplate = argsList.get(0);
        HttpClient client = HttpClient.newBuilder().build();
        List<String> urlList = argsList.subList(1, argsList.size());

        for (int i = 0; i < urlList.size(); i++) {

            String imgUrl = urlList.get(i);
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET().uri(URI.create(imgUrl))
                    .build();

            File tempFile = new File(String.format(fileNameTemplate, i + 1));

            URI fileUri = tempFile.toURI();
            client.send(httpRequest, HttpResponse.BodyHandlers.ofFile(Paths.get(fileUri)));

            System.out.println(fileUri);
        }
    }
}
