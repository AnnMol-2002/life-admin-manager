package com.lifeadminmanager.life_admin_manager_backend.document.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
public class FileValidationServiceImpl implements FileValidationService{
    private static final long MAX_FILE_SIZE=10*1024*1024;

    private static final Set<String> ALLOWED_CONTENT_TYPES=Set.of(
            "application/pdf",
            "image/jpeg",
            "image/png"
    );

    @Override
    public void validate(MultipartFile file){
        if(file==null|| file.isEmpty()){
            throw new IllegalArgumentException("File id required");
        }

        if(file.getSize()>MAX_FILE_SIZE){
            throw new IllegalArgumentException("File size must not exceed 10MB");
        }

        String contentType= file.getContentType();;
        if(contentType==null||!ALLOWED_CONTENT_TYPES.contains(contentType)){
            throw new IllegalArgumentException("Unsupported file type. Only PDF, JPG, JPEG and PNG are allowed");
        }
    }
}
