/*
 * Remove unneeded links (75-76)
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
public class Step12 extends Command {

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
    

    public Step12(Step11 old) {
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
        
        
        bStarMatrix = old.bStarMatrix;        
        W1 = old.W1;
        W0 = old.W0;
        W2 = old.W2;        
        
        
        //Will be changed!!!
        global = new Matrix(old.global);
        
        
        
    }

    
    //Copy paste from step 8
    public void removeExtraConnections() {
        
        Matrix subBase = global.getLeftMatrix();
        //System.out.println(subBase);
        w.printText("Base SubMatrix:\n"+subBase);
        
        Matrix multi = new Matrix(subBase);
            //ArrayList<int[]> foundStuff = new ArrayList<>();
           
            //s=2 may be incorret! see slide 46
            for (int s = 2; s < subBase.getConnections().length; s++) {

                int[][] contentMatrix
                        = MatrixTools.multiply(multi.getConnections(),
                                subBase.getConnections());
                multi.setConnections(contentMatrix);

                w.printText("B0-λ" + (s) + ":\n");
                w.printText(new Matrix(multi.getIds(),
                        contentMatrix).toString() + "\n");
                //Find stuff

                //Compare first one with content matrix
                ArrayList<int[]> found = MatrixTools.remainingOnes(
                        multi.getConnections(), subBase.getConnections());

                if (found.size()>0) {
                    
                    for (int[] item:found) {
                    
                        //Get strings from values recieved
                        String s1 = multi.getIds()[item[0]];
                        String s2 = multi.getIds()[item[1]];
                        
                        global.setValue(s1, s2, 0); 
                        w.printText("Removed link from "+s1+" to "+s2+ "!\n\n");
                    
                    }
                }
        
                if (MatrixTools.isMatrixZeroOnly(multi.getConnections())) return;
                
            }
        
    } 
    
    public void doStep() {
       
        
        removeExtraConnections();        
        w.printText("Global matrix B0:\n"+global+"\n");
        w.addToMatrixBox("B0", global);
        
    }

    @Override
    public boolean execute() {
        w.clearText();
        doStep();    
        return false;
    }

    @Override
    public Command getNext() {

        return new FinalCommand();

    }

}
