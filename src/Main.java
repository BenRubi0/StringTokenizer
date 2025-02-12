// Made by Benjamin Rubio
// For Mr. Gross Software and Programming Dev 2 Class
// Contact at benjamin.rubio@malad.us

import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    // creates a method to easily get tokens in csv formatted strings
    public static String[] getCSVTokens(String csvString) {
        StringTokenizer tokenizer = new StringTokenizer(csvString, ", \t"); // creates a new string tokenizer for the string provided

        String[] tokens = new String[tokenizer.countTokens()]; // creates an array of strings that will contain all the tokens from the tokenizer
        int tokenIndex = 0; // creates a variable to store the current token index

        // loop to set index values
        while (tokenizer.hasMoreTokens()) {
            tokens[tokenIndex] = tokenizer.nextToken(); // sets the current tokenIndex to the tokenizers next token
            tokenIndex += 1; // adds one to the current tokenIndex
        }

        return tokens; // returns the array of all the tokens
    }

    // creates a method to easily find if a string is a number
    public static int getNumber(String str) {
        // try-catch to catch exceptions
        try {
            return Integer.parseInt(str); // returns the string as an int
        }catch (Exception e){
            return -1; // if the method parseInt() failed, then return the failed int
        }
    }

    // creates a method to easily find if a string is a float
    public static float getFloat(String str) {
        // try-catch to catch exceptions
        try {
            return Float.parseFloat(str); // returns the string as a float
        } catch (Exception e) {
            return -11.1f; // if the method parseFloat() failed, then return the failed float
        }
    }

    // creates a method to compare the user input index with a specific token index
    public static boolean compareUserData(int userIndex, int tokenIndex, Runnable c) {
        // check if the two indexes are equal
        if (userIndex == tokenIndex) {
            c.run(); // runs the callback lambda function
            return true; // returns that the check succeed
        } else {
            return false; // returns that the check failed
        }
    }

    public static void main(String[] args) {
        File fileToRead = new File("letter_frequency.csv"); // creates the fileToRead variable
        Scanner fileReader = null; // creates the fileReader variable

        // try-catch to catch an exception
        try {
            fileReader = new Scanner(fileToRead); // initializes the fileReader variable
        }catch (Exception e){
            System.out.println(e.getMessage()); // tells the user what went wrong
        }

        int allFrequenciesAdded = 0; // creates and initializes a variable to store all the frequencies added up
        float allPercentagesAdded = 0.0f; // creates and initializes a variable to store all the percentages added up

        // a loop to read line while the file still has more
        while (fileReader.hasNext()) {
            System.out.println("\n"); // clears a line
            String[] lineTokens = getCSVTokens(fileReader.nextLine()); // gets the csv tokens for the current line

            // goes through all the tokens for the current line
            for (String str : lineTokens) {
                // checks if the current token can be converted into an int
                if (getNumber(str) != -1) {
                    System.out.println("Frequency: " + getNumber(str)); // tells the user the frequency as a number
                    allFrequenciesAdded += getNumber(str); // adds to the allFrequenciesAdded variable
                // checks if the current token can be converted into a float
                } else if (getFloat(str) != -11.1f) {
                    System.out.println("Percentage: " + getFloat(str));// tells the user the percentage as a float
                    allPercentagesAdded += getFloat(str); // adds the percentage to the total of percentages
                // if the current token is not an int or a float, print it out as a string
                } else {
                    System.out.println(str); // prints out the current token
                }
            }
        }

        System.out.println("\nTotal of percentages: "+allPercentagesAdded); // tells the user all the percentages added
        System.out.println("All frequencies added: "+allFrequenciesAdded); // tells the user all the frequencies added
        System.out.println("The average of all frequencies: "+(allFrequenciesAdded/2)+"\n"); // tells the user all the frequencies averaged

        // user input

        System.out.println("\nEnter data (example: John Jimmy, 123 Apple Ln, 12345): "); // tells the user to enter data

        Scanner inputScanner = new Scanner(System.in); // creates a new scanner object for user input

        int nameTokenIndex = 0; // creates a variable to identify the name token in the users input
        int addressTokenIndex = 1; // creates a variable to identify the address token in the users input
        int zipCodeTokenIndex = 2; // creates a variable to identify the zipcode token in the users input

        StringTokenizer userInputTokenizer = new StringTokenizer(inputScanner.nextLine(), ","); // creates a string tokenizer for the users input
        String[] tokens = new String[userInputTokenizer.countTokens()]; // creates an array of strings that will contain all the tokens from the users input

        // loops for the amount of tokens able to be stored in the tokens string array
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = userInputTokenizer.nextToken(); // sets the current token to the string tokenizers current token
            compareUserData(i, nameTokenIndex, () -> {System.out.println("Name: "+tokens[nameTokenIndex]);}); // checks if the current token index is the name token index, and if so repeats the users name input
            compareUserData(i, addressTokenIndex, () -> {System.out.println("Address: "+tokens[addressTokenIndex]);}); // checks if the current token index is the address token index, and if so repeats the users address input
            compareUserData(i, zipCodeTokenIndex, () -> {System.out.println("Zipcode: "+tokens[zipCodeTokenIndex]);}); // checks if the current token index is the zipcode token index, and if so repeats the users zipcode input
        }

        // try-catch to get exceptions
        try {
            fileReader.close(); // closes the file reader scanner
            inputScanner.close(); // closes the user input scanner
        }catch (Exception e){
            System.out.println(e.getMessage()); // tells the user what went wrong
        }
    }
}