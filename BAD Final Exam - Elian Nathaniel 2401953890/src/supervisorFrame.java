import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class supervisorFrame extends JFrame implements ActionListener, MouseListener{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	JPanel northPnl, centerPnl, southPnl, inputPnl, buttonsPnl; 
	TableModel tableModel;
	JLabel operatorIDLbl, memberIDLbl, itemIDLbl, qtyLbl, exchangeLbl, totalLbl, paymentLbl, title;
	JTextField operatorIDTxt, memberIDTxt, itemIDTxt, qtyTxt, exchangeTxt, totalTxt, paymentTxt;
	JMenuBar menuBar;
	JButton add,update,delete, logout;
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
		inputPnl = new JPanel(new GridLayout(7,2));
		buttonsPnl = new JPanel(new GridLayout(1,3));
			
		//menu bar
		menuBar = new JMenuBar();
		
		//table
		transactions = new JTable();
		transactions.addMouseListener(this);
		
		refreshTable();
		
		scrollP = new JScrollPane(transactions);
		scrollP.setPreferredSize(new Dimension(770, 500));
		centerPnl.add(scrollP);
		
		//label
		title = new JLabel("Transactions");
		operatorIDLbl = new JLabel("Operator ID");
		memberIDLbl = new JLabel("Member ID");
		itemIDLbl = new JLabel("Item ID");
		qtyLbl = new JLabel("Quantity");
		totalLbl = new JLabel("Total");
		paymentLbl = new JLabel("Payment");
		exchangeLbl = new JLabel("exchange");
		northPnl.add(title);
		
		//text fields
		operatorIDTxt = new JTextField();
		memberIDTxt = new JTextField();
		itemIDTxt = new JTextField();
		qtyTxt = new JTextField();
		totalTxt = new JTextField();
		paymentTxt = new JTextField();
		exchangeTxt = new JTextField();
		
		//label & field to panel
		inputPnl.add(operatorIDLbl);inputPnl.add(operatorIDTxt);
		inputPnl.add(memberIDLbl);inputPnl.add(memberIDTxt);
		inputPnl.add(itemIDLbl);inputPnl.add(itemIDTxt);
		inputPnl.add(qtyLbl);inputPnl.add(qtyTxt);
		inputPnl.add(totalLbl);inputPnl.add(totalTxt);
		inputPnl.add(paymentLbl);inputPnl.add(paymentTxt);
		inputPnl.add(exchangeLbl);inputPnl.add(exchangeTxt);
		
		//buttons
		add = new JButton("Insert");
		add.addActionListener(this);
		update = new JButton("Update");
		update.addActionListener(this);
		delete = new JButton("Delete");
		delete.addActionListener(this);
		logout = new JButton("Logout");
		logout.addActionListener(this);
		buttonsPnl.add(add);buttonsPnl.add(update);buttonsPnl.add(delete);
		menuBar.add(logout);
		southPnl.add(inputPnl);
		southPnl.add(buttonsPnl);
		
		//add panel
		setJMenuBar(menuBar);
		add(northPnl, BorderLayout.NORTH);
		add(centerPnl, BorderLayout.CENTER);
		add(southPnl, BorderLayout.SOUTH);
			
	}
	
	public void refreshTable() {
		Object [] salesEntryColumnNames = {"ID","Quantity","Price", "Payment", "Exchange"};
		tableModel = new DefaultTableModel(salesEntryColumnNames,0);
		rs = con.runQuery("SELECT purchaseID, quantity, price, payment, exchange FROM `purchase`");		
		try {
			while(rs.next()) {
				int id = rs.getInt("purchaseID");
				int qty = rs.getInt("quantity");
				String price = rs.getString("price");
				String payment = rs.getString("payment");
				int exchange = rs.getInt("exchange");
				
				Vector<Object> dataRow = new Vector<>();
				dataRow.add(id);
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
	
	public supervisorFrame() {
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
	public void mouseClicked(MouseEvent e) {
		temporaryID = transactions.getValueAt(transactions.getSelectedRow(), 0).toString();
		String tempOperatorID = null;
		String tempMemberID = null;
		String tempItemID = null;
		if (e.getSource() == transactions) {
			rs = con.runQuery("SELECT * FROM `purchase` WHERE purchaseID = "+temporaryID+";");	
			try {
				if(rs.next()) {
					tempOperatorID = String.valueOf(rs.getInt("operatorID"));
					tempMemberID = String.valueOf(rs.getInt("memberID"));
					tempItemID = String.valueOf(rs.getInt("itemID"));					
					operatorIDTxt.setText(tempOperatorID);
					memberIDTxt.setText(tempMemberID);
					itemIDTxt.setText(tempItemID);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String temporaryQty = transactions.getValueAt(transactions.getSelectedRow(), 1).toString();
			String temporaryTotal = transactions.getValueAt(transactions.getSelectedRow(), 2).toString();
			String temporaryPayment = transactions.getValueAt(transactions.getSelectedRow(), 3).toString();
			String temporaryExchange = transactions.getValueAt(transactions.getSelectedRow(), 4).toString();
			qtyTxt.setText(temporaryQty);
			totalTxt.setText(temporaryTotal);
			paymentTxt.setText(temporaryPayment);
			exchangeTxt.setText(temporaryExchange);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == add) {
			int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to add this transaction to the list?");
			if (option != JOptionPane.YES_OPTION) {
				return;				
			}	
			String operatorID = operatorIDTxt.getText();
			String memberID = memberIDTxt.getText();
			String itemID = itemIDTxt.getText();		
			String qty = qtyTxt.getText();
			String total = totalTxt.getText();
			String payment = paymentTxt.getText();
			String exchange = exchangeTxt.getText();
					
			String query = ("INSERT INTO purchase VALUES('"+0+"', '"+operatorID+"', '"+memberID+"', '"+itemID+"', '"+qty+"', '"+total+"', '"+payment+"', '"+exchange+"');");
			
			boolean addSuccess = con.runUpdate(query);
			
			if(addSuccess) {
				JOptionPane.showMessageDialog(null, "Addition Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
				operatorIDTxt.setText("");
				memberIDTxt.setText("");
				itemIDTxt.setText("");
				qtyTxt.setText("");
				totalTxt.setText("");
				paymentTxt.setText("");
				exchangeTxt.setText("");
				refreshTable();
			}else {
				JOptionPane.showMessageDialog(null, "Addition Failed", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if(e.getSource() == update) {
			int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to update this row on the list?");
			if (option != JOptionPane.YES_OPTION) {
				return;			
			}
			int operatorID = Integer.parseInt(operatorIDTxt.getText());
			int memberID = Integer.parseInt(memberIDTxt.getText());
			int itemID = Integer.parseInt(itemIDTxt.getText());
			int qty = Integer.parseInt(qtyTxt.getText());
			int total = Integer.parseInt(totalTxt.getText());
			int payment = Integer.parseInt(paymentTxt.getText());
			int exchange = Integer.parseInt(exchangeTxt.getText());
			
			String query = "UPDATE purchase SET operatorID = '"+operatorID+"', memberID = '"+memberID+"', itemID = '"+itemID+"', quantity = '"+qty+"', price = '"+total+"', payment = '"+payment+"', exchange = '"+exchange+"' WHERE purchaseID = '"+temporaryID+"';"; 
			
			boolean updateSuccess = con.runUpdate(query);
			
			if(updateSuccess) {
				JOptionPane.showMessageDialog(null, "Update Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
				operatorIDTxt.setText("");
				memberIDTxt.setText("");
				itemIDTxt.setText("");
				qtyTxt.setText("");
				totalTxt.setText("");
				paymentTxt.setText("");
				exchangeTxt.setText("");
				refreshTable();
			}else {
				JOptionPane.showMessageDialog(null, "Update Failed", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if(e.getSource() == delete) {
			int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this product to the list?");
			if (option != JOptionPane.YES_OPTION) {
				return;
				
			}
			String query = "DELETE FROM purchase WHERE purchaseID = '"+temporaryID+"';";
			
			boolean deleteSuccess = con.runUpdate(query);
			
			if(deleteSuccess) {
				JOptionPane.showMessageDialog(null, "Row Deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
				operatorIDTxt.setText("");
				memberIDTxt.setText("");
				itemIDTxt.setText("");
				qtyTxt.setText("");
				totalTxt.setText("");
				paymentTxt.setText("");
				exchangeTxt.setText("");
				refreshTable();
			}else {
				JOptionPane.showMessageDialog(null, "Deletion Failed", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		if (e.getSource() == logout) {
			int returnLogout = JOptionPane.showConfirmDialog(null, "Are you sure?", "", JOptionPane.YES_NO_CANCEL_OPTION);
			if(returnLogout == JOptionPane.YES_OPTION) {
				this.dispose();
				new salesEntry();
			}	
		}
	
		
	}
	
		
}
