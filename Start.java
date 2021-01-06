import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// view
class StartPanel extends JPanel {
    ModelObservable model;
    StartController cont;
    JButton b;
    JLabel c;
    public StartPanel(ModelObservable mo, StartController co){
        model = mo;
        cont = co;
        this.setLayout(null);
        b = new JButton("Start!!!!!!!");
        //b.setPreferredSize(new Dimension(200, 100));
        b.setFont(new Font("MS ゴシック", Font.BOLD, 24));
        b.setBounds(100, 300, 200, 100);
        b.setForeground(Color.YELLOW);
        b.setBackground(Color.BLUE);
        b.addActionListener(cont);
        b.setActionCommand("start game");
        c = new JLabel("FlappyBird");
        c.setBounds(130, 150, 200, 50);
        c.setForeground(Color.RED);
        c.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 24));

        this.add(c);
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