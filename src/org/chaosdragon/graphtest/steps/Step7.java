/*
 * Remove duplicate elements (slides 43-45)
 */
package org.chaosdragon.graphtest.steps;

import org.chaosdragon.graphtest.matrix.Matrix;
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
public class Step7 extends Command {

    WizardForm w;
    ArrayList<Matrix> requirements; //Bx
    ArrayList<Matrix> newRequirements; //!!!! with deleted   
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
    ArrayList<Map<String, Set<String>>> groupInformation;
    ArrayList<Map<String, Set<String>>> newGroupInformation; //for next step only, previous one bugs for some reason

    public Step7(Step6 old) {
        w = old.w;
        requirements = old.requirements;
        informationalElements = old.informationalElements;
        requirementGroups = old.requirementGroups;
        precedentSets = old.precedentSets;
        reachabilitySets = old.reachabilitySets;
        ids = old.ids;
        reachabilityMatrices = old.reachabilityMatrices;
        submatrices = old.submatrices;
        groupLevels = old.groupLevels;       
        groupInformation = old.groupInformation;
    }

    @Override
    public boolean execute() {
        
        w.clearText();
        newRequirements = new ArrayList<>();

        newGroupInformation = new ArrayList<>();
        
        
        for (int current = 0; current < requirements.size(); current++) {
            
            
            //Doubling element ID -> H(ID)->Set of H
            Map<String, Map<String, Set<String>>> doublingMap = new TreeMap<>(new NaturalOrderComparator());

            //Map<String, Set<String>> H = new TreeMap<String, Set<String>>(groupInformation.get(current));
                    Map<String, Set<String>> H = new TreeMap<>(new NaturalOrderComparator());
                    
                    for (Map.Entry<String, Set<String>> entry : groupInformation.get(current).entrySet()) {
                        
                        Set<String> temp = new TreeSet<>(new NaturalOrderComparator());
                        for (String tempS:entry.getValue()) {
                            
                            temp.add(tempS);
                            
                        }
                        
                        
                        //temp.addAll(entry.getValue());
                        
                        H.put(entry.getKey(), temp);
                        
                    }
                    

            Matrix submatrix = submatrices.get(current);
            Set<String> D = informationalElements.get(current);

            //Find doubling elements and put inside a SUPER MAP
            for (Map.Entry<String, Set<String>> Hx : H.entrySet()) {
                for (String Doubling : Hx.getValue()) {

                    doublingMap.put(Doubling, new TreeMap<String, Set<String>>(new NaturalOrderComparator()));

                    for (Map.Entry<String, Set<String>> HInner : H.entrySet()) {

                        if (HInner.getValue().contains(Doubling)) {

                            doublingMap.get(Doubling).put(HInner.getKey(), HInner.getValue());

                        }

                    }

                    //Remove if only 1 entry
                    if (doublingMap.get(Doubling).size() < 2) {
                        doublingMap.remove(Doubling);
                    }

                }

            }
            w.printText("REPEATING ELEMENTS for S" + (current + 1) + ":\n");
            w.printText(doublingMap + "\n");

            Matrix b2 = new Matrix(requirements.get(current));
            
          //Now delete duplicates from group 
          //For each element that is doubling
            for (Map.Entry<String, Map<String, Set<String>>> Hx : doublingMap.entrySet()) {

                Set<String> save = new TreeSet<>(new NaturalOrderComparator());
                Set<String> delete = new TreeSet<>(new NaturalOrderComparator());
                
                //For each set inside of the element
                for (Map.Entry<String, Set<String>> Hz : Hx.getValue().entrySet()) {
                    
                    
                        
                        //System.out.println(Hz.getKey()+"->"+Hz.getValue());
                        //Same for comparison
                        for (Map.Entry<String, Set<String>> Hy : Hx.getValue().entrySet()) {
                        
                    
                        if (!Hz.getKey().equals(Hy.getKey())) {
                                                
                            if (submatrix.isConnected(Hz.getKey(),Hy.getKey())) {

                                delete.add(""+Hx.getKey()+","+Hy.getKey());
                                b2.setValue(Hx.getKey(), Hy.getKey(), 0);                                
                                
                                //Delete from H
                                H.get(Hy.getKey()).remove(Hx.getKey());                                
                                
                            } else {

                               save.add(""+Hx.getKey()+","+Hy.getKey()); 
                            }
                        }
                    }
                }

                //To avoid duplicates
                save.removeAll(delete);                
                w.printText("Deleted links (doubling):"+delete+"\n");
                w.printText("Will be saved (are lowest or not on the same path):"+save+"\n");
                
            }
            
            
            w.printText("\nResulting matrix:\n"+b2+"\n");
            w.addToMatrixBox("B"+(current+1)+"*", b2);
            newRequirements.add(b2);
            newGroupInformation.add(H);
        }
        
        //groupInformation = H;       
    //     ArrayList<Map<String, Set<String>>>  test = groupInformation;
    //    groupInformation = newGroupInformation; ///FIX ME!!!!
        //System.out.println(groupInformation);
                
        
        return false;

    }

    @Override
    public Command getNext() {

        return new Step8(this);
    }

}
