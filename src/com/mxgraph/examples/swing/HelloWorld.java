package com.mxgraph.examples.swing;


import com.mxgraph.layout.*;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.handler.mxKeyboardHandler;
import com.mxgraph.swing.handler.mxRubberband;
import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.util.mxUndoManager;
import com.mxgraph.view.mxGraph;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JPanel;

public class HelloWorld extends JPanel {

    public enum Mode {

        HAND, BLOCK, ARROW
    };
    
    public enum Arrange {
        CIRCLE, HIERARCHY,FAST_ORGANIC        
    };
    /**
     *
     */
    private static final long serialVersionUID = -2707712944901661771L;
    private mxGraphComponent graphComponent;
    private String style = mxConstants.STYLE_SHAPE + "="
            + mxConstants.SHAPE_ELLIPSE + ";"
            + mxConstants.STYLE_VERTICAL_ALIGN + "="
            + mxConstants.ALIGN_MIDDLE + ";"
            + mxConstants.STYLE_FILLCOLOR + "=yellow";
       
    private void generate(mxGraph graph,Object parent) {
         graph.getModel().beginUpdate();
        try {
//            Object v1 = graph.insertVertex(parent, null, "1", 0, 0, 40,
//                    40, style);
//            Object v2 = graph.insertVertex(parent, null, "2", 0, 0, 40,
//                    40, style);                                    
//            graph.insertEdge(parent, null, "Edge", v1, v2);
           Random rand = new Random();
           ArrayList<Object> list = new ArrayList<>();
            for (int i=0; i<15; i++) {                
                
                list.add(graph.insertVertex(parent, null, String.valueOf(i), 0, 0, 40,40, style));                                                                
            }
            
             for (int i=0; i<55; i++) {                
                 int r1=rand.nextInt(list.size());
                 int r2=rand.nextInt(list.size());
                 
                graph.insertEdge(parent, null, "", list.get(r1), list.get(r2));
            }
            
            


        } finally {
            graph.getModel().endUpdate();
        }
    }
    
    public HelloWorld() {
        //super("Hello, World!");
        super();
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();






       generate(graph,parent);

        graphComponent = new mxGraphComponent(graph);

        //graphComponent.setCenterZoom(true);





        //The selector thingy
        new mxRubberband(graphComponent);
        //The keyboard handler
        new mxKeyboardHandler(graphComponent);



        graphComponent.setEnterStopsCellEditing(true);
        graph.setCellsResizable(false);
        setMode(Mode.HAND);


        //   graphComponent.zoom(20);

        this.setLayout(new BorderLayout());
        this.add(graphComponent, BorderLayout.CENTER);

        
//        graphComponent.getConnectionHandler().
//                addListener(mxEvent.CONNECT, new mxIEventListener()
//                {
//                    public void invoke(Object sender, mxEventObject evt)
//                    {
//                        System.out.println("edge="+evt.getProperty("cell"));
//                    }
//                });
        
        
        //No more disconected edges
        graphComponent.getGraph().setCellsDisconnectable(false);
        //No more CTRL+MOVE orphan edges
        graphComponent.getGraph().setDisconnectOnMove(false);
        graphComponent.getGraph().setAllowDanglingEdges(false);     
        //setVisible(true);                
        
        //Prevetns edge labels
        graphComponent.getGraph().getStylesheet().
                getDefaultEdgeStyle().put(mxConstants.STYLE_NOLABEL, "1");

        //getContentPane().add(graphComponent);                                            

    }

    public void setMode(Mode m) {
        
        mxGraph graph = graphComponent.getGraph();

        if (m == Mode.ARROW) {            
            graph.clearSelection();            
            graph.setCellsMovable(false);
            graphComponent.setConnectable(true);
            graphComponent.getGraph().setCellsSelectable(false);
            //graphComponent.getGraph().setCellsEditable(true);                
            return;
        }

        if (m == Mode.BLOCK) {
            graph.setCellsMovable(false);
            graphComponent.setConnectable(false);
            graphComponent.getGraph().setCellsSelectable(false);
            //graphComponent.getGraph().setCellsEditable(true);
            return;
        }

        if (m == Mode.HAND) {
            graph.setCellsMovable(true);
            graphComponent.setConnectable(false);
            graphComponent.getGraph().setCellsSelectable(true);
            //graphComponent.getGraph().setCellsEditable(true);
            return;
        }

    }

    public void addVertice() {
        mxGraph graph = graphComponent.getGraph();

        graph.getModel().beginUpdate();
        Object parent = graph.getDefaultParent();
        try {
            Object v1 = graph.insertVertex(parent, null, "1", 0, 0, 40,
                    40, style);

        } finally {
            graph.getModel().endUpdate();
        }

    }

    public void autoArrange(Arrange r) {
        
        mxIGraphLayout layout;
        layout = new mxFastOrganicLayout(graphComponent.getGraph());  
        
        if (r== Arrange.CIRCLE) {
        layout = new mxCircleLayout(graphComponent.getGraph());        
        } else {
        if (r== Arrange.HIERARCHY)        
        layout = new mxHierarchicalLayout(graphComponent.getGraph());        
        }

        //Flies offscreen
        //mxIGraphLayout layout = new mxFastOrganicLayout(graphComponent.getGraph());        
                
        try {
            graphComponent.getGraph().getModel().beginUpdate();
            layout.execute(graphComponent.getGraph().getDefaultParent());
        } finally {
            
          mxMorphing morph = new mxMorphing(graphComponent, 20, 1.2, 20);
            
            morph.addListener(mxEvent.DONE, new mxIEventListener() {

            
              @Override
              public void invoke(Object o, mxEventObject eo) {
                  graphComponent.getGraph().getModel().endUpdate();                  
              }

            });

            morph.startAnimation();
            
        }
    }

    public void zoom(int z) {

        if (z > 0) {
            //for (int i=0; i<z; i++) {
            graphComponent.zoomIn();
            //}
        } else {
            if (z < 0) {
                graphComponent.zoomOut();
            }
        }
        System.out.println(this.getSize().height);
        System.out.println(graphComponent.getSize().height);
    }

    public static void main(String[] args) {

        JFrame frames = new JFrame();
        frames.setSize(5000, 5000);

        frames.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        HelloWorld frame = new HelloWorld();
        frame.setSize(1000, 1900);
        frame.setVisible(true);
        frames.add(frame);

        frames.setVisible(true);

    }
}
