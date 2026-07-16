package Project.Base.Controller;

import Project.Base.DTO.LoginRequest;
import Project.Base.DTO.RegisterRequest;
import Project.Base.DTO.UserResponse;
import Project.Base.Entity.User;
import Project.Base.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    //POST use to register the user
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        User user = userService.register(
                request.getFullname(),
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getRole()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.fromEntity(user));
    }

    //POST login to enter the page
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request) {
        User user = userService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(UserResponse.fromEntity(user));
    }

    @GetMapping("/me/{userId}")
    public ResponseEntity<UserResponse> getCurrentUser(@PathVariable Long userId) {
        User user = userService.getById(userId);
        return ResponseEntity.ok(UserResponse.fromEntity(user));
    }
}