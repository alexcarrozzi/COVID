package com.alexcarrozzi.covid.api.COVID19API;

import java.util.ArrayList;
import java.util.List;

public class Service {
	private Dao dao;

	public Service() {
		this.dao = new Dao();
	}

	public ArrayList<TotalCases> getTotalCases(String state) {
		if (state == "" || state == null) {
			return dao.getTotalCases();
		}
		return dao.getTotalCases(state);
	}

	public ArrayList<TotalStateData> getTotalStateData() {
		return dao.getTotalStateData();
	}

	public List<GrowthAggregate> getGrowthAggregate() {
		return dao.getGrowthAggregate();
	}

	public List<LogLog> getLogLog() {
		return dao.getLogLog();
	}

	public List<GrowthByState> getGrowthByState() {
		return dao.getGrowthByState();
	}

	public List<StateGrowthOverTime> getStateGrowthOverTime(String state) {
		if (state == "" || state == null) {
			return dao.getStateGrowthOverTime();
		}
		return dao.getStateGrowthOverTime(state);
	}
}
