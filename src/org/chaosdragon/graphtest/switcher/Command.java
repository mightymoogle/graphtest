/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chaosdragon.graphtest.switcher;

/**
 *
 * @author Mighty
 */
public interface Command {
   void execute();
   void undo();
}
