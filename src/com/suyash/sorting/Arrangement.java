package com.suyash.sorting;

import java.util.Set;
import java.util.TreeMap;

/* This class stores one allocation state. a valid arrangement represents an allocation where
each resident is assigned exactly one room and no room belongs to more than one resident. */
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
	
	public static void main (String[] args) {
		
	}
}
