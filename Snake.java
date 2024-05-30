/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  

public class Snake extends JFrame{

       Snake()
       {
           add(new Board(this));
           pack();
           setVisible(true);
           setTitle("Snake Game");
           setResizable(false);
          setLocationRelativeTo(null);
           ImageIcon icon=new ImageIcon(ClassLoader.getSystemResource("icons/game.png"));
          setIconImage(icon.getImage());
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       }
    public static void main(String[] args) {
        // TODO code application logic here
         Snake ob=new Snake();
    }
    
}




   

 class Board extends JPanel implements ActionListener{
    private int dots;
    private Image apple;
    private Image head;
    private Image dot;
    
    private int score=0;
    
    private int applex;
    private int appley;
    private final int alldots=900;
    private int dotsize=10;
    
    private final  int x[]=new int[alldots];
    private final int y[]=new int[alldots];
                                                                                  
    private final int randompos=29;
    
    private  Timer timer ;
    
    private boolean leftdirection =false;
    private boolean rightdirection =true;
    private boolean updirection =false;
    private boolean downdirection =false;
    JFrame parentFrame;
    private boolean ingame=true;
     JButton restart;
    Board(JFrame parentFrame)
    {
        
           this.parentFrame = parentFrame;
           addKeyListener(new TAdapter());
     setBackground(Color.BLACK);
     setFocusable(true);
     setPreferredSize(new Dimension(500,500));
    
     initGame();
     loadImages();
     
     
    }
    public void loadImages()
    {
      ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/apple.png"));
      apple=i1.getImage();
      
      ImageIcon i2=new ImageIcon(ClassLoader.getSystemResource("icons/dot.png"));
        dot=i2.getImage();
      ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("icons/head.png"));
         head=i3.getImage();
    }
    public void initGame()
    {
        dots=3;
        for(int i=0;i<dots;i++)
        {
            y[i]=50;
            x[i]=50-i*dotsize;
        }
        locateapple();
         timer =new Timer(140,this);
        timer.start();
    }
    public void locateapple()
    {
      int r=(int)(Math.random()*randompos);
     applex=r*dotsize;
      
         r=(int)(Math.random()*randompos);
       appley=r*dotsize;
    }
    public void paintComponent(Graphics g)
    {
          super.paintComponent(g);
          draw(g);
    }
    
    public void draw(Graphics g)
    {
        if(ingame)
        {
        g.drawImage(apple, applex, appley, this);
              for(int i=0;i<dots;i++)
              {
              if(i==0)
              {
                  g.drawImage(head, x[i], y[i], this);
              }
              else
              {
                  g.drawImage(dot, x[i], y[i], this);
               }
              }
              Toolkit.getDefaultToolkit().sync();
        }else{
             gameover(g);
        }
        }
    
    public void gameover(Graphics g)
    {
    String msg="GAME OVER";
     String scorecard="SCORE:-"+score;
    Font font=new Font("SAN_SARIF",Font.BOLD,44);
    FontMetrics metrices=getFontMetrics(font); 
    g.setColor(Color.RED);
    g.setFont(font);
      g.drawString(msg, (500-metrices.stringWidth(msg))/2, 400/2);
        g.drawString(scorecard, (500-metrices.stringWidth(scorecard))/2, 500/2);
        
         restart =new JButton ("RESTART");
        restart.setBounds(175,300,150,30);
        restart.setBackground(Color.GREEN); // Set background color to green
        restart.setForeground(Color.BLACK);  
        restart.setPreferredSize(new Dimension(150, 50)); // Set preferred size
        restart.setFont(new Font("Arial", Font.BOLD, 18)); 
        restart.addActionListener(this);
        add(restart);
    }
    public void move()
    {
       for(int i=dots;i>0;i--)
       {
       x[i]=x[i-1];
       y[i]=y[i-1];
       }
       
       if(leftdirection)
       {
          x[0]=x[0]-dotsize;
       }
       if(rightdirection)
       {
          x[0]=x[0]+dotsize;
       }
       if(updirection)
       {
          y[0]=y[0]-dotsize;
       }
       if(downdirection)
       {
          y[0]=y[0]+dotsize;
       }
       
     
    }
    
    public void checkapple()
    {
       if((x[0]==applex)&& (y[0]==appley))
       {
         dots++;
         score++;
         locateapple();
       }
    }
    public void checkcollision()
    {
        for( int i=dots;i>0;i--)
        {   
                   if((i>4)&&(x[0]==x[i])&& (y[0]==y[i]))
                   {
                      ingame=false;
                   }
        }
        if(y[0]>=500)
        {
            ingame=false;
        }
        if(x[0]>=500)
        {
            ingame=false;
        }if(y[0]<0)
        {
            ingame=false;
        }if(x[0]<0)
        {
            ingame=false;
        }
        
        if(!ingame)
        {
            timer.stop();
        }
        
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        if(ingame)
        {
        checkapple();
        checkcollision();
      move();
        }
      
        if(ae.getSource()==restart)
        {
//           dispose();
              parentFrame.dispose();
           new Snake();
        }
        
      repaint();
    }
    
   
 
     
     
    public class  TAdapter extends KeyAdapter{
            @Override
                 public void keyPressed(KeyEvent e)
                 {
               int key=e.getKeyCode();
               
               if(key ==KeyEvent.VK_LEFT && (!rightdirection))
                   
               {
                      leftdirection=true;
                      updirection=false;
                      downdirection=false;
               }
               if(key ==KeyEvent.VK_RIGHT && (!leftdirection))
                   
               {
                      rightdirection=true;
                      updirection=false;
                      downdirection=false;
               }
               if(key ==KeyEvent.VK_UP && (!downdirection))
                   
               {
                      updirection=true;
                      leftdirection=false;
                      rightdirection=false;
               }
               if(key ==KeyEvent.VK_DOWN && (!updirection))
                   
               {
                      downdirection=true;
                      leftdirection=false;
                      rightdirection=false;
               }
                   }
            }
    
   
}
