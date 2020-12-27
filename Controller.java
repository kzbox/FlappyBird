import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// Controller
class Controller implements MouseListener, KeyListener{
    private ModelObservable model;
    private ViewPanel view;
    public Controller(ModelObservable mo){
        model = mo;
    }
    public void mouseClicked(MouseEvent e){ }
    public void mousePressed(MouseEvent e){
        model.setStartFlag(true);
        model.setT(0);
        model.getBird().setY0asY();
    }
    public void mouseReleased(MouseEvent e){ }
    public void mouseEntered(MouseEvent e){ }
    public void mouseExited(MouseEvent e){ }
    public void keyPressed(KeyEvent e){ 
	if(e.getKeyCode() == KeyEvent.VK_SPACE){
	    model.setStartFlag(true);
	    model.setT(0);
	    model.getBird().setY0asY();
	}
        if(model.getGameOverFlag()){
            model.init();
            System.out.println("---reset---");
        }
    }
    public void keyReleased(KeyEvent e){ }
    public void keyTyped(KeyEvent e){ }
}
