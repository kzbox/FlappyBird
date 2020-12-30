import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// controllerに CardLayout と そのレイアウトのJPanel を渡している
//      controller内で画面遷移のコマンド CardLayout.next(JPanel) を使えるようにするため。
//      ちなみにnext以外にもpreviout,first,endがある。

// JFrameにCardLayoutのレイアウトを持ったJPanelをもたせて、そのパネルに2つのJPanelを追加している。

// イメージ図
// Switcher2(JFrame)→cardPanel(JPanel)→StartPanel(JPanel)とPlay(JPanel)
//                        ↑
//                    CardLayout

public class Switcher2 extends JFrame {
    JPanel cardPanel = new JPanel();
    CardLayout layout = new CardLayout();
    StartPanel spanel = new StartPanel();
    PlayPanel ppanel = new PlayPanel();
    Cont cont;
    public Switcher2(){
        // Switcher2にはcardPanelを追加
        this.add(cardPanel);

        // Switcher2(JFrame)自体にCardLayoutの適用
        cardPanel.setLayout(layout);

        // cardPanelに各パネルを追加
        cardPanel.add(spanel);
        cardPanel.add(ppanel);

        cont = new Cont(cardPanel, layout);
        cardPanel.addKeyListener(cont);
        cardPanel.setFocusable(true);

    }
    public static void main(String[] args) {
        Switcher2 s = new Switcher2();
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
    CardLayout layout;
    public Cont(JPanel c, CardLayout layout){
        cardPanel = c;
        this.layout = layout;
    }
    @Override
    public void keyPressed(KeyEvent e) {    }
    @Override
    public void keyReleased(KeyEvent e) {    }
    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == 'n'){
            layout.next(cardPanel);
        }
        else if(e.getKeyChar() == 'p'){
            layout.previous(cardPanel);
        }
    }
}