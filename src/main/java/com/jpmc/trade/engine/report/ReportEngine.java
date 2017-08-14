package com.jpmc.trade.engine.report;

import java.io.IOException;
import javax.script.ScriptException;
import com.jpmc.trade.engine.util.json.JSONArray;

/**
 * 
 * @author Narasimha
 *
 */
public interface ReportEngine {

	public JSONArray viewTradedJSONData(String filePath, String rootElement) throws ScriptException, IOException;

	}
