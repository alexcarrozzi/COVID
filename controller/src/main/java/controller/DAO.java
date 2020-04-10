package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import services.Prop;

public class DAO {
	private OkHttpClient client;
	private ArrayList<Response> myData;
	public static ArrayList<String> calendar;
	Prop prop;

	public DAO(String startDate) {
		prop = new Prop();
		client = new OkHttpClient();
		this.myData = this.pullAllDatesSince(startDate);
	}

	private ArrayList<Response> pullAllDatesSince(String date) {
		ArrayList<Response> allDates = new ArrayList<Response>();
		calendar = new ArrayList<String>();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		String s = date;
		String e = dateFormat.format(today);

		LocalDate start = LocalDate.parse(s);
		LocalDate end = LocalDate.parse(e);
		while (!start.isAfter(end.minusDays(1))) {
			calendar.add(start.toString());
			start = start.plusDays(1);
		}

		for (String curDate : DAO.calendar) {
			System.out.println("Pulling data for:" + curDate);
			Response day = this.pullData(curDate);
			this.sleep(1000);
			allDates.add(day);
			System.out.println("Done pulling data for:" + curDate);
		}
		System.out.println("DONE");
		return allDates;
	}

	public Response pullData(String date) {
		Response ret = null;

		String apiKey = prop.getPropValues("apiKey");
		Request request = new Request.Builder()
				.url("https://covid-19-statistics.p.rapidapi.com/reports?iso=USA&date=" + date).get()
				.addHeader("x-rapidapi-host", "covid-19-statistics.p.rapidapi.com").addHeader("x-rapidapi-key", apiKey)
				.addHeader("content-type", "application/json").build();
		try {
			ret = client.newCall(request).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public ArrayList<JSONObject> getJSONDatesAsList() {
		ArrayList<JSONObject> ret = new ArrayList<JSONObject>();

		for (Response iter : this.myData) {
			try {
				String body = iter.body().string();
				ret.add(new JSONObject(body));
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return ret;
	}

	private void sleep(int mili) {
		try {
			Thread.sleep(mili);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
