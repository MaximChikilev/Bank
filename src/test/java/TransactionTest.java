import dao.TransactionJpaDao;
import entity.Transaction;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import static org.junit.Assert.assertEquals;

public class TransactionTest extends AbstractTest<Transaction> {
    List<Transaction> entityList;
    @Override
    public Callable<Transaction> getCallableToFillEntityTable() {
        Callable<Transaction> transactionCallable = new Callable<Transaction>() {
            @Override
            public Transaction call() throws Exception {
                em.persist(new Transaction());
                em.persist(new Transaction());
                em.persist(new Transaction());
                return null;
            }
        };
        return transactionCallable;
    }

    @Override
    protected void setJpaDao() {
        jpaDao = new TransactionJpaDao(em);
    }
    @Test
    public void entityTest() {
        entityList = jpaDao.getAll();
        assertEquals(3, entityList.size());
        Transaction testEntity = new Transaction();
        testEntity.setDate(new Date());
        jpaDao.create(testEntity);
        assertEquals(4, jpaDao.getAll().size());
        testEntity.setValue(100);
        jpaDao.update(testEntity);
        assertEquals(100, jpaDao.getEntityById(testEntity.getTransactionId()).getValue(),0);
        jpaDao.delete(testEntity);
        entityList = jpaDao.getAll();
        assertEquals(3, entityList.size());
    }
}
