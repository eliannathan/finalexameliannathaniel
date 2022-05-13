import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class supervisorMenuFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private JPanel northPnl, centerPnl, buttonPnl;
	private JLabel welcome;
	private JButton switchApproval, supervisorFrame;
	
	public void initial() {
		
		//panel
		northPnl = new JPanel();
		centerPnl = new JPanel();
		buttonPnl = new JPanel();
		
		//label
		welcome = new JLabel("Welcome, Supervisor!");
		northPnl.add(welcome);
		
		//button
		switchApproval = new JButton("Operator Switch Approval");
		switchApproval.setPreferredSize(new Dimension (250, 50));
		switchApproval.addActionListener(this);
		supervisorFrame = new JButton("Edit Transactions");
		supervisorFrame.setPreferredSize(new Dimension (250, 50));
		supervisorFrame.addActionListener(this);
		buttonPnl.add(switchApproval);
		buttonPnl.add(supervisorFrame);
		centerPnl.add(buttonPnl);
		
		//add panels
		add(northPnl, BorderLayout.NORTH);
		add(centerPnl,BorderLayout.CENTER);
	
		}
		
	public supervisorMenuFrame() {
		initial();
		
		setVisible(true);
		setSize(600,160);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Supervisor Menu");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == switchApproval) {
			this.dispose();
			new switchApproval();
		} else 	if(e.getSource() == supervisorFrame) {
			this.dispose();
			new supervisorFrame();
		
	}

}
	
}