package com.jpmc.trade.engine.business;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;

import com.jpmc.trade.engine.domain.TradeTransactionVM;
import com.jpmc.trade.engine.domain.TransactionDetailsVM;

public interface ComputeBuynSellSummary {

	public StringBuilder summariseBuynSell(ArrayList<TradeTransactionVM> listOfTradedData );
	public LocalDate computeSettlementDate(Currency tradeCurrency, LocalDate settlementDate) ;
	public  BigDecimal calculateAmount(TransactionDetailsVM transactionDetailsVM) ;
}
