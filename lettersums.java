/*
 * Question from https://www.reddit.com/r/dailyprogrammer/comments/onfehl/20210719_challenge_399_easy_letter_value_sum/
 * 
 * Word list from https://raw.githubusercontent.com/dolph/dictionary/master/enable1.txt
 * 
 * */

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Problem13052024 {

	public static void main(String[] args) {
		//main question
		String test = "excellent";
		System.out.println(getSum(test));

		//reading file for the bonus questions
		//using a hashmap to store word:wordsum as a key value pair
		HashMap<String, Integer> words = new HashMap<String, Integer>();
		try {
			//reading content of file
			File myObj = new File("text.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				//the key is a the word in the line
				String data = myReader.nextLine();
				//the value is the sum of the letters (using getSum() function)
				words.put(data, getSum(data));
			}
			myReader.close();

			//catch block used in case the file is not found
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		//bonus 1
		System.out.println("Looking for word with sum 319");
		System.out.println(getFromSum(words, 319));

		//bonus 2
		System.out.println("Looking for odd letter sums");
		System.out.println(getOLS(words));

		//bonus 3
		System.out.println("Looking for most common sum");
		System.out.println(getMCS(words)[0] + " characters : " + getMCS(words)[1] + " times");

		//bonus 4
		System.out.println("Looking for words with the same sum but 11 difference in length");
		System.out.println(get11diff(words));

		//bonus 5
		System.out.println("Findings words where the letters are different, same sums");
		System.out.println(ssdl(words));

	}

	//function returns the sum of the values of the letters where a = 1 and z = 26
	public static int getSum(String s) {

		//convert string to a lowercase string character array
		//this is done to iterate over the characters in the list
		char ch[] = s.toLowerCase().toCharArray();

		//count variable
		int sum = 0;

		//for loop iterates through each character in the string
		for (int i = 0; i < ch.length; i ++) {

			//typecasting to character returns the ASCII value
			//subtracting 96 because the ASCII value of a = 97 to make a = 1
			sum += ((int)ch[i] - 96);
		}
		return sum;
	}

	//function returns a word given a sum of the letters
	public static String getFromSum(HashMap<String, Integer> words , int sum) {
		String s = "";

		//iterating through hashmap
		for (HashMap.Entry<String, Integer> m: words.entrySet()) {

			//find if the sum of the letters of a given word matches sum (target value)
			if (sum == m.getValue()) {

				//set s as the word
				s = m.getKey();
			}
		}
		return s;
	}

	//get the number of words that have an odd number sum
	public static int getOLS(HashMap<String, Integer> words) {

		//count variable
		int OLS = 0;

		//iterating through hashmap to check if word sum is odd or even
		for (HashMap.Entry<String, Integer> m : words.entrySet()) {

			//using modulo - any odd number n will always be (n mod 2 = 1)
			if ((m.getValue() % 2) == 1) {

				//increment counter variable
				OLS++;
			}
		}
		return OLS;
	}

	//this returns the most common sum
	public static int[] getMCS(HashMap<String, Integer> words) {

		//max[0] is used to store the value of the most common sum
		//max[1] is used to store the number of occurences of the most common sum
		int[] max = new int[2];

		//creating a new hashmap that stores the lengths as the key and the number of occurences as the value
		HashMap<Integer, Integer> lengths = new HashMap<Integer, Integer>();

		//iterating through old hashmap to initialize new hashmap
		for (HashMap.Entry<String, Integer> m: words.entrySet()) {

			//count variable for occurences
			int count = 0;

			//if length is present in the hashmap, get the current value to increment it
			if (lengths.containsKey(m.getValue())) {
				count = lengths.get(m.getValue());
			}

			//if the key was not set yet, it would be set to 0 anyways - see int count = 0
			lengths.put(m.getValue(), count + 1);
		}

		//iterate through lengths to get key with highest value
		for (HashMap.Entry<Integer, Integer> m: lengths.entrySet()) {

			//if value is higher than max, make it the new max
			if (m.getValue() > max[1]) {

				//max[0] is the sum
				max[0] = m.getKey();

				//max[1] is the number of occurences
				max[1] = m.getValue();
			}
		}
		return max;
	}

	//get words with the same sum but have difference in length of 11 characters
	public static HashMap<Integer, ArrayList<String>> get11diff(HashMap<String, Integer> words) {
		
		//using hashmap to store sum as the key and there is now also an
		//arraylist of two words with the same sum and 11 character difference in length
		//key: length
		//value: word list of 2 words
		HashMap<Integer, ArrayList<String>> vals = new HashMap<Integer, ArrayList<String>>();

		//need to make the hashmap to group words with the same length together
		//key: length
		//value: list of words with the length
		HashMap<Integer, ArrayList<String>> wordSums = new HashMap<Integer, ArrayList<String>>();

		//iterate through hashmap to make wordSums
		for (HashMap.Entry<String, Integer> m: words.entrySet()) {

			//if word sum is not in the hashmap yet, make new arraylist
			if (wordSums.get(m.getValue()) == null) {
				wordSums.put(m.getValue(), new ArrayList<String>());
			}

			//add key (the word itself) as a value to the hashmap
			wordSums.get(m.getValue()).add(m.getKey());
		}

		//iterate compare string with strings of same length with each other in new hashmap
		for (HashMap.Entry<Integer, ArrayList<String>> m: wordSums.entrySet()) {

			//get i to use as the key of vals hashmap
			int i = m.getKey();

			//use temp to iterate through arraylist of words per length
			ArrayList<String> temp = m.getValue();

			//compare each string with each other
			for (String s : temp) {
				for (String t : temp) {

					//if condition set to absolute value as the absolute difference must be 11
					//if condition is met, it means that the differences of length is 11
					if (Math.abs(s.length() - t.length()) == 11) {

						//create temporary arraylist before adding to hashmap
						ArrayList<String> tem = new ArrayList<String>();

						//adding words to arraylist
						tem.add(s);
						tem.add(t);

						//adding key (length) and value (the two words in arraylist) to hashmap
						vals.put(i, tem);
					}
				}
			}
		}
		return vals;
	}

	//used to find words with the same sum but different letters in it
	public static ArrayList<ArrayList<String>> ssdl(HashMap<String, Integer> words) {

		//returns arraylist so that it is possible to return pairs with the same sum
		//since sum was the key, it removed duplicates
		//this currently has duplicates
		ArrayList<ArrayList<String>> vals = new ArrayList<ArrayList<String>>();

		//make hashmap of word sums where the key is the length and the value is an arraylist of words
		//key: length
		//value: arraylist of words
		HashMap<Integer, ArrayList<String>> wordSums = new HashMap<Integer, ArrayList<String>>();

		//initialize wordsums hashmap using words hashmap
		for (HashMap.Entry<String, Integer> m: words.entrySet()) {

			//if the length is not already present, put in list with new arraylist
			if (wordSums.get(m.getValue()) == null) {
				wordSums.put(m.getValue(), new ArrayList<String>());
			}

			//add word (key in original hashmap) to wordsums hashmap
			wordSums.get(m.getValue()).add(m.getKey());
		}

		//iterating through word sums to find words that have different letters to each other
		for (HashMap.Entry<Integer, ArrayList<String>> m: wordSums.entrySet()) {

			//used to get length when saving result
			int i = m.getKey();

			//used to check whether a character appears in another string
			boolean stringcomp = true;

			//used to get the words that have the same length
			ArrayList<String> temp = m.getValue();

			//the bonus question is set of words with length over 188
			//removing this would show all words with the same sum different letters
			if (i > 188) {

				//compare each string with each other string
				for (String s : temp) {
					for (String t : temp) {

						//setting as true for each string s and string t before comparing
						stringcomp = true;

						//looping to compare each character with each character in another string
						for (char u : s.toCharArray()) {
							for (char v : t.toCharArray()) {

								//if they are the same, it would skip the if block later
								if (v == u) {
									stringcomp = false;
								}
							}
						}

						//if none of the characters are the same, this block will run
						if (stringcomp == true) {
							//create temporary arraylist to add to the final arraylist (vals)
							ArrayList<String> tem = new ArrayList<String>();

							//add integer as a string
							tem.add(Integer.toString(i));

							//adding the two words s and t to the array list
							tem.add(s);
							tem.add(t);

							//the whole array list will now consist of the length, string s, and string t
							//this is now added to the final answer
							vals.add(tem);
						}
					}
				}  
			}

		}
		return vals;
	}
}