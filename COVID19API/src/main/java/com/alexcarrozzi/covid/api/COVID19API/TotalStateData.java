package com.alexcarrozzi.covid.api.COVID19API;

public class TotalStateData {
	private String dataDate;
	private String stateName;
	private Long latitude;
	private Long longitude;
	private int usconfirmed;

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Long getLatitude() {
		return latitude;
	}

	public void setLatitude(Long latitude) {
		this.latitude = latitude;
	}

	public Long getLongitude() {
		return longitude;
	}

	public void setLongitude(Long longitude) {
		this.longitude = longitude;
	}

	public int getUsconfirmed() {
		return usconfirmed;
	}

	public void setUsconfirmed(int usconfirmed) {
		this.usconfirmed = usconfirmed;
	}

}
