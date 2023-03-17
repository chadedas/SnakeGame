import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
    static final int SCREEN_WIDTH = 1300;
    static final int SCREEN_HEIGHT = 750;
    static final int UNIT_SIZE = 50;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
    static final int DELAY = 175;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    // Gamepanel() เป็นสิ่งแรกที่จะทำงานเป็นอันดับแรก
    GamePanel(){
        //random ถูกสร้างเป็น obj new Random(); เพื่อทำการสุ่มค่าบางอย่าง
        random = new Random();
        //setPreferredSize เซ็ตขนาดของหน้าต่าง GUI เท่ากับ new Dimension(Width,Height)
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        //setBackground ตั้งสีพื้นหลังของเกมส์ Color.Black สีดำ
        this.setBackground(Color.black);
        //setFocusable จะทำงานก็ต่อเมื่อกดปุ่มในโปรแกรมนี้ พับจอไม่ได้
        this.setFocusable(true);
        //addkeyListener รับฟังค่าที่กดเข้ามาโดย new ไปที่คลาส MyKeyAdapter() โดย extends จาก KeyAdapter class
        this.addKeyListener(new MyKeyAdapter());
        // เรียกใช้เมธอด startGame()
        //          newApple();
        //          this.running = true;
        //          timer = new Timer(DELAY,this);
        //          timer.start();
        startGame();
    }
    //เมธอด StartGame()
    public void startGame() {
        //เรียกใช้เมธอด newApple    appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        //                      appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
        newApple();
        //สั่งให้ค่า running = true
        this.running = true;
        // เซ็ตค่า timer = new Timer Delay,This
        timer = new Timer(DELAY,this);
        // เซ็ตค่า timer.start() ให้เริ่มการ Timer
        timer.start();
    }
    //เมธอด paintComponent รับค่า (Graphics g) เข้ามา
    public void paintComponent(Graphics g) {
        //ใช้ super.เพื่อบ่งบอกว่าใช้ paintComponent ของตัวแรกสุดที่สืบทอดมา
        super.paintComponent(g);
        //เรียกใช้เมธอด draw(ส่งค่าgไป)
        draw(g);
    }
    //เมธอด draw
    public void draw(Graphics g) {

        // เงื่อนไข หาก running = true ให้เริ่มคำสั่ง

        if(running) {
			/*
			for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}
			*/
            //ให้ทำการ g.setColor (สีแดง) และ g.fillOval คือการวาดวงกลมสีแดงลงในตำแหน่ง applex appley คือการวาดแอปเปิ้ลนั่นเอง
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // ลูป จำนวน bodyParts ครั้ง ใช้ [i] เป็นตัวเข้าถึง array ตำแหน่งของ x y ของตัวงู ให้ทำการวาดตามตำแหน่งที่งูอยู่ รันคำสั่งไปเรื่อยๆ
            // และตั้งเงื่อนไข if(i == 0) ให้ทำการวาดก่อน i==0 คือหัวงู และ
            for(int i = 0; i< bodyParts;i++) {
                // if คือวาดหัวงู
                if(i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                //else คือการวาดตั้งแต่คองูไปจนถึงหางงู
                else {
                    g.setColor(new Color(128, 219, 97));
                    //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            // หลังจากนั้นให้ทำการเขียนอักษรคำว่า Score : appleEaten คือการแสดงคะแนนในขณะนี้ โดยดึงมาจากค่า appleEaten
            // เมื่องูทำการเอาหัวงูไปถึงแอปเปิ้ล ค่า appleeaten จะเพิ่มขึ้น 1 เสมอ คือคะแนนของการเล่น

            g.setColor(Color.red);
            g.setFont( new Font("Ink Free",Font.BOLD, 40));
            //FontMetrics metrics = getFontMetrics(g.getFont());
            FontMetrics test = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - test.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        }
        // หาก running = false ให้ทำการรัน gameOver ; เกมจบแล้ว ยุติทุกสิ่ง
        else {
            gameOver(g);
        }

    }
    // เมธอด newApple เพื่อทำการสุ่มตำแหน่ง x y ของแอปเปิ้ล เพื่อให้เมธอดอื่นๆได้ทำงานต่อไป
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    // เมธอดการเคลื่อนที่ i = ความยาวตัวงู ถ้าความยาวงูมากกว่า 0 ให้ทำคำสั่ง และ i จะลดลงเรื่อยๆ
    public void move(){
        // ให้ทำการมูฟ ให้ขยับตามตัวไปเรื่อยๆ x[6] = x[5] // x[5] = x[4] ขยับไปเรื่อยๆจนถึง x[0]
        for(int i = bodyParts;i>0;i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];

        }
        // ตรวจจับค่า direction ที่ทำการรับเข้ามา โดยค่าเริ่มต้น char direction = 'R'; คืองูจะไปทางขวาก่อนอันดับแรก
        // จากนั้นจะใช้ keyLister เพื่อรับค่าจากคีย์บอร์ดเข้ามา โดยการทำงานจะอยู่ที่ public class MyKeyAdapter extends KeyAdapter
        // คำสั่งพวกนี้เป็นเพียงตัวทำให้ไปซ้ายขวาหรือหน้าหลัง แต่ public class MyKeyAdapter extends KeyAdapter เป็นตัวดักรับค่าจากคีย์บอร์ด
        // จากนั้นจะส่งมาให้กระบวนการนี้ทำงานต่อไป
        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }
    // เมธอดสำหรับการเก็บแอปเปิ้ลในเกมส์
    public void checkApple() {
        // ถ้า x[0] == appleX , y[0] == appleY หมายความว่า x[0] คือหัวงู และเมื่อใดที่หัวงูมีตำแหน่งเดียวกับแอปเปิ้ลทั้งค่า x , y
        // ให้ทำการเพิ่มความยาวของตัวงู +1 แต้ม Score +1 และ เรียกใช้เมธอด newApple() เพื่อสุ่มให้แอปเปิ้ลไปอยู่ที่อื่นต่อไป
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    // เมธอดสำหรับเช็คว่า หัวงูชนกับตัวงูหรือไม่
    public void checkCollisions() {
        //checks if head collides with body
        // x[0] ห้าม == x[i] และ y[0] ห้ามเท่ากับ y[i] ไม่งั้น running = false คือเกมส์จบ
        for(int i = bodyParts;i>0;i--) {
            if((x[0] == x[i])&& (y[0] == y[i])) {
                running = false;
            }
        }
        //หัวงูห้ามชนกับขอบจอด้านซ้าย  x[0] ตำแหน่งเริ่มต้นที่ 0 จะไม่มีทางน้อยกว่า 0
        if(x[0] < 0) {
            running = false;
        }
        //หัวงูห้ามชนกับขอบจอด้านขวา x[0] ตำแหน่งไม่มีทางมากกว่า ขนาดของความกว้างจอ
        if(x[0] > SCREEN_WIDTH) {
            running = false;
        }
        //หัวงูห้ามชนกับขอบจอด้านบน y[0] ตำแหน่งเริ่มต้นที่ 0 จะไม่มีทางน้อยกว่า 0
        if(y[0] < 0) {
            running = false;
        }
        //หัวงูห้ามชนกับขอบจอด้านล่าง y[0] ตำแหน่งไม่มีทางมากกว่า ขนาดของความสูงจอ
        if(y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        // ถ้า running = false ให้ timer.stop() หยุดการนับ
        if(!running) {
            timer.stop();
        }
    }
    // เมธอดเกมโอเวอร์ ให้ทำการแสดงหน้าจอคำว่า GameOver และ Score : เท่าไร
    public void gameOver(Graphics g) {
        //Score
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        //Game Over text
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
    }
    //ฟังก์ชั่นที่จะทำงานตลอดเวลา
    @Override
    public void actionPerformed(ActionEvent e) {
        // ถ้าเกมส์กำลังเริ่ม running = true
        if(running) {
            // ให้เรียกใช้ฟังก์ชั่น move
            move();
            // ให้ทำการใช้ฟังก์ชั่นเช็ค Apple ตลอด
            checkApple();
            // ให้ทำการใช้ฟังก์ชั่นเช็คว่าหัวชนตัวตลอด
            checkCollisions();
        }
        // เรียกใช้ฟังก์ชั่นการวาดใหม่ของ java
        repaint();
    }
    // ดักจับค่าที่ป้อนเข้ามาทางคีย์บอร์ด คือการบังคับทิศทางของงู
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            // swith case KeyEvent.VK_ปุ่มไหน ซ้ายขวาบนล่าง direction = '' = ส่งค่าไปที่ direction ให้ฟังก์ชั่น move ทำต่อ
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
