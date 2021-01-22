import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

// View
class GamePanel extends JPanel implements Observer {
    private ModelObservable model;
    private GameController cont;
    int i;
    JButton b2 = new JButton("NO");
    JButton b = new JButton("YES");
    JLabel l = new JLabel("CONTINUE?");
    JLabel score1;
    JLabel score2;
    private Image birdImage = Toolkit.getDefaultToolkit().getImage("../images/bird1.3.png");
    private Image dokanImage = Toolkit.getDefaultToolkit().getImage("../images/dokannmiki.png");
    private Image sakippoImage = Toolkit.getDefaultToolkit().getImage("../images/dokannsaki.png");
    private Image haikeiImage = Toolkit.getDefaultToolkit().getImage("../images/sunset.jpg");

    public GamePanel(ModelObservable mo, GameController co){
        this.setBackground(Color.WHITE);
        model = mo;
        cont = co;
        model.addObserver(this);
        b.addActionListener(cont);
        b2.addActionListener(cont);
        this.addMouseListener(cont);
        this.addKeyListener(cont);
        this.setFocusable(true);
        // b.setForeground(Color.RED);
        // b.setOpaque(true);
        b.setBackground(Color.WHITE);
        b2.setBackground(Color.WHITE);
        l.setForeground(Color.white);
        b.setFont(new Font("MS ゴシック", Font.BOLD, 12));
        b2.setFont(new Font("MS ゴシック", Font.BOLD, 12));
        l.setFont(new Font("MS ゴシック", Font.BOLD, 22));
        score1 = new JLabel("SCORE:" + model.getScore());
        score1.setFont(new Font("Arial", Font.ITALIC, 18));
        score1.setForeground(Color.white);
        score2 = new JLabel("SCORE:" + model.getScore());
        score2.setFont(new Font("Arial", Font.ITALIC, 18));
        score2.setForeground(Color.white);
    }

    public void scoreupdate(){
        score1.setText("SCORE:" + model.getScore());
        score2.setText("SCORE:" + model.getScore());
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        scoreupdate();
        score2.setBounds(getSize().width*5/7, 10, getSize().width, getSize().height/12);
        this.add(score2);
        g.drawImage(haikeiImage,
                    0,
                    0,
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
            g.drawImage(dokanImage,
                        (int)upperDokan.getX(),
                        (int)upperDokan.getY(),
                        (int)upperDokan.getWidth(),
                        (int)upperDokan.getHeight(),
                        this);
            
            g.drawImage(sakippoImage,
                        (int)upperDokan.getX() - 2,
                        (int)upperDokan.getY() + (int)upperDokan.getHeight()-20,
                        (int)upperDokan.getWidth() + 5,
                        20,
                        this);

            //model.getLowerDokan().get(i).draw(g);
            Dokan lowerDokan = model.getLowerDokan().get(i);
            g.drawImage(dokanImage,
                        (int)lowerDokan.getX(),
                        (int)lowerDokan.getY(),
                        (int)lowerDokan.getWidth(),
                        (int)lowerDokan.getHeight(),
                        this);

            g.drawImage(sakippoImage,
                        (int)lowerDokan.getX() - 2,
                        (int)lowerDokan.getY(),
                        (int)lowerDokan.getWidth() + 5,
                        20,
                        this);
        }
        g.setColor(new Color(87, 65, 41));
        g.fillRect(0, ModelObservable.SCREEN_HEIGHT-60, ModelObservable.SCREEN_WIDTH, ModelObservable.SCREEN_HEIGHT);
        g.setColor(Color.BLACK);
        if(model.getGameOverFlag()){
            g.fillRect(getSize().width*2/9, getSize().height/6, getSize().width*5/9, getSize().height/3);
            b.setBounds(getSize().width/4 + getSize().width/12,getSize().height*2/5,60,30);
            b2.setBounds(getSize().width/2,getSize().height*2/5,60,30);
            l.setBounds(getSize().width/3, getSize().height/5 + 10, getSize().width, getSize().height/12);
            score1.setBounds(getSize().width*2/5 - 15, getSize().height/5, 
            getSize().width, getSize().height/5);
            scoreupdate();
            this.add(b);
            this.add(b2);
            this.add(l);
            this.add(score1);
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
    private JPanel cardPanel;
    private CardLayout layout;
    public GameController(ModelObservable model,JPanel cardPanel,CardLayout layout){
        this.model = model;
        this.cardPanel = cardPanel;
        this.layout = layout;
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
        if(ev.getSource()==panel.b){
        panel.remove(panel.score1);
        panel.remove(panel.score2);
        panel.remove(panel.b);
        panel.remove(panel.b2);
        panel.remove(panel.l);
        model.setStartFlag(true);
	    model.setTime(0);
	    model.getBird().setY0asY();
        if(model.getGameOverFlag()){
            model.init();
            System.out.println("---reset---");
        }
    }else if(ev.getSource()==panel.b2){
        layout.show(cardPanel, "start");
        panel.remove(panel.score1);
        panel.remove(panel.score1);
        panel.remove(panel.b);
        panel.remove(panel.b2);
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
}
