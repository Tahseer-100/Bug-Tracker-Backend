package Project.Base.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime updatedAt;

    // Many-to-Many => this comment can reference many issues and one issue can be referenced by many comments
    @ManyToMany
    @JoinTable(
            name = "comment_issue_references",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "referenced_issue_id")
    )
    private List<Issue> referencedIssues = new ArrayList<>();

    public Comment() {}

    public Comment(Issue issue, User author, String content) {
        this.issue = issue;
        this.author = author;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Issue getIssue() { return issue; }
    public void setIssue(Issue issue) { this.issue = issue; }

    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<Issue> getReferencedIssues() { return referencedIssues; }
    public void setReferencedIssues(List<Issue> referencedIssues) {
        this.referencedIssues = referencedIssues;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", issueId=" + issue.getId() +
                ", authorId=" + author.getId() +
                ", createdAt=" + createdAt +
                '}';
    }
}