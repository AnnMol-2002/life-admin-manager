package com.lifeadminmanager.life_admin_manager_backend.document.dto;

import com.lifeadminmanager.life_admin_manager_backend.document.enums.DocumentType;

import java.time.OffsetDateTime;

public record DocumentResponse(
        Long id,
        DocumentType documentType,
        String title,
        DocumentVersionResponse currentVersion,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
