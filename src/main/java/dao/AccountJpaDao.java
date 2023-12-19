package dao;

import entity.Account;
import entity.Transaction;
import entity.User;

import javax.persistence.EntityManager;
import java.util.List;

public class AccountJpaDao extends AbstractJpaDao<Account>{
    public AccountJpaDao(EntityManager em) {
        super(em);
    }

    @Override
    public Account getEntityById(int id) {
        criteriaQuery = criteriaBuilder.createQuery(Account.class);
        root = criteriaQuery.from(Account.class);
        criteriaQuery.select(root).
                where(criteriaBuilder.equal(root.get("accountId"),id));
        return em.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<Account> getAll() {
        criteriaQuery = criteriaBuilder.createQuery(Account.class);
        root = criteriaQuery.from(Account.class);
        return em.createQuery(criteriaQuery.select(root)).getResultList();
    }

    public Account getEntityByAccountNumber(String accountNumber) {
        criteriaQuery = criteriaBuilder.createQuery(Account.class);
        root = criteriaQuery.from(Account.class);
        criteriaQuery.select(root).
                where(criteriaBuilder.equal(root.get("accountNumber"),accountNumber));
        return em.createQuery(criteriaQuery).getSingleResult();
    }
}
