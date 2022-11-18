package io.vepo.microblogging.post;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String content;

    public Post() {
    }

    public Post(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Post(String content) {
        this.id = null;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        }
        Post other = (Post) obj;
        return Objects.equals(id, other.id) && Objects.equals(content, other.content);
    }

    @Override
    public String toString() {
        return String.format("Post[id=%d, content=%s]", id, content);
    }
}
