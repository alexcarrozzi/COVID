package com.alexcarrozzi.covid.api.COVID19API;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Dao {
	private Prop prop = null;

	private String dbms = null;
	private String host = null;
	private String port = null;
	private String db = null;
	private String uname = null;
	private String pw = null;
	private StateCodesDict stCode = null;

	public Dao() {
		prop = new Prop();
		dbms = prop.getPropValues("db.dbms");
		host = prop.getPropValues("db.host");
		port = prop.getPropValues("db.port");
		db = prop.getPropValues("db.db");
		uname = prop.getPropValues("db.user");
		pw = prop.getPropValues("db.pw");
		stCode = new StateCodesDict();
	}

	public ArrayList<TotalCases> getTotalCases() {
		ArrayList<TotalCases> ret = new ArrayList<TotalCases>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + db, uname,
					pw);

			String query = "SELECT DATA_DATE, SUM(CONFIRMED_DIFF) AS DIFF " + "FROM STATE_DATE "
					+ "WHERE DATA_DATE NOT LIKE '2020-02-21' " + "GROUP BY DATA_DATE " + "ORDER BY DATA_DATE;";

			PreparedStatement preparedStmt = conn.prepareStatement(query);

			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				TotalCases cse = new TotalCases();
				cse.setDataDate(rs.getString(1));
				cse.setNumNewCases(rs.getInt(2));
				ret.add(cse);
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error while accessing DB");
		}
		return ret;
	}

	public ArrayList<TotalCases> getTotalCases(String state) {
		ArrayList<TotalCases> ret = new ArrayList<TotalCases>();
		String code = stCode.getCode(state);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + db, uname,
					pw);

			String query = "SELECT DATA_DATE, SUM(CONFIRMED_DIFF) AS DIFF " + "FROM STATE_DATE "
					+ "WHERE DATA_DATE NOT LIKE '2020-02-21'" + " AND STATE_NAME = ?" + "GROUP BY DATA_DATE "
					+ "ORDER BY DATA_DATE;";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, code);

			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				TotalCases cse = new TotalCases();
				cse.setDataDate(rs.getString(1));
				cse.setNumNewCases(rs.getInt(2));
				ret.add(cse);
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error while accessing DB");
		}
		return ret;
	}

	public ArrayList<TotalStateData> getTotalStateData() {
		ArrayList<TotalStateData> ret = new ArrayList<TotalStateData>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + db, uname,
					pw);

			String query = "SELECT DATA_DATE, STATE_NAME, LATITUDE, LONGITUDE, SUM(CONFIRMED) AS US_CONFIRMED FROM STATE_DATE "
					+ "GROUP BY DATA_DATE, STATE_NAME, LATITUDE, LONGITUDE " + "ORDER BY DATA_DATE ASC";

			PreparedStatement preparedStmt = conn.prepareStatement(query);

			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				TotalStateData cse = new TotalStateData();
				cse.setDataDate(rs.getString(1));
				cse.setStateName(rs.getString(2));
				cse.setLatitude(rs.getLong(3));
				cse.setLongitude(rs.getLong(4));
				cse.setUsconfirmed(rs.getInt(5));
				ret.add(cse);
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error while accessing DB");
		}
		return ret;
	}

	public ArrayList<GrowthAggregate> getGrowthAggregate() {
		ArrayList<GrowthAggregate> ret = new ArrayList<GrowthAggregate>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + db, uname,
					pw);

			String query = "SELECT t.DATA_DATE, t.myweek, COALESCE(t.CONF_DIFF/tprev.CONF_DIFF, 0) AS GROWTH "
					+ "FROM ( "
					+ "	SELECT SUBDATE(DATA_DATE, WEEKDAY(DATA_DATE)) AS DATA_DATE, WEEK(DATA_DATE) AS myweek, AVG(CONFIRMED_DIFF) AS CONF_DIFF "
					+ "	FROM STATE_DATE " + "	GROUP BY SUBDATE(DATA_DATE, WEEKDAY(DATA_DATE)) " + ") t LEFT JOIN ( "
					+ "	SELECT SUBDATE(DATA_DATE, WEEKDAY(DATA_DATE)) AS DATA_DATE, WEEK(DATA_DATE) AS myweek, AVG(CONFIRMED_DIFF) AS CONF_DIFF "
					+ "	FROM STATE_DATE " + "	GROUP BY SUBDATE(DATA_DATE, WEEKDAY(DATA_DATE)) "
					+ ") tprev ON tprev.myweek = (t.myweek - 1) "
					+ "WHERE COALESCE(t.CONF_DIFF/tprev.CONF_DIFF, 0) > 0 " + "ORDER BY t.myweek";

			PreparedStatement preparedStmt = conn.prepareStatement(query);

			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				GrowthAggregate cse = new GrowthAggregate();
				cse.setDataDate(rs.getString(1));
				cse.setMyWeek(rs.getInt(2));
				cse.setGrowth(rs.getFloat(3));
				ret.add(cse);
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error while accessing DB");
		}
		return ret;
	}

	public List<LogLog> getLogLog() {
		ArrayList<LogLog> ret = new ArrayList<LogLog>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + db, uname,
					pw);

			String query = "SELECT DATA_DATE, SUM(CONFIRMED_DIFF) AS NEW_CASE, SUM(CONFIRMED) AS TOTAL_CASE, SUM(DEATH_DIFF) AS NEW_DEATH, SUM(DEATHS) AS TOTAL_DEATH FROM STATE_DATE "
					+ "GROUP BY DATA_DATE " + "ORDER BY DATA_DATE;";

			PreparedStatement preparedStmt = conn.prepareStatement(query);

			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				LogLog cse = new LogLog();
				cse.setDataData(rs.getString(1));
				cse.setNewCases(rs.getInt(2));
				cse.setTotalCases(rs.getInt(3));
				cse.setNewDeaths(rs.getInt(4));
				cse.setTotalDeaths(rs.getInt(5));
				ret.add(cse);
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error while accessing DB");
		}
		return ret;
	}

	public List<GrowthByState> getGrowthByState() {
		ArrayList<GrowthByState> ret = new ArrayList<GrowthByState>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + db, uname,
					pw);

			String query = "SELECT tprev.SDATE AS RANGE_A_START, tprev.EDATE AS RANGE_A_END, t.SDATE AS RANGE_B_START, t.EDATE AS RANGE_B_END, tprev.CONF_DIFF AS RANGE_A_NEWCASES, t.CONF_DIFF AS RANGE_B_NEWCASES, COALESCE(t.CONF_DIFF/tprev.CONF_DIFF, 0) AS GROWTH, t.STATE_NAME, 1 AS const\r\n"
					+ "FROM (\r\n"
					+ "	SELECT DATE(SUBDATE(SYSDATE(),7)) AS SDATE, DATE(SUBDATE(SYSDATE(),1)) AS EDATE, SUM(CONFIRMED_DIFF) AS CONF_DIFF, STATE_NAME\r\n"
					+ "	FROM STATE_DATE\r\n" + "	WHERE DATE(DATA_DATE) >= DATE(SUBDATE(SYSDATE(),7))\r\n"
					+ "	GROUP BY STATE_NAME\r\n" + ") t LEFT JOIN (\r\n"
					+ "	SELECT DATE(SUBDATE(SYSDATE(),14)) AS SDATE, DATE(SUBDATE(SYSDATE(),7)) AS EDATE, SUM(CONFIRMED_DIFF) AS CONF_DIFF, STATE_NAME\r\n"
					+ "	FROM STATE_DATE\r\n" + "	WHERE DATE(DATA_DATE) < DATE(SUBDATE(SYSDATE(),7))\r\n"
					+ "    AND DATE(DATA_DATE) >=  DATE(SUBDATE(SYSDATE(),14))\r\n" + "	GROUP BY STATE_NAME\r\n"
					+ ") tprev ON tprev.STATE_NAME = t.STATE_NAME\r\n"
					+ "WHERE COALESCE(t.CONF_DIFF/tprev.CONF_DIFF, 0) > 0\r\n"
					+ "AND t.STATE_NAME NOT IN ('Guam', 'Virgin Islands', 'District of Columbia')\r\n"
					+ "ORDER BY GROWTH DESC";

			PreparedStatement preparedStmt = conn.prepareStatement(query);

			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				GrowthByState cse = new GrowthByState();
				cse.setRangeAStart(rs.getString(1));
				cse.setRangeAEnd(rs.getString(2));
				cse.setRangeBStart(rs.getString(3));
				cse.setRangeBEnd(rs.getString(4));
				cse.setRangeANewCases(rs.getInt(5));
				cse.setRangeBNewCases(rs.getInt(6));
				cse.setGrowth(rs.getFloat(7));
				cse.setStateName(rs.getString(8));
				ret.add(cse);
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error while accessing DB");
		}
		return ret;
	}

	public List<StateGrowthOverTime> getStateGrowthOverTime() {
		ArrayList<StateGrowthOverTime> ret = new ArrayList<StateGrowthOverTime>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + db, uname,
					pw);

			String query = "SELECT t.DATA_DATE AS WEEK_B, t.STATE_NAME, COALESCE(t.CONF_DIFF/tprev.CONF_DIFF, 0) AS GROWTH\r\n"
					+ "FROM (\r\n"
					+ "	SELECT SUBDATE(DATA_DATE, WEEKDAY(DATA_DATE)) AS DATA_DATE, STATE_NAME, WEEK(DATA_DATE) AS myweek, SUM(CONFIRMED_DIFF) AS CONF_DIFF\r\n"
					+ "	FROM STATE_DATE\r\n" + "	GROUP BY SUBDATE(DATA_DATE, WEEKDAY(DATA_DATE)), STATE_NAME\r\n"
					+ ") t LEFT JOIN (\r\n"
					+ "	SELECT SUBDATE(DATA_DATE, WEEKDAY(DATA_DATE)) AS DATA_DATE, STATE_NAME, WEEK(DATA_DATE) AS myweek, SUM(CONFIRMED_DIFF) AS CONF_DIFF\r\n"
					+ "	FROM STATE_DATE\r\n" + "	GROUP BY SUBDATE(DATA_DATE, WEEKDAY(DATA_DATE)), STATE_NAME\r\n"
					+ ") tprev ON tprev.STATE_NAME = t.STATE_NAME AND tprev.myweek = (t.myweek - 1)\r\n"
					+ "WHERE COALESCE(t.CONF_DIFF/tprev.CONF_DIFF, 0) > 0\r\n"
					+ "AND t.STATE_NAME NOT IN ('Guam', 'Virgin Islands', 'District of Columbia')\r\n"
					+ "AND t.STATE_NAME NOT LIKE '%,%'\r\n" + "AND t.STATE_NAME NOT LIKE '%Princess%'\r\n"
					+ "ORDER BY STATE_NAME, WEEK_B;";

			PreparedStatement preparedStmt = conn.prepareStatement(query);

			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				StateGrowthOverTime cse = new StateGrowthOverTime();
				cse.setWeekB(rs.getString(1));
				cse.setStateName(rs.getString(2));
				cse.setGrowth(rs.getFloat(3));
				ret.add(cse);
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error while accessing DB");
		}
		return ret;
	}

	public List<StateGrowthOverTime> getStateGrowthOverTime(String state) {
		ArrayList<StateGrowthOverTime> ret = new ArrayList<StateGrowthOverTime>();
		String code = stCode.getCode(state);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + db, uname,
					pw);

			String query = "SELECT t.DATA_DATE AS WEEK_B, t.STATE_NAME, COALESCE(t.CONF_DIFF/tprev.CONF_DIFF, 0) AS GROWTH\r\n"
					+ "FROM (\r\n"
					+ "	SELECT SUBDATE(DATA_DATE, WEEKDAY(DATA_DATE)) AS DATA_DATE, STATE_NAME, WEEK(DATA_DATE) AS myweek, SUM(CONFIRMED_DIFF) AS CONF_DIFF\r\n"
					+ "	FROM STATE_DATE\r\n WHERE STATE_NAME = ?"
					+ "	GROUP BY SUBDATE(DATA_DATE, WEEKDAY(DATA_DATE))\r\n" + ") t LEFT JOIN (\r\n"
					+ "	SELECT SUBDATE(DATA_DATE, WEEKDAY(DATA_DATE)) AS DATA_DATE, STATE_NAME, WEEK(DATA_DATE) AS myweek, SUM(CONFIRMED_DIFF) AS CONF_DIFF\r\n"
					+ "	FROM STATE_DATE\r\n WHERE STATE_NAME = ?"
					+ "	GROUP BY SUBDATE(DATA_DATE, WEEKDAY(DATA_DATE))\r\n"
					+ ") tprev ON tprev.STATE_NAME = t.STATE_NAME AND tprev.myweek = (t.myweek - 1)\r\n"
					+ "WHERE COALESCE(t.CONF_DIFF/tprev.CONF_DIFF, 0) > 0\r\n" + "\r\n"
					+ "ORDER BY STATE_NAME, WEEK_B;";

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, code);
			preparedStmt.setString(2, code);

			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				StateGrowthOverTime cse = new StateGrowthOverTime();
				cse.setWeekB(rs.getString(1));
				cse.setStateName(rs.getString(2));
				cse.setGrowth(rs.getFloat(3));
				ret.add(cse);
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error while accessing DB");
		}
		return ret;
	}

	public List<StateGrowthOverTime> getStateGrowthOverTime(Date date) {
		// TODO Auto-generated method stub
		return null;
	}
}
