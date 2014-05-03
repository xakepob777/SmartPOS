package catalog;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import xkp.comp.NumberRenderer;
import xkp.comp.TableData;
import app.MainPoint;
import xkp.comp.ActionButtons;
import xkp.comp.FieldFloat;
import xkp.comp.LabelSimple;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class Customers extends JDialog {
	
	//ResourceBundle rb; OBSOLETE
	JLabel lblName, lblSearch, lblRequired;
	LabelSimple lblAddress, lblPhones;
	JTextField txtName, txtSearch;
	JScrollPane scrollCustomers, scrollDesc, scrollPhones;
	JButton btnSearch, btnClose, btnSave, btnUpdate, btnCancel, btnNew;
	JTextArea taAddress,taPhones;
	//Action buttons
	ActionListener ab;
	TableData table;
	//store true=insert, false=update
	boolean action;
	int id;//store customer identifier
	
	//constructor
	public Customers(){
		initWindow();
		initComponents();
	}
	
	//init window properties
	private void initWindow(){
		setTitle("CATALOGO DE CLIENTES");
		setSize(500, 430);
		setModal(true);
		setLocationRelativeTo(null);
		setLayout(null);
		getContentPane().setBackground(Color.decode("#b2b2b2"));
	}
	
	//init conponents swing
	private void initComponents(){
		/*Locale currentLocale= new Locale("en", "US"); 
		rb = ResourceBundle.getBundle("MessagesBundle", currentLocale);
		System.out.println(rb.getString("name"));*/
		panelList();
		panelForm();
		statusDefault();
		buttons();
		defaultButtons();
	}
	//list products grid
	private void panelList(){
		lblSearch = new JLabel("Busqueda por Nombre: ");
		lblSearch.setBounds(15, 10, 250, 25);
		lblSearch.setFont(new Font("Arial",1,12));
		lblSearch.setForeground(Color.blue);
		add(lblSearch);
		txtSearch = new JTextField();
		txtSearch.setBounds(220, 10, 180, 25);
		add(txtSearch);
		btnSearch = new JButton("Buscar");
		btnSearch.setBounds(410, 10, 80, 25);
		add(btnSearch);
		btnSearch.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(txtSearch.equals("")){
					//get all list customers
					try {
						loadCustomers("");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					//get list products by refrence search
					try {
						loadCustomers(txtSearch.getText());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
		//data grid
		scrollCustomers = new JScrollPane();
		scrollCustomers.setBounds(10,40,480,170);
		add(scrollCustomers);
		try {
			loadCustomers("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//form products
	private void panelForm(){
		lblRequired = new JLabel("Los campos con * son requeridos.");
		lblRequired.setBounds(280, 210, 280, 25);
		lblRequired.setFont(new Font("Arial",1,11));
		lblRequired.setForeground(Color.decode("#cc3300"));
		add(lblRequired);

		//name
		lblName = new JLabel("Nombre*");
		lblName.setBounds(10, 230, 100, 25);
		lblName.setFont(new Font("Arial",1,12));
		lblName.setForeground(Color.decode("#001f5c"));
		add(lblName);
		txtName = new JTextField();
		txtName.setBounds(10, 250, 300,25);
		add(txtName);
		//address
		lblAddress = new LabelSimple("Direccion*",10,280,100,25,"#001f5c");
		add(lblAddress);
		taAddress = new JTextArea();
		scrollDesc = new JScrollPane(taAddress);
		scrollDesc.setBounds(10, 300, 230, 50);
		add(scrollDesc);
		//phones
		lblPhones = new LabelSimple("Telefonos*",260,280,100,25,"#001f5c");
		add(lblPhones);
		taPhones = new JTextArea();
		scrollPhones = new JScrollPane(taPhones);
		scrollPhones.setBounds(260, 300, 230, 50);
		add(scrollPhones);
		
		
	}
	//load products catalog
	private void loadCustomers(String param) throws SQLException{
		String sql = ""; 
		if(param.equals("")){
			sql = "SELECT * FROM sp_customers";
		}else{
			sql = "SELECT * FROM sp_customers WHERE name LIKE '%"+param+"%'";
		}
		String columnNames[] = { "ID","NOMBRE","DIRECCION","TELEFONOS","REGISTRO"};
		ResultSet dataValues = MainPoint.db.SqliteQuery(sql);
			/*{
				{ "00001", "Coca Cola 600 ml", "4" ,"PZA","10"},
				{ "00001", "Coca Cola 600 ml", "4", "KG", "1" },
				{ "00001", "Coca Cola 600 ml", "4","ML","2" }
			};*/
		
		table = new TableData(columnNames, dataValues);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//table.setBounds(20, 160, 280, 250);
		scrollCustomers.setViewportView(table);
		scrollCustomers.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		table.getColumn("ID").setPreferredWidth(30);
		//table.getColumn("ID").setMaxWidth(0);
		//table.getColumn("ID").setMinWidth(0);
		table.getColumn("NOMBRE").setPreferredWidth(150);
		table.getColumn("DIRECCION").setPreferredWidth(200);
		table.getColumn("TELEFONOS").setPreferredWidth(100);
		table.getColumn("REGISTRO").setPreferredWidth(80);
		
		
		table.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getClickCount() == 2){
					//JOptionPane.showMessageDialog(rootPane, "item seleccionado", "DELETE RECORD", JOptionPane.INFORMATION_MESSAGE);
					int reply = JOptionPane.showConfirmDialog(null, "Desea remover este resgistro !!!.", "CONFIRM DELETE RECORD", JOptionPane.YES_NO_OPTION);
				    if (reply == JOptionPane.YES_OPTION)
				    {
				    	//remove record from det_billing table
				    	deleteCustomer();
				    //  System.out.println("delete record");
				    }else{
				    	//JOptionPane.showMessageDialog(rootPane, "Accion Cancelada !!!.", "CANCEL ACTION EVENT", JOptionPane.INFORMATION_MESSAGE);
				    }
				}else{
					//select row
					id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
					getCustomer(id);
					updateButton();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	//get information customer
	private void getCustomer(int id){
		Object[] info = MainPoint.db.SqliteGetRow("SELECT * FROM sp_customers WHERE id="+id);
		txtName.setText(info[1].toString());
		taAddress.setText(info[2].toString());
		taPhones.setText(info[3].toString());
	}
	//delete products
	private void deleteCustomer(){
		String sql = "DELETE FROM sp_customers WHERE id="+ id;
		MainPoint.db.SqliteExecute(sql);
		try {
			loadCustomers("");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		statusDefault();
		defaultButtons();
		clearFields();
		action = true;
	}
	//buttons
	private void buttons(){
		//new button
		btnNew = new JButton("Nuevo");
		btnNew.setBounds(10, 370, 80, 30);
		add(btnNew);
		btnNew.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				statusNew();
				newButton();
				action = true;
			}
			
		});
		//update button
		btnUpdate = new JButton("Actualizar");
		btnUpdate.setBounds(90, 370, 100, 30);
		add(btnUpdate);
		btnUpdate.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				action = false;
				//updateButton();
				statusUpdate();
				save_cancel();
							
			}
			
		});
		//save 
		btnSave = new JButton("Guardar");
		btnSave.setBounds(190, 370, 90, 30);
		add(btnSave);
		btnSave.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) { 
			
			Date date = new Date();
	        SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
	        
				// TODO Auto-generated method stub
				String sql = null; 
				if(emptyFields()){
					JOptionPane.showMessageDialog(rootPane, "Debe llenar todos los campos !!!.", "CAMPOS REQUERIDOS", JOptionPane.WARNING_MESSAGE);
				}else{
					//update or save
					if(action){
						//save or update information
						sql = "INSERT INTO sp_customers(name, address, phones, registry) VALUES('"+txtName.getText()+"',"
							+ "'"+taAddress.getText()+"','"+taPhones.getText()+"','"+formatter.format(date)+"')";
					}else{
						sql = "UPDATE sp_customers SET name='"+txtName.getText()+"',address='"+taAddress.getText()+"',"
								+ "phones='"+taPhones.getText()+"' WHERE id="+id;
					}
				
					MainPoint.db.SqliteExecute(sql);
					JOptionPane.showMessageDialog(rootPane, "La informacion se ha guardado !!!.", "REGISTRAR CLIENTES", JOptionPane.INFORMATION_MESSAGE);
					statusDefault();
					defaultButtons();
					clearFields();
					action = true;
					try {
						loadCustomers("");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
		});
		//cancel
		btnCancel = new JButton("Cancelar");
		btnCancel.setBounds(280, 370, 90, 30);
		add(btnCancel);
		btnCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				save_cancel();
				statusDefault();
				defaultButtons();
				clearFields();
				action = true;
			}
			
		});
		//ab = new ActionButtons();
		btnClose = new JButton("Cerrar / Salir");
		btnClose.setBounds(370, 370, 120, 30);
		btnClose.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
			
		});
		add(btnClose);
		
		
	}
	//status default
	private void statusDefault(){
		txtName.setEnabled(false);
		taAddress.setEnabled(false);
		taPhones.setEnabled(false);
	}
	//status textfields
	private void statusNew(){
		txtName.setEnabled(true);
		taAddress.setEnabled(true);
		taPhones.setEnabled(true);
	}
	//status update
	private void statusUpdate(){
		txtName.setEnabled(true);
		taAddress.setEnabled(true);
		taPhones.setEnabled(true);
	}
	//defult buttons
	private void defaultButtons(){
		btnNew.setEnabled(true);
		btnUpdate.setEnabled(false);
		btnSave.setEnabled(false);
		btnCancel.setEnabled(false);
	}
	//buttons new
	private void newButton(){
		btnNew.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnSave.setEnabled(true);
		btnCancel.setEnabled(true);
	}
	//button update
	private void updateButton(){
		btnNew.setEnabled(false);
		btnUpdate.setEnabled(true);
		btnSave.setEnabled(false);
		btnCancel.setEnabled(false);
	}
	//save cancel buttons
	private void save_cancel(){
		btnNew.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnSave.setEnabled(true);
		btnCancel.setEnabled(true);
	}
	
	//clear fields
	private void clearFields(){
		txtName.setText("");
		taAddress.setText("");
		taPhones.setText("");
	}
	//empty fields
	private boolean emptyFields(){
		boolean flag = true;
		int empty = 0;
		if(txtName.getText().equals("")){
			empty++;
		}
		if(taAddress.getText().equals("")){
			empty++;
		}
		if(taPhones.getText().equals("")){
			empty++;
		}
		
		if(empty>0){
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}
	
}
