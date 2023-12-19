package entity;

import javax.persistence.*;

@Entity
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "base_currency")
    private CurrencyTicker baseCurrency;
    @Column(name = "target_currency")
    private CurrencyTicker targetCurrency;
    @Column(name = "currency_rate")
    private double currencyRate;

    public CurrencyRate(CurrencyTicker baseCurrency, CurrencyTicker targetCurrency, double currencyRate) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.currencyRate = currencyRate;
    }

    public CurrencyRate() {
    }

    public CurrencyTicker getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(CurrencyTicker baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public CurrencyTicker getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(CurrencyTicker targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public double getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(double currencyRate) {
        this.currencyRate = currencyRate;
    }

    public int getId() {
        return id;
    }
}
