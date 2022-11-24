package io.vepo.microblogging.post;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tb_posts")
@NamedQuery(name = "post-delete", query = "DELETE from Post where id = :id")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Post() {
        this(null, null);
    }

    public Post(String content) {
        this(null, content);
    }

    public Post(Long id, String content) {
        this.id = id;
        this.content = content;
        this.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, createdAt);
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
        return Objects.equals(id, other.id)
                && Objects.equals(content, other.content)
                && Objects.equals(createdAt, other.createdAt);
    }

    @Override
    public String toString() {
        return String.format("Post[id=%d, content=%s, createdAt=%s]", id, content, createdAt);
    }
}
