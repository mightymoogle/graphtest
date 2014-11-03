/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chaosdragon.graphtest.gui;

import java.util.ArrayList;

/**
 *
 * @author Mighty
 */
public class MatrixTools {
    public static int[][] multiply(int[][] A, int[][] B) {

        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        int[][] C = new int[aRows][bColumns];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                C[i][j] = 0;
            }
        }

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
    }
    
    public static boolean isMatrixZeroOnly(int[][] matrix) {
        
        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix.length; j++) {
                
                if (matrix[i][j] == 1) return false;
                
            }
        }        
        
        return true;
        
    }
    
    
    //Check if equals (2=1=3=4=5=6 != 0)
    public static boolean matrixEquals(int[][]a,int[][]b) {
        
        //Implement me
        
        return false;
    }
    
    
    //Adss all 1 and above as 1
    public static int[][] specialAdd(int[][]source,int[][]addition) {
        for (int i=0; i<source.length; i++)  {
            for (int j=0; j<source.length; j++)  {
            
                if (addition[i][j]!=0) source[i][j]=1;
                
            }    
        }
        return source;
    }
    
    
    //
    public static ArrayList<int[]> remainingOnes(int[][] oldMatr, int[][] newMatr) {
        
                
        ArrayList<int[]> foundRemnants = new ArrayList<>();
        int size=oldMatr.length;
        if (size!=newMatr.length) return null; //FAIL
        
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                
                if (oldMatr[i][j]==1 && newMatr[i][j]==1) {
                    
                    
                    int[] p = {i,j};
                    
                    foundRemnants.add(p);
                                        
                }
                
            }
        }
        
        return foundRemnants;
    }
    
    
    public static String printMatrix(int[][] matrix, String[] resultNames) {
        
        //STRING BUILDER HERE!!!!!!!!!!!
                
        int len = matrix.length;        
        int maxLength = 0;
        
        for (int i=0; i<len; i++) {
            if (resultNames[i].length()>maxLength)
                maxLength = resultNames[i].length();
        }
        
        maxLength++;
        
        String r = String.format("%"+(maxLength+1)+"s", "");
        
        for (int i=0; i<len;i++) {
            r=r+String.format("%"+maxLength+"s",resultNames[i]);
        }
        r=r+"\n";
        
        for (int i=0; i<=len; i++) {
            for (int j=0; j<=maxLength; j++) r=r+"-";
        }
        r=r+'\n';
                
        for (int i=0; i<len; i++) {
             r=r+String.format("%"+(maxLength+1)+"s",resultNames[i]+"|");

            for (int j=0; j<len; j++) {
                r=r+String.format("%"+maxLength+"d",matrix[i][j]);                
            }
            
            r=r+""+"\n";
        }
       
        return r;
        
    }
    
    public static void main(String[] args) {
        
        int[][] m = {{1,0},{1,0}};
        int[][] m2 = {{1,1},{0,1}};
        
        ArrayList<int[]>list = remainingOnes(m, m2);
        System.out.println("");
        
        
    }
    
    
}
