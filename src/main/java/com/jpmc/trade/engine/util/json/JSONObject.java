package com.jpmc.trade.engine.util.json;

import java.util.Map;

import com.jpmc.trade.engine.domain.TradeCurrency;
import com.jpmc.trade.engine.domain.TradeOperation;

public class JSONObject {

	private Map<String, Object> map;

	@SuppressWarnings("unchecked")
	public JSONObject(Object object) {
		map = (Map<String, Object>) object;
	}

	public Object get(String key) {
		return map.get(key);
	}

	public String getString(String key) {
		return (String) map.get(key);
	}

	public Boolean getBoolean(String key) {
		return (Boolean) map.get(key);
	}

	public Integer getInteger(String key) {
		return (Integer) map.get(key);
	}

	public Double getDouble(String key) {
		return Double.parseDouble(map.get(key).toString());
		// return map.get(key);
	}

	public JSONObject getObject(String key) {
		return new JSONObject(map.get(key));
	}

	public TradeCurrency getTradeCurrency(String key) {
		String tradeCurrency = (String) map.get(key);

		switch (tradeCurrency) {

		case "AED":
			return TradeCurrency.AED;
		case "GBP":
			return TradeCurrency.GBP;
		case "SGD":
			return TradeCurrency.SGD;
		case "AUD":
			return TradeCurrency.AUD;
		case "CAD":
			return TradeCurrency.CAD;
		case "SAR":
			return TradeCurrency.SAR;

		default:
			return TradeCurrency.GBP;

		}

	}

	public TradeOperation getTradeOperation(String key) {

		String tradeOperation = (String) map.get(key);

		switch (tradeOperation) {

		case "SELL":
			return TradeOperation.SELL;
		case "BUY":
			return TradeOperation.BUY;
		}

		return null;
	}

	public JSONArray getArray(String key) {
		return new JSONArray((Object[]) map.get(key));
	}

}