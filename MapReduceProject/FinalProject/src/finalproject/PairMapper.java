/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Mapper class that filters, maps, and combines entries and puts them in a
 * collection to be reduced.
 * 
 * @author Brandon
 */
public class PairMapper {
    private final int BITSETSIZE = 10000000;
    // bitset for bloom filtering
    private BitSet filterset;
    // the String input to map
    private ArrayList<String> rawinput;
    // the filter to be used 
    private String filter;
    // the collection of stringnumpair arraylists reprenting key and value pairs 
    private ArrayList<ArrayList<StringNumPair>> pairs = new ArrayList<>();
    // constructor that sets the filters string and rawinput
    public PairMapper(String filter, ArrayList<String> rawinput){
        this.filter = filter;
        this.rawinput = rawinput;
        this.filterset = new BitSet(BITSETSIZE);
    }
    
    /**
     * maps the key and value from the input into class and combines by keys
     * in specified arraylist
     * 
     * @return 
     * returns the collection of arraylists by key
     */
    public ArrayList<ArrayList<StringNumPair>> map(){
        // filter if strings are passed
        if (filter != ""){
            // set the filterset before looking at input
            applyFilter();
        }
        // get the key and value pair from each input string
        Iterator<String> it = this.rawinput.iterator();
        // iterate through arraylist of strings to seperate pairs
        while(it.hasNext()){
            // the key for the hashtable
            String key = new String();
            // the value for the hashtable
            int val = -1;
            // determines if an entry was dropped
            boolean filterThis = false;
            try {
                // scan the current string
                Scanner in = new Scanner(it.next());
                // set delimiter to comma
                in.useDelimiter(",");
                // count the number of lines in an entry
                int count =  1;
                // go through each line to find the key and value pair
                // disregard line if hashvalue is in BitSet filter
                while(in.hasNext()){
                    String st = in.next();
                    // if the string is in the bitset, filer the entry out
                    if (this.filterset.get(st.hashCode() % this.BITSETSIZE)) {
                        // disregard entry and go to next string
                        filterThis = true;
                        break;
                    }
                    // if second line in entry, then key
                    if (count == 2) key = st;
                    // if third line, value
                    else if (count == 3) val = Integer.valueOf(st);
                    count++;
                }
                in.close();
                // go to next string to find pairs if entry was filtered
                if (filterThis) continue;
                // combines entries by key into each of their own collections
                // if no collections of pairs, create the first one
                if (this.pairs.isEmpty()){
                    // create first key collection
                    this.pairs.add(new ArrayList<>());
                    // add the first val
                    this.pairs.get(0).add(new StringNumPair(key, val));
                }
                // check in arraylist for this key exists
                // if exists, add key val pair to the list, otherwise add new arraylist
                else {
                    // boolean for determining if the key is in the collection
                    boolean notin = false;
                    Iterator<ArrayList<StringNumPair>> iter = this.pairs.iterator();
                    while(iter.hasNext()){
                        ArrayList<StringNumPair> paircol = iter.next();
                        // if arraylist for this key exists, add the key value pair to it
                        if (paircol.get(0).getKey().equals(key)){
                            paircol.add(new StringNumPair(key, val));
                            break;
                        }
                        // if at end of collection, set notin to true
                        if (!iter.hasNext()){
                            notin = true;
                        }
                    }
                    // if at end of collection, create new arraylist for the specific key
                    if (notin){
                        // adds the new arraylist
                        this.pairs.add(new ArrayList<StringNumPair>());
                        // puts the new kind of key value pair into that arraylist
                        this.pairs.get(pairs.size()-1).add(new StringNumPair(key, val));
                    }
                }
                
            } catch(Exception e){
                System.out.println("Error setting pairs.");
            }
        }
        // return the mapped and combined collection of pairs
        return this.pairs;
        
    }
    
    /**
     * separates filters from string, hashes them, sets the appropriate bit
     * in the bitset
     */
    private void applyFilter(){
        try {
            // create scanner to read strings from filter string
            Scanner stread = new Scanner(this.filter);
            // use the comma delimiter for reading the string filters
            stread.useDelimiter(",");
            // go through each string, hash it, and set the corresponding bit in the bitset
            while(stread.hasNext()){
                String filterstring = stread.next();
                // sets the BitSet bit for the hash value of the filter string
                this.filterset.set(filterstring.hashCode() % this.BITSETSIZE);
            }
            stread.close();
        } catch (Exception e){
            System.out.println("Error building filter.");
        } 
    }
    
    
}
