/*
 * Danny Do
 * CS141
 * Project 1
 * 4/27/18
 */
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
 
public class Driver {
	
	private static final int CURRENTYEAR = 2018;

	// Add method to read the temps file
	public static ArrayList<Record> readTemperatureFile(String fileName) throws IOException{
		//Creating a file out of fileName
		File file = new File(fileName);
		//Creating a scanner for file
		Scanner fileScanner = new Scanner(file);
		//Array list of Record to store temps info
		ArrayList<Record> returnArray = new ArrayList<Record>();
		
		//ends program if invalid file
		if(!file.exists())
		{
			System.out.println("File does not exist");
			System.exit(0);
			}
		//looks for if there is something to read, then read
		while(fileScanner.hasNext()) {
		//Stores the particular values in their correct variable names
		int stationId = fileScanner.nextInt();
		int year = fileScanner.nextInt();
		int month = fileScanner.nextInt();
		double temperature = fileScanner.nextDouble();

		//if data is valid, put them into array list returnArray
		if(temperature>= -50.0 && temperature <= 50.0 || temperature == -99.99) {
			
			if(year>=1800 && year<=CURRENTYEAR){
			
				Record r1 = new Record(stationId,year, month, temperature);
				returnArray.add(r1);	
			}
			else {
				System.out.println("Not correct range of years");
			}
		}else {
			System.out.println("Temperature values are not valid");
		}
		if (fileScanner.hasNextLine())
			fileScanner.nextLine();	
	}
		fileScanner.close();
		return returnArray;
	}
	// Add method to read the queries file
	public static ArrayList<String> readQueryFile(ArrayList<Record> recordArray, String fileName) throws IOException {
		//Create an array list to store queries info
		ArrayList<String> tempArray = new ArrayList<String>();
		//Making a file out of fileName
		File file = new File(fileName);
		//Scanner for file
		Scanner queriesScan = new Scanner(file);
		
		//Ends program if invalid file
		if(!file.exists()) {
			System.out.println("File does not exist");
			System.exit(0);
		}
		//scans query file while it has something to read
		while(queriesScan.hasNext())
		{
			//stores the particular data in their correct variable names
			int stationId = queriesScan.nextInt();
			String queryType = queriesScan.next();
			int minYear = queriesScan.nextInt();
			int maxYear = queriesScan.nextInt();
			//if query type is "mode", take the mode of the temperatures and prints "unknown" if ==0
				if (queryType.equalsIgnoreCase("mode")) {
					if(getMode(recordArray,stationId, minYear, maxYear) ==0) {
						tempArray.add(stationId + " " + minYear + " " + maxYear + " " + queryType + " " + "unknown");
						}
					else {
						tempArray.add(stationId + " " + minYear + " " + maxYear + " " + queryType + " " + getMode(recordArray, stationId, minYear, maxYear));
					}
				}
				//if query type is "avg", take average of the temperatures and prints "unknown" if ==0
				else if(queryType.equalsIgnoreCase("avg")) {
					if(getAverage(recordArray, stationId, minYear, maxYear) == 0.0) {
						tempArray.add(stationId + " " + minYear + " " + maxYear + " " + queryType+ " " + "unknown");
					}
					else {
						tempArray.add(stationId + " " + minYear + " " + maxYear + " " + queryType+ " " + getAverage(recordArray, stationId, minYear, maxYear));
					}
					
				}
			
				if (queriesScan.hasNextLine())
				queriesScan.nextLine();
			}
		queriesScan.close();
		return tempArray;
		}

	
	//method for getting average
	public static double getAverage(ArrayList<Record> recordArray, int stationId, int minYear, int maxYear) {
		double total = 0;
		int counter = 0;
		ArrayList<Record> filtArray = getFilteredArray(recordArray, stationId, minYear, maxYear);
		//goes through the filtered array
		for(Record r: filtArray) {
			Record r2 = r;
			total+= r2.getTemperature();
			counter ++;
		}
		double avg = Math.round(100.0 *(total/counter)) / 100.0;
		return avg;
	}
	
	//method to get mode for readQueries method
	public static int getMode(ArrayList<Record> recordArray, int stationId, int yearLow, int yearHigh) {
		int mode = 	0;
		int counter = 0;
		//creates ArrayList with the correct temperatures for the year range and station id
		ArrayList<Record> correctArray = getFilteredArray(recordArray, stationId, yearLow, yearHigh);
		//goes through correctArray and sets temp mode to value at indexes in array list
		for(Record r: correctArray) {
			int tempMode = (int)Math.round(r.getTemperature());
			int counter2 = 0;
			//checks for freq of values in correctArray
			for(Record r2: correctArray) {
				if(Math.round(r2.getTemperature())==tempMode) {
					counter2++;
				}
			}
			//checks if the freq matches of counter2 is greater than counter
			if(counter2> counter) {
				mode = tempMode;
				counter =counter2;
			}
		}
		return mode;
	}
	
	//method that returns array list with only the correct data for the given year range and staionId
	public static ArrayList<Record>getFilteredArray(ArrayList<Record>recordArray, int stationId, int yearLow, int yearHigh){
		//creating new object of array list of records for the filtered array list 
		ArrayList<Record> filtArray = new ArrayList<Record>();
		//checks if id matches and range of years is correct and adds values to the filtered array
		for(Record r: recordArray) {
			if(r.getStationID() == stationId){
				if(r.getYear() >= yearLow && r.getYear() <=yearHigh) {
					filtArray.add(r);
				}
			}
		}
		return filtArray;
	}
	
	// Add method to write to a results.dat file 
	public static void writeResults(ArrayList<String>arraylist1) throws IOException {
		//print writer to write to a file named results.dat
		PrintWriter pw = new PrintWriter("results.dat");
		//goes through the array list and stores the values in String s and then prints out s
		for(String s :arraylist1) {
			pw.println(s);
		}
		pw.close();
	}
	
	public static void sortRecordsList(ArrayList<Record> recordArray){
		Collections.sort(recordArray,new Comparator<Record>() {
				public int compare(Record one, Record other){
					return Integer.valueOf(one.getStationID()).compareTo(other.getStationID());
				}
		});
		
		Collections.sort(recordArray,new Comparator<Record>() {
				public int compare(Record one, Record other){
					return Integer.valueOf(one.getYear()).compareTo(other.getYear());
				}
		});	
			
	}
	//main method
	public static void main(String[] args) throws IOException{
		ArrayList<Record> recordList = readTemperatureFile("src/temps-2000s.dat");
		 sortRecordsList(recordList);
		 writeResults(readQueryFile(recordList,"src/queries-2000s.dat"));
		 
	}

}
