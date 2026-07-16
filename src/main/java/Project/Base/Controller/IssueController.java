package Project.Base.Controller;

import Project.Base.DTO.*;
import Project.Base.Entity.Issue;
import Project.Base.Entity.Enums.IssueStatus;
import Project.Base.Service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms/{roomId}/issues")
public class IssueController {

    private final IssueService issueService;

    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    //POST create the issues
    @PostMapping
    public ResponseEntity<IssueResponse> createIssue(@PathVariable Long roomId,
                                                     @RequestBody IssueRequest request) {
        Issue issue = issueService.createIssue(
                roomId,
                request.getCreatorId(),
                request.getTitle(),
                request.getDescription(),
                request.getStepsToReproduce(),
                request.getExpectedBehavior(),
                request.getActualBehavior(),
                request.getEnvironment(),
                request.getPriority()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(IssueResponse.fromEntity(issue));
    }

    //GET: get the issues
    @GetMapping
    public ResponseEntity<List<IssueResponse>> getIssues(
            @PathVariable Long roomId,
            @RequestParam(required = false) IssueStatus status) {

        List<Issue> issues = (status != null)
                ? issueService.getIssuesForRoomByStatus(roomId, status)
                : issueService.getIssuesForRoom(roomId);

        List<IssueResponse> response = issues.stream()
                .map(IssueResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(response);
    }

    //GET shows the issues aloted to the user
    @GetMapping("/my")
    public ResponseEntity<List<IssueResponse>> getMyIssues(
            @PathVariable Long roomId,
            @RequestParam Long userId) {

        List<Issue> issues = issueService.getMyIssues(roomId, userId);
        List<IssueResponse> response = issues.stream()
                .map(IssueResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(response);
    }

    //GET: get the issue using issueId
    @GetMapping("/{issueId}")
    public ResponseEntity<IssueResponse> getIssueById(@PathVariable Long roomId,
                                                      @PathVariable Long issueId) {
        Issue issue = issueService.getById(issueId);
        return ResponseEntity.ok(IssueResponse.fromEntity(issue));
    }

    //PATCH use to change status
    @PatchMapping("/{issueId}/status")
    public ResponseEntity<IssueResponse> changeStatus(@PathVariable Long roomId,
                                                      @PathVariable Long issueId,
                                                      @RequestBody StatusChangeRequest request) {
        Issue issue = issueService.changeStatus(
                issueId,
                request.getActingUserId(),
                request.getNewStatus()
        );
        return ResponseEntity.ok(IssueResponse.fromEntity(issue));
    }

    //PATCH use to assign issues to the developers
    @PatchMapping("/{issueId}/assign")
    public ResponseEntity<IssueResponse> assignIssue(@PathVariable Long roomId,
                                                     @PathVariable Long issueId,
                                                     @RequestBody AssignRequest request) {
        Issue issue = issueService.assignIssue(
                issueId,
                request.getActingUserId(),
                request.getAssigneeId()
        );
        return ResponseEntity.ok(IssueResponse.fromEntity(issue));
    }

    //DELETE: delete the issue using issueId
    @DeleteMapping("/{issueId}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long roomId,
                                            @PathVariable Long issueId) {
        issueService.getById(issueId);       // verify it exists first
        return ResponseEntity.noContent().build();
    }
}