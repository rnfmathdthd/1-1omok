package multiroom;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.JButton;

class Omok_Board extends Canvas {
 public static final int BLACK = 1, WHITE = -1;
 public static final int size = 30; // �� ĭ ũ��
 public static final int num = 20; // ���� �� �ִ� �� �ִ� ����
 public static final int x = 30; // ���� ��ġ x
 public static final int y = 30; // ���� ��ġ y
 public static final int width = 30; // �� ũ�� : ����
 public static final int height = 30; // �� ũ�� : ����
 
 // true�̸� ����ڰ� ���� ���� �� �ִ� ���¸� �ǹ��ϰ�,
 // false�̸� ����ڰ� ���� ���� �� ���� ���¸� �ǹ��Ѵ�.
 private int color; // ���� �� ��
 private int client_color; // Ŭ���̾�Ʈ �� ��
 private String info = "���� ���"; // ��� ��Ʈ��
 private String str_color; // ������ ���� ��� ��Ʈ��
 private boolean enable = false; // ������ Ȱ��ȭ ���θ� ����
 private PrintWriter writer; // ������� �޽����� �����ϱ� ���� ��Ʈ��
 private int stone[][] = new int[num][num]; // �� ���� �� ����
  
 Omok_Board(){
  this.setVisible(true);
  this.setBackground(new Color(200, 200, 100));
  
  addMouseListener(new MouseAdapter() {
   public void mousePressed(MouseEvent me) { // ���콺�� ������
    if(!enable) { // �������� ��Ȱ��ȭ�� ���
     return;
    }
    else if(me.getX()>x+size*(num-1) || me.getY()>y+size*(num-1)) { // ������ ������ ���� ���� ���(�Ʒ�, ������)
     return;
    }
    else if(me.getX()<(x-size/2)|| me.getY()<(y-size/2)) // ������ ������ ���� ���� ���(��, ����)
     return;
    else if(stone[(me.getX()-x+size/2)/size][(me.getY()-y+size/2)/size] != 0) // ���� ���� ������
     return;
    else { // �� ����
     stone[(me.getX()-x+size/2)/size][(me.getY()-y+size/2)/size] = color;
    }
    
    // Ŭ���̾�Ʈ���� �� ���� ��ġ�� ����
    writer.println("[STONE]" + (me.getX()-x+size/2)/size + "," + (me.getY()-y+size/2)/size);
    
    if(check(color)==true) { // ���� ���� üũ
     repaint();
     JOptionPane.showMessageDialog(null, "���� �̰��"); // �¸� �޼��� ���
     writer.println("[LOSE]����� �����ϴ�."); // ���濡�� �й踦 �˸�
     info="��밡 ���� �����ϰ� �ֽ��ϴ�."; // �޼��� ����

     reset();
     return;
    }
    info="������� ���� ���� �ֽ��ϴ�.";
    repaint();

    enable = false; // �� ���� ����
   }
  });
 }

 boolean check(int color) { // ���� üũ �޼ҵ�
  for(int i=0;i<num-4;i++) {
   for(int j=0;j<num;j++) {
    if(stone[i][j]==color)
     if(stone[i+1][j]==color)
      if(stone[i+2][j]==color)
       if(stone[i+3][j]==color)
        if(stone[i+4][j]==color)
         return true;
   }
  }
  for(int i=0;i<num;i++) {
   for(int j=0;j<num-4;j++) {
    if(stone[i][j]==color)
     if(stone[i][j+1]==color)
      if(stone[i][j+2]==color)
       if(stone[i][j+3]==color)
        if(stone[i][j+4]==color)
         return true;
   }
  }
  for(int i=0;i<num-4;i++) {
   for(int j=0;j<num-4;j++) {
    if(stone[i][j]==color)
     if(stone[i+1][j+1]==color)
      if(stone[i+2][j+2]==color)
       if(stone[i+3][j+3]==color)
        if(stone[i+4][j+4]==color)
         return true;
   }
  }
  for(int i=19;i>3;i--) {
   for(int j=0;j<num-4;j++) {
    if(stone[i][j]==color)
     if(stone[i-1][j+1]==color)
      if(stone[i-2][j+2]==color)
       if(stone[i-3][j+3]==color)
        if(stone[i-4][j+4]==color)
         return true;
   }
  }
  return false;
 }
 
 void reset() { // ���� ���� �� �ʱ�ȭ
  for(int i=0;i<num;i++) {
   for(int j=0;j<num;j++) {
    stone[i][j]=0;
   }
  }
  repaint();
  setEnable(false); // �ٵ��� ��Ȱ��ȭ
 }
 
