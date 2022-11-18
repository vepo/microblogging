package io.vepo.microblogging.post;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

@Transactional
@ApplicationScoped
public class Posts {

    @PersistenceContext
    EntityManager em;

    public List<Post> list() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Post> query = criteriaBuilder.createQuery(Post.class);
        query.from(Post.class);
        return em.createQuery(query).getResultList();
    }

    public Post createPost(Post post) {
        return em.merge(post);
    }
}
