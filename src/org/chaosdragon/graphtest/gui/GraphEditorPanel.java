package org.chaosdragon.graphtest.gui;

import org.chaosdragon.graphtest.matrix.Matrix;
import com.mxgraph.io.mxCodec;
import com.mxgraph.layout.*;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.handler.mxKeyboardHandler;
import com.mxgraph.swing.handler.mxRubberband;
import javax.swing.JFrame;
import java.util.*;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.util.png.mxPngEncodeParam;
import com.mxgraph.util.png.mxPngImageEncoder;
import com.mxgraph.view.mxGraph;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.chaosdragon.tools.NaturalOrderComparator;

public class GraphEditorPanel extends JPanel {

    public enum Mode {

        HAND, ARROW, READ_ONLY
    };
    private Mode currentMode;
    private Mode previousMode;
    private int newCounter = 0; //Counts the index for insertion.
    
    public enum Arrange {

        CIRCLE, HIERARCHY, FAST_ORGANIC
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

    private Matrix editMatrix;
    
    
    public void loadMatrix(Matrix matr) {

        editMatrix = matr;
        //nothing to load
        if (matr == null) {
            return;
        }

        setMode(Mode.HAND); //Make editable

        mxGraph graph = graphComponent.getGraph();
        
        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();
        graph.selectAll();
        graph.removeCells();
        
        try {
            String[] names = matr.getIds();
            Object[] vertices = new Object[names.length];
            
            
            for (int i = 0; i < names.length; i++) {
                
                vertices[i]=graph.insertVertex(parent, null, names[i], 0, 0, 40, 40, style);
            }
            
            int[][] connections = matr.getConnections();
            
            for (int i=0; i<connections.length; i++) {
                for (int j=0; j<connections[0].length; j++) {
                    
                    if (connections[i][j]==1) {
                        graph.insertEdge(parent, null, "", vertices[i], vertices[j]);
                    }
                    
                }
            }
            
            
            
            

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            graph.getModel().endUpdate();
        }

        undoMode(); //Get back to whatever mode it was



    }

