package org.mallik.examples.weaviate_demo.controller;

import io.weaviate.client.v1.data.model.WeaviateObject;
import org.mallik.examples.weaviate_demo.service.WeaviateService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final WeaviateService weaviateService;

    public ProductController(WeaviateService weaviateService) {
        this.weaviateService = weaviateService;
    }

    @PostMapping(value = "/products", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> createProduct(
            @RequestParam("name") String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "price", required = false) String priceStr,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "video", required = false) MultipartFile video
    ) throws IOException {
        Map<String, Object> resp = new HashMap<>();

        if (name == null || name.trim().isEmpty()) {
            resp.put("error", "name is required");
            return ResponseEntity.badRequest().body(resp);
        }


        Map<String, Object> props = new HashMap<>();
        props.put("name", name);
        props.put("description", description);
        props.put("price", priceStr);

        if (image != null && !image.isEmpty()) {
            // convert image to Base64 string and include in properties
            byte[] imgBytes = image.getBytes();
            String imgB64 = Base64.getEncoder().encodeToString(imgBytes);
            props.put("image", imgB64);
        }

        if (video != null && !video.isEmpty()) {
            byte[] vidoeBytes = video.getBytes();
            String imgB64 = Base64.getEncoder().encodeToString(vidoeBytes);
            props.put("video", vidoeBytes);
        }

        // Insert into Weaviate (demo). Ensure a "Product" class/schema exists in your Weaviate instance.
        WeaviateObject inserted = weaviateService.insertData("Product", props);
        resp.put("inserted", inserted);

        return ResponseEntity.ok(resp);
    }
}
