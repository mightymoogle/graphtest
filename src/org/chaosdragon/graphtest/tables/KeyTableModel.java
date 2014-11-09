/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chaosdragon.graphtest.tables;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mighty
 */
public class KeyTableModel extends AbstractTableModel {

    private String[] columnNames;
    private byte[][] data;
    
    
    public String[] getColumnNames() {
        return columnNames;
    }
    
    public KeyTableModel(String[] columnNames, byte[][] data) {        
        this.columnNames =  columnNames;
        this.data = data;        
    }        
    
    @Override
    public int getRowCount() {        
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int col) {
        return "d"+columnNames[col];
    }
    
    
    @Override
    public Byte getValueAt(int rowIndex, int columnIndex) {       
        return data[rowIndex][columnIndex];
    }
    
    @Override
        public boolean isCellEditable(int row, int col) {
        //row==col means elemnent to itself, which always is 1
        if (row==col) {
            return false;
        } else {
            return true;
        }
    }
        
    @Override
    public void setValueAt(Object value, int row, int col) {
       
        try {
            
           byte val = Byte.valueOf((String) value);
            
            if (val==1 || val==2) {
                
            data[row][col] = val;
            fireTableCellUpdated(row, col);
            
            }
        } catch (Exception e) {
            
        }
        
        
        
    }
    
}
