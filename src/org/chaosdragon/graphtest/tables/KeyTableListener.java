/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chaosdragon.graphtest.tables;

import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.chaosdragon.graphtest.gui.WizardForm;

/**
 *
 * @author Mighty
 */
public class KeyTableListener implements TableModelListener{
    
    WizardForm w;
    
    public KeyTableListener(WizardForm w) {
                       
        this.w=w;        
        
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        KeyTableModel m = (KeyTableModel)e.getSource();

        //LOOKS FOR POSSIBLE KEYS AND ADS TO THE JLIST MODEL
               
        ArrayList<String> keyCandidates = new ArrayList<>();
        //Filter potencial keys, do not find it automaticaly, not your job
        int[] twoCounter = new int[m.getRowCount()];
        
        for (int i=0; i<m.getRowCount(); i++) {                       
            for (int j=0; j<m.getColumnCount(); j++) {                
                if (m.getValueAt(i, j)==2) {                    
                    twoCounter[i]++;                    
                }
                                
            }        
            
            if (twoCounter[i]==0) {
                keyCandidates.add(m.getColumnName(i));
            }
            
        }
        
        //Do stuff with buttons
        String[] temp = keyCandidates.toArray(new String[0]);
        w.setPossibleKeys(temp);
        
    }
    
}