 public void paint(Graphics g) {
  g.clearRect(0, 0, getWidth(), getHeight());
  
  g.setColor(Color.RED);
  g.drawString(info, 30, 20); // Info ��� �κ�
  
  for(int i=0;i<num;i++) { // ������ �׸���.
   g.setColor(Color.BLACK); // ������
   g.drawLine(x,y+(size*i),x+(size*(num-1)),y+(size*i)); // ���μ�
   g.drawLine(x+(size*i),y, x+(size*i),y+(size*(num-1))); // ���μ�
  }
  for(int i=0;i<num;i++) { // ���� ���´�.
   for(int j=0;j<num;j++) {
    if(stone[i][j]==BLACK) { // �浹�� �������� ���
     g.setColor(Color.BLACK);
     g.fillOval((x-size/2)+i*size, (y-size/2)+j*size, width, height);
    }
    else if(stone[i][j]==WHITE) { // �鵹�� �������� ���
     g.setColor(Color.WHITE);
     g.fillOval((x-size/2)+i*size, (y-size/2)+j*size, width, height);
    }
   }
  }
 }

 
 public void stoneSelect() { // �� ���� �޼���
  if(JOptionPane.showOptionDialog(this, "��� �� �� �ϳ� �����ϼ���", "�� ���� ����",
    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,
    null, new String[]{"��", "��"},"��") == 0) { // �� ���ý�
   setColor(BLACK,WHITE);
   writer.println("[COLOR]" + WHITE + "," + BLACK);
  }
  else { // �� ���ý�
   setColor(WHITE,BLACK);
   writer.println("[COLOR]"+ BLACK + "," + WHITE);
  }
 }
 
 public void setEnable(boolean enable) { // �ٵ��� Ȱ��ȭ ���� �޼ҵ�
  this.enable = enable;
 }
 
 public void setWriter(PrintWriter writer) { // Ŭ���̾�Ʈ���� ������ ����ϴ� PrintWriter
  this.writer = writer;
 }
 
 public void setColor(int color,int client_color) { // �� �� ����
  this.color = color; // ������ �� �� ����
  this.client_color = client_color; // Ŭ���̾�Ʈ�� �� �� ����
  
  if(color==BLACK){ // ���� ���� ��� ����
   setEnable(true);
   info="�� ������ �Ϸ�Ǿ����ϴ� - ��(����)";
   str_color = "��";
  }
  else { // �� ���� ��� �İ�
   setEnable(false);
   info="�� ������ �Ϸ�Ǿ����ϴ� - ��(�İ�)";
   str_color = "��";
  }
  repaint();
 }
 
 public void setInfo(String info) {
  this.info = info;
 }
 
 public void putOpponent(int x, int y) { // Ŭ���̾�Ʈ�� ���� ���´�.
  stone[x][y] = client_color;
  info="���� ��(" + str_color + ") - ���� ��������";
  repaint();
  
  if(check(client_color)==true) { // Ŭ���̾�Ʈ ���� üũ
   repaint();
   JOptionPane.showMessageDialog(null, "���� ����"); // �й� �޼��� ���
   writer.println("[WIN]����� �̰���ϴ�."); // Ŭ���̾�Ʈ���� �¸��� �˸�
   info="���� ����";
   reset();
   stoneSelect(); // �� �� ���� ȭ�� ȣ��
  }
 }
}

public class Omok_Server extends Frame {
 private Omok_Board board = new Omok_Board(); // ������ ��ü
 ServerSocket serverSocket = null;
 Socket socket = null;
 private BufferedReader reader; // �Է� ��Ʈ��
 private PrintWriter writer; // ��� ��Ʈ��
 /**
  * @wbp.nonvisual location=57,264
  */
 
 public Omok_Server(String title) { // ������
  super(title);

  add(board);
  
  addWindowListener(new WindowAdapter() {
   public void windowClosing(WindowEvent we) {
    System.exit(0);
   }
  });
 }
 
 private void connect() { // ����
  try {
   serverSocket = new ServerSocket(5000);
   
   socket = serverSocket.accept();
   reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
   writer = new PrintWriter(socket.getOutputStream(),true);
   board.setWriter(writer);
   board.setInfo("��밡 ���� �����ϰ� �ֽ��ϴ�.");
   
   String msg;
   
   while((msg = reader.readLine()) != null) {
    if (msg.startsWith("[STONE]")) { // ������ ���� ������ ���
     msg=msg.substring(7);
     board.setEnable(true); // ������ ������ ����
     board.putOpponent(Integer.parseInt(msg.substring(0, msg.indexOf(",")))
       ,Integer.parseInt(msg.substring(msg.indexOf(",") + 1)));
    }
    else if (msg.startsWith("[COLOR]")) { // Ŭ���̾�Ʈ�� �� ���� �������� ���
     msg=msg.substring(7);
     board.setColor(Integer.parseInt(msg.substring(0, msg.indexOf(",")))
       ,Integer.parseInt(msg.substring(msg.indexOf(",") + 1)));
    }
   }
  }
  catch(Exception e) {
   System.out.println(e.getMessage());
  }
  finally {
   try {
    serverSocket.close();
    socket.close();
   }
   catch(Exception e) { }
  }
 }
 public static void play() {

  Omok_Server server = new Omok_Server("��Ʈ��ũ ���� ���� - ����"); // ������ ��ü
  server.setBounds(0,0,650,670);
  server.setVisible(true);
  server.connect();
 }
}