package com.lifeadminmanager.life_admin_manager_backend.document.repository;

import com.lifeadminmanager.life_admin_manager_backend.document.entity.DocumentVersion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentVersionRepository extends JpaRepository<DocumentVersion,Long> {
}
