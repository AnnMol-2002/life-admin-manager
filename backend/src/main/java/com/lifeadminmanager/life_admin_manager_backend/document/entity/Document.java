package com.lifeadminmanager.life_admin_manager_backend.document.entity;

import com.lifeadminmanager.life_admin_manager_backend.document.enums.DocumentType;
import com.lifeadminmanager.life_admin_manager_backend.user.entity.User;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id",nullable = false, foreignKey=@ForeignKey(name="fk_document_user"))
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type",nullable = false)
    private DocumentType documentType;

    @Column(name = "title",nullable = false,length = 255)
    private String title;

    @Column(name = "created_at",nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at",nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public String getTitle() {
        return title;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public OffsetDateTime getDeletedAt() {
        return deletedAt;
    }
}
