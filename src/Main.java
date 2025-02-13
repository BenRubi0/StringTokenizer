// Made by Benjamin Rubio
// For Mr. Gross Software and Programming Dev 2 Class
// Contact at benjamin.rubio@malad.us

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    // creates a method to easily create a file writer object
    public static FileWriter createNewFileWriter(File file) {
        try {
            return new FileWriter(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        File fileToRead = new File("letter_frequency.csv"); // creates the fileToRead variable
        Scanner fileReader = null; // creates the fileReader variable

        // try-catch to catch an exception
        try {
            fileReader = new Scanner(fileToRead); // initializes the fileReader variable
        } catch (Exception e) {
            System.out.println(e.getMessage()); // tells the user what went wrong
        }

        int allFrequenciesAdded = 0; // creates and initializes a variable to store all the frequencies added up
        float allPercentagesAdded = 0.0f; // creates and initializes a variable to store all the percentages added up

        // a loop to read line while the file still has more
        while (fileReader.hasNext()) {
            System.out.println("\n"); // creates a new line
            String[] lineTokens = getCSVTokens(fileReader.nextLine()); // gets the csv tokens for the current line

            // goes through all the tokens for the current line
            for (String str : lineTokens) {
                // checks if the current token can be converted into an int
                if (getNumber(str) != -1) {
                    System.out.println("\t\t\tFrequency: " + getNumber(str)); // tells the user the frequency as a number
                    allFrequenciesAdded += getNumber(str); // adds to the allFrequenciesAdded variable
                // checks if the current token can be converted into a float
                } else if (getFloat(str) != -11.1f) {
                    System.out.println("\t\t\tPercentage: " + getFloat(str));// tells the user the percentage as a float
                    allPercentagesAdded += getFloat(str); // adds the percentage to the total of percentages
                // if the current token is not an int or a float, print it out as a string
                } else {
                    System.out.println("\t\t"+str); // prints out the current token
                }
            }
        }

        System.out.println("\n\tTotal of percentages: "+allPercentagesAdded+"%"); // tells the user all the percentages added
        System.out.println("\tAll frequencies added: "+allFrequenciesAdded); // tells the user all the frequencies added
        System.out.println("\tThe average of all frequencies: "+(allFrequenciesAdded/2)+"\n"); // tells the user all the frequencies averaged

        // user data

        int maxUsers = 5; // creates a variable to store the max amount of users
        int currentUser = 1; // creates a variable to store the current user
        int dataLots = 5; // creates a variable for the amount of each users max lot size of data
        int userDataBufferSize = (dataLots*maxUsers)+20; // creates a variable to store the max amount of data to be stored in the user data string

        String[] userData = new String[userDataBufferSize]; // creates a string to store each user's data

        File userDataFile = new File("user_data.txt"); // creates the file we're going to use to store user data
        FileWriter userDataWriter = createNewFileWriter(userDataFile);

        Scanner userInputScanner = new Scanner(System.in); // creates a new scanner object for user input

        // keeps running while not all user data has been stored
        while (currentUser <= maxUsers) {
            System.out.printf("\nEnter data for user %d (example: John Jimmy, 123 Apple Ln, 12345): ", currentUser); // tells the user to enter data

            StringTokenizer userInputTokenizer = new StringTokenizer(userInputScanner.nextLine(), ",;"); // creates a string tokenizer for the users input
            String[] userDataTokens = new String[userInputTokenizer.countTokens()]; // creates an array of strings that will contain all the tokens from the users input
            String userDataString = "";

            for (int tokenIndex = 0; tokenIndex < userDataTokens.length; tokenIndex++) {
                String currentDataValue = ""; // stores the current data type the user entered

                userDataTokens[tokenIndex] = userInputTokenizer.nextToken(); // sets the current token in the tokens array to the current next string tokenizer token

                // checks if the current number matches the index for the data value
                if (tokenIndex == 0) { currentDataValue = "Name"; /* sets the current data value */ System.out.printf("\tName entered: %s", userDataTokens[tokenIndex]); /* tells the user what name the user input */ }
                else if (tokenIndex == 1) { currentDataValue = "Address"; /* sets the current data value */ System.out.printf("\t\tAddress entered: %s", userDataTokens[tokenIndex]); /* tells the user what address the user input */ }
                else if (tokenIndex == 2) { currentDataValue = "Zipcode"; /* sets the current data value */ System.out.printf("\t\tZipcode entered: %s", userDataTokens[tokenIndex]); /* tells the user what zipcode the user input */ }
                else if (tokenIndex == 3) { currentDataValue = "Phone number"; /* sets the current data value */ System.out.printf("\t\tPhone number entered: %s", userDataTokens[tokenIndex]); /* tells the user what phone number the user input */ }

                userDataString = userDataString.concat("\n\t" + currentDataValue + ": " + userDataTokens[tokenIndex]); // adds the current user token formatted to the user data string
            }

            userData[currentUser] = String.format("\nUser %d data:", currentUser) + userDataString;

            try {
                if (userDataWriter != null) {
                    userDataWriter.write("\n" + userData[currentUser]);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            currentUser += 1;
        }

        // try-catch to get exceptions
        try {
            if (userDataWriter != null) {
                userDataWriter.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // try-catch to get exceptions
        try {
            fileReader.close(); // closes the file reader scanner
            userInputScanner.close(); // closes the current user input scanner
        } catch (Exception e) {
            System.out.println(e.getMessage()); // tells the user what went wrong
        }
    }
}