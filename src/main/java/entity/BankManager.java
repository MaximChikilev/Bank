package entity;

import dao.*;
import entity.exception.BadAccountNumberException;
import entity.exception.NoEnoughMoneyException;
import entity.exception.NoExchangeRateException;
import entity.exception.NoUserException;

import javax.persistence.*;
import java.util.Date;

public class BankManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Bank");
    private EntityManager em = emf.createEntityManager();
    private UserJpaDao userJpaDao = new UserJpaDao(em);
    private AccountJpaDao accountJpaDao = new AccountJpaDao(em);
    private CurrencyRateJpaDao currencyRateJpaDao = new CurrencyRateJpaDao(em);
    private TransactionJpaDao transactionJpaDao = new TransactionJpaDao(em);

    public EntityManager getEm() {
        return em;
    }

    public UserJpaDao getUserJpaDao() {
        return userJpaDao;
    }

    public AccountJpaDao getAccountJpaDao() {
        return accountJpaDao;
    }

    public CurrencyRateJpaDao getCurrencyRateJpaDao() {
        return currencyRateJpaDao;
    }

    public TransactionJpaDao getTransactionJpaDao() {
        return transactionJpaDao;
    }
    public void refillAccount(String accountNumber, double value){
        Account account = accountJpaDao.getEntityByAccountNumber(accountNumber);
        double accountValue = account.getValue();
        Transaction bankTransaction = new Transaction();
        bankTransaction.setDate(new Date());
        bankTransaction.setTransactionType(TransactionType.REFILL);
        bankTransaction.setValue(value);
        bankTransaction.setFrom(null);
        bankTransaction.setTo(account);
        bankTransaction.setTransactionRate(1);
        account.setValue(accountValue+value);
        accountJpaDao.update(account);
        transactionJpaDao.create(bankTransaction);
    }

    public double getAllUserAccountsValues(int id) throws NoUserException {
        User user;
        double allUAHAccountValues = 0;
        double rate = 1;
        try {
            user = userJpaDao.getEntityById(id);
        } catch (NoResultException e) {
            throw new NoUserException("User is not exist");
        }
        for (Account account : user.getAccounts()) {
            rate = (account.getCurrency() == CurrencyTicker.UAH) ? 1 : currencyRateJpaDao.getEntityByBaseAndTargetCurrency(CurrencyTicker.UAH, account.getCurrency()).getCurrencyRate();
            allUAHAccountValues += account.getValue() * rate;
        }
        return allUAHAccountValues;
    }

    public void executeTransferTransaction(String fromAccountNumber, String toAccountNumber, double value) throws NoExchangeRateException, NoEnoughMoneyException, BadAccountNumberException {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            Transaction bankTransaction = getTransaction(fromAccountNumber, toAccountNumber, value);
            double fromAccountValue = bankTransaction.getFrom().getValue();
            double toAccountValue = bankTransaction.getTo().getValue();
            bankTransaction.getFrom().setValue(fromAccountValue - bankTransaction.getValue());
            bankTransaction.getTo().setValue(toAccountValue + bankTransaction.getValue() * bankTransaction.getTransactionRate());
            em.persist(bankTransaction.getFrom());
            em.persist(bankTransaction.getTo());
            em.persist(bankTransaction);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction.isActive())
                transaction.rollback();
            if (ex.getClass().equals(BadAccountNumberException.class))
                throw new BadAccountNumberException("account is not exist");
            if (ex.getClass().equals(NoExchangeRateException.class))
                throw new NoExchangeRateException("It is impossible to exchange. There is not currency rate");
            if (ex.getClass().equals(NoEnoughMoneyException.class))
                throw new NoEnoughMoneyException("There is not enough money to transaction");
        }
    }

    private Transaction getTransaction(String fromAccountNumber, String toAccountNumber, double value) throws BadAccountNumberException, NoExchangeRateException, NoEnoughMoneyException {
        Account accountFrom;
        Account accountTo;
        CurrencyRate currencyRate;
        double rate = 1.0;
        try {
            accountFrom = accountJpaDao.getEntityByAccountNumber(fromAccountNumber);
            accountTo = accountJpaDao.getEntityByAccountNumber(toAccountNumber);
        } catch (NoResultException e) {
            throw new BadAccountNumberException("account is not exist");
        }
        TransactionType transactionType = (accountFrom.getCurrency() == accountTo.getCurrency()) ? TransactionType.TRANSFER : TransactionType.EXCHANGE;
        if (transactionType.equals(TransactionType.EXCHANGE)) {
            try {
                currencyRate = currencyRateJpaDao.getEntityByBaseAndTargetCurrency(accountFrom.getCurrency(), accountTo.getCurrency());
            } catch (NoResultException e) {
                throw new NoExchangeRateException("It is impossible to exchange. There is not currency rate");
            }
            rate = currencyRate.getCurrencyRate();
        }
        if (accountFrom.getValue() < value)
            throw new NoEnoughMoneyException("There is not enough money to transaction");
        return new Transaction(new Date(), accountFrom, accountTo, value, transactionType, rate);
    }
}


