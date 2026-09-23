package com.lifeadminmanager.life_admin_manager_backend.document.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String store(MultipartFile file);
    void delete(String storageKey);
}
