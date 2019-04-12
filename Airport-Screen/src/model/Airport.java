package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import model.Flight;

public class Airport {


	private static final String AIRLINE_PATH = "documents/airlines.txt";
	private static final String CITIES_PATH = "documents/cities.txt";
	public enum SortingsTypes {
		TIME,DATE, AIRLINE,FLIGHT_NUMBER, DESTINATION, GATE;
	}

	private SortingsTypes currentSortType;

	private ArrayList<Flight> flights; 

	public Airport() {

		flights = new ArrayList<Flight>();
		currentSortType = SortingsTypes.TIME;
	}


	public void generateFlights(int flightsNumber) throws IOException{
		flights.clear();
		int i = 0;
		while(i<flightsNumber) {
			LocalDate date = generateDate();
			String hour = generateHour();
			String airlines = generateAirline();
			int fn = generateFlightNumber(flightsNumber);
			verifyFlightNumber(flightsNumber);
			String destination= generateDestination();
			String gate= generateGate();
			Flight f = new Flight(date, hour, airlines, fn, destination, gate);
			flights.add(f);
			i++;
		}
		setSortingsTypes(SortingsTypes.TIME);
		sort();

	}

	public LocalDate generateDate() {

		long minDay = LocalDate.of(2018, 1, 1).toEpochDay();
		long maxDay = LocalDate.of(2021, 12, 31).toEpochDay();
		long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
		LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
		return randomDate;
	}

	public String generateHour() {
		String msg = "";
		LocalTime medium = LocalTime.of(12,0);
		Random random = new Random();
		for (int i = 0; i < 25; i++) {
			LocalTime time = LocalTime.of(random.nextInt(23), random.nextInt(23));
			if(time.compareTo(medium)>0) {
				time = time.minus(12, ChronoUnit.HOURS);
				msg = time + "PM";
			}else {
				msg = time + "AM";
			}
		}
		return msg;
	}


	public String getAirlines(int airlineNumber) throws IOException {
		File f = new File(AIRLINE_PATH);
		BufferedReader br = new BufferedReader(new FileReader(f));
		int i = 0;
		String msg = br.readLine();
		while(i < airlineNumber) {
			msg = br.readLine();
			i++;
		}
		br.close();
		return msg;
	}

	public String generateAirline() throws IOException {
		Random rnd = new Random();
		String airline = getAirlines(rnd.nextInt(100));
		return airline;
	}

	public int generateFlightNumber(int flightsNumber) {
		Random rnd = new Random();
		int flightNumber = (int)(rnd.nextInt(flightsNumber));
		//verifyFlightNumber(flightsNumber);
		return flightNumber;
	}

	public void verifyFlightNumber(int flightsNumber) {
		for(int i=0;i<flights.size();i++) {
			int id1 = flights.get(i).getFlightNumber();
			for(int j=i+1;j<flights.size();j++) {
				int id2 = flights.get(j).getFlightNumber();
				if(id1 == id2) {
					flights.get(j).setFlightNumber(generateFlightNumber(flightsNumber));
				}
			}
		}
	}

	public String getDestination(int destinationNumber) throws IOException {
		File f = new File(CITIES_PATH);
		BufferedReader br = new BufferedReader(new FileReader(f));
		int i = 0;
		String msg = br.readLine();
		while(i < destinationNumber) {
			msg = br.readLine();
			i++;
		}
		br.close();
		return msg;
	}
	public String generateDestination() throws IOException {
		Random rnd = new Random();
		String airline = getDestination(rnd.nextInt(100));
		return airline;
	} 

	public String generateGate() {
		int gateNumber=(int)(Math.random()*10-1);
		gateNumber++;

		String gate=""+gateNumber;

		return gate;
	}

	public void setSortingsTypes(SortingsTypes st) {
		currentSortType = st;
	}

	public ArrayList<Flight> getFlights() {
		return flights;
	}

	public void sort() {
		switch(currentSortType) {
		case TIME:
			sortTime();
			break;
		case DATE:
			sortDate();
		case AIRLINE:
			sortAirline();
			break;
		case FLIGHT_NUMBER:
			sortFN();
			break;
		case DESTINATION:
			sortDestination();
			break;
		case GATE:
			sortGates();
			break;

		}
	}

	public void sortTime() {
		int length = flights.size();
		for (int i = 0; i < length-1; i++) {
			for (int j = 0; j < length-i-1 ; j++) {
				Flight current = flights.get(j);
				Flight next = flights.get(j+1);
				if(current.compareTo(next) > 0) {
					Flight temp = flights.get(j);
					flights.set(j, next);
					flights.set(j+1, temp);
				}
			}
		}

	}

	public void sortDate() {

	}
	public void sortAirline() {
		int size = flights.size();
		for (int i = 0; i<size; i++) {
			Flight toInsert = flights.get(i);
			boolean ended = false;
			for(int j = i; j>0 && !ended; j--) {
				Flight current = flights.get(j-1);
				if(current.compareToAirline(toInsert) > 0) {
					flights.set(j, current);
					flights.set(j-1, toInsert);
				}else {
					ended = true;
				}
			}
		}
	}

	public void sortDestination() {
		int length = flights.size();
		for (int i = 0; i < length-1; i++) {
			int min = i;
			for (int j = i+1; j < length; j++) {
				Flight minimum = flights.get(min);
				Flight current = flights.get(j);
				if(minimum.compareToDestination(current)>0) {
					min = j;
				}
			}
			Flight temp = flights.get(i);
			flights.set(i, flights.get(min));
			flights.set(min, temp);
		}
	}
	public void sortFN() {
		int length = flights.size();
		for (int i = 0; i < length-1; i++) {
			int min = i;
			for (int j = i+1; j < length; j++) {
				Flight minimum = flights.get(min);
				Flight current = flights.get(j);
				if(minimum.compareToFN(current)>0) {
					min = j;
				}
			}
			Flight temp = flights.get(i);
			flights.set(i, flights.get(min));
			flights.set(min, temp);
		}
	}

	public void sortGates() {
		flights.sort(new Comparator<Flight>() {
			public int compare(Flight o1, Flight o2) {
				return o1.compareToGate(o2);
			}

		});

	}

}
