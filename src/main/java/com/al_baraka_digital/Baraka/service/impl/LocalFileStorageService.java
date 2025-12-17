package com.al_baraka_digital.Baraka.service.impl;

import com.al_baraka_digital.Baraka.service.FileStorageService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@ConfigurationProperties(prefix="storage")
public class LocalFileStorageService implements FileStorageService {
    private String localPath;

    @Override
    public String store(MultipartFile file) {

        try {
            Files.createDirectories(Paths.get(localPath));

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path destination = Paths.get(localPath).resolve(filename);

            Files.copy(file.getInputStream(), destination);

            return destination.toString();

        } catch (IOException e) {
            throw new RuntimeException("File storage failed", e);
        }
    }
}
