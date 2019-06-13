/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Uses euclidean distance to find the similarity between the specified key and
 * all other keys in the passed collection of key value pairs. Outputs results
 * to a file called "similaritymeasure.txt".
 * 
 * @author Brandon
 */
public class SimOutputter {
    
    private ArrayList<ArrayList<StringNumPair>> simlist;
    // String key to find similarity for
    private String key;
    
    public SimOutputter(ArrayList<ArrayList<StringNumPair>> simlist, String key){
        this.simlist = simlist;
        this.key = key;
    }
    
    /**
     * calculate the similarity of the specified key's values to other key's values
     * and output the results to a file
     */
    public void calcSim(){
        ArrayList<StringNumPair> comparesim = null;
        // iterate through the collection to find the list of key value pairs by the
        // specified key
        Iterator<ArrayList<StringNumPair>> itl = this.simlist.iterator();
        while(itl.hasNext()){
            // find the specified key with values from the collection
            ArrayList<StringNumPair> keyval = itl.next();
            String colkey = keyval.get(0).getKey();
            if (colkey.equals(this.key)){
                comparesim = keyval;
                break;
            }
            // if the specified key cant be found, output a message and leave
            if (!itl.hasNext()){
                System.out.println("Specified key not found.");
                return;
            }
        }
        try {
            // open file to print the results to
            PrintWriter out = new PrintWriter("similaritymeasure.txt","UTF-8");
            out.println("Similarity Metrics:");
            out.println();
            // iterate through the list of keys and values and calculate the similarity
            // of each collection of values by key to the specified key
            itl = this.simlist.iterator();
            while(itl.hasNext()){
                ArrayList<StringNumPair> compareto = itl.next();
                // if the same list of key and values, go to next listto compare
                if (comparesim.get(0).getKey().equals(compareto.get(0).getKey())){
                    continue;
                }
                // if the list of values is longer than the list of values we want to
                // find the similarity for, then truncate the other list
                if (comparesim.size() < compareto.size()){
                    compareto = (ArrayList<StringNumPair>)compareto.subList(0, comparesim.size());
                }
                // if the other list of values is shorter than the list of values we
                // want to find the similarity for, then add additional values of 0
                // until its the same size as the list were finding similarity for
                else if (comparesim.size() > compareto.size()){
                    for (int i=compareto.size(); i<comparesim.size(); i++){
                        compareto.add(new StringNumPair(compareto.get(0).getKey(), 0));
                    }
                }
                // calculate the euclidean distance between the specified key list and
                // the other list of values for the key and output the results to 
                // a file
                out.print(comparesim.get(0).getKey() + " to " + compareto.get(0).getKey() + ": ");
                out.println(euclidean(comparesim, compareto));
                out.println();
            }
            out.close();
        } catch(Exception e){
            System.out.println("Error outputting sim file.");
        }
        
    }
    
    /**
     * Gets the euclidean distance of two collections of values for different keys
     * 
     * @param main
     * the main list for the key we want to compare to other keys
     * @param comp
     * the list were comparing to the key list
     * @return 
     * returns the euclidean distance between the two
     */
    private double euclidean(ArrayList<StringNumPair> main, ArrayList<StringNumPair> comp){
        // keeps track of the summation
        float num = 0;
        // iterates through each value in the two lists and performs the subtraction
        // and squaring part of the euclidean calculation
        for (int i=0; i<main.size(); i++){
            int mainval = main.get(i).getVali();
            int compval = comp.get(i).getVali();
            float subval = (float)mainval - compval;
            float sqrval = subval * subval;
            num += sqrval;
        }
        // get the distance as a similarity
        double denom = 1 + Math.sqrt(num);
        // returns 1 over the square root of the total summation + 1 to get the euclidean distance
        return 1/denom;
    }
    
}
