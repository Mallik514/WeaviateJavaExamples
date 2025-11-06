package org.mallik.examples.weaviate_demo.controller;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SearchController {

    @SuppressWarnings("unused")
    private final WeaviateService weaviateService;

    public SearchController(WeaviateService weaviateService) {
        this.weaviateService = weaviateService;
    }

    @PostMapping(value = "/search", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> search(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {
        Map<String, Object> resp = new HashMap<>();
        resp.put("query", query);

        if (image != null && !image.isEmpty()) {
            resp.put("imageName", image.getOriginalFilename());
            resp.put("imageSize", image.getSize());
            // save to temp file (demo only)
            Path tmp = Files.createTempFile("upload-", "-" + (image.getOriginalFilename() == null ? "file" : image.getOriginalFilename()));
            Files.copy(image.getInputStream(), tmp, StandardCopyOption.REPLACE_EXISTING);
            resp.put("savedTo", tmp.toString());
        } else {
            resp.put("image", null);
        }

        // Placeholder: you can call weaviateService here to execute a real search
        resp.put("results", Collections.emptyList());

        return ResponseEntity.ok(resp);
    }
}
