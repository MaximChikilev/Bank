import dao.AbstractJpaDao;
import dao.AccountJpaDao;
import dao.UserJpaDao;
import entity.Account;
import entity.CurrencyRate;
import entity.CurrencyTicker;
import entity.User;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Callable;

import static org.junit.Assert.assertEquals;

public class AccountTest extends AbstractTest<Account> {
    List<Account> entityList;
    UserJpaDao userJpaDao = new UserJpaDao(em);
    @Override
    public Callable<Account> getCallableToFillEntityTable() {
        Callable<Account> accountCallable = new Callable<Account>() {
            @Override
            public Account call() throws Exception {
                em.persist(new Account("000001", CurrencyTicker.USD,0));
                em.persist(new Account("000002", CurrencyTicker.UAH,0));
                em.persist(new Account("000003", CurrencyTicker.EUR,0));
                return null;
            }
        };
        return accountCallable;
    }

    @Override
    protected void setJpaDao() {
        jpaDao = new AccountJpaDao(em);
    }
    @Test
    public void entityTest() {
        entityList = jpaDao.getAll();
        assertEquals(3, entityList.size());
        assertEquals(CurrencyTicker.USD, entityList.get(0).getCurrency());
        assertEquals("000001", entityList.get(0).getAccountNumber());
        assertEquals(0, entityList.get(0).getValue(),0);
        User newUser = new User("Anatoliy", "Chikilev", "(067)222-22-22", "Some address 2");
        for (Account account:entityList){
            account.setUser(newUser);
            newUser.addAccount(account);
        }
        userJpaDao.create(newUser);
        Account testEntity = new Account("000004", CurrencyTicker.EUR,0);
        User user = new User("Maxim", "Chikilev", "(067)111-11-11", "Some address 1");
        user.addAccount(testEntity);
        testEntity.setUser(user);
        jpaDao.create(testEntity);
        assertEquals(4, jpaDao.getAll().size());
        testEntity.setAccountNumber("000005");
        jpaDao.update(testEntity);
        assertEquals("000005", jpaDao.getEntityById(testEntity.getAccountId()).getAccountNumber());
        testEntity.setUser(null);
        user.getAccounts().remove(testEntity);
        jpaDao.delete(testEntity);
        entityList = jpaDao.getAll();
        assertEquals(3, entityList.size());
    }
}
