/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chaosdragon.graphtest.gui;

import java.io.*;
import java.util.Arrays;

/**
 *
 * @author Mighty
 */
public class MatrixFiles {

    public static Matrix loadFromCsvFile(String sFileName) {

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        Matrix newMatrix=null;

        try {

                     
             
            
            br = new BufferedReader(new FileReader(sFileName));
            String[] names=br.readLine().split(cvsSplitBy);
            
            int[][] items = new int[names.length][names.length];
            int j=0;
            while ((line = br.readLine()) != null) {
                
                String[] itemLine = line.split(cvsSplitBy);
                
                for (int i=0; i<itemLine.length;i++) {
                    //BACKWARDS!
                    items[j][i]=Integer.parseInt(itemLine[i]);
                    
                }                
                j++;
               // System.out.println(Arrays.toString(itemLine));
                newMatrix = new Matrix(names, items);
                
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
       // System.out.println(newMatrix.print());
        return newMatrix;

    }

    public static void generateCsvFile(String sFileName, Matrix m) {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(sFileName));
            StringBuilder sb = new StringBuilder();

            int[][] c = m.getConnections();
            String[] names = m.getIds();
            
             for (int i = 0; i < names.length; i++) {
                 
                 sb.append(names[i]);
                 sb.append(",");
             }
               sb.append("\r\n");
            
            for (int i = 0; i < c.length; i++) {

                for (int j = 0; j < c[0].length; j++) {

                    sb.append(c[i][j]);
                    sb.append(",");

                }

                sb.append("\r\n");

            }


            br.write(sb.toString());
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
