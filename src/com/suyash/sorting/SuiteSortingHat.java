package com.suyash.sorting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.Set;
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
	
	public Arrangement allocateRooms(String[][] preferences) {
		return null;
	}
	
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
	
	public class Arrangement {
		int happiness;
		TreeMap<String, String> arrangement; 
		String[][] preferences;
		String[] names;
		
		
		public Arrangement(TreeMap<String, String> arrangement, String[][] preferences, String[] names) {
			this.arrangement = arrangement;
			this.preferences = preferences;
			this.names = names;
			this.happiness = calculateHappinessIndex();
		}
		
		public void printAllocations() {
			Set<String> keySet = arrangement.keySet();
			String[] keys = new String[6];
			keySet.toArray(keys);
			for (int i = 0; i < arrangement.size(); i++) {
				String key = keys[i];
				String value = arrangement.get(key);
				System.out.println(key + " is assigned room " + value);
			}
		}
		
		public int calculateHappinessIndex() {
			int happiness = 0;
			for(int i = 0; i < names.length; i++) {
				String name = names[i];
				String roomAllocated = arrangement.get(name);
				String[] preference = preferences[i];
				
				for(int j = 0; j < preference.length; j++) {
					if (roomAllocated.equals(preference[j])) {
						happiness += j;
					}
				}
			}
			return happiness;
		}
	}
	
	public static void main (String[] args) {
		
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
//		Arrangement biasedAllocation = hobohat.orderBiasedAllocate();
//		System.out.println("The happiness score for the first-come-first-serve allocation is: " + biasedAllocation.happiness);
//		biasedAllocation.printAllocations();
		
		Arrangement randomAllocation = hobohat.randomAllocate();
		System.out.println("The happiness score for the first-come-first-serve allocation is: " + randomAllocation.happiness);
		randomAllocation.printAllocations();
	}
	
}
