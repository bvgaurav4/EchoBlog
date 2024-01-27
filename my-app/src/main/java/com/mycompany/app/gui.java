import javax.swing.JButton ;
import javax.swing.JFrame ;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Toolkit;


public class  gui { 
    public static void  main ( String [ ]  args
    ) { 
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height =(int) screenSize.getHeight();
        JFrame  frame  = new  JFrame ( "My First GUI in java" ) ; 
        JButton  button  = new  JButton ( "Press" ) ; 
        frame . setDefaultCloseOperation ( JFrame . EXIT_ON_CLOSE ) ; 
        frame . setSize ( width, height ) ; 
        frame.setLayout(new FlowLayout()); // set the layout manager
        frame . getContentPane ( ) . add ( button ) ; // Adds Button to content pane of frame
        frame . setVisible ( true ) ; 
        System.out.println("Screen resolution: " + width + "x" + height);
    }
 }