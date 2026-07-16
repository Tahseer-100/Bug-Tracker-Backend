package Project.Base.Service;

import Project.Base.Exception.ResourceNotFoundException;
import Project.Base.Entity.Attachment;
import Project.Base.Entity.Comment;
import Project.Base.Entity.Issue;
import Project.Base.Entity.User;
import Project.Base.Repository.AttachmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttachmentService {

    private final AttachmentRepo attachmentRepo;
    private final IssueService issueService;
    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public AttachmentService(AttachmentRepo attachmentRepo, IssueService issueService,
                             CommentService commentService, UserService userService) {
        this.attachmentRepo = attachmentRepo;
        this.issueService = issueService;
        this.commentService = commentService;
        this.userService = userService;
    }

    public Attachment attachToIssue(Long issueId, Long uploaderId, String fileName, String filePath) {
        Issue issue = issueService.getById(issueId);
        User uploader = userService.getById(uploaderId);

        Attachment attachment = new Attachment(issue, fileName, filePath, uploader);
        return attachmentRepo.save(attachment);
    }

    public Attachment attachToComment(Long commentId, Long uploaderId, String fileName, String filePath) {
        Comment comment = commentService.getById(commentId);
        User uploader = userService.getById(uploaderId);

        Attachment attachment = new Attachment(comment, fileName, filePath, uploader);
        return attachmentRepo.save(attachment);
    }

    public Attachment getById(Long attachmentId) {
        return attachmentRepo.findById(attachmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found: " + attachmentId));
    }

    public List<Attachment> getAttachmentsForIssue(Long issueId) {
        return attachmentRepo.findByIssueId(issueId);
    }

    public List<Attachment> getAttachmentsForComment(Long commentId) {
        return attachmentRepo.findByCommentId(commentId);
    }

    public void deleteAttachment(Long attachmentId) {
        Attachment attachment = getById(attachmentId);
        attachmentRepo.delete(attachment);
    }
}