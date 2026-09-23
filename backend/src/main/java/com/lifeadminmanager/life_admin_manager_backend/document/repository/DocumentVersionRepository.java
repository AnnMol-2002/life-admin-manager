package com.lifeadminmanager.life_admin_manager_backend.document.repository;

import com.lifeadminmanager.life_admin_manager_backend.document.entity.DocumentVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentVersionRepository extends JpaRepository<DocumentVersion,Long> {
    Optional<DocumentVersion> findByDocumentIdAndIsCurrentTrue(Long documentId);
}
