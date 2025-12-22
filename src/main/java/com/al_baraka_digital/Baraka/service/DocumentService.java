package com.al_baraka_digital.Baraka.service;

import com.al_baraka_digital.Baraka.model.Document;
import com.al_baraka_digital.Baraka.model.Operation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {
    Document uploadDocument(Operation operation, MultipartFile file);
    List<Document> getDocumentsByOperation(Operation operation);
}
