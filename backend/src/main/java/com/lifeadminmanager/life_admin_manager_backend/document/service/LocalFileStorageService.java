package com.lifeadminmanager.life_admin_manager_backend.document.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class LocalFileStorageService implements FileStorageService{

    private final Path storageLocation;

    public LocalFileStorageService(
            @Value("${file.storage.location}") String storageLocation
    ){
        this.storageLocation= Paths.get(storageLocation)
                .toAbsolutePath()
                .normalize();
        try{
            Files.createDirectories(this.storageLocation);
        }catch (IOException exception){
            throw new RuntimeException("Could not initialize file storage location",exception);
        }
    }

    @Override
    public String store(MultipartFile file){
        String originalFileName=file.getOriginalFilename();
        if(originalFileName==null || originalFileName.isBlank()){
            throw new IllegalArgumentException("File name is required");
        }

        String storageKey= UUID.randomUUID()+"_"+originalFileName;
        Path targetLocation=storageLocation.resolve(storageKey);
        try{
            Files.copy(file.getInputStream(),targetLocation);
            return storageKey;
        }catch (IOException exception){
            throw new RuntimeException("Could not store file",exception);
        }
    }

    @Override
    public void delete(String storageKey){
        Path fileLocation=storageLocation
                .resolve(storageKey)
                .normalize();
        try{
            Files.deleteIfExists(fileLocation);
        }catch (IOException exception){
            throw new RuntimeException("Could not delete file",exception);
        }
    }
}
