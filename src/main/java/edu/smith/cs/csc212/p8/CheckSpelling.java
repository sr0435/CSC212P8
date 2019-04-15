package edu.smith.cs.csc212.p8;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CheckSpelling {
	/**
	 * Read all lines from the UNIX dictionary.
	 * @return a list of words!
	 */
	public static List<String> loadDictionary() {
		long start = System.nanoTime();
		List<String> words;
		try {
			// Read from a file:
			words = Files.readAllLines(new File("src/main/resources/words").toPath());
		} catch (IOException e) {
			throw new RuntimeException("Couldn't find dictionary.", e);
		}
		long end = System.nanoTime();
		double time = (end - start) / 1e9;
		System.out.println("Loaded " + words.size() + " entries in " + time +" seconds.");
		return words;
	}
	
	public static List<String> loadMisspelled() {
		long start = System.nanoTime();
		List<String> misspelled;
		try {
			// Read from a file:
			misspelled = Files.readAllLines(new File("src/main/resources/misspelled").toPath());
		} catch (IOException e) {
			throw new RuntimeException("Couldn't find dictionary.", e);
		}
		long end = System.nanoTime();
		double time = (end - start) / 1e9;
		System.out.println("Loaded " + misspelled.size() + " entries in " + time +" seconds.");
		return misspelled;
	}
	
	public static List<String> loadBook(String filePath) {
		long start = System.nanoTime();
		List<String> book = new ArrayList<>();
		try {
			// Read from a file:
			for(String line : Files.readAllLines(new File(filePath).toPath())) {
				book.addAll(WordSplitter.splitTextToWords(line));
			}
		} catch (IOException e) {
			throw new RuntimeException("Couldn't find dictionary.", e);
		}
		long end = System.nanoTime();
		double time = (end - start) / 1e9;
		System.out.println("Loaded " + book.size() + " entries in " + time +" seconds.");
		return book;
	}
	
	
	
	/**
	 * This method looks for all the words in a dictionary.
	 * @param words - the "queries"
	 * @param dictionary - the data structure.
	 */
	public static void timeLookup(List<String> words, Collection<String> dictionary) {
		long startLookup = System.nanoTime();
		
		int found = 0;
		for (String w : words) {
			if (dictionary.contains(w)) {
				found++;
			}
		}
		
		long endLookup = System.nanoTime();
		double fractionFound = found / (double) words.size();
		double timeSpentPerItem = (endLookup - startLookup) / ((double) words.size());
		int nsPerItem = (int) timeSpentPerItem;
		System.out.println(dictionary.getClass().getSimpleName()+": Lookup of items found="+fractionFound+" time="+nsPerItem+" ns/item");
	}
	
	public static List<String> createMixedDataset(List<String> yesWords, int numSamples, double fractionYes) {
		// Hint to the ArrayList that it will need to grow to numSamples size:
		List<String> output = new ArrayList<>(numSamples);
		double numYesWords = numSamples * fractionYes;
		double numNoWords = numSamples - numYesWords;
		for (int i=0;i<numSamples;i++) {
			if (i<numYesWords) {
				output.add(yesWords.get(i));
			}
			else {
				String wrong = yesWords.get(i);
				wrong = wrong.concat("lrglk");
				output.add(wrong);
			}
		}
		// : select numSamples * fractionYes words from yesWords; create the rest as no words.
		return output;
	}
	
	public static void main(String[] args) {
		// --- Load the dictionary.
		List<String> listOfWords = loadDictionary();
		List<String> nonsense = loadMisspelled();
		
		// --- Create a bunch of data structures for testing:
		TreeSet<String> treeOfWords = new TreeSet<>(listOfWords);
		HashSet<String> hashOfWords = new HashSet<>(listOfWords);
		SortedStringListSet bsl = new SortedStringListSet(listOfWords);
		CharTrie trie = new CharTrie();
		for (String w : listOfWords) {
			trie.insert(w);
		}
		LLHash hm100k = new LLHash(100000);
		for (String w : listOfWords) {
			hm100k.add(w);
		}
		long startLookup = System.nanoTime();
		SortedStringListSet bsll = new SortedStringListSet(Arrays.asList("banana", "apple","grape","orange",
				"pear", "grapefruit","watermelon", "canteloupe","kiwi", "mango","lychee","guava"));
		//System.out.println(bsll);
		//System.out.println(bsll.contains("apple3392"));
		System.out.println(bsl.contains("as"));
		long endLookup = System.nanoTime();
		System.out.println((endLookup-startLookup)/1e9 + " seconds");
		
		// --- Testing the insert speed
			// HashSet and TreeSet constructed with input data
		/*
		 * long start = System.nanoTime(); TreeSet<String> treeOfWords2 = new
		 * TreeSet<>(listOfWords); long end = System.nanoTime(); double time = (end -
		 * start) / 1e9; double timePer = time / 235886;
		 * System.out.println("TreeSet Time Per Element:" + timePer);
		 * System.out.println("TreeSet With Input Data Fill Time:" + time);
		 * 
		 * start = System.nanoTime(); HashSet<String> hashOfWords2 = new
		 * HashSet<>(listOfWords); end = System.nanoTime(); time = (end - start) / 1e9;
		 * timePer = time / 235886; System.out.println("HashSet Time Per Element:" +
		 * timePer); System.out.println("HashSet With Input Data Fill Time:" + time);
		 * 
		 * start = System.nanoTime(); SortedStringListSet bsl2 = new
		 * SortedStringListSet(listOfWords); end = System.nanoTime(); time = (end -
		 * start) / 1e9; timePer = time / 235886;
		 * System.out.println("SSLSet Time Per Element:" + timePer);
		 * System.out.println("SSLSet With Input Data Fill Time:" + time);
		 * System.out.println("\n");
		 */
		
			// Now everything is made with a loop
		/*
		 * start = System.nanoTime(); TreeSet<String> treeOfWords3 = new TreeSet<>();
		 * for (String w : listOfWords) { treeOfWords3.add(w); } end =
		 * System.nanoTime(); time = (end - start) / 1e9; timePer = time / 235886;
		 * System.out.println("TreeSet (Loop) Time Per Element:" + timePer);
		 * System.out.println("TreeSet With Loop Time:" + time);
		 * 
		 * start = System.nanoTime(); HashSet<String> hashOfWords3 = new HashSet<>();
		 * for (String w : listOfWords) { hashOfWords3.add(w); } end =
		 * System.nanoTime(); time = (end - start) / 1e9; timePer = time / 235886;
		 * System.out.println("HashSet (Loop) Time Per Element:" + timePer);
		 * System.out.println("HashSet With Loop Time:" + time);
		 * 
		 * start = System.nanoTime(); CharTrie trie2 = new CharTrie(); for (String w :
		 * listOfWords) { trie2.insert(w); } end = System.nanoTime(); time = (end -
		 * start) / 1e9; timePer = time / 235886;
		 * System.out.println("CharTrie Time Per Element:" + timePer);
		 * System.out.println("CharTrie With Loop Time:" + time);
		 * 
		 * start = System.nanoTime(); LLHash hm100k2 = new LLHash(100000); for (String w
		 * : listOfWords) { hm100k2.add(w); } end = System.nanoTime(); time = (end -
		 * start) / 1e9; timePer = time / 235886;
		 * System.out.println("LLHash Time Per Element:" + timePer);
		 * System.out.println("LLHash With Loop Time:" + time);
		 */
		
		// --- Make sure that every word in the dictionary is in the dictionary:
		//timeLookup(listOfWords, treeOfWords);
		//timeLookup(listOfWords, hashOfWords);
		//timeLookup(listOfWords, bsl);
		//timeLookup(listOfWords, trie);
		//timeLookup(listOfWords, hm100k);
		System.out.println("\n");
		
		// checks how many words in misspelled aren't in the dictionary
		/*
		 * System.out.println("Starting Nonsense:"); timeLookup(nonsense, treeOfWords);
		 * timeLookup(nonsense, hashOfWords); timeLookup(nonsense, bsl);
		 * timeLookup(nonsense, trie); timeLookup(nonsense, hm100k);
		 * System.out.println("\n");
		 */
		
		/*
		 * System.out.println("Earnest"); List<String> earnest =
		 * loadBook("src/main/resources/theImportanceOf"); timeLookup(earnest,
		 * treeOfWords); timeLookup(earnest, hashOfWords); timeLookup(earnest, bsl);
		 * timeLookup(earnest, trie); timeLookup(earnest, hm100k);
		 * System.out.println("\n");
		 */
		
		for (int i=0; i<10; i++) {
			// --- Create a dataset of mixed hits and misses with p=i/10.0
			List<String> hitsAndMisses = createMixedDataset(listOfWords, 10_000, i/10.0);
			
			// --- Time the data structures.
			/*
			 * timeLookup(hitsAndMisses, treeOfWords); timeLookup(hitsAndMisses,
			 * hashOfWords); timeLookup(hitsAndMisses, bsl); timeLookup(hitsAndMisses,
			 * trie); timeLookup(hitsAndMisses, hm100k); System.out.println("\n");
			 */
		}
		System.out.println("\n");
			

		
		// --- linear list timing:
		// Looking up in a list is so slow, we need to sample:
		System.out.println("Start of list: ");
		timeLookup(listOfWords.subList(0, 1000), listOfWords);
		System.out.println("End of list: ");
		timeLookup(listOfWords.subList(listOfWords.size()-100, listOfWords.size()), listOfWords);
		
	
		// --- print statistics about the data structures:
		System.out.println("Count-Nodes: "+trie.countNodes());
		System.out.println("Count-Items: "+hm100k.size());
		System.out.println("Count-Collisions[100k]: "+hm100k.countCollisions());
		System.out.println("Count-Used-Buckets[100k]: "+hm100k.countUsedBuckets());
		System.out.println("Load-Factor[100k]: "+hm100k.countUsedBuckets() / 100000.0);

		
		System.out.println("log_2 of listOfWords.size(): "+listOfWords.size());
		
		System.out.println("Done!");
	}
}
