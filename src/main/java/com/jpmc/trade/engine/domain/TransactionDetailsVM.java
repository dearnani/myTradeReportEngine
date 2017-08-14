package com.jpmc.trade.engine.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * Represents the price details of transaction
 */
public class TransactionDetailsVM implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2775914123347321915L;

	// In which currency
	private final Currency currency;

	// The foreign exchange rate with respect to USD that was agreed
	private final BigDecimal agreedFx;

	// Number of shares to be bought or sold
	private final int units;

	// The price per unit
	private final BigDecimal pricePerUnit;

	// The total trade amount in a given Currency
	private BigDecimal tradeAmount;

	public TransactionDetailsVM(Currency currency, BigDecimal agreedFx, int units, BigDecimal pricePerUnit) {
		this.currency = currency;
		this.agreedFx = agreedFx;
		this.units = units;
		this.pricePerUnit = pricePerUnit;
	}
	
	public BigDecimal getAgreedFx() {
		return agreedFx;
	}

	public int getUnits() {
		return units;
	}

	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public Currency getCurrency() {
		return currency;
	}
	
	public void setTradeAmount(BigDecimal tradeAmount) {
		
		this.tradeAmount = tradeAmount;
	}
	
	
}