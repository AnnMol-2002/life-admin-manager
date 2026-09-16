package com.lifeadminmanager.life_admin_manager_backend.document.service;

import com.lifeadminmanager.life_admin_manager_backend.document.dto.CreateDocumentRequest;
import com.lifeadminmanager.life_admin_manager_backend.document.dto.DocumentResponse;
import com.lifeadminmanager.life_admin_manager_backend.document.repository.DocumentRepository;
import com.lifeadminmanager.life_admin_manager_backend.document.repository.DocumentVersionRepository;
import com.lifeadminmanager.life_admin_manager_backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentVersionRepository documentVersionRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final FileValidationService fileValidationService;

    public DocumentService(
            DocumentRepository documentRepository,
            DocumentVersionRepository documentVersionRepository,
            UserRepository userRepository,
            FileStorageService fileStorageService,
            FileValidationService fileValidationService
    ){
        this.documentRepository=documentRepository;
        this.documentVersionRepository=documentVersionRepository;
        this.userRepository=userRepository;
        this.fileStorageService=fileStorageService;
        this.fileValidationService=fileValidationService;
    }

//    public DocumentResponse createDocument(
//            Long userId,
//            CreateDocumentRequest request,
//            MultipartFile file
//    ){
//        if(!request.expiryDate().isAfter(LocalDate.now())){
//            throw new IllegalArgumentException("Expiry Date should be in future");
//        }
//
//        if(!request.issueDate().isAfter(request.expiryDate())){
//            throw new IllegalArgumentException("Issue date cannot be after Expiry date");
//        }
//    }
}
