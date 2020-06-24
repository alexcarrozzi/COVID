package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import services.Persister;

public class DataTransform {
	private DAO dao;
	private ArrayList<COVIDDate> allDatesData;
	public final static Logger logger = Logger.getLogger(DataTransform.class);
	private Persister per;

	public DataTransform(String sd) {
		dao = new DAO(sd);
		this.constructStructure();

		if (this.saveToDB()) {
			logger.info("Data written successfully for " + sd);
		} else {
			logger.error("DB write failed for " + sd);
		}
	}

	public DataTransform() throws ParseException {
		String sd = null, ld = null;

		per = new Persister();
		ld = per.getLastDate();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date ldDate = sdf.parse(ld);
		Calendar c = Calendar.getInstance();
		c.setTime(ldDate);
		c.add(Calendar.DAY_OF_MONTH, 1);
		sd = sdf.format(c.getTime());

		dao = new DAO(sd);
		this.constructStructure();

		if (this.saveToDB()) {
			logger.info("Data written successfully for " + sd);
		} else {
			logger.error("DB write failed for " + sd);
		}
	}

	private void constructStructure() {
		ArrayList<JSONObject> resp = dao.getJSONDatesAsList();
		this.allDatesData = new ArrayList<COVIDDate>();

		// Create Dates
		for (int i = 0; i < resp.size(); i++) {
			try {
				JSONArray curDate = ((JSONArray) resp.get(i).get("data"));
				COVIDDate curCOVIDDate = new COVIDDate();

				// create state
				for (int j = 0; j < curDate.length(); j++) {
					JSONObject curStateMaster = ((JSONObject) curDate.get(j));
					curCOVIDDate.setDate(curStateMaster.getString("date"));

					JSONObject curState = curDate.getJSONObject(j).getJSONObject("region");
					COVIDState curCOVIDState = new COVIDState();

					if (curState.isNull("province")) {
						curCOVIDState.setStateName("");
					} else {
						curCOVIDState.setStateName(curState.getString("province"));
					}

					if (curStateMaster.isNull("date")) {
						curCOVIDState.setDate("");
					} else {
						curCOVIDState.setDate(curStateMaster.getString("date"));
					}

					if (curStateMaster.isNull("confirmed")) {
						curCOVIDState.setConfirmed(0);
					} else {
						curCOVIDState.setConfirmed(curStateMaster.getInt("confirmed"));
					}

					if (curStateMaster.isNull("deaths")) {
						curCOVIDState.setDeaths(0);
					} else {
						curCOVIDState.setDeaths(curStateMaster.getInt("deaths"));
					}

					if (curStateMaster.isNull("recovered")) {
						curCOVIDState.setRecovered(0);
					} else {
						curCOVIDState.setRecovered(curStateMaster.getInt("recovered"));
					}

					if (curStateMaster.isNull("confirmed_diff")) {
						curCOVIDState.setConfDiff(0);
					} else {
						curCOVIDState.setConfDiff(curStateMaster.getInt("confirmed_diff"));
					}

					if (curStateMaster.isNull("deaths_diff")) {
						curCOVIDState.setDeathDiff(0);
					} else {
						curCOVIDState.setDeathDiff(curStateMaster.getInt("deaths_diff"));
					}

					if (curStateMaster.isNull("recovered_diff")) {
						curCOVIDState.setRecoveredDiff(0);
					} else {
						curCOVIDState.setRecoveredDiff(curStateMaster.getInt("recovered_diff"));
					}

					if (curStateMaster.isNull("last_update")) {
						curCOVIDState.setLastUpdate("");
					} else {
						curCOVIDState.setLastUpdate(curStateMaster.getString("last_update"));
					}

					if (curStateMaster.isNull("active")) {
						curCOVIDState.setActive(0);
					} else {
						curCOVIDState.setActive(curStateMaster.getInt("active"));
					}

					if (curStateMaster.isNull("active_diff")) {
						curCOVIDState.setFatalityRate((float) 0.00);
					} else {
						curCOVIDState.setActiveDiff(curStateMaster.getInt("active_diff"));
					}

					if (curStateMaster.isNull("fatality_rate")) {
						curCOVIDState.setFatalityRate((float) 0.00);
					} else {
						curCOVIDState.setFatalityRate(curStateMaster.getFloat("fatality_rate"));
					}

					if (curState.isNull("lat")) {
						curCOVIDState.setLat((long) 0.0);
					} else {
						curCOVIDState.setLat(curState.getLong("lat"));
					}

					if (curState.isNull("long")) {
						curCOVIDState.setLon((long) 0.0);
					} else {
						curCOVIDState.setLon(curState.getLong("long"));
					}

					// Create Cities
					for (int k = 0; k < curState.getJSONArray("cities").length(); k++) {
						JSONObject curCity = (JSONObject) curState.getJSONArray("cities").get(k);

						COVIDCity curCOVIDCity = new COVIDCity();

						if (curCity.isNull("name")) {
							curCOVIDCity.setCityName("");
						} else {
							curCOVIDCity.setCityName(curCity.getString("name"));
						}

						if (curCity.isNull("date")) {
							curCOVIDCity.setDate("");
						} else {
							curCOVIDCity.setDate(curCity.getString("date"));
						}

						if (curCity.isNull("fips")) {
							curCOVIDCity.setFips(0);
						} else {
							curCOVIDCity.setFips(curCity.getInt("fips"));
						}

						if (curCity.isNull("confirmed")) {
							curCOVIDCity.setConfirmed(0);
						} else {
							curCOVIDCity.setConfirmed(curCity.getInt("confirmed"));
						}

						if (curCity.isNull("deaths")) {
							curCOVIDCity.setDeaths(0);
						} else {
							curCOVIDCity.setDeaths(curCity.getInt("deaths"));
						}

						if (curCity.isNull("confirmed_diff")) {
							curCOVIDCity.setConfirmedDiff(0);
						} else {
							curCOVIDCity.setConfirmedDiff(curCity.getInt("confirmed_diff"));
						}

						if (curCity.isNull("deaths_diff")) {
							curCOVIDCity.setDeathsDiff(0);
						} else {
							curCOVIDCity.setDeathsDiff(curCity.getInt("deaths_diff"));
						}

						if (curCity.isNull("last_update")) {
							curCOVIDCity.setLastUpdate("");
						} else {
							curCOVIDCity.setLastUpdate(curCity.getString("last_update"));
						}

						curCOVIDState.addCity(curCOVIDCity);
					}
					curCOVIDDate.addState(curCOVIDState);

				}

				allDatesData.add(curCOVIDDate);
			} catch (Exception e) {
				logger.error("Error Parsing Data...");
				System.out.println("Error Parsing Data...");
				logger.error(e.getMessage());
				System.out.println(e.getMessage());
			}
		}
	}

	public boolean saveToDB() {
		per = new Persister();
		boolean success = per.saveArrayList(this.allDatesData);
		return success;
	}

	public static void main(String[] args) {
		if (args[0].equals("-fresh")) {
			DataTransform dt = new DataTransform("2020-01-01");
		} else if (args[0].equals("-update")) {
			try {
				DataTransform dt = new DataTransform();
			} catch (ParseException e) {
				System.out.println(e.getMessage());
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
