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
import java.util.Locale;
import java.util.ResourceBundle;

public class Products extends JDialog {
	
	//ResourceBundle rb; OBSOLETE
	JLabel lblCode, lblName, lblSearch, lblRequired;
	LabelSimple lblDescription, lblUnit, lblCost;
	JTextField txtCode, txtName, txtUnit, txtSearch;
	FieldFloat ffCost; 
	JScrollPane scrollProducts, scrollDesc;
	JButton btnSearch, btnClose, btnSave, btnUpdate, btnCancel, btnNew;
	JTextArea taDescription;
	//Action buttons
	ActionListener ab;
	TableData table;
	//store true=insert, false=update
	boolean action;
	String code;//store product code
	
	//constructor
	public Products(){
		initWindow();
		initComponents();
	}
	
	//init window properties
	private void initWindow(){
		setTitle("CATALOGO DE PRODUCTOS");
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
		lblSearch = new JLabel("Busqueda por Codigo o Producto: ");
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
					//get all list productos
					try {
						loadProducts("");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					//get list products by refrence search
					try {
						loadProducts(txtSearch.getText());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
		//data grid
		scrollProducts = new JScrollPane();
		scrollProducts.setBounds(10,40,480,170);
		add(scrollProducts);
		try {
			loadProducts("");
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
		//code
		lblCode = new JLabel("Codigo*");
		lblCode.setBounds(10, 210, 100, 25);
		lblCode.setFont(new Font("Arial",1,12));
		lblCode.setForeground(Color.decode("#001f5c"));
		add(lblCode);
		txtCode = new JTextField();
		txtCode.setBounds(10, 230, 120, 25);
		add(txtCode);
		//name
		lblName = new JLabel("Nombre*");
		lblName.setBounds(10, 260, 100, 25);
		lblName.setFont(new Font("Arial",1,12));
		lblName.setForeground(Color.decode("#001f5c"));
		add(lblName);
		txtName = new JTextField();
		txtName.setBounds(10, 280, 200,25);
		add(txtName);
		//description
		lblDescription = new LabelSimple("Descripcion*",220,260,100,25,"#001f5c");
		add(lblDescription);
		taDescription = new JTextArea();
		scrollDesc = new JScrollPane(taDescription);
		scrollDesc.setBounds(220, 280, 270, 50);
		add(scrollDesc);
		//unit
		lblUnit = new LabelSimple("Unidad*",10,310,100,25,"#001f5c");
		add(lblUnit);
		txtUnit = new JTextField();
		txtUnit.setBounds(10, 330, 60, 25);
		add(txtUnit);
		//cost
		lblCost = new LabelSimple("Precio*",120,310,100,25,"#001f5c");
		add(lblCost);
		ffCost = new FieldFloat(120,330,90,25,this.getRootPane(),0);
		add(ffCost);
		
	}
	//load products catalog
	private void loadProducts(String param) throws SQLException{
		String sql = ""; 
		if(param.equals("")){
			sql = "SELECT * FROM sp_products";
		}else{
			sql = "SELECT * FROM sp_products WHERE code LIKE '%"+param+"%' OR name LIKE '%"+param+"%'";
		}
		String columnNames[] = { "ID","CODIGO","PRODUCTO","DESCRIPCION","UNIDAD","PRECIO"};
		ResultSet dataValues = MainPoint.db.SqliteQuery(sql);
			/*{
				{ "00001", "Coca Cola 600 ml", "4" ,"PZA","10"},
				{ "00001", "Coca Cola 600 ml", "4", "KG", "1" },
				{ "00001", "Coca Cola 600 ml", "4","ML","2" }
			};*/
		
		table = new TableData(columnNames, dataValues);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//table.setBounds(20, 160, 280, 250);
		scrollProducts.setViewportView(table);
		scrollProducts.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		table.getColumn("ID").setPreferredWidth(30);
		//table.getColumn("ID").setMaxWidth(0);
		//table.getColumn("ID").setMinWidth(0);
		table.getColumn("CODIGO").setPreferredWidth(80);
		table.getColumn("PRODUCTO").setPreferredWidth(150);
		table.getColumn("DESCRIPCION").setPreferredWidth(250);
		table.getColumn("UNIDAD").setPreferredWidth(80);
		table.getColumn("PRECIO").setPreferredWidth(80);
		table.getColumn("PRECIO").setCellRenderer(NumberRenderer.getCurrencyRenderer());
		
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
				    	deleteProduct();
				    //  System.out.println("delete record");
				    }else{
				    	//JOptionPane.showMessageDialog(rootPane, "Accion Cancelada !!!.", "CANCEL ACTION EVENT", JOptionPane.INFORMATION_MESSAGE);
				    }
				}else{
					//select row
					code = table.getValueAt(table.getSelectedRow(), 1).toString();
					getProduct(table.getValueAt(table.getSelectedRow(), 1).toString());
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
	//get information product
	private void getProduct(String code){
		System.out.println(code);
		Object[] info = MainPoint.db.SqliteGetRow("SELECT * FROM sp_products WHERE code='"+code+"'");
		txtCode.setText(code);
		txtName.setText(info[2].toString());
		taDescription.setText(info[3].toString());
		txtUnit.setText(info[4].toString());
		ffCost.setValue(info[5]);
	}
	//delete products
	private void deleteProduct(){
		String sql = "DELETE FROM sp_products WHERE code='"+code+"'";
		MainPoint.db.SqliteExecute(sql);
		try {
			loadProducts("");
			
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
				// TODO Auto-generated method stub
				String sql = null; 
				if(emptyFields()){
					JOptionPane.showMessageDialog(rootPane, "Debe llenar todos los campos !!!.", "CAMPOS REQUERIDOS", JOptionPane.WARNING_MESSAGE);
				}else{
					//update or save
					if(action){
						//VALIDATE PRODUCT CODE
						try {
							if(MainPoint.db.SqliteRowCount("SELECT COUNT(*) as rows FROM sp_products WHERE code='"+txtCode.getText()+"'") > 0){
								JOptionPane.showMessageDialog(rootPane, "El codigo ingresado ya existe !!!.", "CODIGO DEL PRODUCTO", JOptionPane.WARNING_MESSAGE);
								return;
							}else{
								//save or update information
								sql = "INSERT INTO sp_products(code, name, description, unit, price) VALUES('"+txtCode.getText()+"',"
									+ "'"+txtName.getText()+"','"+taDescription.getText()+"','"+txtUnit.getText()+"',"+ffCost.getValue()+")";
							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
				
					}else{
						sql = "UPDATE sp_products SET name='"+txtName.getText()+"',description='"+taDescription.getText()+"',unit='"+txtUnit.getText()+"',price="+ffCost.getValue()+""
								+ " WHERE code='"+txtCode.getText()+"'";
					}
				
					MainPoint.db.SqliteExecute(sql);
					JOptionPane.showMessageDialog(rootPane, "La informacion se ha guardado !!!.", "REGISTRAR PRODUCTOS", JOptionPane.INFORMATION_MESSAGE);
					statusDefault();
					defaultButtons();
					clearFields();
					action = true;
					try {
						loadProducts("");
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
		txtCode.setEnabled(false);
		txtName.setEnabled(false);
		taDescription.setEnabled(false);
		txtUnit.setEnabled(false);
		ffCost.setEnabled(false);
	}
	//status textfields
	private void statusNew(){
		txtCode.setEnabled(true);
		txtName.setEnabled(true);
		taDescription.setEnabled(true);
		txtUnit.setEnabled(true);
		ffCost.setEnabled(true);
	}
	//status update
	private void statusUpdate(){
		txtCode.setEnabled(false);
		txtName.setEnabled(true);
		taDescription.setEnabled(true);
		txtUnit.setEnabled(true);
		ffCost.setEnabled(true);
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
		txtCode.setText("");
		txtName.setText("");
		taDescription.setText("");
		txtUnit.setText("");
		ffCost.setValue(0);
	}
	//empty fields
	private boolean emptyFields(){
		boolean flag = true;
		int empty = 0;
		if(txtCode.getText().equals("")){
			empty++;
		}
		if(txtName.getText().equals("")){
			empty++;
		}
		if(taDescription.getText().equals("")){
			empty++;
		}
		if(txtUnit.getText().equals("")){
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
