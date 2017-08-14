package com.jpmc.trade.engine;

import com.jpmc.trade.engine.report.DailyTradeReportEngine;
import com.jpmc.trade.engine.report.DailyTradeReportEngineImpl;

public class ReportEngineFactory {
	
	 public static DailyTradeReportEngine getDailyTradeReportEngineInstance(ReportType model) {
		 
		 switch(model) {
		 
		 case DAILY:
			 	return new DailyTradeReportEngineImpl();
			 	
		default:
				
			break;
		 
		 }
		return null;
		 
	 }

}
