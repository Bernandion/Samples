/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

/**
 * Class for a String and Number pair for the key and value to be reduced.
 * 
 * @author Brandon
 */
public class StringNumPair {
    
    private String key;
    
    private Integer vali;
    
    private Float valf;
    
    // constructs the key val pair by int
    public StringNumPair(String key, Integer val){
        this.key = key;
        this.vali = val;
    }
    
    // constructs the key val pair by float
    public StringNumPair(String key, Float val){
        this.key = key;
        this.valf = val;
    }
    
    /**
     * get the key (string)
     * 
     * @return 
     * returns the String key
     */
    public String getKey(){
        return this.key;
    }
    
    /**
     * get the integer value 
     * 
     * @return 
     * returns the Integer value
     */
    public Integer getVali(){
        return this.vali;
    }
    
    /**
     * get the float value
     * 
     * @return 
     * returns the Float value
     */
    public Float getValf(){
        return this.valf;
    }
    
}
