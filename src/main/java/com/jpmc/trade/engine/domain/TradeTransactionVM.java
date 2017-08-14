package com.jpmc.trade.engine.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Currency;

/**
 * 
 * TradeViewModel is a POJO to interact with Reporting Engine Operations.
 * 
 * @author Narasimha
 *
 */
public class TradeTransactionVM implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1015128061164013677L;

	private final String businessEntity;
	private final TradeOperation tradeBuyOrSell;
	private Currency tradeCurrency;
	private final LocalDate instructionDate;
	private final LocalDate settlementDate;

	private final TransactionDetailsVM transactionDetailsVM;

	public TradeTransactionVM(String businessEntity, TradeOperation tradeBuyOrSell, LocalDate instructionDate,
			LocalDate settlementDate, TransactionDetailsVM transactionDetailsVM) {
		this.businessEntity = businessEntity;
		this.tradeBuyOrSell = tradeBuyOrSell;
		this.instructionDate = instructionDate;
		this.settlementDate = settlementDate;
		this.transactionDetailsVM = transactionDetailsVM;
	}

	public LocalDate getSettlementDate() {
		return settlementDate;
	}

	public String getBusinessEntity() {
		return businessEntity;
	}

	public TradeOperation getTradeBuyOrSell() {
		return tradeBuyOrSell;
	}

	public Currency getTradeCurrency() {
		return tradeCurrency;
	}

	public LocalDate getInstructionDate() {
		return instructionDate;
	}

	public TransactionDetailsVM getTransactionDetailsVM() {
		return transactionDetailsVM;
	}

}
