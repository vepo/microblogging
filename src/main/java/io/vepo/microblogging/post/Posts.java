package io.vepo.microblogging.post;

import java.util.List;
import java.util.Objects;

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

    public List<Post> list(int offset, int limit) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Post> query = criteriaBuilder.createQuery(Post.class);
        var root = query.from(Post.class);
        query.orderBy(criteriaBuilder.asc(root.get("createdAt")));
        return em.createQuery(query)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public Post createPost(Post post) {
        return em.merge(post);
    }

    public Post delete(Long postId) {
        var post = em.find(Post.class, postId);
        if (Objects.nonNull(post)) {
            em.createNamedQuery("post-delete")
                    .setParameter("id", postId)
                    .executeUpdate();
        }
        return post;
    }
}
