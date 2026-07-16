package Project.Base.DTO;

import Project.Base.Entity.Comment;
import java.time.LocalDateTime;
import java.util.List;

public class CommentResponse {

    private Long id;
    private Long issueId;
    private Long authorId;
    private String authorUsername;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Long> referencedIssueIds;

    public CommentResponse() {}

    public static CommentResponse fromEntity(Comment comment) {
        CommentResponse dto = new CommentResponse();
        dto.id = comment.getId();
        dto.issueId = comment.getIssue().getId();
        dto.authorId = comment.getAuthor().getId();
        dto.authorUsername = comment.getAuthor().getUsername();
        dto.content = comment.getContent();
        dto.createdAt = comment.getCreatedAt();
        dto.updatedAt = comment.getUpdatedAt();

        //Extract only IDs
        dto.referencedIssueIds = comment.getReferencedIssues()
                .stream()
                .map(issue -> issue.getId())
                .toList();

        return dto;
    }

    public Long getId() { return id; }
    public Long getIssueId() { return issueId; }
    public Long getAuthorId() { return authorId; }
    public String getAuthorUsername() { return authorUsername; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<Long> getReferencedIssueIds() { return referencedIssueIds; }
}