package multiroom;

import java.awt.Color;



import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JLabel;

import javax.swing.JList;

import javax.swing.JPanel;

import javax.swing.JScrollPane;

import javax.swing.JTextArea;

import javax.swing.JTextField;



public class ChatClient extends JFrame{

   //채팅방

   JTextField sendTF;

   JLabel la_msg;

   

   JTextArea ta;

   JScrollPane sp_ta,sp_list;   	

   

   JList<String> li_inwon;

   JButton bt_start,bt_start2p,bt_exit;   

   

   JPanel p;	

   public ChatClient() {

	  setTitle("채팅방");

	  sendTF = new JTextField(15);	  

	  la_msg = new JLabel("Message");

	  	  

	  ta = new JTextArea();

	    ta.setLineWrap(true);//TextArea 가로길이를 벗어나는 text발생시 자동 줄바꿈

	  li_inwon = new JList<String>();

	  

	  sp_ta = new JScrollPane(ta);

	  sp_list = new JScrollPane(li_inwon);

	  	  

	  bt_start = new JButton("시작하기2p");

	  bt_exit = new JButton("나가기");

	  bt_start2p = new JButton("시작하기");
	  

	  p = new JPanel();

	  

	  sp_ta.setBounds(10,10,380,390); 

	  la_msg.setBounds(10,410,60,30); 

	  sendTF.setBounds(70,410,320,30); 

	  

	  sp_list.setBounds(400,10,120,250); 

	  bt_start.setBounds(402,328,120,30); 

	  bt_exit.setBounds(400,410,120,30); 

	  bt_start2p.setBounds(402, 370, 120, 30);

	  p.setLayout(null);

	  p.setBackground(Color.PINK);

	  p.add(sp_ta);

	  p.add(la_msg);

	  p.add(sendTF);

	  p.add(sp_list);

	  p.add(bt_start);

	  p.add(bt_exit);

	  p.add(bt_start2p);

	  getContentPane().add(p);
	  


	 

	  setBounds(300,200,550,500);

	  //setVisible(true);

	  sendTF.requestFocus();	  

	  

   }//생성자   
}

