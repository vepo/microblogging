package io.vepo.microblogging.user;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import io.vepo.microblogging.infra.PasswordCrypt;

@Transactional
@ApplicationScoped
public class Users {
    @PersistenceContext
    EntityManager em;

    @Inject
    PasswordCrypt passwordCrypt;

    public User create(User user) {
        if (findUserByHandle(user.getHandle()).isPresent()) {
            throw new WebApplicationException(String.format("Handle already in use! handle=%s", user.getHandle()),
                    Status.CONFLICT);
        }

        if (findUserByEmail(user.getEmail()).isPresent()) {
            throw new WebApplicationException(String.format("Email already in use! email=%s", user.getEmail()),
                    Status.CONFLICT);
        }
        user.setHashedPassword(passwordCrypt.hashPassword(user.getHashedPassword()));
        return em.merge(user);

    }

    public Optional<User> findUserByHandle(String handle) {
        return em.createNamedQuery("user-by-handle", User.class)
                .setParameter("handle", handle)
                .getResultList()
                .stream()
                .findFirst();
    }

    public Optional<User> findUserByEmail(String email) {
        return em.createNamedQuery("user-by-email", User.class)
                .setParameter("email", email)
                .getResultList()
                .stream()
                .findFirst();
    }

    public Optional<User> findByHandleAndPassword(String handle, String password) {
        return em.createNamedQuery("user-login", User.class)
                .setParameter("handle", handle)
                .setParameter("hashedPassword", passwordCrypt.hashPassword(password))
                .getResultList()
                .stream()
                .findFirst();
    }

    public Optional<User> findUserById(Long userId) {
        return Optional.ofNullable(em.find(User.class, userId));
    }
}
