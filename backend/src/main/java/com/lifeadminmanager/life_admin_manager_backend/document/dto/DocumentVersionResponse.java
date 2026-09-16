package com.lifeadminmanager.life_admin_manager_backend.document.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record DocumentVersionResponse(
        Long id,
        Integer versionNumber,
        String fileName,
        String mimeType,
        Long fileSizeBytes,
        LocalDate issueDate,
        LocalDate expiryDate,
        Boolean isCurrent,
        OffsetDateTime uploadedAt
) {
}
