package org.example.util;

import org.example.Main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExtractResource {
    public static void extractResource(String resourcePath) throws IOException {
        InputStream resourceStream = Main.class.getClassLoader().getResourceAsStream(resourcePath);
        if (resourceStream == null) {
            throw new FileNotFoundException("Resource not found: " + resourcePath);
        }

        Path currentWorkingDirectory = Paths.get("").toAbsolutePath();
        Path targetPath = currentWorkingDirectory.resolve(resourcePath);

        if (!Files.exists(targetPath)) {
            Files.createDirectories(targetPath.getParent());

            try (OutputStream outStream = new FileOutputStream(targetPath.toFile())) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = resourceStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
            }
        }
    }
}
