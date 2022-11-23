package io.vepo.microblogging.user;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String handle;

    private String email;

    @Column(name = "hashed_password")
    private String hashedPassword;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public User() {
        this(null, null, null);
    }

    public User(String handle, String email, String password) {
        this.handle = handle;
        this.email = email;
        this.hashedPassword = password;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, handle, email, hashedPassword, createdAt);
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
        User other = (User) obj;
        return Objects.equals(id, other.id)
                && Objects.equals(handle, other.handle)
                && Objects.equals(email, other.email)
                && Objects.equals(hashedPassword, other.hashedPassword)
                && Objects.equals(createdAt, other.createdAt);
    }

    @Override
    public String toString() {
        return String.format("User[id=%d, handle=%s, email=%s, hashedPassword=%s, createdAt=%s]",
                id, handle, email, hashedPassword, createdAt);
    }
}
