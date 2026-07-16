package Project.Base.Service;

import Project.Base.Exception.DuplicateResourceException;
import Project.Base.Exception.InvalidCredentialsException;
import Project.Base.Exception.ResourceNotFoundException;
import Project.Base.Entity.Enums.Role;
import Project.Base.Entity.User;
import Project.Base.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String fullname, String username, String email,
                         String rawPassword, Role role) {


        if (userRepo.existsByUsername(username)) {
            throw new DuplicateResourceException("Username already taken: " + username);
        }
        if (userRepo.existsByEmail(email)) {
            throw new DuplicateResourceException("Email already registered: " + email);
        }

        User user = new User();
        user.setFullname(fullname);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));  // never store raw password
        user.setRole(role);
        user.setCreatedAt(LocalDateTime.now());

        return userRepo.save(user);
    }

    public User login(String username, String rawPassword) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));


        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        user.setLastLogin(LocalDateTime.now());
        return userRepo.save(user);
    }

    public User getById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public User getByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }
}