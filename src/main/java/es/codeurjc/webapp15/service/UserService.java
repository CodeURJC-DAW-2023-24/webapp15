package es.codeurjc.webapp15.service;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import es.codeurjc.webapp15.model.User;
import es.codeurjc.webapp15.repository.UserRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User updateUserName(Long userId, String newName) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(newName);
            return userRepository.save(user);
        }
        return null; // Or throw an exception
    }

    public User updateUserEmail(Long userId, String newEmail) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEmail(newEmail);
            return userRepository.save(user);
        }
        return null; // Or throw an exception
    }

    public void updateUserImage(Long userId, MultipartFile file) throws Exception {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setImg_user(BlobProxy.generateProxy(file.getInputStream(), file.getSize()));
            user.setImage(true);
            
            userRepository.save(user);
        } else {
            throw new Exception("User not found");
        }
    }

    public User createUser(String name, String email, String pass, MultipartFile image, String... roles) throws IOException {
        User user = new User();

        user.setName(name);
        user.setEmail(email);
        user.setEncodedPassword(passwordEncoder.encode(pass));
        user.setRoles(List.of(roles));

        if (!image.isEmpty()) {
            user.setImg_user(BlobProxy.generateProxy(image.getInputStream(), image.getSize()));
            user.setImage(true);
        }

        userRepository.save(user);

        return user;
    }
}
