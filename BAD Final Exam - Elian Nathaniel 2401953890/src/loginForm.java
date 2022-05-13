import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

	public class loginForm extends JFrame implements ItemListener, ActionListener{
		
		/**
		 * 
		 */
		
		private static final long serialVersionUID = 1L;
		JPanel northPnl, centerPnl, southPnl, usernamePnl, passwordPnl, radioBtnPnl, loginPnl;
		JRadioButton showPass;
		JLabel title, usernameLbl, passwordLbl;
		JTextField username;
		JPasswordField password;
		JButton login;
		String validUname = "Elian",validPass = "nathaniel890";
		
		
		public void initialize(){
			
			//panels
			northPnl = new JPanel();
			centerPnl = new JPanel(new GridLayout(2,2));
			southPnl = new JPanel(new GridLayout(2,1));
			usernamePnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		    passwordPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
		    radioBtnPnl = new JPanel();
			loginPnl = new JPanel();
		    
			//label
		    title = new JLabel("Welcome Binusian");
		    usernameLbl = new JLabel("User Name :");
		    passwordLbl = new JLabel("Password	:");
		    
		    //radio button
		    showPass = new JRadioButton("Show Password");
			showPass.addItemListener(new ItemListener() {
			
				@Override
				public void itemStateChanged(ItemEvent e) {
					 if (e.getStateChange() == ItemEvent.SELECTED) {
				            password.setEchoChar((char) 0);
				        } else {
				            password.setEchoChar('*');
				        }		
				}	 		
			});
			radioBtnPnl.add(showPass);
			
			//fields
			username = new JTextField();
			username.setPreferredSize(new Dimension(200,20));
			password = new JPasswordField();
			password.setPreferredSize(new Dimension(200,20));
			
			//button
			login = new JButton("Login");
			login.setPreferredSize(new Dimension(65,25));	
			login.addActionListener(this);
			loginPnl.add(login);
			
			//adding to panels
			usernamePnl.add(usernameLbl);
			usernamePnl.add(username);
			passwordPnl.add(passwordLbl);
			passwordPnl.add(password);
			
			northPnl.add(title);
			centerPnl.add(usernamePnl);
			centerPnl.add(passwordPnl);
			southPnl.add(radioBtnPnl);
			southPnl.add(loginPnl);
			
			//add panels
			add(northPnl, BorderLayout.NORTH);
			add(centerPnl, BorderLayout.CENTER);
			add(southPnl, BorderLayout.SOUTH);
			
		}
		
		public loginForm() {	
				initialize();
				
				setVisible(true);
				setSize(300,200);
				setLocationRelativeTo(null);
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				setResizable(false);
				setTitle("Login");
			
		}
			 
		@Override
		public void itemStateChanged(ItemEvent e) {	
			 if (e.getStateChange() == ItemEvent.SELECTED) {
		            password.setEchoChar((char) 0);
		        } else {
		            password.setEchoChar('*');
		        }
		}

		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == login) {
				
				if(username.getText().length() > 20 || password.getText().length() > 20) {
					JOptionPane.showMessageDialog(null, "Fields can't be more than 20 characters");
					
				} 
				
				if(!username.getText().equals(validUname) || !password.getText().equals(validPass)) {
					JOptionPane.showMessageDialog(null, "Incorrect Information");
				} else {
					JOptionPane.showMessageDialog(null, "Login Success");
					this.dispose();
				}
			}
				
			
		}
	
	}
