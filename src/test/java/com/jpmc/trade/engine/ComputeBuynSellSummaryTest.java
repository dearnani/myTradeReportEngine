package com.jpmc.trade.engine;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;

import com.jpmc.trade.engine.business.ComputeBuynSellSummary;
import com.jpmc.trade.engine.business.ComputeBuynSellSummaryImpl;
import com.jpmc.trade.engine.domain.TradeTransactionVM;
import com.jpmc.trade.engine.domain.TransactionDetailsVM;
import com.jpmc.trade.engine.report.DailyTradeReportEngine;

public class ComputeBuynSellSummaryTest {
	
	ComputeBuynSellSummary computeBuynSellSummary;
	ArrayList<TradeTransactionVM> tradedTxnData;
	DateTimeFormatter formatter;
	
	  @Before
	      public void setData() {

			 DailyTradeReportEngine engine = ReportEngineFactory.getDailyTradeReportEngineInstance(ReportType.DAILY);
    		tradedTxnData = engine.populateTradedVolumeVMs("/data.json", "tradedData");
    		computeBuynSellSummary = new ComputeBuynSellSummaryImpl();
    		formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

	  }

	@Test
	public void testSummariseBuynSell() {
		
	StringBuilder testData = computeBuynSellSummary.summariseBuynSell(tradedTxnData);
	assertNotNull(testData.toString());
	}
	
	@Test
	public void testCalculateAmount() {
		
		Currency currency =  Currency.getInstance("SAR");
		BigDecimal agreedFx = new BigDecimal("0.50");
		BigDecimal pricePerUnit = new BigDecimal("200.50");
		
		TransactionDetailsVM transactionDetailsVM = new TransactionDetailsVM(currency, agreedFx, 5, pricePerUnit);
	
		BigDecimal resultData = computeBuynSellSummary.calculateAmount(transactionDetailsVM);
		System.out.println("resultData:"+resultData);
		assertEquals(new BigDecimal(501.25),resultData);
	}
	
	@Test
	public void computeArabianCurrencySettlementDate() {
		Currency arabianCurrency =  Currency.getInstance("AED");
		
		LocalDate instructionDateOne = LocalDate.parse("10 Aug 2017", formatter);
		LocalDate settlementDate = computeBuynSellSummary.computeSettlementDate(arabianCurrency, instructionDateOne);
		assertEquals(settlementDate,instructionDateOne);
		
		
	}
		@Test
		public void computeBritishCurrencySettlementDate() {
			
			Currency britishCurrency =  Currency.getInstance("GBP");
			LocalDate instructionDateTwo = LocalDate.parse("11 Aug 2017", formatter);
			LocalDate settlementDateTwo = computeBuynSellSummary.computeSettlementDate(britishCurrency, instructionDateTwo);
			assertEquals(settlementDateTwo,instructionDateTwo);
		}
}
