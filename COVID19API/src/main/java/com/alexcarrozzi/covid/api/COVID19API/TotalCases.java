package com.alexcarrozzi.covid.api.COVID19API;

public class TotalCases {

	private String dataDate;

	private int numNewCases;

	public TotalCases() {
		super();

	}

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	public int getNumNewCases() {
		return numNewCases;
	}

	public void setNumNewCases(int numNewCases) {
		this.numNewCases = numNewCases;
	}

	@Override
	public String toString() {
		return "TotalCases [dataDate=" + dataDate + ", numNewCases=" + numNewCases + "]";
	}

}
