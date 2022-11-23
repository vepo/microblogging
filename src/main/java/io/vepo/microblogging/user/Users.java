package io.vepo.microblogging.user;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
}
