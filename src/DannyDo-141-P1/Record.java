/*
 * Danny Do
 * CS141
 * Project 1
 * 4/27/18
 */
public class Record implements Comparable<Record>{
	private int stationID;
	private int year;
	private int month;
	private double temperature;
	
	//constructor for station id, year, month, temperature
	public Record(int stationId, int year, int month, double temperature) {
		this.stationID = stationId;
		this.year = year;
		this.month = month;
		this.temperature = temperature;	
	}
	
	public int compareTo(Record other){
		return Integer.valueOf(year).compareTo(other.year);
	}
	 // getters and setters for data fields

	public int getStationID() {
		return stationID;
	}

	public void setStationID(int stationID) {
		this.stationID = stationID;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

}
