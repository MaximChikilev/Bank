package dao;

import entity.Account;
import entity.CurrencyRate;
import entity.CurrencyTicker;
import entity.User;

import javax.persistence.EntityManager;
import java.util.List;
import javax.persistence.criteria.Predicate;

public class CurrencyRateJpaDao extends AbstractJpaDao<CurrencyRate> {
    public CurrencyRateJpaDao(EntityManager em) {
        super(em);
    }

    @Override
    public CurrencyRate getEntityById(int id) {
        criteriaQuery = criteriaBuilder.createQuery(CurrencyRate.class);
        root = criteriaQuery.from(CurrencyRate.class);
        criteriaQuery.select(root).
                where(criteriaBuilder.equal(root.get("id"), id));
        return em.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public List<CurrencyRate> getAll() {
        criteriaQuery = criteriaBuilder.createQuery(CurrencyRate.class);
        root = criteriaQuery.from(CurrencyRate.class);
        return em.createQuery(criteriaQuery.select(root)).getResultList();
    }

    public CurrencyRate getEntityByBaseAndTargetCurrency(CurrencyTicker base, CurrencyTicker target) {
        criteriaQuery = criteriaBuilder.createQuery(CurrencyRate.class);
        root = criteriaQuery.from(CurrencyRate.class);
        Predicate baseCurrencyPredicate = criteriaBuilder.equal(root.get("baseCurrency"), base);
        Predicate targetCurrencyPredicate = criteriaBuilder.equal(root.get("targetCurrency"), target);
        criteriaQuery.select(root).
                where(criteriaBuilder.and(baseCurrencyPredicate, targetCurrencyPredicate));
        return em.createQuery(criteriaQuery).getSingleResult();
    }
}
