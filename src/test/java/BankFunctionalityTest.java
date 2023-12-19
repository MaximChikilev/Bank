import entity.*;
import entity.exception.BadAccountNumberException;
import entity.exception.NoEnoughMoneyException;
import entity.exception.NoExchangeRateException;
import entity.exception.NoUserException;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BankFunctionalityTest {
    private BankManager bankManager = new BankManager();
    EntityManager em = bankManager.getEm();
    List<User> users = new ArrayList<>();
    List<Account> accounts = new ArrayList<>();
    List<CurrencyRate> currencyRates = new ArrayList<>();


    private void initDataBase() {
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        try {
            users.add(new User("Maxim", "Chikilev", "(067)111-11-11", "Some address 1"));
            users.add(new User("Anatoliy", "Chikilev", "(067)222-22-22", "Some address 2"));
            accounts.add(new Account("000001", CurrencyTicker.USD, 1000));
            accounts.add(new Account("000002", CurrencyTicker.UAH, 1000));
            accounts.add(new Account("000003", CurrencyTicker.EUR, 1000));
            accounts.add(new Account("000004", CurrencyTicker.USD, 1000));
            accounts.add(new Account("000005", CurrencyTicker.UAH, 1000));
            accounts.add(new Account("000006", CurrencyTicker.EUR, 1000));
            for (int i = 0; i <= 2; i++) {
                users.get(0).addAccount(accounts.get(i));
                accounts.get(i).setUser(users.get(0));
            }
            for (int i = 3; i <= 5; i++) {
                users.get(1).addAccount(accounts.get(i));
                accounts.get(i).setUser(users.get(1));
            }
            currencyRates.add(new CurrencyRate(CurrencyTicker.UAH, CurrencyTicker.USD, 36.8));
            currencyRates.add(new CurrencyRate(CurrencyTicker.EUR, CurrencyTicker.USD, 1.09));
            currencyRates.add(new CurrencyRate(CurrencyTicker.UAH, CurrencyTicker.EUR, 39.6));
            createEntityFromList(users);
            createEntityFromList(accounts);
            createEntityFromList(currencyRates);
            entityTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        }
    }

    private void createEntityFromList(List list) {
        for (int i = 0; i < list.size(); i++) {
            em.persist(list.get(i));
        }

    }

    @Test
    public void transferTest() throws NoExchangeRateException, NoEnoughMoneyException, BadAccountNumberException {
        initDataBase();
        bankManager.executeTransferTransaction("000001", "000004", 100);
        Account accountFrom = bankManager.getAccountJpaDao().getEntityByAccountNumber("000001");
        assertEquals(900.0,accountFrom.getValue(),0);
        Account accountTo = bankManager.getAccountJpaDao().getEntityByAccountNumber("000004");
        assertEquals(1100.0,accountTo.getValue(),0);
        bankManager.executeTransferTransaction("000003", "000001", 100);
        accountFrom = bankManager.getAccountJpaDao().getEntityByAccountNumber("000001");
        assertEquals(1009.0,accountFrom.getValue(),0);
        accountTo = bankManager.getAccountJpaDao().getEntityByAccountNumber("000003");
        assertEquals(900.0,accountTo.getValue(),0);
    }
    @Test(expected = BadAccountNumberException.class)
    public void testBadAccountNumberException() throws NoExchangeRateException, NoEnoughMoneyException, BadAccountNumberException {
        initDataBase();
        bankManager.executeTransferTransaction("000006", "000007", 100);
    }
    @Test(expected = NoExchangeRateException.class)
    public void testNoExchangeRateException() throws NoExchangeRateException, NoEnoughMoneyException, BadAccountNumberException {
        initDataBase();
        bankManager.executeTransferTransaction("000001", "000002", 100);
    }
    @Test(expected = NoEnoughMoneyException.class)
    public void testNoEEnoughMoneyException() throws NoExchangeRateException, NoEnoughMoneyException, BadAccountNumberException {
        initDataBase();
        bankManager.executeTransferTransaction("000001", "000004", 1100);
    }
    @Test
    public void testGetSumValueAllUsersAccount() throws NoUserException {
        initDataBase();
        assertEquals(77400,bankManager.getAllUserAccountsValues(1),0);
    }
    @Test
    public void testRefillAccountTest(){
        initDataBase();
        bankManager.refillAccount("000001",500);
        Account account= bankManager.getAccountJpaDao().getEntityByAccountNumber("000001");
        assertEquals(1500.0,account.getValue(),0);
    }
}
