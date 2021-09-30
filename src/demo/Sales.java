package demo;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;

public class Sales implements ActionListener {

	private JFrame frame;
	private JTextField codereader;
	private JTextField productreader;
	private JTextField pricereader;
	JButton btnDelete;
	private JTextField feedcode;
	private JTextField feedproduct;
	private JTextField feedprice;
	private JTextField feedquantity;
	private JTextArea Confirm;
	private JLabel total;
	Connection conn;
	String driver="com.mysql.cj.jdbc.Driver";
	String url="jdbc:mysql://localhost:3306/PointOfSale";
	String username="root";
	String password="";
	private JTextField idfield;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sales window = new Sales();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Sales() {
		initialize();
	}
	public void Get_Connection() throws SQLException {
		
		try {
			Class.forName(driver);
		   conn=DriverManager.getConnection(url,username, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("POINT OF SALE SYSTEM");
		frame.getContentPane().setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 22));
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.CYAN);
		frame.setBackground(Color.BLACK);
		frame.setForeground(Color.WHITE);
		frame.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 19));
		frame.setBounds(100, 100, 567, 407);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("@franc_designs");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setBackground(Color.MAGENTA);
		lblNewLabel.setBounds(12, 351, 110, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Product");
		lblNewLabel_1.setBounds(330, 48, 70, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		codereader = new JTextField();
		codereader.setBounds(402, 9, 151, 27);
		frame.getContentPane().add(codereader);
		codereader.setColumns(10);
		codereader.addActionListener(args->{
			
			try {
				Get_Connection();
				Statement stm=conn.createStatement();
				ResultSet rs=stm.executeQuery("SELECT * FROM products where BarCode="+codereader.getText());
				if(rs.next()) {
					int id=rs.getInt(1);
					String name=rs.getString(3);
					int price=rs.getInt(4);
					
					productreader.setText(name);
					pricereader.setText(price+"");
					idfield.setText(id+"");
				}
				else {
					JOptionPane.showMessageDialog(null, "Item Requested for not available...");
					codereader.setText(null);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		productreader = new JTextField();
		productreader.setFont(new Font("Dialog", Font.BOLD, 13));
		productreader.setForeground(Color.BLACK);
		productreader.setEditable(false);
		productreader.setBounds(402, 48, 151, 27);
		frame.getContentPane().add(productreader);
		productreader.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("P.Price");
		lblNewLabel_2.setBounds(330, 93, 70, 15);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Bar code");
		lblNewLabel_3.setBounds(330, 9, 70, 15);
		frame.getContentPane().add(lblNewLabel_3);
		
		pricereader = new JTextField();
		pricereader.setFont(new Font("Dialog", Font.BOLD, 12));
		pricereader.setForeground(Color.BLACK);
		pricereader.setEditable(false);
		pricereader.setBounds(402, 87, 151, 27);
		frame.getContentPane().add(pricereader);
		pricereader.setColumns(10);
		
		JTextArea RecieptArea = new JTextArea();
		RecieptArea.setFont(new Font("Nimbus Mono PS", Font.BOLD | Font.ITALIC, 12));
		RecieptArea.setForeground(Color.GREEN);
		RecieptArea.setBackground(Color.BLACK);
		RecieptArea.setEditable(false);
		RecieptArea.setBounds(327, 144, 226, 192);
		
		frame.getContentPane().add(RecieptArea);
		
		JLabel lblNewLabel_4 = new JLabel("Reciept");
		lblNewLabel_4.setBounds(462, 126, 70, 15);
		frame.getContentPane().add(lblNewLabel_4);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBackground(Color.GRAY);
		btnAdd.setForeground(Color.WHITE);
		btnAdd.addActionListener(args->{
			try {
	
				String code=feedcode.getText();
				String pname=feedproduct.getText();
				String pprice=feedprice.getText();
				int price=Integer.parseInt(pprice);
				String pquantity=feedquantity.getText();
				int quantity=Integer.parseInt(pquantity);
				Get_Connection();
				PreparedStatement ps=conn.prepareStatement("insert into products values(?,?,?,?,?)");
				ps.setString(1, null);
				ps.setInt(2,Integer.parseInt(code));
				ps.setString(3, pname);
				ps.setInt(4, price);
				ps.setInt(5, quantity);
				ps.execute();
				Confirm.setText("Data upload a success..."+"\n"+"Code: "+code+"\n"+"P.Name: "+pname+"Price @: "+pprice+"\n"+"Quantity: "+pquantity);
				Confirm.setVisible(true);
				total.setText("TOTAL EXPENSE: "+(price*quantity));
				RecieptArea.setText("_____----->_____---->_____----->"+"\n"+"\n"+"RECIEPT FOR TOTAL PURCHASE\n"+"\n"+"\n"+
				"ITEM:\t---> "+pname+"<---\t"+"\n"+"PRICE EACH:\t---> "+pprice+"<---\t"+"\n"+"Quantity:\t---> "+pquantity+"<---\t"+"\n"+"----------------------------------------"+"\n"+
						"TOTAL EXPENSE:--> "+(price*quantity)+"<--"+"\n"+"Thankyou..."+"\n"+"_____----->_____----->_____---->");
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		btnAdd.setBounds(12, 177, 91, 46);
		frame.getContentPane().add(btnAdd);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setForeground(Color.WHITE);
		btnClear.setBackground(Color.DARK_GRAY);
		btnClear.setBounds(115, 177, 100, 46);
		btnClear.addActionListener(args->{
			feedcode.setText("");
			feedproduct.setText("");
			feedprice.setText("");
			feedquantity.setText("");
		Confirm.setText("");
		Confirm.setVisible(false);
		total.setText("");
		});
		frame.getContentPane().add(btnClear);
		
		
		btnDelete = new JButton("Delete");
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setBackground(Color.BLACK);
		btnDelete.setBounds(227, 177, 88, 46);
	btnDelete.addActionListener(this);
		frame.getContentPane().add(btnDelete);
		
		JButton btnPrintReciept = new JButton("Print Reciept");
		btnPrintReciept.setForeground(Color.WHITE);
		btnPrintReciept.setBackground(Color.RED);
		btnPrintReciept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RecieptArea.setText("");
				JOptionPane.showMessageDialog(null, "Printing Reciept.....");
			}
		});
		btnPrintReciept.setBounds(362, 346, 137, 25);
		frame.getContentPane().add(btnPrintReciept);
		
		JLabel lblPointOfSale = new JLabel("POINT OF SALE ");
		lblPointOfSale.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 20));
		lblPointOfSale.setBounds(12, 12, 203, 38);
		frame.getContentPane().add(lblPointOfSale);
		
		JLabel lblBarcode = new JLabel("BarCode");
		lblBarcode.setBackground(Color.DARK_GRAY);
		lblBarcode.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
		lblBarcode.setForeground(Color.RED);
		lblBarcode.setBounds(12, 54, 70, 15);
		frame.getContentPane().add(lblBarcode);
		
		JLabel lblProduct = new JLabel("Product\n");
		lblProduct.setForeground(Color.BLUE);
		lblProduct.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
		lblProduct.setBounds(12, 81, 70, 15);
		frame.getContentPane().add(lblProduct);
		
		JLabel lblPrice = new JLabel("Price ");
		lblPrice.setForeground(Color.BLUE);
		lblPrice.setBounds(22, 113, 45, 15);
		frame.getContentPane().add(lblPrice);
		
		JLabel lblQuantity = new JLabel("Quantity:");
		lblQuantity.setForeground(Color.BLUE);
		lblQuantity.setBounds(12, 144, 70, 15);
		frame.getContentPane().add(lblQuantity);
		
		feedcode = new JTextField();
		feedcode.setBounds(81, 52, 148, 23);
		frame.getContentPane().add(feedcode);
		feedcode.setColumns(10);
		
		feedproduct = new JTextField();
		feedproduct.setBounds(81, 81, 150, 23);
		frame.getContentPane().add(feedproduct);
		feedproduct.setColumns(10);
		
		feedprice = new JTextField();
		feedprice.setBounds(81, 108, 148, 25);
		frame.getContentPane().add(feedprice);
		feedprice.setColumns(10);
		
		feedquantity = new JTextField();
		feedquantity.setBounds(81, 138, 148, 27);
		frame.getContentPane().add(feedquantity);
		feedquantity.setColumns(10);
		
		Confirm= new JTextArea();
		Confirm.setBackground(Color.BLACK);
		Confirm.setForeground(Color.BLUE);
		Confirm.setEditable(false);
		Confirm.setBounds(12, 229, 303, 79);
		Confirm.setVisible(false);
		frame.getContentPane().add(Confirm);
		
		
	    total = new JLabel("");
		total.setBounds(12, 310, 181, 29);
		frame.getContentPane().add(total);
		
		JLabel lblSearchBy = new JLabel("Search By:");
		lblSearchBy.setBounds(227, 9, 92, 15);
		frame.getContentPane().add(lblSearchBy);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(365, 123, 35, 9);
		frame.getContentPane().add(lblId);
		
		idfield = new JTextField();
		idfield.setFont(new Font("Dialog", Font.BOLD, 14));
		idfield.setForeground(Color.GREEN);
		idfield.setBackground(Color.BLACK);
		idfield.setEditable(false);
		idfield.setBounds(400, 116, 54, 25);
		frame.getContentPane().add(idfield);
		idfield.setColumns(10);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	Object object=arg0.getSource();
	if (object.equals(btnDelete)) {
		String answer=JOptionPane.showInputDialog("Enter the Product's ID:");
		int answercode=Integer.parseInt(answer);
		String delete="delete from products where id = ?";
		try {
			Get_Connection();
			PreparedStatement ps=conn.prepareStatement(delete);
			ps.setInt(1, answercode);
			ps.execute();
			conn.close();
			JOptionPane.showMessageDialog(null,"Item deleted  successfully!!!");
			codereader.setText("");
			pricereader.setText("");
			idfield.setText("");
			productreader.setText("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
		
	}
}
