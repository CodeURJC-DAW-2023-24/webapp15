package es.codeurjc.webapp15.service;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.webapp15.model.User;
import es.codeurjc.webapp15.repository.UserRepository;

import java.sql.Blob;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

@Service
public class UserService {

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
}
