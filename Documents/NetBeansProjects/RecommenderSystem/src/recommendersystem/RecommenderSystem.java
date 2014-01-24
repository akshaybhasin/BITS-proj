/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package recommendersystem;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Akshay
 */
public class RecommenderSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException 
    {
 
       String [] exceptions = {"a","an","the","is","are","of","be","been","has","have",
                                          "not","no","yes","go","to","too","gone","went","you","me",
                                          "in","on","and","or","at","will","from","these","those",
                                          "there","their","for","make","it","such","as","with","during",
                                        "being","others","this","that","across","all","by","what","how",
                                "when","where","over","under","should","could","would","therefore","do"
                                ,"moreover","along","its","around","must","also","which","between","yet"
                                ,"now","can","of","however","thus","give","take","they","able"};
    
       List<String> temp = Arrays.asList(exceptions);
       
       ArrayList<String> keywordExceptionList = new ArrayList<>(temp);
       
       
        // Get the list of files in the folder
        File folder = new File("C:\\Users\\Akshay\\Documents\\NetBeansProjects\\awardsDb");
        File [] listOfFiles = folder.listFiles();
        File keywordFile = new File("C:\\Users\\Akshay\\Documents\\NetBeansProjects\\keywords.txt");
        PrintWriter writer = new PrintWriter(keywordFile);
        KeywordList [] keywordOfFile = new KeywordList[listOfFiles.length];
        // find the keywords in each file
        for(int i=0;i<listOfFiles.length;i++)
        {
            
            keywordOfFile[i] = new KeywordList(listOfFiles[i].getName());
            writer.print(listOfFiles[i].getName());
            writer.println(":");
            FileInputStream f = new FileInputStream(listOfFiles[i].getAbsolutePath());
            DataInputStream in = new DataInputStream(f);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String temp2;
            boolean readAbstract = false;
            while((temp2 = reader.readLine())!= null)
            {
                if(readAbstract)
                {
                    //Split the string into words and then check with keywords
                    temp2.trim();
                    String [] tempBuf = temp2.split(" ");
                    for(int j=0;j<tempBuf.length;j++)
                    {
                        tempBuf[j].trim();
                        String toLower = tempBuf[j].toLowerCase();
                        if(!keywordExceptionList.contains(toLower) && !tempBuf[j].equals("\n") && !tempBuf[j].equals(" ") && !tempBuf[j].equals(""))
                        {
                            if(!(keywordOfFile[i].keywords.contains(toLower)))
                            {
                                
                                keywordOfFile[i].keywords.add(toLower);
                                keywordOfFile[i].keywordFrequency.add(1);
                                keywordOfFile[i].keywordDocumentFrequency.add(1);
                                System.out.println(tempBuf[j]);
                                writer.println(tempBuf[j]+" ");
                            }
                            else
                            {
                                int index = keywordOfFile[i].keywords.indexOf(toLower);
                                int count = keywordOfFile[i].keywordFrequency.get(index);
                                count  = count + 1;
                                System.out.println(toLower+ " " + count);
                                keywordOfFile[i].keywordFrequency.set(index, count);
                            }
                            //keyWord.add(tempBuf[j]);
                        }
                    }
                    writer.println();
                    //System.out.println(temp2);
                    //continue;
                }
               if(temp2.contains("Abstract"))
                   readAbstract = true;
            }
       }
       writer.close();
       
       //To find the document frequency of each term. Most inefficient part of the algorithm
       for(int i=0;i<keywordOfFile.length;i++)
       {
           for(int j=0;j<keywordOfFile[i].keywords.size();j++)
           {
               String tempBuf = keywordOfFile[i].keywords.get(j);
               int index = keywordOfFile[i].keywords.indexOf(tempBuf);
               for(int k=0;k<keywordOfFile.length;k++)
               {
                   if(k==i)
                       continue;
                   else
                   {
                       if(keywordOfFile[k].keywords.contains(tempBuf))
                       {
                           int docFreq = keywordOfFile[i].keywordDocumentFrequency.get(index);
                           docFreq = docFreq + 1;
                           keywordOfFile[i].keywordDocumentFrequency.set(index, docFreq);
                       }
                   }
               }
           }
       }
       
       //to find the score for each word
       for(int i=0;i<keywordOfFile.length;i++)
       {
           for(int j=0;j<keywordOfFile[i].keywords.size();j++)
           {
               String temp1 = keywordOfFile[i].keywords.get(j);
               int frequency = keywordOfFile[i].keywordFrequency.get(j);
               int docFreq = keywordOfFile[i].keywordDocumentFrequency.get(j);
               double docfr = keywordOfFile.length/docFreq*1.0;
               double score = Math.log(1+frequency)*Math.log(1+docfr);
               keywordOfFile[i].keywordScore.add(j, score);
               System.out.println(temp1+" "+score);
           }
       }
    
    }
}
