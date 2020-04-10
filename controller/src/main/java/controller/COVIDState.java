package controller;

import java.util.ArrayList;

public class COVIDState {
	private String stateName;
	private String date;
	private ArrayList<COVIDCity> cities;
	private Long lat;
	private Long lon;

	// state level data (assuming single date
	private int confirmed;
	private int deaths;
	private int recovered;
	private int confDiff;
	private int deathDiff;
	private int recoveredDiff;
	private String lastUpdate;
	private int active;
	private int activeDiff;
	private Float fatalityRate;

	public COVIDState() {
		cities = new ArrayList<COVIDCity>();
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public ArrayList<COVIDCity> getCities() {
		return cities;
	}

	public void setCities(ArrayList<COVIDCity> towns) {
		this.cities = towns;
	}

	public Long getLat() {
		return lat;
	}

	public void setLat(Long lat) {
		this.lat = lat;
	}

	public Long getLon() {
		return lon;
	}

	public void setLon(Long lon) {
		this.lon = lon;
	}

	public int getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getRecovered() {
		return recovered;
	}

	public void setRecovered(int recovered) {
		this.recovered = recovered;
	}

	public int getConfDiff() {
		return confDiff;
	}

	public void setConfDiff(int confDiff) {
		this.confDiff = confDiff;
	}

	public int getDeathDiff() {
		return deathDiff;
	}

	public void setDeathDiff(int deathDiff) {
		this.deathDiff = deathDiff;
	}

	public int getRecoveredDiff() {
		return recoveredDiff;
	}

	public void setRecoveredDiff(int recoveredDiff) {
		this.recoveredDiff = recoveredDiff;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getActiveDiff() {
		return activeDiff;
	}

	public void setActiveDiff(int activeDiff) {
		this.activeDiff = activeDiff;
	}

	public Float getFatalityRate() {
		return fatalityRate;
	}

	public void setFatalityRate(Float fatalityRate) {
		this.fatalityRate = fatalityRate;
	}

	public void addCity(COVIDCity town) {
		this.cities.add(town);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "COVIDState [stateName=" + stateName + ", date=" + date + ", cities=" + cities + ", lat=" + lat
				+ ", lon=" + lon + ", confirmed=" + confirmed + ", deaths=" + deaths + ", recovered=" + recovered
				+ ", confDiff=" + confDiff + ", deathDiff=" + deathDiff + ", recoveredDiff=" + recoveredDiff
				+ ", lastUpdate=" + lastUpdate + ", active=" + active + ", activeDiff=" + activeDiff + ", fatalityRate="
				+ fatalityRate + "]";
	}

}
