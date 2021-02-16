package services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.exxeleron.qjava.QBasicConnection;
import com.exxeleron.qjava.QException;

import controller.COVIDState;

public class KDBPersister {
	private Prop prop = null;
	private StateCodesDict stDict = null;
	// public final static Logger logger = Logger.getLogger(Persister.class);

	private String khost = null;
	private int kport;

	private String dbms = null;
	private String host = null;
	private String port = null;
	private String db = null;
	private String uname = null;
	private String pw = null;

	private QBasicConnection conn = null;

	public KDBPersister() {
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		stDict = new StateCodesDict();
		prop = new Prop();
		khost = prop.getPropValues("kdb.host");
		kport = (int) Integer.parseInt(prop.getPropValues("kdb.port"));

		try {
			System.out.println("Trying KDB connection on: " + this.khost + ":" + this.kport);
			conn = new QBasicConnection(khost, kport, "", "");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
		}

		dbms = prop.getPropValues("db.dbms");
		host = prop.getPropValues("db.host");
		port = prop.getPropValues("db.port");
		db = prop.getPropValues("db.db");
		uname = prop.getPropValues("db.user");
		pw = prop.getPropValues("db.pw");
	}

	public boolean insertStateData() throws SQLException, ClassNotFoundException {
		Object kdbObj = null;
		boolean ret = false;
		ResultSet rs = null;

		try {
			conn.open();
			System.out.println("Connected!");

			// Grab all records from RDS

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connRDS = DriverManager.getConnection("jdbc:" + dbms + "://" + host + ":" + port + "/" + db,
					uname, pw);

			String queryR = "SELECT DATA_DATE, STATE_NAME, CONFIRMED, CONFIRMED_DIFF FROM STATE_DATE";
			PreparedStatement preparedStmt = connRDS.prepareStatement(queryR);
			rs = preparedStmt.executeQuery();

			// create table
			kdbObj = conn
					.sync("covidData:flip (`data_date`state`confirmed`confirmed_diff)!(`date$();`$();`int$();`int$())");

			String stateCode = "";
			while (rs.next()) {
				COVIDState myState = new COVIDState();
				myState.setDate(rs.getString(1));
				myState.setStateName(rs.getString(2));
				myState.setConfirmed(rs.getInt(3));
				myState.setConfDiff(rs.getInt(4));

				stateCode = stDict.getCode(myState.getStateName());
				String kdbDate = "0001.01.01";
				try {
					kdbDate = new SimpleDateFormat("yyyy.mm.dd")
							.format(new SimpleDateFormat("yyyy-mm-dd").parse(myState.getDate()));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				if (stateCode != null) {
					String queryK = "upsert[`covidData; (`date$" + kdbDate + ";`" + stateCode + ";"
							+ myState.getConfirmed() + ";" + myState.getConfDiff() + ")]";
					System.out.println(queryK);
					kdbObj = conn.sync(queryK);
				}
			}
			System.out.println("Finished writing");
			ret = true;

			connRDS.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (QException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				System.out.println("Connection closed");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
}