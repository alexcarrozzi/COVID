package controller;

import java.util.ArrayList;

public class COVIDDate {
	private String date;
	private ArrayList<COVIDState> states;

	public COVIDDate() {
		states = new ArrayList<COVIDState>();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public ArrayList<COVIDState> getStates() {
		return states;
	}

	public void setStates(ArrayList<COVIDState> states) {
		this.states = states;
	}

	public void addState(COVIDState state) {
		this.states.add(state);
	}

	@Override
	public String toString() {
		return "COVIDDate [date=" + date + ", states=" + states + "]";
	}

}
