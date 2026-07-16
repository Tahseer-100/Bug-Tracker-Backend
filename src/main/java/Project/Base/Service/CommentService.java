package Project.Base.Service;

import Project.Base.Exception.ResourceNotFoundException;
import Project.Base.Entity.Comment;
import Project.Base.Entity.Issue;
import Project.Base.Entity.User;
import Project.Base.Repository.CommentRepo;
import Project.Base.Repository.IssueRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CommentService {

    private static final Pattern ISSUE_REFERENCE_PATTERN = Pattern.compile("#(\\d+)");

    private final CommentRepo commentRepo;
    private final IssueRepo issueRepo;
    private final IssueService issueService;
    private final UserService userService;

    @Autowired
    public CommentService(CommentRepo commentRepo, IssueRepo issueRepo,
                          IssueService issueService, UserService userService) {
        this.commentRepo = commentRepo;
        this.issueRepo = issueRepo;
        this.issueService = issueService;
        this.userService = userService;
    }

    @Transactional
    public Comment addComment(Long issueId, Long authorId, String content) {

        Issue issue = issueService.getById(issueId);
        User author = userService.getById(authorId);

        Comment comment = new Comment(issue, author, content);

        List<Issue> referenced = extractReferencedIssues(content, issue.getRoom().getId());
        comment.setReferencedIssues(referenced);

        return commentRepo.save(comment);
    }

    private List<Issue> extractReferencedIssues(String content, Long roomId) {

        List<Issue> referencedIssues = new ArrayList<>();
        Matcher matcher = ISSUE_REFERENCE_PATTERN.matcher(content);

        while (matcher.find()) {
            Long referencedId = Long.parseLong(matcher.group(1));


            issueRepo.findById(referencedId).ifPresent(referencedIssue -> {
                if (referencedIssue.getRoom().getId().equals(roomId)) {
                    referencedIssues.add(referencedIssue);
                }
            });
        }

        return referencedIssues;
    }

    public Comment updateComment(Long commentId, String newContent) {
        Comment comment = getById(commentId);
        comment.setContent(newContent);
        comment.setUpdatedAt(LocalDateTime.now());


        List<Issue> referenced = extractReferencedIssues(newContent, comment.getIssue().getRoom().getId());
        comment.setReferencedIssues(referenced);

        return commentRepo.save(comment);
    }

    public void deleteComment(Long commentId) {
        Comment comment = getById(commentId);
        commentRepo.delete(comment);
    }

    public Comment getById(Long commentId) {
        return commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found: " + commentId));
    }

    public List<Comment> getCommentsForIssue(Long issueId) {
        return commentRepo.findByIssueIdOrderByCreatedAtAsc(issueId);
    }
}