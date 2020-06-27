package com.alexcarrozzi.covid.api.COVID19API;

import java.util.ArrayList;
import java.util.List;

public class Service {
	private Dao dao;

	public Service() {
		this.dao = new Dao();
	}

	public ArrayList<TotalCases> getTotalCases() {
		return dao.getTotalCases();
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

	public List<StateGrowthOverTime> getStateGrowthOverTime() {
		return dao.getStateGrowthOverTime();
	}
}
