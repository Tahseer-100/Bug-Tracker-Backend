package Project.Base.Controller;

import Project.Base.DTO.CommentRequest;
import Project.Base.DTO.CommentResponse;
import Project.Base.Entity.Comment;
import Project.Base.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms/{roomId}/issues/{issueId}/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //POST add the comments on the issues using issueId
    @PostMapping
    public ResponseEntity<CommentResponse> addComment(
            @PathVariable Long roomId,
            @PathVariable Long issueId,
            @RequestBody CommentRequest request) {

        Comment comment = commentService.addComment(
                issueId,
                request.getAuthorId(),
                request.getContent()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(CommentResponse.fromEntity(comment));
    }

    //GET: get the comments on the issue using issueId
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(
            @PathVariable Long roomId,
            @PathVariable Long issueId) {

        List<Comment> comments = commentService.getCommentsForIssue(issueId);
        List<CommentResponse> response = comments.stream()
                .map(CommentResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(response);
    }

    //PUT update the comments using commentId
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long roomId,
            @PathVariable Long issueId,
            @PathVariable Long commentId,
            @RequestBody CommentRequest request) {

        Comment comment = commentService.updateComment(commentId, request.getContent());
        return ResponseEntity.ok(CommentResponse.fromEntity(comment));
    }

    //DELETE comment using comment ID
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long roomId,
            @PathVariable Long issueId,
            @PathVariable Long commentId) {

        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}

