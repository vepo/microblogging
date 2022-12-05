package io.vepo.microblogging.image;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public class Images {
    @PersistenceContext
    EntityManager em;

    public Optional<Image> find(Long imageId) {
        return Optional.ofNullable(em.find(Image.class, imageId));
    }

}
