import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// View

class ViewPanel extends JPanel implements Observer,ActionListener {
    private ModelObservable model;
    private Controller cont;
    int i;
    JButton b = new JButton("YES");
    JLabel l = new JLabel("CONTINUE?");
    public ViewPanel(ModelObservable mo, Controller co){
        this.setBackground(Color.WHITE);
        model = mo;
        cont = co;
        model.addObserver(this);
        this.addMouseListener(cont);
        this.addKeyListener(cont);
        this.setFocusable(true);
        b.addActionListener(this);
    }
    public void actionPerformed(ActionEvent ev){
        this.remove(b);
        this.remove(l);
        model.setStartFlag(true);
	    model.setT(0);
	    model.getBird().setY0asY();
        if(model.getGameOverFlag()){
            model.init();
            System.out.println("---reset---");
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        model.getBird().draw(g);
        for(i = 0; i < ModelObservable.DOKAN_BUF; i++){
            model.getUpperDokan().get(i).draw(g);
            model.getLowerDokan().get(i).draw(g);
        }
        g.setColor(Color.black);
        if(model.getGameOverFlag()){
            g.fillRect(100, 100, 200, 200);
            b.setBounds(130,240,60,30);
            l.setBounds(165,130,180,80);
            l.setForeground(Color.white);
            this.add(b);
            this.add(l);
        }
        
    }
    public void update(Observable o, Object obj){
        repaint();
    }
}
