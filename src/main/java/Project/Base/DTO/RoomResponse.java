package Project.Base.DTO;

import Project.Base.Entity.Room;
import java.time.LocalDateTime;

public class RoomResponse {

    private Long id;
    private String name;
    private String description;
    private Long createdById;
    private String createdByUsername;
    private LocalDateTime createdAt;
    private boolean active;

    public RoomResponse() {}

    public static RoomResponse fromEntity(Room room) {
        RoomResponse dto = new RoomResponse();
        dto.id = room.getId();
        dto.name = room.getName();
        dto.description = room.getDescription();
        dto.createdById = room.getCreatedBy().getId();
        dto.createdByUsername = room.getCreatedBy().getUsername();
        dto.createdAt = room.getCreatedAt();
        dto.active = room.isActive();
        return dto;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Long getCreatedById() { return createdById; }
    public String getCreatedByUsername() { return createdByUsername; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isActive() { return active; }
}