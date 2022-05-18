package instAwarePlanning.communication.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeedReader {

	public static Map<String, String> getFeedParams(String paramFeedString) {
		
		Map<String, String> params = new HashMap<>();

		paramFeedString = paramFeedString.trim();

		Pattern p = Pattern.compile("\\((.*?)\\)");
		Matcher m = p.matcher(paramFeedString);

		//System.out.println("Recieved Params: ");
		while (m.find()) {
			
			String keyAndValue = m.group(1);
			//System.out.println(keyAndValue);
			String[] keyValue = keyAndValue.split(" ");
			
			String key = keyValue[0];
			String value = keyValue[1];
			if ((key!=null || key!="") && (value!=null || value!="") ) {
				params.put(key, value);
			}
			else {
				throw new Error("Key or Value is empty"); // new Exception?
			}
		}

		return params;
	}
	
	
	
}
