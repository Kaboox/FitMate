package kabox.fitmate.service;


import kabox.fitmate.Model.User;
import kabox.fitmate.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class AvatarService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final UserRepository userRepository;

    @Autowired
    public AvatarService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String storeAvatar(MultipartFile file, User user) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Missing file");
        }

        String contentType = file.getContentType();

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("File is not an image");
        }

        long max = 5L * 1024 * 1024; // 5 MB

        if (file.getSize() > max) {
            throw new IllegalArgumentException("File is too big (max 5 MB)");
        }

        String original = file.getOriginalFilename();
        String ext = ".jpg";
        if (original != null && original.contains(".")) {
            ext = original.substring(original.lastIndexOf('.'));
        }
        String filename = UUID.randomUUID().toString() + ext;

        Path uploadPath = Paths.get(uploadDir);
        Files.createDirectories(uploadPath);
        Path target = uploadPath.resolve(filename);
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }

        // Relative path by ResourceHandler (ex. /uploads/uuid.jpg)
        String avatarUrl = "/uploads/" + filename;

        user.setAvatarUrl(avatarUrl);
        userRepository.save(user);

        return avatarUrl;
    }

}
