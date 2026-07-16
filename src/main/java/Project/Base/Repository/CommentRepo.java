package Project.Base.Repository;

import Project.Base.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {


    List<Comment> findByIssueIdOrderByCreatedAtAsc(Long issueId);


    List<Comment> findByAuthorId(Long authorId);


    long countByIssueId(Long issueId);
}