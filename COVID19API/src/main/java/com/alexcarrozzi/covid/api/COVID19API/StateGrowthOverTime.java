package com.alexcarrozzi.covid.api.COVID19API;

public class StateGrowthOverTime {
	private String weekB;
	private String stateName;
	private Float growth;

	public String getWeekB() {
		return weekB;
	}

	public void setWeekB(String weekB) {
		this.weekB = weekB;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Float getGrowth() {
		return growth;
	}

	public void setGrowth(Float growth) {
		this.growth = growth;
	}

}
