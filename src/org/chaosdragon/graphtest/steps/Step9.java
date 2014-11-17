/*
 * 
 */
package org.chaosdragon.graphtest.steps;

import com.sun.glass.events.KeyEvent;
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

    ArrayList<ElementTable> elementTableList; //Step9 tables
    private int currentElement; //For ^ 

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

    }

    public class ElementTable {

        private String[] names;
        private byte[][] data;

        /**
         * @return the names
         */
        public String[] getNames() {
            return names;
        }

        /**
         * @param names the names to set
         */
        public void setNames(String[] names) {
            this.names = names;
        }

        /**
         * @return the data
         */
        public byte[][] getData() {
            return data;
        }

        /**
         * @param data the data to set
         */
        public void setData(byte[][] data) {
            this.data = data;
        }

        public KeyTableModel getModel() {
            return new KeyTableModel(names, data);
        }

        public ElementTable() {

        }

        public void fillData() {
            data = new byte[names.length][names.length];

            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data.length; j++) {
                    data[i][j] = 1;
                }
            }

        }

    }

    public boolean hasNext() {

        return (currentElement + 1 < elementTableList.size());

    }

    public void nextKey() {

        if (currentElement < elementTableList.size()) {

            currentElement++;

            //IF SIZE == 1 (NOTHING CAN BE CHANGED -> just set the key automaticaly)
            //- CAN BE LAZY WITH THIS ONE
            setModel(currentElement);
        }

    }

    private void setModel(int element) {
        KeyTableModel m = elementTableList.get(element).getModel();
        w.setKeyModel(m);

        //MAYBE ONE FOR ALL?
        m.addTableModelListener(new KeyTableListener(w));
        m.fireTableDataChanged();       
        
        //Sets selection of the keyList to the first
        w.setSelectedKeyInList();
        
        if (m.getColumnCount()<2) {            
            w.pressNextKeyKey();            
        }
        
        
    }

    public void prepare() {

        //Global storage
        elementTableList = new ArrayList<>();

        for (int current = 0; current < requirements.size(); current++) {

       // ArrayList<ElementTable> elementTableList = new ArrayList<>();
            Set<String> s = requirementGroups.get(current);
            Set<String> currentSet = new TreeSet<>();
            for (String p : s) {
                ElementTable e = new ElementTable();
                currentSet = groupInformation.get(current).get(p);
                
//                if (currentSet!=null) {
                    //Sets names
                    e.setNames(currentSet.toArray(new String[0]));
                    e.fillData();
                    elementTableList.add(e);
                //}
            }

       // elementTableListList.add(elementTableList);
        }

        currentElement = 0;
        setModel(0);
     //CALL CLEARING OF THE KEYLIST ON W? RESET THE KEYBAR AND SO ON???

    }

    @Override
    public boolean execute() {
        prepare();
        return false;
    }

    @Override
    public Command getNext() {

        return new Step10(this);

    }

}
