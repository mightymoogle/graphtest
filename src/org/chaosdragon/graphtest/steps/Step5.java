/*
 * Groups levels??? NOT COMPLETE!!!!!!!! (Slide - 35)
 */
package org.chaosdragon.graphtest.steps;

import org.chaosdragon.graphtest.gui.Matrix;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JPanel;
import org.chaosdragon.graphtest.gui.WizardForm;
import org.chaosdragon.tools.NaturalOrderComparator;

/**
 *
 * @author Mighty
 */
public class Step5 extends Command{
   
    WizardForm w;   
    ArrayList<ArrayList<Set<String>>> precedentSets;
    ArrayList<Matrix> requirements;
    ArrayList<ArrayList<Set<String>>> reachabilitySets;
    ArrayList<String[]> ids;
    ArrayList<Matrix> reachabilityMatrices;
    
    
    //Must be put inside an arraylist...
    ArrayList<Set<String>> informationalElements;
    ArrayList<Set<String>> requirementGroups;
   
    public Step5(Step4 old) {
        w=old.w;
       requirements=old.requirements;
        informationalElements=old.informationalElements;
       requirementGroups=old.requirementGroups;
        precedentSets=old.precedentSets;
        reachabilitySets=old.reachabilitySets;
        ids = old.ids;
        reachabilityMatrices=old.reachabilityMatrices;
    }
    
    public static int getNumberFromIDS(String[] ids, String description) {
        
        int num = 0;
        for (String s:ids) {
            
            if (s.equals(description)) return num;
            num++;
            
        }
        
        
        return -1;
    }
    
    
    @Override
    public boolean execute() {        
    
      w.clearText();
      w.printText("STEP 5\n");         
      
      //For each requirement
      for (int current=0; current<requirementGroups.size(); current++) {
           
          //For each requirement group per requirement?
        
            Set<String> D= new TreeSet<String>(requirementGroups.get(current));     
            Set<String> level1 = new TreeSet<String>(new NaturalOrderComparator());
            
            
            for (String SS: D) {                          
                      
                //CurrentElement = 1 (2nd in Set, but SS = 4...)
             int  currentElement = getNumberFromIDS(ids.get(current), SS);
                
            Set<String> F = new TreeSet<String>(reachabilitySets.get(current).get(currentElement));          
            Set<String> C = new TreeSet<String>(precedentSets.get(current).get(currentElement));

            String thisElement;
            
            thisElement = SS;
            //Add the current element for some reason....?????
            F.add(thisElement);
            C.add(thisElement);
            
            Set<String> C2;          
            
            //For m=1
            int level =1;
            C2 = new TreeSet<String>(C);
            C2.retainAll(F);
            if(F.equals(C2)) {
                //System.out.println(thisElement+ " P"+level);
                level1.add(thisElement);
                level++;
            }          
    
          }
            w.printText("--------------\n");
            w.printText("P1 group:"+level1+"\n");
            
            
            int level = 1;
            Set<String> level2 = new HashSet<>(level1);
            //FOR OTHER LEVELS    
            //FIX SIZE HERE
            int sizeLimit = D.size();            
            while (level1.size()<sizeLimit)  {
            level++;
            level1=new HashSet<>(level2);
            D.removeAll(level1);
             
             for (String SS: D) {                          
                      
             //CurrentElement = 1 (2nd in Set, but SS = 4...)
             int  currentElement = getNumberFromIDS(ids.get(current), SS);
                
                Set<String> F = new TreeSet<String>(reachabilitySets.get(current).get(currentElement));          
                Set<String> C = new TreeSet<String>(precedentSets.get(current).get(currentElement));
                
                F.add(SS);
                C.add(SS);
                
                    Set<String> C2;                      
                F.removeAll(level1);                    
                    
               C2 = new TreeSet<String>(C);
                           
               C2.retainAll(F);            
               if(F.equals(C2)){                
                    w.printText("P"+level+" also "+SS+"\n");
                    level2.add(SS);
               }
                
               }
      }
            

          
         
      }

     return false;
    
    }

    @Override
    public void undo() {          
        
      //  w.clearText();        
        
    }

    @Override
    public Command getNext() {
        //return new Step1(requirements,w);
        //return new Step3();
        
        
        //NOT COMPLETE!
        return new Step6(this);
    }
 
    
}
