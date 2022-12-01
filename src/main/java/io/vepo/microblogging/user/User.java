package io.vepo.microblogging.user;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_users")
@NamedQuery(name = "user-by-email", query = "FROM User where email = :email")
@NamedQuery(name = "user-by-handle", query = "FROM User where handle = :handle")
@NamedQuery(name = "user-login", query = "FROM User where handle = :handle and hashedPassword = :hashedPassword")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String handle;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_id", referencedColumnName = "id", nullable = true)
    private Image cover;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "avatar_id", referencedColumnName = "id", nullable = true)
    private Image avatar;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public User() {
        this(null, null, null);
    }

    public User(String handle, String email, String password) {
        this.handle = handle;
        this.email = email;
        this.hashedPassword = password;
        this.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
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

    public Optional<Image> getAvatar() {
        return Optional.ofNullable(avatar);
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public Optional<Image> getCover() {
        return Optional.ofNullable(cover);
    }

    public void setCover(Image cover) {
        this.cover = cover;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return String.format("User[id=%d, handle=%s, email=%s, hashedPassword=%s, createdAt=%s]",
                id, handle, email, hashedPassword, createdAt);
    }
}
