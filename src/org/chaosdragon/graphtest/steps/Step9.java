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
import org.chaosdragon.graphtest.gui.GraphEditor;
import org.chaosdragon.graphtest.gui.MatrixTools;
import org.chaosdragon.graphtest.gui.WizardForm;
import org.chaosdragon.graphtest.tables.KeyTableModel;
import org.chaosdragon.tools.NaturalOrderComparator;

/**
 *
 * @author Mighty
 */
public class Step9 extends Command {

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

    public Step9(Step8 old) {
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
        
       // skippable = false;
        
        //Prepare the table;      
        
    }
    
    public void prepare() {
        String[] names = requirements.get(0).getIds();
        
        groupInformation = null;
        
        byte[][] data= new byte[names.length][names.length];  
        
        for (int i=0; i<data.length; i++) {
            for (int j=0; j<data.length; j++) {
                data[i][j]=1;
            }
        }
        
        KeyTableModel tableModel = new KeyTableModel(names, data);        
        w.setKeyModel(tableModel); 
    }
    

    @Override
    public boolean execute() {

        prepare();        
        return false;

    }

    @Override
    public Command getNext() {

        return new FinalCommand();

    }

}
