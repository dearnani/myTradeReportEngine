package com.jpmc.trade.engine.report;

import java.util.ArrayList;
import com.jpmc.trade.engine.domain.TradeTransactionVM;

public interface DailyTradeReportEngine extends ReportEngine {

	public ArrayList<TradeTransactionVM> populateTradedVolumeVMs(String filePath, String rootElement);
	public StringBuilder generateBuynSellSummaryReport(ArrayList<TradeTransactionVM> listOfTradeVM);

}
