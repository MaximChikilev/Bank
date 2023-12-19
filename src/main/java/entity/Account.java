package entity;

import javax.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "currency")
    private CurrencyTicker currency;
    @Column(name = "value")
    private double value;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User user;

    public Account() {
    }

    public Account(String accountNumber, CurrencyTicker currency, double value) {
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.value = value;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public CurrencyTicker getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyTicker currency) {
        this.currency = currency;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getAccountId() {
        return accountId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", accountNumber=" + accountNumber +
                ", currency=" + currency.name() +
                ", value=" + value +
                "}\n";
    }
}
