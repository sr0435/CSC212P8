package edu.smith.cs.csc212.p8;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This is an alternate implementation of a dictionary, based on a sorted list.
 * It often makes the most sense if the dictionary never changes (compared to a
 * TreeMap). You could write a delete, but it's tricky.
 * 
 * @author jfoley
 */
public class SortedStringListSet extends AbstractSet<String> {
	/**
	 * This is the sorted list of data.
	 */
	private List<String> data;

	/**
	 * This is the constructor: we take in data, copy and sort it (just to be sure).
	 * 
	 * @param data the input list.
	 */
	public SortedStringListSet(List<String> data) {
		this.data = new ArrayList<>(data);
		Collections.sort(this.data);
	}

	/**
	 * So we can use it in a for-loop.
	 */
	@Override
	public Iterator<String> iterator() {
		return data.iterator();
	}

	/**
	 * This method takes an object because it was invented before Java 5.
	 */
	@Override
	public boolean contains(Object key) {
		return binarySearch((String) key, 0, this.data.size()) >= 0;
	}

	/**
	 * @param query - the string to look for.
	 * @param start - the left-hand side of this search (inclusive)
	 * @param end   - the right-hand side of this search (exclusive)
	 * @return the index found, OR negative if not found.
	 */
	private int binarySearch(String query, int start, int end) {
		
		int mid = (start + end) / 2;
		String midVal = data.get(mid);
	    if (start > end) {
	        return -1;
	    }
	    if (midVal.compareTo(query)==0) {
	    	System.out.println(midVal);
	        return mid;
	    } else {
	        if (midVal.compareTo(query) < 0) {
	            return binarySearch(query, mid+1,end);
	        }
	        else {
	            return binarySearch(query,start,mid-1);
	        }
	    }
		
		// TODO: replace this with your own binary search.
		// end--;
		// System.out.println("start: " + start);
		// System.out.println("end: " + end);
		/*
		 * while (start<=end) { int mid = start + ((end-start)/2); String midVal =
		 * data.get(mid); //System.out.println("mid value: " + mid); if (midVal==query)
		 * { System.out.println("should be found "); System.out.println(midVal);
		 * System.out.println(mid); return mid; //break; } if
		 * (midVal.compareTo(query)<0) { //ystem.out.println("mid is less "); start =
		 * mid+1; //int found = binarySearch(query,start,end); break; //return found; }
		 * else if (midVal.compareTo(query)>0) { //System.out.println("mid is more ");
		 * end = mid-1; //int found = binarySearch(query,start,end); break; //return
		 * found; } else if (midVal==query) { System.out.println("should be found ");
		 * System.out.println("mid value: " + midVal); return mid; } else { return -1; }
		 * } if (start>end) { return -1; } int found = binarySearch(query,start,end);
		 * System.out.println("found: " + found);
		 * 
		 * 
		 * if (midVal.compareTo(query)<0) { int found = binarySearch(query, start,mid);
		 * return found; //for (int i=0; i<midVal.length(); i++) { } else if
		 * (midVal.compareTo(query)>0) { int found = binarySearch(query, mid,end);
		 * return found; } else { int found = 0; data.add(mid, query); return found;
		 * 
		 * }
		 * 
		 * //return Collections.binarySearch(this.data.subList(start, end), query);
		 * //System.out.println(mid); return found;
		 */
	}

	/**
	 * So we know how big this set is.
	 */
	@Override
	public int size() {
		return data.size();
	}

}
