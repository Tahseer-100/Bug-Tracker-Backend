package Project.Base.DTO;

public class CommentRequest {

    private Long authorId;    //Temporary
    private String content;

    public CommentRequest() {}

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
