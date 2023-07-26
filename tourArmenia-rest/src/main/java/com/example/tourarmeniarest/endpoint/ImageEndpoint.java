package com.example.tourarmeniarest.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
@RestController
@Slf4j
public class ImageEndpoint {

    @Value("${upload.image.path}")
    private String uploadPath;

   // Endpoint for retrieving an image by its picName from the system.
    @GetMapping(value = "/getImage",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("picName") String picName) throws IOException {
        File file = new File(uploadPath + picName);
        if (file.exists()) {
            log.info("Retrieving image: {}", picName);
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                return IOUtils.toByteArray(fileInputStream);
            } catch (IOException e) {
                log.error("Error while reading the image file: {}", picName, e);
                throw e;
            }
        } else {
            log.warn("Image not found: {}", picName);
            return null;
        }
    }
}
