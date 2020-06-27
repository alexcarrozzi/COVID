package com.alexcarrozzi.covid.api.COVID19API;

public class GrowthByState {
	private String rangeAStart;
	private String rangeAEnd;
	private String rangeBStart;
	private String rangeBEnd;
	private int rangeANewCases;
	private int rangeBNewCases;
	private float growth;
	private String stateName;

	public String getRangeAStart() {
		return rangeAStart;
	}

	public void setRangeAStart(String rangeAStart) {
		this.rangeAStart = rangeAStart;
	}

	public String getRangeAEnd() {
		return rangeAEnd;
	}

	public void setRangeAEnd(String rangeAEnd) {
		this.rangeAEnd = rangeAEnd;
	}

	public String getRangeBStart() {
		return rangeBStart;
	}

	public void setRangeBStart(String rangeBStart) {
		this.rangeBStart = rangeBStart;
	}

	public String getRangeBEnd() {
		return rangeBEnd;
	}

	public void setRangeBEnd(String rangeBEnd) {
		this.rangeBEnd = rangeBEnd;
	}

	public int getRangeANewCases() {
		return rangeANewCases;
	}

	public void setRangeANewCases(int rangeANewCases) {
		this.rangeANewCases = rangeANewCases;
	}

	public int getRangeBNewCases() {
		return rangeBNewCases;
	}

	public void setRangeBNewCases(int rangeBNewCases) {
		this.rangeBNewCases = rangeBNewCases;
	}

	public float getGrowth() {
		return growth;
	}

	public void setGrowth(float growth) {
		this.growth = growth;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

}
