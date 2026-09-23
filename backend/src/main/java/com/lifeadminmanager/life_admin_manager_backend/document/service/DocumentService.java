package com.lifeadminmanager.life_admin_manager_backend.document.service;

import com.lifeadminmanager.life_admin_manager_backend.document.dto.CreateDocumentRequest;
import com.lifeadminmanager.life_admin_manager_backend.document.dto.DocumentResponse;
import com.lifeadminmanager.life_admin_manager_backend.document.dto.DocumentVersionResponse;
import com.lifeadminmanager.life_admin_manager_backend.document.entity.Document;
import com.lifeadminmanager.life_admin_manager_backend.document.entity.DocumentVersion;
import com.lifeadminmanager.life_admin_manager_backend.document.repository.DocumentRepository;
import com.lifeadminmanager.life_admin_manager_backend.document.repository.DocumentVersionRepository;
import com.lifeadminmanager.life_admin_manager_backend.user.entity.User;
import com.lifeadminmanager.life_admin_manager_backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.OffsetDateTime;

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
                        FileValidationService fileValidationService) {
                this.documentRepository = documentRepository;
                this.documentVersionRepository = documentVersionRepository;
                this.userRepository = userRepository;
                this.fileStorageService = fileStorageService;
                this.fileValidationService = fileValidationService;
        }

        @Transactional
        public DocumentResponse createDocument(
                        Long userId,
                        CreateDocumentRequest request,
                        MultipartFile file) {
                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new IllegalArgumentException("User not found"));
                if (!request.expiryDate().isAfter(LocalDate.now())) {
                        throw new IllegalArgumentException("Expiry Date should be in future");
                }

                if (request.issueDate().isAfter(request.expiryDate())) {
                        throw new IllegalArgumentException("Issue date cannot be after Expiry date");
                }

                fileValidationService.validate(file);
                String storageKey = fileStorageService.store(file);

                Document document = new Document();
                document.setUser(user);
                document.setDocumentType(request.documentType());
                document.setTitle(request.title());

                OffsetDateTime now = OffsetDateTime.now();

                document.setCreatedAt(now);
                document.setUpdatedAt(now);

                Document savedDocument = documentRepository.save(document);

                DocumentVersion documentVersion = new DocumentVersion();

                documentVersion.setDocument(savedDocument);
                documentVersion.setVersionNumber(1);
                documentVersion.setFileName(file.getOriginalFilename());
                documentVersion.setStorageKey(storageKey);
                documentVersion.setMimeType(file.getContentType());
                documentVersion.setFileSizeBytes(file.getSize());
                documentVersion.setIssueDate(request.issueDate());
                documentVersion.setExpiryDate(request.expiryDate());
                documentVersion.setIsCurrent(true);
                documentVersion.setUploadedAt(now);

                DocumentVersion savedVersion = documentVersionRepository.save(documentVersion);

                DocumentVersionResponse versionResponse = new DocumentVersionResponse(
                                savedVersion.getId(),
                                savedVersion.getVersionNumber(),
                                savedVersion.getFileName(),
                                savedVersion.getMimeType(),
                                savedVersion.getFileSizeBytes(),
                                savedVersion.getIssueDate(),
                                savedVersion.getExpiryDate(),
                                savedVersion.getIsCurrent(),
                                savedVersion.getUploadedAt());
                return new DocumentResponse(
                                savedDocument.getId(),
                                savedDocument.getDocumentType(),
                                savedDocument.getTitle(),
                                versionResponse,
                                savedDocument.getCreatedAt(),
                                savedDocument.getUpdatedAt());
        }

        public DocumentResponse getDocument(Long userId, Long documentId){
                Document document=documentRepository.findById(documentId)
                        .orElseThrow(()->
                                new IllegalArgumentException("Document not found"));

                if(!document.getUser().getId().equals(userId)){
                        throw new IllegalArgumentException("Document does not belongs to the user");
                }

                if(document.getDeletedAt()!=null){
                        throw new IllegalArgumentException("Document not found");
                }

                DocumentVersion currentVersion =
                        documentVersionRepository
                                .findByDocumentIdAndIsCurrentTrue(documentId)
                                .orElseThrow(() ->
                                        new IllegalArgumentException(
                                                "Current document version not found"
                                        )
                                );

                DocumentVersionResponse versionResponse =
                        new DocumentVersionResponse(
                                currentVersion.getId(),
                                currentVersion.getVersionNumber(),
                                currentVersion.getFileName(),
                                currentVersion.getMimeType(),
                                currentVersion.getFileSizeBytes(),
                                currentVersion.getIssueDate(),
                                currentVersion.getExpiryDate(),
                                currentVersion.getIsCurrent(),
                                currentVersion.getUploadedAt()
                        );

                return new DocumentResponse(
                        document.getId(),
                        document.getDocumentType(),
                        document.getTitle(),
                        versionResponse,
                        document.getCreatedAt(),
                        document.getUpdatedAt()
                );
        }
}
