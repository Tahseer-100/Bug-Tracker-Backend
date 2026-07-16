package Project.Base.Controller;

import Project.Base.DTO.*;
import Project.Base.Entity.Room;
import Project.Base.Entity.RoomMembership;
import Project.Base.Service.RoomMembershipService;
import Project.Base.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    private final RoomMembershipService roomMembershipService;

    @Autowired
    public RoomController(RoomService roomService, RoomMembershipService roomMembershipService) {
        this.roomService = roomService;
        this.roomMembershipService = roomMembershipService;
    }

    //GET show the rooms for user(only in which user is added)
    @GetMapping
    public ResponseEntity<List<RoomResponse>> getRoomsForUser(@RequestParam Long userId) {
        List<Room> rooms = roomService.getRoomsForUser(userId);
        List<RoomResponse> response = rooms.stream()
                .map(RoomResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(response);
    }

    //GET: get the room using roomId
    @GetMapping("/{roomId}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable Long roomId) {
        Room room = roomService.getById(roomId);
        return ResponseEntity.ok(RoomResponse.fromEntity(room));
    }

    //POST create a room
    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(@RequestBody RoomRequest request) {
        Room room = roomService.createRoom(
                request.getName(),
                request.getDescription(),
                request.getCreatorId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(RoomResponse.fromEntity(room));
    }

    //PUT update room details
    @PutMapping("/{roomId}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long roomId,
                                                   @RequestBody RoomRequest request) {
        Room room = roomService.updateRoom(roomId, request.getName(), request.getDescription());
        return ResponseEntity.ok(RoomResponse.fromEntity(room));
    }

    //DELETE deactivate/delete room
    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deactivateRoom(@PathVariable Long roomId) {
        roomService.deactivateRoom(roomId);
        return ResponseEntity.noContent().build();
    }

    //Member endpoints
    //GET: get members using roomId
    @GetMapping("/{roomId}/members")
    public ResponseEntity<List<RoomMemberResponse>> getMembers(@PathVariable Long roomId) {
        List<RoomMembership> members = roomMembershipService.getMembers(roomId);
        List<RoomMemberResponse> response = members.stream()
                .map(RoomMemberResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(response);
    }

    //POST add a member
    @PostMapping("/{roomId}/members")
    public ResponseEntity<RoomMemberResponse> addMember(@PathVariable Long roomId,
                                                        @RequestBody AddMemberRequest request) {
        RoomMembership membership = roomMembershipService.addMember(
                roomId,
                request.getActingUserId(),
                request.getNewUserId(),
                request.getRoleInRoom()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(RoomMemberResponse.fromEntity(membership));
    }

    //DELETE remove the members using userId
    @DeleteMapping("/{roomId}/members/{userId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long roomId,
                                             @PathVariable Long userId,
                                             @RequestParam Long actingUserId) {
        roomMembershipService.removeMember(roomId, actingUserId, userId);
        return ResponseEntity.noContent().build();
    }

    //PUT change role using userId
    @PutMapping("/{roomId}/members/{userId}/role")
    public ResponseEntity<RoomMemberResponse> changeRole(@PathVariable Long roomId,
                                                         @PathVariable Long userId,
                                                         @RequestBody ChangeRoleRequest request) {
        RoomMembership membership = roomMembershipService.changeRole(
                roomId,
                request.getActingUserId(),
                userId,
                request.getNewRole()
        );
        return ResponseEntity.ok(RoomMemberResponse.fromEntity(membership));
    }
}