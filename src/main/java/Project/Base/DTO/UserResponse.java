package Project.Base.DTO;

import Project.Base.Entity.Enums.Role;
import Project.Base.Entity.User;

import java.time.LocalDateTime;

public class UserResponse {

    private Long id;
    private String fullname;
    private String username;
    private String email;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    public UserResponse() {}

    //Static "factory method" which converts an Entity into this DTO
    public static UserResponse fromEntity(User user) {
        UserResponse dto = new UserResponse();
        dto.id = user.getId();
        dto.fullname = user.getFullname();
        dto.username = user.getUsername();
        dto.email = user.getEmail();
        dto.role = user.getRole();
        dto.createdAt = user.getCreatedAt();
        dto.lastLogin = user.getLastLogin();
        return dto;
    }

    public Long getId() { return id; }
    public String getFullname() { return fullname; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getLastLogin() { return lastLogin; }
}