    private void initEditor() {
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        //generate(graph,parent);
        graphComponent = new mxGraphComponent(graph);

        //graphComponent.setCenterZoom(true);        

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
    
    
    public GraphEditorPanel(Matrix matr, boolean readOnly) {

        super();
        initEditor();


        if (readOnly == true) {


            //LoadMatrix
            loadMatrix(matr);
            //Do usual stuff
            setMode(Mode.READ_ONLY);

        } else {



            loadMatrix(matr);
            setMode(Mode.HAND);

            //The selector thingy
            new mxRubberband(graphComponent);
            //The keyboard handler
            new mxKeyboardHandler(graphComponent);
        }

    }

    public void setMode(Mode m) {

        if (m == null) {
            return;
        }

        previousMode = currentMode;
        currentMode = m;

        mxGraph graph = graphComponent.getGraph();



        if (m == Mode.ARROW) {
            graph.clearSelection();
            graph.setCellsMovable(false);
            graphComponent.setConnectable(true);
            graphComponent.getGraph().setCellsSelectable(false);
            //graphComponent.getGraph().setCellsEditable(true);                
            return;
        }

        if (m == Mode.READ_ONLY) {
            graph.setCellsMovable(false);
            graphComponent.setConnectable(false);
            graphComponent.getGraph().setCellsSelectable(false);
            graphComponent.getGraph().setCellsEditable(false);
            //graphComponent.getGraph().setCellsMovable(false);

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

    public void undoMode() {
        setMode(previousMode);
    }

    protected void saveXmlPng(String filename,
            Color bg) throws IOException {
        mxGraphComponent graphComponent = this.graphComponent;
        mxGraph graph = graphComponent.getGraph();

        // Creates the image for the PNG file
        BufferedImage image = mxCellRenderer.createBufferedImage(graph,
                null, 1, bg, graphComponent.isAntiAlias(), null,
                graphComponent.getCanvas());

        // Creates the URL-encoded XML data
        mxCodec codec = new mxCodec();
        String xml = URLEncoder.encode(
                mxXmlUtils.getXml(codec.encode(graph.getModel())), "UTF-8");
        mxPngEncodeParam param = mxPngEncodeParam
                .getDefaultEncodeParam(image);
        param.setCompressedText(new String[]{"mxGraphModel", xml});

        // Saves as a PNG file
        FileOutputStream outputStream = new FileOutputStream(new File(
                filename));
        try {
            mxPngImageEncoder encoder = new mxPngImageEncoder(outputStream,
                    param);

            if (image != null) {
                encoder.encode(image);

                //	editor.setModified(false);
                //	editor.setCurrentFile(new File(filename));
            } else {
                JOptionPane.showMessageDialog(graphComponent,
                        mxResources.get("noImageData"));
            }
        } finally {
            outputStream.close();
        }
    }

    public void save() {
        try {
            //saveXmlPng("C:\\ss.png", Color.WHITE);
         //   toMatrix().print();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addVertice() {
        mxGraph graph = graphComponent.getGraph();

        graph.getModel().beginUpdate();
        Object parent = graph.getDefaultParent();
        try {
            Object v1 = graph.insertVertex(parent, null, String.valueOf(++newCounter), 0, 0, 40,
                    40, style);

        } finally {
            graph.getModel().endUpdate();
        }

    }
    
    
    public int validateMatrix() {
        
        mxGraph graph = graphComponent.getGraph();
        Object[] vertices = graph.getChildVertices(graph.getDefaultParent());  
        HashSet<String> names= new HashSet<>();
                
       for (int i = 0; i < vertices.length; i++) {
           //Add the names to hasset.
           names.add((String) ((mxCell) vertices[i]).getValue());
        }
       
       if (names.size()!=vertices.length) return -1;
        
       return 0;
        
    }
    

    public void updateMatrix() {

        mxGraph graph = graphComponent.getGraph();

        Object[] vertices = graph.getChildVertices(graph.getDefaultParent());                        
        int len = vertices.length;
        
        //For the matrix object
        int[][] matrix = new int[len][len];
        String[] resultNames = new String[len];
        
        
        //SORTING THE MATRIX
        
        //Ensures 2<10 with strings
        TreeMap<String,Object> map = new TreeMap<>(new NaturalOrderComparator());
                
        for (int i = 0; i < len; i++) {
            String name= (String) ((mxCell) vertices[i]).getValue();
            map.put(name, vertices[i]);          
        }
        int ele=0;
        //REPOSITIONING
        for (Map.Entry<String,Object> m : map.entrySet()) {            
            vertices[ele++]=m.getValue();            
        }
        map = null;
        //SORTED THE MATRIX
        
        
        for (int i = 0; i < len; i++) {
            resultNames[i] = (String) ((mxCell) vertices[i]).getValue();                       
        }
        
        //Get all vertices
        for (int i = 0; i < len; i++) {

            mxCell one = ((mxCell) vertices[i]);

            for (int j = 0; j < len; j++) {


                mxCell two = ((mxCell) vertices[j]);

                //Now check vertices
                //Source-Target-Directed
                if (graph.getEdgesBetween(one, two, true).length > 0) {

                    matrix[i][j] = 1;
                   // System.out.println("" + one.getValue() + "->" + two.getValue());

                }

            }

        }
         
        //Sort matrix
        
        
        editMatrix.setConnections(matrix);
        editMatrix.setIds(resultNames);        
       
    }

    public void autoArrange(Arrange r) {

        mxIGraphLayout layout;
        layout = new mxFastOrganicLayout(graphComponent.getGraph());

        if (r == Arrange.CIRCLE) {
            layout = new mxCircleLayout(graphComponent.getGraph());
        } else {
            if (r == Arrange.HIERARCHY) {
                layout = new mxHierarchicalLayout(graphComponent.getGraph());
            }
        }

        //Flies offscreen
        //mxIGraphLayout layout = new mxFastOrganicLayout(graphComponent.getGraph());        

        try {
            graphComponent.getGraph().getModel().beginUpdate();
            layout.execute(graphComponent.getGraph().getDefaultParent());
        } finally {

//            mxMorphing morph = new mxMorphing(graphComponent, 20, 1.2, 20);
//
//            morph.addListener(mxEvent.DONE, new mxIEventListener() {
//                @Override
//                public void invoke(Object o, mxEventObject eo) {
                    graphComponent.getGraph().getModel().endUpdate();
//                }
//            });
//
//            morph.startAnimation();
                        
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
        
      //  System.out.println(this.getSize().height);
      //  System.out.println(graphComponent.getSize().height);
    }

    public static void main(String[] args) {

        JFrame frames = new JFrame();
        frames.setSize(5000, 5000);

        frames.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GraphEditorPanel frame = new GraphEditorPanel(null, false);
        frame.setSize(1000, 1900);
        frame.setVisible(true);
        frames.add(frame);
        frames.setVisible(true);

    }
}
