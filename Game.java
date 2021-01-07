import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// View
class GamePanel extends JPanel implements Observer,ActionListener {
    private ModelObservable model;
    private GameController cont;
    int i;
    JButton b = new JButton("YES");
    JLabel l = new JLabel("CONTINUE?");
    public GamePanel(ModelObservable mo, GameController co){
        this.setBackground(Color.WHITE);
        model = mo;
        cont = co;
        model.addObserver(this);
        this.addMouseListener(cont);
        this.addKeyListener(cont);
        this.setFocusable(true);
        b.addActionListener(this);
        // b.setForeground(Color.RED);
        // b.setOpaque(true);
        b.setBackground(Color.PINK);
    }
    public void actionPerformed(ActionEvent ev){
        this.remove(b);
        this.remove(l);
        model.setStartFlag(true);
	    model.setTime(0);
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


// Controller
class GameController implements MouseListener, KeyListener{
    private ModelObservable model;
    private GamePanel panel;
    public GameController(ModelObservable mo){
        model = mo;
    }
    public void setPanel(GamePanel p){
        panel = p;
    }
    // publicメソッド
    public void mouseClicked(MouseEvent e){ }
    public void mousePressed(MouseEvent e){
        model.flyBird();
    }
    public void mouseReleased(MouseEvent e){ }
    public void mouseEntered(MouseEvent e){ }
    public void mouseExited(MouseEvent e){ }
    public void keyPressed(KeyEvent e){ 
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            model.flyBird();
        }
    }
    public void keyReleased(KeyEvent e){ }
    public void keyTyped(KeyEvent e){ }
}
