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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class salesEntry extends JFrame implements ActionListener, MouseListener{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	JPanel northPnl, centerPnl,southPnl, leftTablePnl, rightTablePnl, buttonPnl, fieldsAndLblPnl, fieldsAndLblBottomPnl, namePriceQtyMemberPnl, totalPaymentPnl, switchShiftPnl, askHelpSupervisorPnl;
	TableModel tableModel;
	JTextField itemName, itemPrice, itemQty, totalPrice,memberIDField, paymentInput;
	JLabel label, itemNameLbl, itemPriceLbl, itemQtyLbl, totalPriceLbl,memberIDLbl, paymentInputLbl;
	JTable forSaleList, salesEntry;
	JButton switchShift, askHelpSupervisor, pay;
	JScrollPane scrollP;
	connect con;
	Image image;
	String temporaryID, currentOperatorID = operatorLoginFrame.printID();
	int memberID, priceAmount;
	TableRowSorter<TableModel> sort;	
	ResultSet rs;
	
	public void initialize() {
		//connect
		con = new connect();
		
		//panel
		northPnl = new JPanel();
		centerPnl = new JPanel(new GridLayout(1,2));
		southPnl = new JPanel(new GridLayout(1,2));	
		leftTablePnl = new JPanel();
		rightTablePnl = new JPanel();	
		buttonPnl = new JPanel(new GridLayout(2,2));
		fieldsAndLblPnl = new JPanel(new GridLayout(2,1));
		fieldsAndLblBottomPnl = new JPanel(new GridLayout(1,2));
		namePriceQtyMemberPnl = new JPanel(new GridLayout(2,3));
		totalPaymentPnl = new JPanel(new GridLayout(4,1));
		switchShiftPnl = new JPanel();
		askHelpSupervisorPnl = new JPanel();		
		
		//labels
		itemNameLbl = new JLabel("Item Name");
		itemPriceLbl = new JLabel("Item Price");
		itemQtyLbl = new JLabel("Quantity [Enter to update]");
		totalPriceLbl = new JLabel("Total Price");
		paymentInputLbl = new JLabel("Payment");
		memberIDLbl = new JLabel("Member ID (Optional) [Enter to update]");
		
		//text fields
		itemName = new JTextField();
		itemPrice = new JTextField();
		itemQty = new JTextField();
		totalPrice = new JTextField();
		paymentInput = new JTextField();
		memberIDField = new JTextField();
		namePriceQtyMemberPnl.add(itemName);
		namePriceQtyMemberPnl.add(itemPrice);
		namePriceQtyMemberPnl.add(itemQty);
		namePriceQtyMemberPnl.add(memberIDField);
		itemQty.addActionListener(this);
		paymentInput.addActionListener(this);
		memberIDField.addActionListener(this);
		itemName.setEditable(false);
		itemPrice.setEditable(false);
		totalPrice.setEditable(false);
			
		//tables
		forSaleList = new JTable();
		forSaleList.addMouseListener(this);
		salesEntry = new JTable();
		
		refreshTable();		
		scrollP = new JScrollPane(salesEntry);
		scrollP.setPreferredSize(new Dimension(770, 670));
		leftTablePnl.add(scrollP);
		
		refreshProductTable();	
		scrollP = new JScrollPane(forSaleList);
		scrollP.setPreferredSize(new Dimension(770, 670));
		rightTablePnl.add(scrollP);
		
		//button
		askHelpSupervisor = new JButton("Ask Help From Supervisor");
		askHelpSupervisor.setPreferredSize(new Dimension(250,50));
		switchShift = new JButton("Switch Shifts");
		switchShift.setPreferredSize(new Dimension(250,50));
		pay = new JButton("Pay");
		askHelpSupervisor.addActionListener(this);
		switchShift.addActionListener(this);
		pay.addActionListener(this);
							
		//panel addition
		switchShiftPnl.add(switchShift);
		askHelpSupervisorPnl.add(askHelpSupervisor);
		buttonPnl.add(switchShiftPnl);
		buttonPnl.add(askHelpSupervisorPnl);
		namePriceQtyMemberPnl.add(itemNameLbl);
		namePriceQtyMemberPnl.add(itemPriceLbl);
		namePriceQtyMemberPnl.add(itemQtyLbl);
		namePriceQtyMemberPnl.add(memberIDLbl);
		totalPaymentPnl.add(totalPriceLbl);
		totalPaymentPnl.add(totalPrice);
		totalPaymentPnl.add(paymentInputLbl);
		totalPaymentPnl.add(paymentInput);
		fieldsAndLblBottomPnl.add(totalPaymentPnl);
		fieldsAndLblBottomPnl.add(pay);
		fieldsAndLblPnl.add(namePriceQtyMemberPnl);
		fieldsAndLblPnl.add(fieldsAndLblBottomPnl);		
		centerPnl.add(leftTablePnl);
		centerPnl.add(rightTablePnl);
		southPnl.add(buttonPnl);
		southPnl.add(fieldsAndLblPnl);
			
		//add panel
		add(northPnl, BorderLayout.NORTH);
		add(centerPnl, BorderLayout.CENTER);
		add(southPnl, BorderLayout.SOUTH);
				
	}

	public void refreshProductTable() {
		Object [] forSaleColumnNames = {"Id","Images","Available For Sale"};
		tableModel = new DefaultTableModel(forSaleColumnNames,0);
		rs = con.runQuery("SELECT itemID, unitsOnHand, productImg FROM `item`");		
		try {
			while(rs.next()) {
				label = new JLabel(" ");
				int id = rs.getInt("itemID");
				int qty = rs.getInt("unitsOnHand");
				byte[] images = rs.getBytes("productImg");
				ImageIcon image = new ImageIcon(images);
				label.setIcon(image);
				
				Vector<Object> dataRow = new Vector<>();
				dataRow.add(id);
				dataRow.add(label);
				dataRow.add(qty);				
				((DefaultTableModel) tableModel).addRow(dataRow);
		
			}			
				
			forSaleList.setModel(tableModel);
			forSaleList.getColumnModel().getColumn(1).setCellRenderer(new CellRenderer());	
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void refreshTable() {
		Object [] salesEntryColumnNames = {"Quantity","Price", "Payment", "Exchange"};
		tableModel = new DefaultTableModel(salesEntryColumnNames,0);
		rs = con.runQuery("SELECT quantity, price, payment, exchange FROM `purchase`");		
		try {
			while(rs.next()) {
				int qty = rs.getInt("quantity");
				String price = rs.getString("price");
				String payment = rs.getString("payment");
				int exchange = rs.getInt("exchange");
				
				Vector<Object> dataRow = new Vector<>();
				dataRow.add(qty);
				dataRow.add(price);
				dataRow.add(payment);
				dataRow.add(exchange);			
				((DefaultTableModel) tableModel).addRow(dataRow);
				sort = new TableRowSorter<>(salesEntry.getModel());
			}
			salesEntry.setModel(tableModel);
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public salesEntry() {
		initialize();
		
		setVisible(true);
		setSize(1600,900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Sales Entry");
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		//item quantity field enter button
		if (e.getSource() == itemQty) {
			String quantity = itemQty.getText();
			String price = itemPrice.getText();
			int productQty = Integer.parseInt(quantity);
			int productPrice = Integer.parseInt(price);
			priceAmount = productQty * productPrice;
			String priceAmountString = String.valueOf(priceAmount);
			totalPrice.setText(priceAmountString);
		}
		
		if(e.getSource() == memberIDField) {
			memberID = Integer.parseInt(memberIDField.getText());
		}
		
		if(e.getSource() == pay) {
			rs = con.runQuery("SELECT * FROM `item` WHERE itemID = "+temporaryID+";");	
			try {
				if (rs.next());{
					int totalPaymentInput = 0;
					totalPaymentInput = Integer.parseInt(paymentInput.getText());
					int availableQty = rs.getInt("unitsOnHand");
					int productQty = Integer.parseInt(itemQty.getText());
						if(productQty > availableQty) {
							JOptionPane.showMessageDialog(null, "Not enough products in stock!");
						} else if (totalPaymentInput<priceAmount) {
							JOptionPane.showMessageDialog(null, "Not enough funds");
						} else {
							int exchange = totalPaymentInput - priceAmount;
							String query = ("INSERT INTO purchase VALUES('"+0+"', '"+currentOperatorID+"', '"+memberID+"', '"+ temporaryID+"', '"+productQty+"', '"+priceAmount+"', '"+totalPaymentInput+"', '"+exchange+"');");
							boolean addSuccess = con.runUpdate(query);
							if(addSuccess) {
								
								//update qty in forSaleList table
								int stockReduced = availableQty - productQty;
								String query2 = "UPDATE item SET unitsOnHand = '"+stockReduced+"' WHERE itemID = '"+temporaryID+"';";
								con.runUpdate(query2);
								JOptionPane.showMessageDialog(null, "Purchase Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
								
								//empty fields
								itemName.setText("");
								itemPrice.setText("");
								itemQty.setText("");
								memberIDField.setText("");
								totalPrice.setText("");
								paymentInput.setText("");
								
								//refresh tables
								refreshTable();
								refreshProductTable();
							}else {
								JOptionPane.showMessageDialog(null, "Purchase Failed", "Error", JOptionPane.ERROR_MESSAGE);
							}
							
						}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		if (e.getSource() == askHelpSupervisor) {
			new supervisorLoginFrame();
			this.dispose();
		}
		
		if (e.getSource() == switchShift) {
			new supervisorLoginFrame();
			this.dispose();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//get information from item table
		String temporaryPriceString;
		temporaryID = forSaleList.getValueAt(forSaleList.getSelectedRow(), 0).toString();
		if (e.getSource() == forSaleList) {
			rs = con.runQuery("SELECT * FROM `item` WHERE itemID = "+temporaryID+";");	
				try {
					if(rs.next()) {				
						temporaryPriceString = String.valueOf(rs.getInt("itemPrice"));
					    itemPrice.setText(temporaryPriceString);
					    itemName.setText(rs.getString("itemName"));
					    itemQty.setText("1");
					}
				} catch (SQLException e1) {					
					e1.printStackTrace();
				}

		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	class CellRenderer implements TableCellRenderer {		 
        @Override
        public Component getTableCellRendererComponent(JTable forSaleList, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
 
            TableColumn columnRendered = forSaleList.getColumnModel().getColumn(1);
            columnRendered.setMaxWidth(500);
            columnRendered.setMinWidth(500);
            forSaleList.setRowHeight(200);
            return (Component) value;          
        }
    }

}
