import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class operatorLoginFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	JPanel northPnl, centerPnl, usernamePnl, passwordPnl, loginButtonPnl;
	JLabel title, usernameLbl, passwordLbl;
	JTextField username;
	JPasswordField password;
	JButton login,back;
	static String operatorID;
	connect con;
	
	public void initial() {
		con = new connect();
		//panel
		northPnl = new JPanel();
		centerPnl = new JPanel(new GridLayout(3,2));
		usernamePnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    passwordPnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    loginButtonPnl = new JPanel(new GridLayout(1, 2,10,0));
		
		//label
		title = new JLabel("Operator Login");
		usernameLbl = new JLabel("Username");
		passwordLbl = new JLabel("Password");
		northPnl.add(title);
		
		//text field
		username = new JTextField();
		username.setPreferredSize(new Dimension(200,20));
		usernamePnl.add(usernameLbl);usernamePnl.add(username);
		
		//password
		password = new JPasswordField();
		password.setPreferredSize(new Dimension(200,20));
		passwordPnl.add(passwordLbl);passwordPnl.add(password);
		
		//Button
		login = new JButton("Login");
		login.setPreferredSize(new Dimension(65,25));	
		login.addActionListener(this);				
		back = new JButton("Back");		
		back.setPreferredSize(new Dimension(65,25));
		back.addActionListener(this);

		//login button panel add
		centerPnl.add(usernamePnl);
		centerPnl.add(passwordPnl);
		loginButtonPnl.add(back);
		loginButtonPnl.add(login);
				
		//add panels
		add(northPnl, BorderLayout.NORTH);
		add(centerPnl, BorderLayout.CENTER);
		add(loginButtonPnl, BorderLayout.SOUTH);
		
	}
	
	public operatorLoginFrame() {
		initial();
		
		setVisible(true);
		setSize(300,200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Operator Login");
	}
	
	public static String printID() {
		System.out.print(operatorID);
		return operatorID;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == back) {
			this.dispose();
		} else if(e.getSource() == login) {
			ResultSet rs = null;
			String temporaryUsername = username.getText();
			String temporaryPassword = String.valueOf(password.getPassword());
			boolean correctUsername = false;
			boolean correctPassword = false;
								
			try {
				rs = con.runQuery("SELECT operatorName FROM operator");
				while(rs.next()){
					String name = rs.getString("operatorName");
					if(temporaryUsername.equals(name)) {
						correctUsername = true;
						break;
					} else {
							
					}
				}
				if(correctUsername == true) {
					rs = con.runQuery("SELECT operatorPass FROM operator WHERE operatorName = '"+temporaryUsername+"'");
					while(rs.next()) {
						String pass = rs.getString("operatorPass");
						if(temporaryPassword.equals(pass)) {
							correctPassword = true;
							break;
						}
					} 
				}
			if (correctUsername && correctPassword == true) {
					rs = con.runQuery("SELECT operatorID FROM operator WHERE operatorName = '"+temporaryUsername+"'");
					
					while(rs.next()) {
						operatorID = rs.getString("operatorID");
						
					}
					new salesEntry();													
					this.dispose();
			
			} else {
				JOptionPane.showMessageDialog(null, "Incorrect information!");
			}
				
			}
			catch (SQLException e1) {					
				e1.printStackTrace();
			}							
		}
	
	}

}
					
							
			
		
	
		
	


