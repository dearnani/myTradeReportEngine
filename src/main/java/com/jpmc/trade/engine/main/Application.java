package com.jpmc.trade.engine.main;

import java.io.IOException;
import java.util.ArrayList;

import javax.script.ScriptException;

import com.jpmc.trade.engine.ReportEngineFactory;
import com.jpmc.trade.engine.ReportType;
import com.jpmc.trade.engine.domain.TradeTransactionVM;
import com.jpmc.trade.engine.report.DailyTradeReportEngine;

/**
 * 
 * @author Narasimha
 *
 */
public class Application {

	public static void main(String[] args) throws ScriptException, IOException {

		DailyTradeReportEngine engine = ReportEngineFactory.getDailyTradeReportEngineInstance(ReportType.DAILY);

		ArrayList<TradeTransactionVM> tradedTxnData = engine.populateTradedVolumeVMs("/data.json", "tradedData");
		StringBuilder reportData = engine.generateBuynSellSummaryReport(tradedTxnData);
		System.out.println(String.format("\n                                Report                               \n %s",
				reportData.toString()));

	}
}