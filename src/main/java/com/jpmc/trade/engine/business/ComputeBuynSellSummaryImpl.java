package com.jpmc.trade.engine.business;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.jpmc.trade.engine.domain.TradeTransactionVM;
import com.jpmc.trade.engine.domain.TransactionDetailsVM;

public class ComputeBuynSellSummaryImpl implements ComputeBuynSellSummary {

	public StringBuilder summariseBuynSell(ArrayList<TradeTransactionVM> listOfTradedData ) {

		TreeMap<LocalDate, BigDecimal> buyTradedSortedData = new TreeMap<LocalDate, BigDecimal>();
		HashMap<String, BigDecimal> buyTradedEntityData = new HashMap<String, BigDecimal>();

		TreeMap<LocalDate, BigDecimal> sellTradedSortedData = new TreeMap<LocalDate, BigDecimal>();
		HashMap<String, BigDecimal> sellTradedEntityData = new HashMap<String, BigDecimal>();

		for (TradeTransactionVM tradedDataRecord : listOfTradedData) {

			if (tradedDataRecord.getTradeBuyOrSell() != null) {
				
				tradedDataRecord.getTransactionDetailsVM().setTradeAmount(calculateAmount(tradedDataRecord.getTransactionDetailsVM()));

				switch (tradedDataRecord.getTradeBuyOrSell()) {
				case SELL:
					updateSortedTradedDataBySettlementDate(buyTradedSortedData, tradedDataRecord);
					updateSortedEntitiesByEntity(buyTradedEntityData, tradedDataRecord);
					break;
				case BUY:
					updateSortedTradedDataBySettlementDate(sellTradedSortedData, tradedDataRecord);
					updateSortedEntitiesByEntity(sellTradedEntityData, tradedDataRecord);
					break;

				}
			}
		}

		return acquireTradeSummary(buyTradedSortedData, buyTradedEntityData, "Buy SUMMARY", "RANKING OF Buy ENTITIES")
				.append("\n").append(acquireTradeSummary(sellTradedSortedData, sellTradedEntityData, "Sell SUMMARY",
						"RANKING OF Sell ENTITIES"));

	}

	private TreeMap<LocalDate, BigDecimal> updateSortedTradedDataBySettlementDate(TreeMap<LocalDate, BigDecimal> treeMap,
			TradeTransactionVM tradeVM) {
		if (tradeVM != null) {
			LocalDate computedlocalDate = computeSettlementDate(tradeVM.getTransactionDetailsVM().getCurrency(),
					tradeVM.getSettlementDate());
			if (!treeMap.containsKey(computedlocalDate)) {
				treeMap.put(computedlocalDate, tradeVM.getTransactionDetailsVM().getTradeAmount());
			} else {
				BigDecimal value = (BigDecimal) treeMap.get(computedlocalDate);
				treeMap.put(computedlocalDate, value.add(tradeVM.getTransactionDetailsVM().getTradeAmount()));
			}
		}
		return treeMap;
	}

	private HashMap<String, BigDecimal> updateSortedEntitiesByEntity(HashMap<String, BigDecimal> hashMap,
			TradeTransactionVM tradeVM) {
		if (!hashMap.containsKey(tradeVM.getBusinessEntity())) {
			hashMap.put(tradeVM.getBusinessEntity(), tradeVM.getTransactionDetailsVM().getTradeAmount());
		} else {
			BigDecimal value = (BigDecimal) hashMap.get(tradeVM.getBusinessEntity());
			hashMap.put(tradeVM.getBusinessEntity(), value.add(tradeVM.getTransactionDetailsVM().getTradeAmount()));
		}
		return hashMap;
	}

	private StringBuilder acquireTradeSummary(TreeMap<LocalDate, BigDecimal> buySummarySortedData,
			HashMap<String, BigDecimal> buySummaryEntityData, String summaryTest, String entityText) {

		StringBuilder summaryData = new StringBuilder();

		summaryData.append(summaryTest).append("\n SettledmentDate     TradedAmount \n")
				.append("---------------------------------\n");

		Iterator<Entry<LocalDate, BigDecimal>> buySummaryItertor = buySummarySortedData.entrySet().iterator();
		while (buySummaryItertor.hasNext()) {
			Entry<LocalDate, BigDecimal> pairs = buySummaryItertor.next();
			summaryData.append(String.format("%16s", pairs.getKey())).append("|")
					.append(String.format("%14s", pairs.getValue())).append("  \n");
		}

		StringBuilder entitySummary = new StringBuilder();
		entitySummary.append(entityText).append("\n entity     Value \n")
				.append("----------------------------------\n");
		HashMap<String, BigDecimal> rankHashMap = new LinkedHashMap<String, BigDecimal>();
		rankHashMap = (HashMap<String, BigDecimal>) sortMapByValues(buySummaryEntityData);

		Iterator<Entry<String, BigDecimal>> itEntity = rankHashMap.entrySet().iterator();
		while (itEntity.hasNext()) {
			Entry<String, BigDecimal> pairs = itEntity.next();
			entitySummary.append(String.format("%16s", pairs.getKey())).append("|")
					.append(String.format("%14s", pairs.getValue())).append(" \n");
		}

		return summaryData.append("\n").append(entitySummary.toString());
	}

	private <K, V extends Comparable<? super V>> Map<K, V> sortMapByValues(Map<K, V> mapToBeSortedByValues) {
		return mapToBeSortedByValues.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}

	public LocalDate computeSettlementDate(Currency tradeCurrency, LocalDate settlementDate) {

		LocalDate actualSettlementDate = settlementDate;
		int dayOfWeek = settlementDate.getDayOfWeek().getValue();

		if (tradeCurrency != null) {
			switch (tradeCurrency.getCurrencyCode()) {
			case "AED":
			case "SAR" :
				if (dayOfWeek == 5) {// Friday
					// add 2 days on to settlementDate
					actualSettlementDate = settlementDate.plusDays(2);
				} else if (dayOfWeek == 6) {// Saturday
					// add 1 day on to settlementDate
					actualSettlementDate =	settlementDate.plusDays(1);
				}

				break;

			case "GBP":
			case "CAD":
			case "SGD":
				if (dayOfWeek == 6) {// Saturday
					// add 2 days on to settlementDate
					actualSettlementDate =	settlementDate.plusDays(2);
				} else if (dayOfWeek == 0) {// Sunday
					// add 1 day on to settlementDate
					actualSettlementDate = settlementDate.plusDays(1);
				}

				break;
			}
			
		}
		return actualSettlementDate;
		
	}
	
	public  BigDecimal calculateAmount(TransactionDetailsVM transactionDetailsVM) {
		return transactionDetailsVM.getPricePerUnit().multiply(new BigDecimal(transactionDetailsVM.getUnits())).multiply(transactionDetailsVM.getAgreedFx()).setScale(2,
				BigDecimal.ROUND_HALF_EVEN);
	}

}
