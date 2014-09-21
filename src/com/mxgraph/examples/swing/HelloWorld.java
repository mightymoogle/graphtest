package com.mxgraph.examples.swing;

import com.mxgraph.io.mxCodec;
import com.mxgraph.layout.*;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.handler.mxKeyboardHandler;
import com.mxgraph.swing.handler.mxRubberband;
import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxUndoManager;
import com.mxgraph.util.mxUtils;
import com.mxgraph.util.mxXmlUtils;
import com.mxgraph.util.png.mxPngEncodeParam;
import com.mxgraph.util.png.mxPngImageEncoder;
import com.mxgraph.view.mxGraph;
import graphtest.MatrixTools;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;
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
            for (int i=0; i<5; i++) {                
                
                list.add(graph.insertVertex(parent, null, String.valueOf(i), 0, 0, 40,40, style));                                                                
            }
            
             for (int i=0; i<5; i++) {                
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
    
    protected void saveXmlPng(String filename,
				Color bg) throws IOException
		{
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
			param.setCompressedText(new String[] { "mxGraphModel", xml });

			// Saves as a PNG file
			FileOutputStream outputStream = new FileOutputStream(new File(
					filename));
			try
			{
				mxPngImageEncoder encoder = new mxPngImageEncoder(outputStream,
						param);

				if (image != null)
				{
					encoder.encode(image);

				//	editor.setModified(false);
				//	editor.setCurrentFile(new File(filename));
				}
				else
				{
					JOptionPane.showMessageDialog(graphComponent,
							mxResources.get("noImageData"));
				}
			}
			finally
			{
				outputStream.close();
			}
		}
    
    
    public void save() {
        try {
        saveXmlPng("C:\\ss.png", Color.WHITE);
        toMatrix(null);        
                       
        } catch (Exception e) {
        e.printStackTrace();}
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
    
    
    public int[][] toMatrix(String[] resultNames) {
        
        mxGraph graph = graphComponent.getGraph();
                
        Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
        
        int len = vertices.length;
        
        int[][] matrix = new int[len][len];
        resultNames = new String[len];
        
        for (int i=0;i<len; i++) {
            resultNames[i] = (String)((mxCell)vertices[i]).getValue();
        }
        
        
        //Get all vertices
        for (int i=0; i<len; i++) { 
            
             mxCell one = ((mxCell)vertices[i]);
             
           for (int j=0; j<len; j++) {       
            
               
                mxCell two = ((mxCell)vertices[j]);
                
               //Now check vertices
                //Source-Target-Directed
                if (graph.getEdgesBetween(one,two,true).length>0) {
                    
                    matrix[i][j]=1;
                    System.out.println(""+one.getValue()+"->"+two.getValue());
                    
                }
                
        }           
           
        }
        
        System.out.println("Base matrix"); 
      MatrixTools.printMatrix(matrix, resultNames);
        
      
      int counter = 0;      
      int[][]m2 = matrix;
      
      //At first it is the base matrix, will add other later
      int[][] end = matrix;
      
      //FIX 10 to "is equals to the last one or numbers just grow"
      while (counter<10 && !MatrixTools.isMatrixZeroOnly(m2)) {
         System.out.println("Matrix "+counter);
         m2=MatrixTools.multiply(m2, matrix);              
         
         MatrixTools.printMatrix(m2, resultNames);     
         
         //Add all 1
         end = MatrixTools.specialAdd(end, m2);
         counter++;       
                  
      }
      
        System.out.println("FINAL MATRIX");
         MatrixTools.printMatrix(end, resultNames); 
        return null;
    }
    
    public void loadMatrix(int x[][]) {
        
        return;
        
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
