import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Switcher extends JFrame {
    JPanel cardPanel = new JPanel();
    CardLayout layout = new CardLayout();
    StartPanel spanel = new StartPanel();
    PlayPanel ppanel = new PlayPanel();
    Cont cont;
    public Switcher(){
        // SwitcherにはcardPanelを追加
        this.add(cardPanel);

        // Switcher(JFrame)自体にCardLayoutの適用
        cardPanel.setLayout(layout);

        // cardPanelに各パネルを追加
        cardPanel.add(spanel);
        cardPanel.add(ppanel);

        cont = new Cont(cardPanel, this);
        cardPanel.addKeyListener(cont);
        cardPanel.setFocusable(true);

    }
    public static void main(String[] args) {
        Switcher s = new Switcher();
        s.setVisible(true);
        s.setSize(800, 800);
    }    
}

class StartPanel extends JPanel {
    JLabel label = new JLabel("hello");
    JButton button = new JButton("click me");
    public StartPanel(){
        this.setLayout(new GridLayout(1,2));
        
        this.add(label);
        this.add(button);
    }
}

class PlayPanel extends JPanel {
    JLabel label = new JLabel("you made it");
    JButton button = new JButton("boiled water");
    public PlayPanel(){
        this.setLayout(new GridLayout(2,1));
        this.add(label);
        this.add(button);
    }
}

// cont 
class Cont implements KeyListener {
    JPanel cardPanel;
    Switcher switcher;
    public Cont(JPanel c, Switcher s){
        cardPanel = c;
        switcher = s;
    }
    @Override
    public void keyPressed(KeyEvent e) {    }
    @Override
    public void keyReleased(KeyEvent e) {    }
    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == 'n'){
            switcher.layout.next(cardPanel);
        }
        else if(e.getKeyChar() == 'p'){
            switcher.layout.previous(cardPanel);
        }
    }
}