/*
 * CREATES INFORMATIONAL ELEMENTS AND GROUP REQUIREMENTS (Slide 33)
 */
package org.chaosdragon.graphtest.steps;

import org.chaosdragon.graphtest.matrix.Matrix;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import org.chaosdragon.graphtest.gui.WizardForm;
import org.chaosdragon.tools.NaturalOrderComparator;

/**
 *
 * @author Mighty
 */
public class Step3 extends Command {
    WizardForm w;
    ArrayList<ArrayList<Set<String>>> precedentSets;
    ArrayList<ArrayList<Set<String>>> reachabilitySets;
    ArrayList<Matrix> reachabilityMatrices;
    ArrayList<Matrix> requirements;
    ArrayList<String[]> ids;

    ArrayList<Set<String>> informationalElements;
    ArrayList<Set<String>> requirementGroups;

    public Step3(Step2 old) {
        w = old.w;
        precedentSets = old.precedentSets;
        requirements = old.requirements;
        reachabilitySets = old.reachabilitySets;
        ids = old.newIds;
        //Pass Throught
        reachabilityMatrices = old.reachabilityMatrices;

    }

    @Override
    public void execute() {
        w.clearText();
        informationalElements = new ArrayList<>();
        requirementGroups = new ArrayList<>();

        for (int i = 0; i < precedentSets.size(); i++) {
            informationalElements.add(new TreeSet<String>(new NaturalOrderComparator()));
            requirementGroups.add(new TreeSet<String>(new NaturalOrderComparator()));
        }

        for (int current = 0; current < precedentSets.size(); current++) {

            for (int i = 0; i < precedentSets.get(current).size(); i++) {

                //If has something -> to group requirements
                if (precedentSets.get(current).get(i).size() > 0) {
                    requirementGroups.get(current).add(ids.get(current)[i]);

                } //Otherwise -> informational elements         
                else {
                    informationalElements.get(current).add(ids.get(current)[i]);
                }
            }
        }

        w.printText("Informational elements:");
        for (int current = 0; current < informationalElements.size(); current++) {

            w.printText("\nD" + (current + 1) + "f=" + informationalElements.get(current));
        }

        w.printText("\n\nRequirement groups:");
        for (int current = 0; current < informationalElements.size(); current++) {

            w.printText("\nD" + (current + 1) + "g=" + requirementGroups.get(current));
        }
    }

    @Override
    public Command getNext() {
        return new Step4(this);
    }

}
