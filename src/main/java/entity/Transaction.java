package entity;

import javax.persistence.*;
import java.util.Date;
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;
    @Column(name = "transaction_date")
    private Date date;
    @OneToOne
    @JoinColumn(name = "from_account_id")
    private Account from;
    @OneToOne
    @JoinColumn(name = "to_account_id")
    private Account to;
    @Column(name = "value")
    private double value;
    @Column(name = "transaction_type")
    private TransactionType transactionType;
    @Column(name = "tansaction_rate")
    private double transactionRate;

    public Transaction() {
    }

    public Transaction(Date date, Account from, Account to, double value, TransactionType transactionType,double transactionRate) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.value = value;
        this.transactionType = transactionType;
        this.transactionRate = transactionRate;
    }

    public Date getDate() {
        return date;
    }

    public Account getFrom() {
        return from;
    }

    public void setFrom(Account from) {
        this.from = from;
    }

    public Account getTo() {
        return to;
    }

    public void setTo(Account to) {
        this.to = to;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public double getTransactionRate() {
        return transactionRate;
    }

    public void setTransactionRate(double transactionRate) {
        this.transactionRate = transactionRate;
    }
}
