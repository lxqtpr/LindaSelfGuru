package dev.lxqtpr.lindaSelfGuru.Core.Services;

import dev.lxqtpr.lindaSelfGuru.Core.Excreptions.ImageUploadException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    private final Path root = Paths.get("src\\main\\resources\\static");

    public String upload(MultipartFile file) {
        try{
            var fileName = UUID.randomUUID()+"."+file.getOriginalFilename();
            Files.copy(file.getInputStream(), root.resolve(fileName).toAbsolutePath());
            return fileName;
        }
        catch (Exception e) {
            throw new ImageUploadException("Image upload failed:");
        }
    }
    public void deleteFile(String fileName){
        try {
            Files.delete(root.resolve(fileName));
        }
        catch (Exception e) {
            throw new ImageUploadException("Image upload failed: "
                    + e.getMessage());
        }
    }
}
