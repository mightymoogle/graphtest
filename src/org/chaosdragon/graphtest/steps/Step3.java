/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author Mighty
 */
public class Step3 extends Command{
   
    WizardForm w;   
    ArrayList<Set<String>> precedentSets;
    ArrayList<Set<String>> reachabilitySets;
    String[] ids;
    
    
    //Must be put inside an arraylist...
    Set<String> informationalElements=new TreeSet<>();
    Set<String> requirementGroups=new TreeSet<>();            
   
    public Step3(WizardForm p,ArrayList<Set<String>> precedentSets,ArrayList<Set<String>> reachabilitySets,String[] ids) {
        w=p;
        this.precedentSets=precedentSets;
        this.reachabilitySets=reachabilitySets;
        this.ids = ids;
    }
    
    
    @Override
    public boolean execute() {        
    
      w.clearText();
      int current=0; // CURRENT INDEX;
            
    
      
      
     for (int i=0; i<precedentSets.size(); i++) {
         
         //If has something -> to group requirements
         if (precedentSets.get(i).size()>0) {
             requirementGroups.add(ids[i]);
             //w.printText(""+ids[i]+"="+precedentSets.get(i)+"\n");
             
         } 
         //Otherwise -> informational elements         
         else {
             informationalElements.add(ids[i]);
         }
         
     }
     
      
     //Current = requirement number!!!
     w.printText("Informational elements:");
     w.printText("\n");         
     w.printText("D"+(current+1)+"f="+informationalElements.toString());
     w.printText("\n");         
     w.printText("\n");         
     w.printText("Requirement groups:");
     w.printText("\n");
     w.printText("D"+(current+1)+"g="+requirementGroups.toString());
      
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
        return new Step3(w, precedentSets, reachabilitySets,ids);
    }
 
    
}
