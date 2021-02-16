package services;

import java.util.HashMap;

public class StateCodesDict {
	private HashMap<String, String> map;

	public StateCodesDict() {
		map = new HashMap<String, String>();
		map.put("Alabama", "AL");
		map.put("Alaska", "AK");
		map.put("Arizona", "AZ");
		map.put("Arkansas", "AR");
		map.put("California", "CA");
		map.put("Colorado", "CO");
		map.put("Connecticut", "CT");
		map.put("Delaware", "DE");
		map.put("Florida", "FL");
		map.put("Georgia", "GA");
		map.put("Hawaii", "HI");
		map.put("Idaho", "ID");
		map.put("Illinois", "IL");
		map.put("Indiana", "IN");
		map.put("Iowa", "IA");
		map.put("Kansas", "KS");
		map.put("Kentucky", "KY");
		map.put("Louisiana", "LA");
		map.put("Maine", "ME");
		map.put("Maryland", "MD");
		map.put("Massachusetts", "MA");
		map.put("Michigan", "MI");
		map.put("Minnesota", "MN");
		map.put("Mississippi", "MS");
		map.put("Missouri", "MO");
		map.put("Montana", "MT");
		map.put("Nebraska", "NE");
		map.put("Nevada", "NV");
		map.put("New Hampshire", "NH");
		map.put("New Jersey", "NJ");
		map.put("New Mexico", "NM");
		map.put("New York", "NY");
		map.put("North Carolina", "NC");
		map.put("North Dakota", "ND");
		map.put("Ohio", "OH");
		map.put("Oklahoma", "OK");
		map.put("Oregon", "OR");
		map.put("Pennsylvania", "PA");
		map.put("Rhode Island", "RI");
		map.put("South Carolina", "SC");
		map.put("South Dakota", "SD");
		map.put("Tennessee", "TN");
		map.put("Texas", "TX");
		map.put("Utah", "UT");
		map.put("Vermont", "VT");
		map.put("Virginia", "VA");
		map.put("Washington", "WA");
		map.put("West Virginia", "WV");
		map.put("Wisconsin", "WI");
		map.put("Wyoming", "WY");
		map.put("District of Columbia", "DC");
	}

	public String getCode(String longName) {
		return map.get(longName);
	}
}
