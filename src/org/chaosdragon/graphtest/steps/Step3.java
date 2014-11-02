/*
 * CREATES INFORMATIONAL ELEMENTS AND GROUP REQUIREMENTS (Slide 33)
 */
package org.chaosdragon.graphtest.steps;

import org.chaosdragon.graphtest.gui.Matrix;
import java.util.ArrayList;
import java.util.HashSet;
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
public class Step3 extends Command{
   
    WizardForm w;   
    ArrayList<ArrayList<Set<String>>> precedentSets;
    ArrayList<ArrayList<Set<String>>> reachabilitySets;
    ArrayList<Matrix> reachabilityMatrices;
    
    ArrayList<String[]> ids;
    
    
    //Must be put inside an arraylist...
    ArrayList<Set<String>> informationalElements;
    ArrayList<Set<String>> requirementGroups;
   
    public Step3(WizardForm p, ArrayList<ArrayList<Set<String>>> precedentSets, 
            ArrayList<ArrayList<Set<String>>> reachabilitySets,ArrayList<String[]> ids,
            ArrayList<Matrix> reachabilityMatrices) {
        w=p;
        this.precedentSets=precedentSets;
        this.reachabilitySets=reachabilitySets;
        this.ids = ids;
        
        //Pass Throught
        this.reachabilityMatrices = reachabilityMatrices;
    }
    
    
    @Override
    public boolean execute() {        
    
      w.clearText();
      
      informationalElements = new ArrayList<>();
      requirementGroups = new ArrayList<>();
      
      for (int i=0; i<precedentSets.size(); i++) {
        informationalElements.add(new TreeSet<String>(new NaturalOrderComparator()));
        requirementGroups.add(new TreeSet<String>(new NaturalOrderComparator()));
      }
      
            
    for (int current=0; current<precedentSets.size();current++) {
      
      
     for (int i=0; i<precedentSets.get(current).size(); i++) {
         
         //If has something -> to group requirements
         if (precedentSets.get(current).get(i).size()>0) {
             requirementGroups.get(current).add(ids.get(current)[i]);
             //w.printText(""+ids[i]+"="+precedentSets.get(i)+"\n");
             
         } 
         //Otherwise -> informational elements         
         else {
             informationalElements.get(current).add(ids.get(current)[i]);
         }
         
     }
     
    
      
    }    
    
    
    w.printText("Informational elements:");
    for (int current=0; current<informationalElements.size(); current++) {
     
     w.printText("\nD"+(current+1)+"f="+informationalElements.get(current));
    }
      
     
     w.printText("\n\nRequirement groups:");
      for (int current=0; current<informationalElements.size(); current++) {
     
     w.printText("\nD"+(current+1)+"g="+requirementGroups.get(current));
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
        
        
        //MUST ALSO PROVIDE F FROM PREVIOUS (REACHABILITY)???????
        return new Step4(w, informationalElements, requirementGroups,precedentSets, reachabilitySets,ids,reachabilityMatrices);
    }
 
    
}
