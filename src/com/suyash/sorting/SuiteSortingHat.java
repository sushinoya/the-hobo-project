package com.suyash.sorting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class SuiteSortingHat {
	String[] residents;
	String[][] preferences;
	TreeMap<String, Integer> roomPopularity = new TreeMap<String, Integer>();
	
	public SuiteSortingHat(String[] namesArray, String[][] preferenceArray) {
		residents = namesArray;
		preferences = preferenceArray;
		calculatePopularity();
	}
	
	//Assigns each room a number. The lower the number, the more popular is the room according to the given preferences
	public void calculatePopularity() {
		for (int i = 0; i < preferences.length; i++) {
			for (int j = 0; j < preferences[i].length; j++) {
				if (roomPopularity.keySet().contains(preferences[i][j])) {
					roomPopularity.put(preferences[i][j], roomPopularity.get(preferences[i][j]) + j);
				} else {
					roomPopularity.put(preferences[i][j], j);
				}
			}
		}
	}
	
	public void printPopularity() {
		String[] rooms = {"A", "B", "C", "D", "E", "F"};
		for (int i = 0; i < rooms.length; i++) {
			String room = rooms[i];
			Integer value = roomPopularity.get(room);
			System.out.println(room + " has " + value + " points.");
		}
	}
	
	public static int randomNumberBetween(int inclusiveStart, int exclusiveEnd) {
		int randomNum = ThreadLocalRandom.current().nextInt(inclusiveStart, exclusiveEnd + 1);
		return randomNum;
	}
	
	//Generates a random allocation of rooms
	public Arrangement randomAllocate() {
		ArrayList<String> names = new ArrayList<String>();
		String[] rooms = {"A", "B", "C", "D", "E", "F"};
		TreeMap<String, String> allocationsHash= new TreeMap<String, String>();
		for (int i = 0; i < residents.length; i++) {
			names.add(residents[i]);
		}
		Collections.shuffle(names);
		
		for (int i = 0; i < names.size(); i++) {
			allocationsHash.put(names.get(i), rooms[i]);
		}
		return new Arrangement(allocationsHash, preferences, residents);
	}
	
	//Allocates based on first come first serve.
	public Arrangement orderBiasedAllocate() {
		String[][] preferences = this.preferences;
		TreeMap<String, String> allocations = new TreeMap<String, String>();
		for (int i = 0; i < residents.length; i++) {
			String name = residents[i];
			String[] preference = preferences[i];
			for (int j = 0; j < preference.length; j++) {
				if (!allocations.values().contains(preference[j])) {
					allocations.put(name, preference[j]);
					break;
				}
			}
		}
		return new Arrangement(allocations, preferences, residents);
	}
	
	public static void main (String[] args) {
		
		//Test Data
		String[] namesArray = {"Suyash", "Zhenye", "Miguel", "Haozhe", "Shitian", "Alistair"};
		String[] suyash =   {"B", "D", "A", "C", "E", "F"};
		String[] zhenye =   {"C", "E", "F", "B", "D", "A"};
		String[] miguel =   {"B", "E", "F", "D", "A", "C"};
		String[] haozhe =   {"E", "F", "B", "D", "A", "C"};
		String[] shitian =  {"C", "B", "D", "A", "E", "F"};
		String[] alistair = {"A", "B", "F", "D", "E", "C"};

		String[][] preferenceArray = {suyash, zhenye, miguel, haozhe, shitian, alistair};
		
		SuiteSortingHat hobohat = new SuiteSortingHat(namesArray, preferenceArray);
		hobohat.printPopularity();
		
		//TESTS
//		Arrangement biasedAllocation = hobohat.orderBiasedAllocate();
//		System.out.println("The happiness score for the first-come-first-serve allocation is: " + biasedAllocation.happiness);
//		biasedAllocation.printAllocations();
		
		Arrangement randomAllocation = hobohat.randomAllocate();
		System.out.println("The happiness score for the first-come-first-serve allocation is: " + randomAllocation.happiness);
		randomAllocation.printAllocations();
	}
	
}
