import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// View
class GamePanel extends JPanel implements Observer {
    private ModelObservable model;
    private GameController cont;
    int i;
    JButton b = new JButton("YES");
    JLabel l = new JLabel("CONTINUE?");
    private Image birdImage = Toolkit.getDefaultToolkit().getImage("../images/bird_yatsugashira.png");
    private Image sitadokanImage = Toolkit.getDefaultToolkit().getImage("../images/sitadokan.png");
    private Image uedokanImage = Toolkit.getDefaultToolkit().getImage("../images/uedokan.png");
    private Image haikeiImage = Toolkit.getDefaultToolkit().getImage("../images/startview.jpg");
    public GamePanel(ModelObservable mo, GameController co){
        this.setBackground(Color.WHITE);
        model = mo;
        cont = co;
        model.addObserver(this);
        b.addActionListener(cont);
        this.addMouseListener(cont);
        this.addKeyListener(cont);
        this.setFocusable(true);
        // b.setForeground(Color.RED);
        // b.setOpaque(true);
        b.setBackground(Color.PINK);
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(haikeiImage,
                    0,
                    0,
                    model.SCREEN_WIDTH,
                    model.SCREEN_HEIGHT,
                    this);
        //model.getBird().draw(g);
        Bird bird = model.getBird();
        g.drawImage(birdImage,
                    (int)bird.getX(),
                    (int)bird.getY(),
                    (int)bird.getWidth(),
                    (int)bird.getHeight(),
                    this);
        for(i = 0; i < ModelObservable.DOKAN_BUF; i++){
            //model.getUpperDokan().get(i).draw(g);
            Dokan upperDokan = model.getUpperDokan().get(i);
            g.drawImage(uedokanImage,
                        (int)upperDokan.getX(),
                        (int)upperDokan.getY(),
                        (int)upperDokan.getWidth(),
                        (int)upperDokan.getHeight(),
                        this);

            //model.getLowerDokan().get(i).draw(g);
            Dokan lowerDokan = model.getLowerDokan().get(i);
            g.drawImage(sitadokanImage,
                        (int)lowerDokan.getX(),
                        (int)lowerDokan.getY(),
                        (int)lowerDokan.getWidth(),
                        (int)lowerDokan.getHeight(),
                        this);
        }
        g.setColor(Color.black);
        if(model.getGameOverFlag()){
            g.fillRect(model.SCREEN_WIDTH/4, model.SCREEN_HEIGHT/8, model.SCREEN_WIDTH/2, model.SCREEN_HEIGHT/4);
            b.setBounds(model.SCREEN_WIDTH/4 + model.SCREEN_WIDTH/12,240,60,30);
            l.setBounds((model.SCREEN_WIDTH/2)-(model.SCREEN_WIDTH/12), model.SCREEN_HEIGHT/6, model.SCREEN_WIDTH/2, model.SCREEN_HEIGHT/12);
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
class GameController implements MouseListener, KeyListener,ActionListener{
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
    public void actionPerformed(ActionEvent ev){
        panel.remove(panel.b);
        panel.remove(panel.l);
        model.setStartFlag(true);
	    model.setTime(0);
	    model.getBird().setY0asY();
        if(model.getGameOverFlag()){
            model.init();
            System.out.println("---reset---");
        }
    }
}
