import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class switchApproval extends JFrame implements ActionListener{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	JPanel northPnl, centerPnl, southPnl, buttonsPnl; 
	TableModel tableModel;
	JLabel title;
	JButton approve, back;
	JTable transactions;
	JScrollPane scrollP;		
	connect con;
	ResultSet rs;
	
	public void initialize() {
		//connect
		con = new connect();
		
		//panel
		northPnl = new JPanel();
		centerPnl = new JPanel();
		southPnl = new JPanel(new GridLayout(2,1));
		buttonsPnl = new JPanel(new GridLayout(1,3));
		
		//table
		transactions = new JTable();	
		
		refreshTable();		
		scrollP = new JScrollPane(transactions);
		scrollP.setPreferredSize(new Dimension(770, 670));
		centerPnl.add(scrollP);
		
		//label
		title = new JLabel("Transactions");
		northPnl.add(title);
		
		//buttons
		approve = new JButton("Approve Operator Switch");
		approve.addActionListener(this);
		back = new JButton("Back");
		back.addActionListener(this);
		buttonsPnl.add(approve);buttonsPnl.add(back);
		southPnl.add(buttonsPnl);
		
		//add panel
		add(northPnl, BorderLayout.NORTH);
		add(centerPnl, BorderLayout.CENTER);
		add(southPnl, BorderLayout.SOUTH);
			
	}
	
	public void refreshTable() {
		Object [] transactionPurchasesColumnNames = {"ID","Member ID","ItemID","Quantity","Price", "Payment", "Exchange"};
		tableModel = new DefaultTableModel(transactionPurchasesColumnNames,0);
		rs = con.runQuery("SELECT purchaseID, memberID, itemID, quantity, price, payment, exchange FROM `purchase`");		
		try {
			while(rs.next()) {
				int purchaseID = rs.getInt("purchaseID");
				int memberID = rs.getInt("memberID");
				int itemID = rs.getInt("itemID");
				int qty = rs.getInt("quantity");
				String price = rs.getString("price");
				String payment = rs.getString("payment");
				int exchange = rs.getInt("exchange");
				
				Vector<Object> dataRow = new Vector<>();
				dataRow.add(purchaseID);
				dataRow.add(memberID);
				dataRow.add(itemID);
				dataRow.add(qty);
				dataRow.add(price);
				dataRow.add(payment);
				dataRow.add(exchange);
				
				((DefaultTableModel) tableModel).addRow(dataRow);
			}
			transactions.setModel(tableModel);
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public switchApproval() {
		initialize();
		
		setVisible(true);
		setSize(800,900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Supervisor Window");
	}
	
	String temporaryID;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == approve) {
			int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to allow operator to switch shifts?");
			if (option != JOptionPane.YES_OPTION) {
				return;				
			}	
					
			String query = ("DELETE FROM `purchase`");
			
			boolean addSuccess = con.runUpdate(query);
			
			if(addSuccess) {
				JOptionPane.showMessageDialog(null, "Allowed Switching", "Success", JOptionPane.INFORMATION_MESSAGE);
				this.dispose();
				new operatorLoginFrame();
			}else {
				JOptionPane.showMessageDialog(null, "Error", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if(e.getSource() == back) {			
			this.dispose();
			new salesEntry();
		}	
		
	}
			
}

