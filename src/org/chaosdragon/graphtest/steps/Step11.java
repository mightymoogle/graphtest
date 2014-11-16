/*
 * Remove duplicate elements (slides 43-45)
 */
package org.chaosdragon.graphtest.steps;

import org.chaosdragon.graphtest.matrix.Matrix;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.swing.JPanel;
import org.chaosdragon.graphtest.gui.GraphEditor;
import org.chaosdragon.graphtest.matrix.MatrixTools;
import org.chaosdragon.graphtest.gui.WizardForm;
import org.chaosdragon.graphtest.tables.KeyTableListener;
import org.chaosdragon.graphtest.tables.KeyTableModel;
import org.chaosdragon.tools.NaturalOrderComparator;

/**
 *
 * @author Mighty
 */
public class Step11 extends Command {

    WizardForm w;
    ArrayList<Matrix> requirements; //Bx       
    ArrayList<Matrix> newRequirements; //!!!! with deleted from step7
    ArrayList<Set<String>> informationalElements; //DxF
    ArrayList<Set<String>> requirementGroups;     //DxG       
    ArrayList<ArrayList<Set<String>>> precedentSets;
    ArrayList<ArrayList<Set<String>>> reachabilitySets;
    ArrayList<String[]> ids;
    ArrayList<Matrix> reachabilityMatrices;
    ArrayList<Matrix> submatrices;
    //Current requirement -> Level-> Group
    ArrayList<ArrayList<Set<String>>> groupLevels;

    //For new matrices after Round 1 (from step 8 )
    ArrayList<Matrix> newMatrices;

    //Current requirement -> Group id from D-> Content
    ArrayList<Map<String, Set<String>>> groupInformation;

    ArrayList<Step9.ElementTable> elementTableList; //Step9 tables

    //Will need another arraylist!
    ArrayList<ArrayList<String>> keys;    //W1 
    ArrayList<ArrayList<String>> notKeys; //W2
    ArrayList<ArrayList<String>> fakeKeys;    //for doubling

    ArrayList<String> elements; //W2 subset  
    ArrayList<Matrix> bStarMatrix; //New matrices
    
    //The global ones
    ArrayList<String> W1; 
    ArrayList<String> W2;
    ArrayList<String> W0;
    //Global matrix
    Matrix global;    
    

    public Step11(Step10 old) {
        w = old.w;
        requirements = old.requirements; ///!!!!!!!!!!
        newRequirements = old.newRequirements;
        informationalElements = old.informationalElements;
        requirementGroups = old.requirementGroups;
        precedentSets = old.precedentSets;
        reachabilitySets = old.reachabilitySets;
        ids = old.ids;
        reachabilityMatrices = old.reachabilityMatrices;
        submatrices = old.submatrices;
        groupLevels = old.groupLevels;
        groupInformation = old.groupInformation;

        //SEEMS BUGGED!
        newMatrices = old.newMatrices;
        elementTableList = old.elementTableList;
        //skippable = false;
        keys = old.keys;
        notKeys = old.notKeys;
        fakeKeys = old.fakeKeys;
                
        
        
        //Will be changed!!!
        bStarMatrix = old.bStarMatrix;        
        ArrayList<Matrix> replacementMatrices = new ArrayList<>();
        for (int i=0; i<bStarMatrix.size(); i++) {                   
            replacementMatrices.add(new Matrix(bStarMatrix.get(i)));
        }
        //So we copy everything!
        bStarMatrix = replacementMatrices;
        
        
    }

    //Makes the first part of the step - nonremainder sets (Both W1-0 and W2-0)
    public ArrayList<String> nonRemainderSet(
            ArrayList<ArrayList<String>> keys,
            String title) {

        ArrayList<ArrayList<String>> Wxx = new ArrayList<>();

        for (int i = 0; i < keys.size(); i++) {

            for (int j = (i + 1); j < keys.size(); j++) {

                ArrayList<String> temp = new ArrayList<>();

                //Intersection
                temp.addAll(keys.get(i));
                temp.retainAll(keys.get(j));
                //System.out.println("W"+(i+1+","+(j+1)+"="+temp));       

                Wxx.add(temp);

            }

        }

        ArrayList<String> W1 = new ArrayList<>();
        W1.addAll(keys.get(0));
        for (int i = 1; i < keys.size(); i++) {
            ArrayList<String> temp = new ArrayList<>();
            temp.addAll(keys.get(i));
            temp.removeAll(W1);
            W1.addAll(temp);
        }

        W1.sort(new NaturalOrderComparator());
        w.printText(title + "=" + W1 + "\n");

        return W1;

    }

    //Removes from B* table, returns the "to add" connection
    public String[] removeExtraElement(int num, String check) {
        
        //Clone the array bStar?        
        String[] result = {"-1","-1"};
        Matrix newBMatrix;
        
        
        for (String k: fakeKeys.get(num)) {
            Matrix currentMatrix = new Matrix(bStarMatrix.get(num));
            if (currentMatrix.isConnected(k, check)) {
         
               result[0] =k;              
               currentMatrix.removeAttributeColumn(check);
               //System.out.println(currentMatrix);
               result[1]=check;                             
               return result;
                   
               }
        }
        
        return result;
    }
    
    
    public void doStep() {

        //ArrayList<String> keySet = keys.get(current);
        //ArrayList<String> notKeySet = notKeys.get(current);
        W1 = nonRemainderSet(keys, "W1^0");
        W2 = nonRemainderSet(notKeys, "W2^0");
           
        ArrayList<String> splitz = new ArrayList<>();
        splitz.addAll(W1);
        splitz.retainAll(W2);

        ArrayList<String[]> toAdd = new ArrayList<>();
        
        
        //If has extra elements, do the painful removal procedure
        if (splitz.size() > 0) {
            w.printText("Remainder: " + splitz+"\n");

            for (int i = 0; i < splitz.size(); i++) {
                int listCounter = 0;
                for (ArrayList<String> list : notKeys) {
                    String currentKey = splitz.get(i);
                    if (list.contains(currentKey)) {
                        
                        String[] adder = removeExtraElement(listCounter,currentKey);
                        w.printText("B"+(listCounter+1)+"** (column "+currentKey+
                                " removed):\n"+bStarMatrix.get(listCounter)+"\n");
                        
                        
                        toAdd.add(adder);

                    }
                    listCounter++;
                }

            }
        }
            //Form the global matrix
            W0 = new ArrayList<>();
            W0.addAll(W1);
            W2.removeAll(splitz);
            W0.addAll(W2);
            
                        
            int[][]data = new int[W1.size()][W0.size()];
            
            
            for (Matrix current: bStarMatrix) {
                for (int i=0; i<W1.size(); i++) {

                    for (int j=0; j<W0.size(); j++) {

                        if (current.isConnected(W1.get(i), W0.get(j))) {
                            
                            data[i][j] =1;
                            
                        }

                    }

                }
            }
            
            global = new Matrix(W0.toArray(new String[0]),data);            
            //add toRemove from the final matrix
            for (String[] adder:toAdd) {
                
                global.setValue(adder[0], adder[1], 1);
                w.printText("Added a link between keys "+adder[0]+" and "+adder[1]+"\n");
                
            }

            w.printText("\nCombined matrix B0:\n"+global);
            w.addToMatrixBox("B0", global);
            
    }

    @Override
    public boolean execute() {
        w.clearText();

    //    for (int current = 0; current < requirements.size(); current++) {
        doStep();

    //    }       
        return false;
    }

    @Override
    public Command getNext() {

        return new Step12(this);

    }

}
