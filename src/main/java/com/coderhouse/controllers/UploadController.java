package com.coderhouse.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.*;
import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/api")
public class UploadController {

    private static final Path UPLOAD_DIR = Paths.get("uploads");

    @PostMapping("/uploads")
public Map<String, String> upload(@RequestParam("file") MultipartFile file) throws Exception {
    if (!Files.exists(UPLOAD_DIR)) Files.createDirectories(UPLOAD_DIR);

    String ext = Optional.ofNullable(file.getOriginalFilename())
            .filter(n -> n.contains("."))
            .map(n -> n.substring(n.lastIndexOf(".")))
            .orElse(".png");

    String filename = Instant.now().toEpochMilli() + "_" + UUID.randomUUID() + ext;
    Path target = UPLOAD_DIR.resolve(filename);
    Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

    // <<--- URL ABSOLUTA
    String url = ServletUriComponentsBuilder
            .fromCurrentContextPath()       // http://localhost:8080
            .path("/uploads/")              // + /uploads/
            .path(filename)                 // + nombre
            .toUriString();

    // si quieres, también devuelve la ruta relativa:
    return Map.of("url", url, "path", "/uploads/" + filename);
}

    public static class Base64Body { public String dataUrl; }

    @PostMapping("/uploads/base64")
    public Map<String, String> uploadBase64(@RequestBody Base64Body body) throws Exception {
        if (!Files.exists(UPLOAD_DIR)) Files.createDirectories(UPLOAD_DIR);
        // data:image/png;base64,AAAA...
        String[] parts = body.dataUrl.split(",");
        String meta = parts[0];
        String base64 = parts[1];
        String ext = meta.contains("image/") ? "." + meta.substring(meta.indexOf("image/")+6, meta.indexOf(";")) : ".png";

        byte[] bytes = Base64.getDecoder().decode(base64);
        String filename = Instant.now().toEpochMilli() + "_" + UUID.randomUUID() + ext;
        Path target = UPLOAD_DIR.resolve(filename);
        Files.write(target, bytes);

        return Map.of("url", "/uploads/" + filename);
    }
}

