/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package recommendersystem;

import java.util.ArrayList;

/**
 *
 * @author Akshay Bhasin, Shantanu Joshi
 */
public class KeywordList 
{
    String title;
    
    String fileName;
    
    ArrayList<String> keywords;
    
    ArrayList<Integer> keywordFrequency;
    
    ArrayList<Integer> keywordDocumentFrequency;
    
    ArrayList<Double> keywordScore;
    
    int docScore;

    public KeywordList(String fileName) 
    {
        this.fileName = fileName;
        keywords = new ArrayList<>();
        keywordDocumentFrequency = new ArrayList<>();
        keywordFrequency = new ArrayList<>();
        keywordScore = new ArrayList<>();
    }
    
   
}
