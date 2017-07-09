package cn.edu.njue.blackStone.backend.model;

/**
 * Created by EdwardZhou on 2017/5/27.
 * All Rights Reserved
 */
public class Feedback {
    private Long id;
    private Long userId;
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
