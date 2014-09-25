/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chaosdragon.graphtest.gui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author Mighty
 */
public class MatrixListModel extends AbstractListModel{

    ArrayList<Matrix> m;
    
    public ArrayList<Matrix> getList() {
        return m;
    }
    
    public MatrixListModel(ArrayList<Matrix> m){
        this.m=m;
    }
    
    @Override
    public int getSize() {
        return m.size();
    }

    @Override
    public Object getElementAt(int index) {
        return m.get(index).getName()+"["+index+"]";
    }    
    
    public Matrix get(int index) {
        return m.get(index);
    }
    
    public void add(Matrix t) {        
        
        m.add(t);
        int index = m.size();
        fireContentsChanged(this, index, index);
        
    }
    
    public void remove(int index) {
        
        if (index<0) return;
        
        m.remove(index);
        fireContentsChanged(this, index, index);
    }
        
}
