package model;

import java.time.LocalDate;

public class Flight implements Comparable<Flight>{

	private LocalDate date;
	private String hour;
	private String airline;
	private int flightNumber;
	private String destination;
	private String gate;


	public Flight(LocalDate date, String hour, String airline, int flightNumber, String destination,
			String gate) {
		this.date = date;
		this.hour = hour;
		this.airline = airline;
		this.flightNumber = flightNumber;
		this.destination = destination;
		this.gate = gate;
	}



	public String toString() {
		return "a";
	}
	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}




	public String getHour() {
		return hour;
	}



	public void setHour(String hour) {
		this.hour = hour;
	}



	public String getAirline() {
		return airline;
	}


	public void setAirline(String airline) {
		this.airline = airline;
	}


	public int getFlightNumber() {
		return flightNumber;
	}



	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
	}



	public String getDestination() {
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}


	public String getGate() {
		return gate;
	}


	public void setGate(String gate) {
		this.gate = gate;
	}


	@Override
	public int compareTo(Flight next) {
		return date.compareTo(next.getDate());
	}


}
