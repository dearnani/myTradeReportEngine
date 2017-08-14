package com.jpmc.trade.engine.report;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.jpmc.trade.engine.domain.TradeTransactionVM;
import com.jpmc.trade.engine.util.json.JSONArray;
import com.jpmc.trade.engine.util.json.JSONFile;

/**
 * 
 * @author Narasimha
 *
 */
public class TradingReportEngineTest {
	
	DailyTradeReportEngine engine;
	JSONArray tradedDataRecords;
	JSONArray actualtradedDataRecords; 
	  @Before
	      public void setData() {

			 engine = new DailyTradeReportEngineImpl();
			 actualtradedDataRecords = JSONFile.parseFromResource("/data.json").getArray("tradedData");
	  
	  }

	@Test
	public void testReadTradedJSONData() {
		
		try {
			JSONArray tradedDataRecords = JSONFile.parseFromResource("/testData.json").getArray("tradedData");
			HashMap<?,?> testData = (HashMap<?,?>) tradedDataRecords.get(0);
			HashMap<?,?> myData = (HashMap<?,?>) actualtradedDataRecords.get(0);

			assertEquals(testData.values().toString(), myData.values().toString());
		}catch (Exception testFailException) {
			testFailException.printStackTrace();
			throw new AssertionError();
		}
		
	}

	@Test
	public void testSummariseBuynSell() {
		
		ArrayList<TradeTransactionVM> tratedDate = engine.populateTradedVolumeVMs("/testData.json","tradedData");
		try {
		engine.generateBuynSellSummaryReport(tratedDate);
		}catch (Exception e) {
			e.printStackTrace();
			throw new AssertionError();
		}
		
	}

	@Test
	public void testPopulateTradeVM() {
		ArrayList<TradeTransactionVM> tratedData = engine.populateTradedVolumeVMs("/testData.json","tradedData");
		TradeTransactionVM tradeVM = tratedData.get(1);
			assertEquals("AAL",tradeVM.getBusinessEntity());
	}

}
