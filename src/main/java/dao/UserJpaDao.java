package dao;

import entity.Transaction;
import entity.User;

import javax.persistence.EntityManager;
import java.util.List;

public class UserJpaDao extends AbstractJpaDao<User>{
    public UserJpaDao(EntityManager em) {
        super(em);
    }

    @Override
    public User getEntityById(int id) {
        criteriaQuery = criteriaBuilder.createQuery(User.class);
        root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).
                where(criteriaBuilder.equal(root.get("userId"),id));
        return em.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<User> getAll() {
        criteriaQuery = criteriaBuilder.createQuery(User.class);
        root = criteriaQuery.from(User.class);
        return em.createQuery(criteriaQuery.select(root)).getResultList();
    }
}
