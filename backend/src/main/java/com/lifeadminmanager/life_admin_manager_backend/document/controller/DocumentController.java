package com.lifeadminmanager.life_admin_manager_backend.document.controller;

import com.lifeadminmanager.life_admin_manager_backend.document.dto.CreateDocumentRequest;
import com.lifeadminmanager.life_admin_manager_backend.document.dto.DocumentResponse;
import com.lifeadminmanager.life_admin_manager_backend.document.enums.DocumentType;
import com.lifeadminmanager.life_admin_manager_backend.document.service.DocumentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentResponse createDocument(
            @RequestParam Long userId,

            @RequestParam DocumentType documentType,

            @RequestParam String title,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate issueDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate expiryDate,

            @RequestPart("file") MultipartFile file
    ) {

        CreateDocumentRequest request =
                new CreateDocumentRequest(
                        documentType,
                        title,
                        issueDate,
                        expiryDate
                );

        return documentService.createDocument(
                userId,
                request,
                file
        );
    }
}