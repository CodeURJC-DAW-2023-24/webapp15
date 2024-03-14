package es.codeurjc.webapp15.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ImageController {

    private static final Path IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "/src/main/resources/images/");
    
    @GetMapping("/image/{imageName}")
    public ResponseEntity<Object> getMethodName(Model model, @PathVariable String imageName)
        throws MalformedURLException {
        
            Path imagePath = IMAGES_FOLDER.resolve(imageName);
            Resource image = new UrlResource(imagePath.toUri());

            return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
            .body(image);
    }
    
}
