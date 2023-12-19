package dao;

import entity.Transaction;
import entity.User;

import javax.persistence.EntityManager;
import java.util.List;

public class TransactionJpaDao extends AbstractJpaDao<Transaction>{
    public TransactionJpaDao(EntityManager em) {
        super(em);
    }

    @Override
   public Transaction getEntityById(int id) {
        criteriaQuery = criteriaBuilder.createQuery(Transaction.class);
        root = criteriaQuery.from(Transaction.class);
        criteriaQuery.select(root).
                where(criteriaBuilder.equal(root.get("transactionId"),id));
        return em.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<Transaction> getAll() {
        criteriaQuery = criteriaBuilder.createQuery(Transaction.class);
        root = criteriaQuery.from(Transaction.class);
        return em.createQuery(criteriaQuery.select(root)).getResultList();
    }
}
