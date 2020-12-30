import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// view
class StartPanel extends JPanel {
    ModelObservable model;
    StartController cont;
    JButton b;
    public StartPanel(ModelObservable mo, StartController co){
        model = mo;
        cont = co;
        b = new JButton("Start!!!!!!!");
        b.addActionListener(cont);
        b.setActionCommand("start game");
        this.add(b);
    }
}

// controller
class StartController implements ActionListener {
    ModelObservable model;
    StartPanel panel;
    JPanel cardPanel;
    CardLayout layout;
    public StartController(ModelObservable model, JPanel cardPanel, CardLayout layout){
        this.model = model;
        this.cardPanel = cardPanel;
        this.layout = layout;
    }
    public void setPanel(StartPanel pa){
        panel = pa;
    }
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand() == "start game"){
            layout.show(cardPanel, "game");
        }
    }
}