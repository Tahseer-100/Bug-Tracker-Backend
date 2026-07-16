package Project.Base.Repository;

import Project.Base.Entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepo extends JpaRepository<Attachment, Long> {

    List<Attachment> findByIssueId(Long issueId);

    List<Attachment> findByCommentId(Long commentId);

    List<Attachment> findByUploadedById(Long userId);
}