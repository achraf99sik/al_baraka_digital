package com.al_baraka_digital.Baraka.service.impl;

import com.al_baraka_digital.Baraka.exception.BusinessValidationException;
import com.al_baraka_digital.Baraka.model.Document;
import com.al_baraka_digital.Baraka.model.Operation;
import com.al_baraka_digital.Baraka.repository.DocumentRepository;
import com.al_baraka_digital.Baraka.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Value("${storage.local.path:uploads}")
    private String storagePath;

    @Override
    public Document uploadDocument(Operation operation, MultipartFile file) {
        Path fileStorageLocation = Paths.get(storagePath).toAbsolutePath().normalize();
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
            // Internal server error better, but we don't have custom internal error exception, RuntimeException is fine or wrapping it
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }

        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileExtension = "";
        
        int i = originalFileName.lastIndexOf('.');
        if (i > 0) {
            fileExtension = originalFileName.substring(i);
        }
        
        if (!fileExtension.equalsIgnoreCase(".pdf") && 
            !fileExtension.equalsIgnoreCase(".jpg") && 
            !fileExtension.equalsIgnoreCase(".png") && 
            !fileExtension.equalsIgnoreCase(".jpeg")) {
             throw new BusinessValidationException("Invalid file type. Only PDF, JPG, PNG are allowed.");
        }

        String fileName = UUID.randomUUID().toString() + fileExtension;

        try {
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Document document = Document.builder()
                    .fileName(originalFileName)
                    .fileType(file.getContentType())
                    .storagePath(targetLocation.toString())
                    .operation(operation)
                    .build();

            return documentRepository.save(document);
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public List<Document> getDocumentsByOperation(Operation operation) {
        return documentRepository.findByOperation(operation);
    }
}
