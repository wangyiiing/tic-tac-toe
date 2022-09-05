package tictaktoc;
import javax.swing.*;
import java.awt.*;
public class MainFunction extends JFrame
{
    /**
     * @param args
     */
    public static void main (String[] args) 
    {
    	TicTacToe frame = new TicTacToe();
    	frame.setTitle("TicTacToe");
    	frame.setSize(1000,1050);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	
    	//Récupère la largeur et la hauteur de l'écran
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	int screenWidth = screenSize.width;
    	int screenHeight = screenSize.height;
    	
    	//Obtenir la largeur et la hauteur du cadre
    	Dimension frameSize = frame.getSize();
    	int x = (screenWidth - frameSize.width)/2;
    	int y = (screenHeight - frameSize.height)/2;


    	frame.setLocation(x,y);
    	frame.setVisible(true);
    }
}


						