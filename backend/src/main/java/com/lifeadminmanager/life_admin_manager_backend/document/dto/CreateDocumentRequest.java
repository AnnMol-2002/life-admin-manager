package com.lifeadminmanager.life_admin_manager_backend.document.dto;

import com.lifeadminmanager.life_admin_manager_backend.document.enums.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateDocumentRequest(
        @NotNull(message = "Document Type is required")
        DocumentType documentType,

        @NotBlank(message = "Title is required")
        String title,

        @NotNull(message = "Issue Date is required")
        LocalDate issueDate,

        @NotNull(message = "Expiry Date is required")
        LocalDate expiryDate
) {
}
