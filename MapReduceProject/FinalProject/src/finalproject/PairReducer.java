/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Takes the passed collection and reduces it to get the average value from
 * all passed values in the collection for the specific key
 * 
 * @author Brandon
 */
public class PairReducer {
    // arraylist for the key value pairs for a specific key
    private ArrayList<StringNumPair> redlist;
    
    // sets the list of key value pairs to be reduced
    public PairReducer(ArrayList<StringNumPair> redlist){
        this.redlist = redlist;
    }
    
    /**
     * reduces the iterable by averaging the values for the specific key
     * 
     * @return 
     * returns a StringNumPair for the key and the value of the average
     */
    public StringNumPair reduce(){
        // float for averaging the values
        float average = 0;
        // iterate through arraylist to sum up the values
        Iterator<StringNumPair> it = this.redlist.iterator();
        while(it.hasNext()){
            average += it.next().getVali().floatValue();
        }
        // get average
        average /= (float)this.redlist.size();
        // return StringNumPair containing the key and average of values
        return new StringNumPair(this.redlist.get(0).getKey(), average);
    }
}
