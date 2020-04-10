package controller;

public class COVIDCity {
	private String cityName;
	private String date;
	private int fips;
	private int confirmed;
	private int deaths;
	private int confirmedDiff;
	private int deathsDiff;
	private String lastUpdate;

	public COVIDCity() {

	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getFips() {
		return fips;
	}

	public void setFips(int fips) {
		this.fips = fips;
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

	public int getConfirmedDiff() {
		return confirmedDiff;
	}

	public void setConfirmedDiff(int confirmedDiff) {
		this.confirmedDiff = confirmedDiff;
	}

	public int getDeathsDiff() {
		return deathsDiff;
	}

	public void setDeathsDiff(int deathsDiff) {
		this.deathsDiff = deathsDiff;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return "COVIDCity [cityName=" + cityName + ", date=" + date + ", fips=" + fips + ", confirmed=" + confirmed
				+ ", deaths=" + deaths + ", confirmedDiff=" + confirmedDiff + ", deathsDiff=" + deathsDiff
				+ ", lastUpdate=" + lastUpdate + "]";
	}

}
