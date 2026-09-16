package com.lifeadminmanager.life_admin_manager_backend.document.repository;

import com.lifeadminmanager.life_admin_manager_backend.document.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document,Long> {
}
