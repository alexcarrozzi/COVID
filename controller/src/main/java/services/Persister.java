package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import controller.COVIDCity;
import controller.COVIDDate;
import controller.COVIDState;

public class Persister {
	private Prop prop = null;
	public final static Logger logger = Logger.getLogger(Persister.class);

	private String dbms = null;
	private String host = null;
	private String port = null;
	private String db = null;
	private String uname = null;
	private String pw = null;

	public Persister() {
		prop = new Prop();
		dbms = prop.getPropValues("db.dbms");
		host = prop.getPropValues("db.host");
		port = prop.getPropValues("db.port");
		db = prop.getPropValues("db.db");
		uname = prop.getPropValues("db.user");
		pw = prop.getPropValues("db.pw");

	}

	public boolean saveArrayList(ArrayList<COVIDDate> input) {
		boolean success = true;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + db, uname,
					pw);

			for (COVIDDate myDate : input) {

				for (COVIDState myState : myDate.getStates()) {
					// if (myState.getStateName().contains(",")) {
					// logger.info("Bad Data: " + myState.getDate().toString() + " - " +
					// myState.getStateName()
					// + " skipping...");
					// System.out.println("Bad Data: " + myState.getDate().toString() + " - " +
					// myState.getStateName()
					// + " skipping...");
					// } else {
					logger.info("Writing: " + myState.getDate().toString() + " - " + myState.getStateName());
					System.out.println("Writing: " + myState.getDate().toString() + " - " + myState.getStateName());
					insertState(conn, myState);
					// }
					for (COVIDCity myCounty : myState.getCities()) {
						logger.info("Writing: " + myCounty.getDate().toString() + " - " + myCounty.getCityName());
						System.out
								.println("Writing: " + myCounty.getDate().toString() + " - " + myCounty.getCityName());
						insertCounty(conn, myCounty, myState);
					}
				}
			}
			logger.info("Data successfully written to DB");
			System.out.println("Data successfully written to DB");
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while trying to write to DB");
			System.out.println("Error while trying to write to DB");
			logger.error(e.getMessage());
			success = false;
		}
		return success;
	}

	private void insertState(Connection conn, COVIDState state) throws SQLException {
		String query = " insert into STATE_DATE (DATA_DATE, STATE_NAME, LATITUDE, LONGITUDE, CONFIRMED, DEATHS, RECOVERED,"
				+ "CONFIRMED_DIFF, DEATH_DIFF, RECOVERED_DIFF, LAST_UPDATE, NUM_ACTIVE, ACTIVE_DIFF, FATALITY_RATE)"
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setString(1, state.getDate());
		preparedStmt.setString(2, state.getStateName());
		preparedStmt.setLong(3, state.getLat());
		preparedStmt.setLong(4, state.getLon());
		preparedStmt.setInt(5, state.getConfirmed());
		preparedStmt.setInt(6, state.getDeaths());
		preparedStmt.setInt(7, state.getRecovered());
		preparedStmt.setInt(8, state.getConfDiff());
		preparedStmt.setInt(9, state.getDeathDiff());
		preparedStmt.setInt(10, state.getRecoveredDiff());
		preparedStmt.setString(11, state.getLastUpdate());
		preparedStmt.setInt(12, state.getActive());
		preparedStmt.setInt(13, state.getActiveDiff());
		preparedStmt.setFloat(14, state.getFatalityRate());

		preparedStmt.execute();
	}

	private void insertCounty(Connection conn, COVIDCity county, COVIDState state) throws SQLException {
		String query = " insert into COUNTY_DATE (DATA_DATE, COUNTY_NAME, STATE_NAME, FIPS, CONFIRMED, DEATHS,"
				+ "CONFIRMED_DIFF, DEATHS_DIFF, LAST_UPDATE)" + " values (?,?,?,?,?,?,?,?,?)";

		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setString(1, county.getDate());
		preparedStmt.setString(2, county.getCityName());
		preparedStmt.setString(3, state.getStateName());
		preparedStmt.setInt(4, county.getFips());
		preparedStmt.setInt(5, county.getConfirmed());
		preparedStmt.setInt(6, county.getDeaths());
		preparedStmt.setInt(7, county.getConfirmedDiff());
		preparedStmt.setInt(8, county.getDeathsDiff());
		preparedStmt.setString(9, county.getLastUpdate());

		preparedStmt.execute();
	}

	public String getLastDate() {
		String ret = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + db, uname,
					pw);

			String query = "SELECT MAX(DATA_DATE) FROM STATE_DATE";

			PreparedStatement preparedStmt = conn.prepareStatement(query);

			ResultSet rs = preparedStmt.executeQuery();
			rs.first();
			ret = rs.getString(1);
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while accessing DB");
			System.out.println("Error while accessing DB");
			logger.error(e.getMessage());
		}
		return ret;
	}
}
