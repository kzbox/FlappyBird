import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// View
class ViewPanel extends JPanel implements Observer {
    private ModelObservable model;
    private Controller cont;
    public ViewPanel(ModelObservable mo, Controller co){
        this.setBackground(Color.WHITE);
        model = mo;
        cont = co;
        model.addObserver(this);
        this.addMouseListener(cont);
        this.addKeyListener(cont);
        this.setFocusable(true);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(model.getGameOverFlag()){
            g.fillRect(100, 100, 200, 200);
        }
        model.getBird().draw(g);
        for(int i = 0; i < model.DOKAN_BUF; i++){
            model.getUpperDokan().get(i).draw(g);
            model.getLowerDokan().get(i).draw(g);
        }
    }
    public void update(Observable o, Object obj){
        repaint();
    }
}
