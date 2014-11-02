/*
 * Remove duplicate elements (slides 43-45)
 */
package org.chaosdragon.graphtest.steps;

import org.chaosdragon.graphtest.gui.Matrix;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.swing.JPanel;
import org.chaosdragon.graphtest.gui.WizardForm;
import org.chaosdragon.tools.NaturalOrderComparator;

/**
 *
 * @author Mighty
 */
public class Step7 extends Command{
   
    WizardForm w;   
    ArrayList<Matrix> requirements; //Bx
    ArrayList<Set<String>> informationalElements; //DxF
    ArrayList<Set<String>> requirementGroups;     //DxG       
    ArrayList<ArrayList<Set<String>>> precedentSets;
    ArrayList<ArrayList<Set<String>>> reachabilitySets;
    ArrayList<String[]> ids;
    ArrayList<Matrix> reachabilityMatrices;   
    ArrayList<Matrix> submatrices;
    //Current requirement -> Level-> Group
    ArrayList<ArrayList<Set<String>>> groupLevels;
    
    //Current requirement -> Group id from D-> Content
     ArrayList<Map<String,Set<String>>> groupInformation;
    
    public Step7(Step6 old) {
        w=old.w;
        requirements = old.requirements;
        informationalElements=old.informationalElements;
        requirementGroups=old.requirementGroups;
        precedentSets=old.precedentSets;
        reachabilitySets=old.reachabilitySets;
        ids = old.ids;
        reachabilityMatrices=old.reachabilityMatrices;
        submatrices = old.submatrices;
        groupLevels=old.groupLevels;
        groupInformation = old.groupInformation;
    }
    
     
    
    @Override
    public boolean execute() {        
    
      w.clearText();          
            
      
      for (int current=0; current<requirements.size(); current++) {
          
          
          //Doubling element ID -> H(ID)->Set of H
          Map<String,
                  Map<String,Set<String>>>
                  doublingMap = new TreeMap<>(new NaturalOrderComparator());          
          
          Map<String, Set<String>> H = groupInformation.get(current);
          
          Matrix submatrix = submatrices.get(current);
          Set<String> D = informationalElements.get(current);
          
          //Find doubling elements and put inside a SUPER MAP
          for(Map.Entry<String, Set<String>> Hx : H.entrySet()) {
             for (String Doubling:Hx.getValue()) {
                
                 doublingMap.put(Doubling, new TreeMap<String,Set<String>>(new NaturalOrderComparator()));
           
                for (Map.Entry<String, Set<String>> HInner : H.entrySet()) {
                  
                    if (HInner.getValue().contains(Doubling)) {
        
                        doublingMap.get(Doubling).put(HInner.getKey(), HInner.getValue());
                      
                    }
                             
                }
                
                //Remove if only 1 entry
                if (doublingMap.get(Doubling).size()<2) doublingMap.remove(Doubling);
                
            }
              
          }                          
          w.printText("REPEATING ELEMENTS for S"+(current+1)+":\n");
          w.printText(doublingMap+"\n");
                    
          //Now delete duplicates from group 
          
          
          
                }
      
     return false;
    
    }


    @Override
    public Command getNext() {
    
        return new FinalCommand();
    }
 
    
}
