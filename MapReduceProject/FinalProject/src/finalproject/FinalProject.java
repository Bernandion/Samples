
package finalproject;

// LOOK AT PROGRAMMING LABS AND EXAMPLE FOR HOW TO DO

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;


/**
 * Final Project
 * Big Data Processing and Analysis
 * 
 * Mainly incorporates algorithms for Mapping, Reducing, Clustering,
 * and filtering. Example data to be processed is located in the file
 * "weatherrecords.txt". This implementation filters out data specified
 * in the main, maps average rainfall values by month in mm to
 * the key of the year, find the similarity between a specified year and other years before reducing and
 * outputs the results to a file, and reduces the values to get the average rainfall by year.
 * This kind of data can be used in determining information
 * such as weather trends. The input file data must be organized by "[String],[String],[Integer]"
 * per line to get the correct results, with the filter being specified in main and separated by commas.
 * 
 * @author Brandon Verigin
 */
public class FinalProject {
    
    // stores the initial input
    private ArrayList<String> initInput = new ArrayList<>();
    
    /**
     * Reads file input into initInput
     * 
     * @param input 
     * The input file to be read
     */
    private void readFile(String input){
        try {
            // input string
            String ins = "";
            // get file from string
            FileReader read = new FileReader(input);
            // create scanner to read file
            BufferedReader in = new BufferedReader(read);
            while ((ins = in.readLine()) != null){
                this.initInput.add(ins);
            }
            in.close();
        } catch (Exception e){
            System.out.println("Error reading input file");
        }
        
    }
    
    /**
     * Writes results of reducer to file
     * 
     * @param list 
     * The arraylist containing reduced values and their keys
     */
    private void outFile(ArrayList<StringNumPair> list){
        // iterates over the passed arraylist of key and average pairs
        // and outputs each key and val to a line in a file
        Iterator<StringNumPair> it = list.iterator();
        try {
            // opens the file
            PrintWriter writer = new PrintWriter("reduceoutput.txt","UTF-8");
            writer.println("Average of Values by Key:");
            // writes the cooresponding key and average to a line for each
            // StringNumPair
            while(it.hasNext()){
                StringNumPair pair = it.next();
                writer.print(pair.getKey() + ": ");
                writer.println(pair.getValf());
            }
            writer.close();
        } catch(Exception e){
            System.out.println("Error outputing reduce file.");
        }
        
    }
    
    /**
     * gets the initial input list to be used by the mapper
     * 
     * @return 
     * the arraylist of input
     */
    private ArrayList<String> getInput(){
        return this.initInput;
    }
    
    

    /**
     * runs the program by the input file
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Create string to filter by
        // pass "" for no filtering
        String filter = new String("jul,aug");
        // starts the program
        FinalProject run = new FinalProject();
        // read the specified file, first line for filters
        run.readFile("weatherrecords.txt");
        // create the mapper
        PairMapper map = new PairMapper(filter, run.getInput());
        // map the entries to key value pairs 
        ArrayList<ArrayList<StringNumPair>> mapped = map.map();
        // create the similarity calculator
        // specify string for key to find similarity for
        SimOutputter simout = new SimOutputter(mapped, "2015");
        // run the calculation method
        simout.calcSim();
        // create iterator for the returned collection of combined key collections
        Iterator it = mapped.iterator();
        // arraylist of new StringNum pairs for collection of reduced values by key
        ArrayList<StringNumPair> reducevals = new ArrayList<>();
        // iterate through the collection of arraylists and pass each array list
        // to a reducer
        while(it.hasNext()){
            // create a reducer and pass a collection of vals for a certain key
            PairReducer red = new PairReducer((ArrayList<StringNumPair>)it.next());
            // reduce the values and put the result in reducevals for the key
            reducevals.add(red.reduce());
        }
        
        // outputs the reduced values to text file called "reduceoutput.txt"
        run.outFile(reducevals);
        
        
        
    }
    
}
