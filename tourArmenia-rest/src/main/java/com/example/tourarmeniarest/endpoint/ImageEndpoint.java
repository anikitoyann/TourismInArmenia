package com.example.tourarmeniarest.endpoint;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
@Controller
public class ImageEndpoint {

    @Value("${upload.image.path}")
    private String uploadPath;
    @Value("${site.url}")
    private String siteUrl;
    @GetMapping(value = "/getImage",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("picName") String picName) throws IOException {
        File file = new File(uploadPath + picName);
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file);
            return IOUtils.toByteArray(fileInputStream);
        }
        return null;
    }
}
