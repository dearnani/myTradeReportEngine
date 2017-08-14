package com.jpmc.trade.engine.report;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Currency;

import javax.script.ScriptException;

import com.jpmc.trade.engine.business.ComputeBuynSellSummaryImpl;
import com.jpmc.trade.engine.domain.TradeTransactionVM;
import com.jpmc.trade.engine.domain.TransactionDetailsVM;
import com.jpmc.trade.engine.util.json.JSONArray;
import com.jpmc.trade.engine.util.json.JSONFile;
import com.jpmc.trade.engine.util.json.JSONObject;

/**
 * 
 * @author Narasimha
 *
 */
public class DailyTradeReportEngineImpl implements DailyTradeReportEngine {

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

	public ArrayList<TradeTransactionVM> populateTradedVolumeVMs(String filePath, String rootElement) {

		JSONArray tradedDataRecords = JSONFile.parseFromResource(filePath).getArray(rootElement);
		ArrayList<TradeTransactionVM> tradedDataList = new ArrayList<TradeTransactionVM>();

		for (int index = 0; index < tradedDataRecords.length(); index++) {

			JSONObject tradedDataRecord = tradedDataRecords.getObject(index);

			TransactionDetailsVM transcationViewModel = new TransactionDetailsVM(
					Currency.getInstance(tradedDataRecord.getString("tradeCurrency")),
					new BigDecimal(tradedDataRecord.getDouble("forexRate")),
					tradedDataRecord.getInteger("numberOfUnits"),
					new BigDecimal(tradedDataRecord.getDouble("unitPrice")));
			TradeTransactionVM tradedVM = new TradeTransactionVM(tradedDataRecord.getString("businessEntity"),
					tradedDataRecord.getTradeOperation("tradeBuyOrSell"),
					LocalDate.parse(tradedDataRecord.getString("instructionDate"), formatter),
					LocalDate.parse(tradedDataRecord.getString("settlementDate"), formatter), transcationViewModel);

			tradedDataList.add(tradedVM);
		}

		return tradedDataList;
	}

	public JSONArray viewTradedJSONData(String filePath, String rootElement) throws ScriptException, IOException {

		JSONArray tradedDataRecords = JSONFile.parseFromResource("/data.json").getArray("tradedData");

		StringBuilder generateTradeData = new StringBuilder();

		generateTradeData.append("         Total Traded Volume          \n");
		generateTradeData.append(
				"--------------------------------------------------------------------------------------------\n");
		generateTradeData.append("entity|").append("TradeType|").append("FxRate|").append("TradeCurrency|")
				.append("Instruction Date|").append("Settlement Date|").append("No of Units|").append("Unit Price\n");
		generateTradeData.append(
				"--------------------------------------------------------------------------------------------\n");
		for (int index = 0; index < tradedDataRecords.length(); index++) {

			JSONObject tradedDataRecord = tradedDataRecords.getObject(index);

			generateTradeData.append(String.format("%4s", tradedDataRecord.getString("businessEntity"))).append("|")
					.append(String.format("%9s", tradedDataRecord.getString("tradeBuyOrSell"))).append("|")
					.append(String.format("%6s", tradedDataRecord.getDouble("forexRate").toString())).append("|")
					.append(String.format("%13s", tradedDataRecord.getString("tradeCurrency"))).append("|")
					.append(String.format("%18s", tradedDataRecord.getString("instructionDate"))).append("|")
					.append(String.format("%14s", tradedDataRecord.getString("settlementDate"))).append("|")
					.append(String.format("%12s", tradedDataRecord.getInteger("numberOfUnits"))).append("|")
					.append(String.format("%6s", tradedDataRecord.get("unitPrice"))).append("\n");

		}

		System.out.println(generateTradeData.toString());
		return tradedDataRecords;
	}

	public StringBuilder generateBuynSellSummaryReport(ArrayList<TradeTransactionVM> listOfTradeVM) {

		return new ComputeBuynSellSummaryImpl().summariseBuynSell(listOfTradeVM);
	}
}
