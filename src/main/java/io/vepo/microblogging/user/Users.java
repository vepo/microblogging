package io.vepo.microblogging.user;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import io.vepo.microblogging.infra.PasswordCrypt;

@Transactional
@ApplicationScoped
public class Users {
    @PersistenceContext
    EntityManager em;

    @Inject
    PasswordCrypt passwordCrypt;

    public User create(User user) {
        user.setHashedPassword(passwordCrypt.hashPassword(user.getHashedPassword()));
        return em.merge(user);
    }

    public Optional<User> findByHandleAndPassword(String handle, String password) {
        return em.createNamedQuery("user-login", User.class)
                .setParameter("handle", handle)
                .setParameter("hashedPassword", passwordCrypt.hashPassword(password))
                .getResultList()
                .stream()
                .findFirst();
    }
}
