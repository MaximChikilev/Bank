import dao.CurrencyRateJpaDao;
import entity.CurrencyRate;
import entity.CurrencyTicker;
import entity.User;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Callable;

import static org.junit.Assert.assertEquals;

public class CurrencyRateTest extends AbstractTest<CurrencyRate> {
    List<CurrencyRate> entityList;
    @Override
    public Callable<CurrencyRate> getCallableToFillEntityTable() {
        Callable<CurrencyRate> currencyRateCallable = new Callable<CurrencyRate>() {
            @Override
            public CurrencyRate call() throws Exception {
                em.persist(new CurrencyRate(CurrencyTicker.UAH,CurrencyTicker.USD,36.8));
                em.persist(new CurrencyRate(CurrencyTicker.EUR,CurrencyTicker.USD,1.09));
                em.persist(new CurrencyRate(CurrencyTicker.UAH,CurrencyTicker.EUR,39.6));
                return null;
            }
        };
        return currencyRateCallable;
    }
    @Override
    protected void setJpaDao() {
        jpaDao = new CurrencyRateJpaDao(em);
    }
    @Test
    public void entityTest() {
        entityList = jpaDao.getAll();
        assertEquals(3, entityList.size());
        assertEquals(CurrencyTicker.UAH, entityList.get(0).getBaseCurrency());
        assertEquals(CurrencyTicker.USD, entityList.get(0).getTargetCurrency());
        assertEquals(36.8, entityList.get(0).getCurrencyRate(),0);
        CurrencyRate testEntity = new CurrencyRate(CurrencyTicker.EUR,CurrencyTicker.USD,0.92);
        jpaDao.create(testEntity);
        assertEquals(4, jpaDao.getAll().size());
        testEntity.setCurrencyRate(0.9);
        jpaDao.update(testEntity);
        assertEquals(0.9, jpaDao.getEntityById(testEntity.getId()).getCurrencyRate(),0);
        jpaDao.delete(testEntity);
        entityList = jpaDao.getAll();
        assertEquals(3, entityList.size());
    }
}
