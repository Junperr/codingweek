package com.example.codingweek.data;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class ImageFile extends File {

    public UUID imageId;

    public String directory;

    public ImageFile(String pathname, String directory) {
        super(pathname);
        imageId = UUID.randomUUID();
        this.directory = directory;
    }

    public String getFileExtension() {
        String fileName = getName();
        int lastDotIndex = fileName.lastIndexOf('.');

        // Check if the file has an extension
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        } else {
            return null; // No extension or the file is a directory
        }
    }

    public static String getImageName(ImageFile image){
        if (image == null) return "default.png";
        else return image.directory + image.imageId.toString() + "." + image.getFileExtension();
    }

    public static void saveImage(ImageFile image) throws Exception {
        if (image != null) {
            // Full path for copying
            String imageName = ImageFile.getImageName(image);
            Path sourcePath = image.toPath();
            Path targetPath = Paths.get("src/main/resources/static/images", imageName);
            try {
                // Copy the file using Files.copy
                //@todo make target path dynamic
                File newImage =  new File(targetPath.toString());
                System.out.println("yo");
                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("yoyo");
                waitForFileCompletion(newImage,2000);
            } catch (Exception e) {
                // Handle the exception
                throw new Exception("Error while saving the image", e);
            }
        }
    }

    private static void waitForFileCompletion(File file, long timeout) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        long lastModified = file.lastModified();

        while (System.currentTimeMillis() - startTime < timeout) {
            Thread.sleep(200); // Sleep for 1 second

            long currentModified = file.lastModified();
            if (currentModified == lastModified) {
                // File hasn't been modified, assume it's completed
                return;
            }

            lastModified = currentModified;
        }

        throw new RuntimeException("Timeout waiting for file completion");
    }


}
