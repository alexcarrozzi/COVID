package com.alexcarrozzi.covid.api.COVID19API;

public class GrowthAggregate {
	private String dataDate;
	private int myWeek;
	private Float growth;

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	public int getMyWeek() {
		return myWeek;
	}

	public void setMyWeek(int myWeek) {
		this.myWeek = myWeek;
	}

	public Float getGrowth() {
		return growth;
	}

	public void setGrowth(Float growth) {
		this.growth = growth;
	}

}
