/*
 * 
 */
package org.chaosdragon.graphtest.steps;

import org.chaosdragon.graphtest.matrix.Matrix;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.chaosdragon.graphtest.gui.WizardForm;
/**
 *
 * @author Mighty
 */
public class Step10 extends Command {
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
    ArrayList<ArrayList<Set<String>>> groupLevels;

    //For new matrices after Round 1 (from step 8 )
    ArrayList<Matrix> newMatrices;

    //Current requirement -> Group id from D-> Content
    ArrayList<Map<String, Set<String>>> groupInformation;

    ArrayList<Step9.ElementTable> elementTableList; //Step9 tables
    private int currentElement; //For ^ 

    //Will need another arraylist!
    ArrayList<ArrayList<String>> keys;    //W1
    ArrayList<ArrayList<String>> fakeKeys;    //for doubling
    ArrayList<ArrayList<String>> notKeys; //W2
    
    ArrayList<String> elements; //W2 subset  
    
    ArrayList<Matrix> bStarMatrix; //New matrices

    public Step10(Step9 old) {
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
        skippable = false;
    }

    public static int numberInArrayList(ArrayList<String> a, String b) {

        int i = 0;
        for (String s : a) {
            if (s.equals(b)) {
                return i;
            }
            i++;
        }
        System.err.println("["+b+"] not in "+a);
        return -1;
    }

    private int numberInSet(Set<String> a, String b) {

        int i = 0;
        for (String s : a) {
            if (s.equals(b)) {
                return i;
            }
            i++;
        }

        return -1;
    }

    //GENERETE SUBMATRICES FOR THIS STUPID STEP WITH NO DESCRIPTION IN THE SLIDES!      
    public void changeSubMatrices() {

        submatrices = new ArrayList<>();

        for (int current = 0; current < requirementGroups.size(); current++) {

            Set<String> group = requirementGroups.get(current);
            int[][] matr = new int[group.size()][group.size()];

            int i = 0;
            int j = 0;

            //IDs for the new sub-matrix
            String[] newIds = new String[matr.length];

            for (String s1 : group) {

                //Setting of ids
                newIds[i] = s1;
                for (String s2 : group) {

                    if (newMatrices.get(current).isConnected(s1, s2)) {
                        matr[i][j] = 1;
                    }

                    j++;
                }
                j = 0;
                i++;
            }

            Matrix m = new Matrix(newIds, matr);

            submatrices.add(m);

        }

    }

    public void setKeys(ArrayList<String> keys) {

        //fakeKeys = keys;

       //Hail LinkedHashSet! Keeps the order intact!
//       Set<String> temp = new LinkedHashSet<>();
//       temp.addAll(keys);
        this.keys = new ArrayList<>();
        fakeKeys = new ArrayList<>();
        for (int i = 0; i < requirements.size(); i++) {
            this.keys.add(new ArrayList<String>());
            fakeKeys.add(new ArrayList<String>());
        }

        
        int counter=0;
        int current=0;
        for (String str : keys) {
            
            
            this.keys.get(current).add(str);
            fakeKeys.get(current).add(str);
            
            counter++;
            
            if (requirementGroups.get(current).size()<=counter) {                
                current++;
                counter =0;                
            }            
        }
        
        ArrayList<ArrayList<String>> tempKeys = new ArrayList<>();
        
        for (int i=0; i<this.keys.size(); i++) {
        
        //for (ArrayList<String> arr:this.keys) {
            
             Set<String> temp = new LinkedHashSet<>();
             temp.addAll(this.keys.get(i));             
             
            ArrayList<String> t =  this.keys.get(i);             
            t = new ArrayList<>(temp);
            tempKeys.add(t);
        }       
      
        this.keys = tempKeys;
    }

    public void setElements(ArrayList<String> elements) {
        this.elements = elements;
    }

    public String[] concat(String[] A, String[] B) {
        int aLen = A.length;
        int bLen = B.length;
        String[] C = new String[aLen + bLen];
        System.arraycopy(A, 0, C, 0, aLen);
        System.arraycopy(B, 0, C, aLen, bLen);
        return C;
    }

    @Override
    public void execute() {
        w.clearText();
        bStarMatrix = new ArrayList<>();
        
        if (keys == null) {
            w.printText("FATAL ERROR!!!\n");
            return;       
        }

        int current = 0;
        
        notKeys = new ArrayList<>();
                
        
        for (current = 0; current < requirements.size(); current++) {

            ArrayList<String> keys = this.keys.get(current);
                    
            
            Map<String, Set<String>> sets = groupInformation.get(current);


            ArrayList<String> e = new ArrayList<>();

            for (Map.Entry<String, Set<String>> set : sets.entrySet()) {
                e.addAll(set.getValue());
            }

            e.removeAll(keys);


            Set<String> temp = new LinkedHashSet<>(e);
            e = new ArrayList<>(temp);

            setElements(e);
            notKeys.add(e);            
            
            int[][] m = new int[keys.size()][keys.size() + elements.size()];

            int i = 0; //ROW

            //Add links to atributes from the key
            for (Map.Entry<String, Set<String>> set : sets.entrySet()) {
                //PER ROW
                String currentKey = fakeKeys.get(current).get(i); //EPIC FAIL

                for (String setElement : set.getValue()) {
                    if (!currentKey.equals(setElement) && elements.contains(setElement)) {
                        int elementIndex = keys.size() + numberInArrayList(elements, setElement);
                 
                        int keyIndex = numberInArrayList(keys, fakeKeys.get(current).get(i));
                      
                        m[keyIndex][elementIndex] = 1;
                    }

                }
                i++;
            }

            Matrix req = newRequirements.get(current);

            //Now set intergroup links
            i = 0;
            for (String key1 : keys) {
                int j = 0;
                for (String key2 : keys) {

                    //Cros key links               
                    if (req.isConnected(key1, key2)) {

                        m[i][j] = 1;
                    }

                    j++;
                }

                i++;
            }

            Map<String, Set<String>> groups = groupInformation.get(current);
            changeSubMatrices();
            Matrix sub = submatrices.get(current);

            //Reachability to other groups
            String[] subIds = sub.getIds();

            for (int j = 0; j < subIds.length; j++) {
                for (int k = 0; k < subIds.length; k++) {

                    if (sub.isConnected(subIds[j], subIds[k])
                            && !fakeKeys.get(current).get(j).equals(fakeKeys.get(current).get(k))) {

                        int keyIndex = numberInArrayList(keys, fakeKeys.get(current).get(j));
                        int keyIndex2 = numberInArrayList(keys, fakeKeys.get(current).get(k));


                        m[keyIndex][keyIndex2] = 1;

                    }

                }

            }

            //Find the labels
            String[] ss = concat(keys.toArray(new String[0]), elements.toArray(new String[0]));

            Matrix done = new Matrix(ss, m);
            String doneLabel = "B" + (current + 1) + "*";

            w.printText(doneLabel + ":\n" + done + "\n");
            w.addToMatrixBox(doneLabel, done);
            w.printText("Keys: "+this.keys.get(current)+ "\n");
            w.printText("Elements: "+notKeys.get(current)+ "\n\n");
            bStarMatrix.add(done);
        }     
    }

    @Override
    public Command getNext() {
        return new Step11(this);
    }

}
