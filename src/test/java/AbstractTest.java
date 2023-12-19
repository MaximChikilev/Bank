import dao.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class AbstractTest<T> {
    protected static final String NAME = "Bank";
    protected EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(NAME);
    protected EntityManager em = emFactory.createEntityManager();
    protected AbstractJpaDao<T> jpaDao;

    @Before
    public void init() {
        jpaDao.performTransaction(getCallableToFillEntityTable());
    }

    @After
    public void finish() {
        if (em != null) em.close();
        if (emFactory != null) emFactory.close();
    }

    public AbstractTest() {
        setJpaDao();
    }

    public abstract Callable<T> getCallableToFillEntityTable();

    protected abstract void setJpaDao();


}
