package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ops.ReceiptGoods;
import catalog.Customers;
import catalog.Products;
import xkp.Label;
import xkp.comp.Calculator;
import xkp.comp.ImageButton;
import xkp.textfield.TextField;

public class PanelScreen extends JFrame {
	
	//PROPERTIES
	JMenuBar menuBar;
	JMenu menuSystem, menuCatalogs, menuConfig, menuHelp;
	JMenuItem itemExit, itemProducts, itemUsers, itemManual, itemAbout ;
	JMenuItem itemCustomers, itemProviders, itemEmployees, itemPayments;
	JLabel lblCode, lblProduct, lblImgLogo, lblFolio;
	JLabel lblSubTotal, lblTax, lblTotal, lblDiscount, SubTotal, Tax, Total, Discount;
	TextField txtCode, txtProduct;
	JPanel panHeader, panButtons, panService;
	JScrollPane scrollStock, scrollOrder, scrollMsgs;
	JButton btnLeft, btnRight;
		
	//CONSTRUCTOR
	public PanelScreen(){
		initWindow();
		initComponents();
		initMenu();
		panHeader();
	}
	
	//METHODS
	
	private void initWindow(){
		setLayout(null);
		setTitle("SMARTPOS Starter Version");
		setSize(960, 630);
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		//set look and feel
		try
		     {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		     }
		     catch (ClassNotFoundException e)
		     {
		       e.printStackTrace();
		     }
		     catch (InstantiationException e) {
		       e.printStackTrace();
		     }
		     catch (IllegalAccessException e) {
		       e.printStackTrace();
		     }
		     catch (UnsupportedLookAndFeelException e) {
		       e.printStackTrace();
		     }
	}
	
	private void initComponents(){
		//LABELS
		lblCode = new Label(12,"Codigo / Producto", 20, 100, 100, 25);
		//lblProduct = new Label(12,"Productos",20,90,100,25);
		//TEXTFIELDS
		txtCode = new TextField(20,120,200,30,"#CC7A00",1);
		//txtProduct = new TextField(20,110,150,25,"#CC7A00",1);
		
		add(lblCode);
	//	add(lblProduct);
		add(txtCode);
	//	add(txtProduct);
		
		Calculator calc = new Calculator();
		calc.setLocation(660, 220);
		add(calc);
		loadStock();
		loadOrder();
		buttons();
		labels();
		messages();
		panelButtons();
		buttonsService();
	}
	
	private void initMenu(){
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 960, 25);
		//Menus
		menuSystem = new JMenu("Sistema");
		menuSystem.setMnemonic('S');
		menuCatalogs = new JMenu("Catalogos");
		menuCatalogs.setMnemonic('C');
		menuConfig = new JMenu("Configuracion");
		menuConfig.setMnemonic('f');
		menuHelp = new JMenu("Ayuda");
		menuHelp.setMnemonic('A');
		//SubMenus
		itemExit = new JMenuItem("Salir");
		itemExit.setMnemonic('l');
		itemExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		});

		itemProducts = new JMenuItem("Productos");
		itemProducts.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Products prod = new Products();
				prod.setVisible(true);
				//IF ADD A NEW PRODUCT REFRESH LIST PRODUCTS STOCK
			}
			
		});
		itemCustomers = new JMenuItem("Clientes");
		menuCatalogs.add(itemCustomers);
		itemCustomers.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Customers customer = new Customers();
				customer.setVisible(true);
			}
			
		});
		
		itemUsers = new JMenuItem("Usuarios");
		menuConfig.add(itemUsers);
		itemManual = new JMenuItem("Manual");
		itemAbout = new JMenuItem("Acerca ...");
		menuHelp.add(itemAbout);
		menuHelp.add(itemManual);
		
		
		menuBar.add(menuSystem);
		menuBar.add(menuCatalogs);
		menuBar.add(menuConfig);
		menuBar.add(menuHelp);
		menuSystem.add(itemExit);
		menuCatalogs.add(itemProducts);
		add(menuBar);
		
	}
	
	//panel header
	private void panHeader(){
		panHeader = new JPanel();
		panHeader.setBounds(0, 20, 960, 70);
		//panHeader.setLayout(null);
		panHeader.setBackground(Color.decode("#001A33"));
		add(panHeader);
		ImageIcon iconLogo = new ImageIcon(getClass().getResource("/res/logo.png"));
		lblImgLogo = new JLabel();
		lblImgLogo.setIcon(iconLogo);
		lblImgLogo.setBounds(10, 10, 145 , 65);
		panHeader.add(lblImgLogo);
	}
	
	//load Stock
	private void loadStock(){
		scrollStock = new JScrollPane();
		scrollStock.setBounds(20,160,280,300);
		add(scrollStock);
		String columnNames[] = { "CODIGO","PRODUCTO","QTY"};
		String dataValues[][] =
			{
				{ "00001", "Coca Cola 600 ml", "4" },
				{ "00001", "Coca Cola 600 ml", "4" },
				{ "00001", "Coca Cola 600 ml", "4" }
			};
		JTable table = new JTable(dataValues, columnNames);
		//table.setBounds(20, 160, 280, 250);
		scrollStock.setViewportView(table);
	}
	
	//load Order Sale
	private void loadOrder(){
		
		//label folio
		lblFolio = new JLabel("FOLIO / No.Orden: ");
		lblFolio.setBounds(360, 115, 250, 30);
		lblFolio.setFont(new Font("Impact",1,14));
		lblFolio.setForeground(Color.red);
		add(lblFolio);
		
		String columnNames[] = { "CODIGO","PRODUCTO","QTY"};
		String dataValues[][] =
			{
				{ "00001", "Coca Cola 600 ml", "4" },
				{ "00001", "Coca Cola 600 ml", "4" },
				{ "00001", "Coca Cola 600 ml", "4" }
			};
		JTable table = new JTable(dataValues, columnNames);
		table.setBounds(360, 160, 280, 300);
		add(table);
	}
	
	//buttons
	private void buttons(){
		ImageIcon iconLeft = new ImageIcon(getClass().getResource("/res/left.png"));
		btnLeft = new JButton();
		btnLeft.setIcon(iconLeft);
		btnLeft.setToolTipText("Quitar Producto");
		btnLeft.setBounds(309, 260, 40, 40);
		add(btnLeft);
		ImageIcon iconRight = new ImageIcon(getClass().getResource("/res/right.png"));
		btnRight = new JButton();
		btnRight.setIcon(iconRight);
		btnRight.setToolTipText("Agregar Producto");
		btnRight.setBounds(309, 300, 40, 40);
		add(btnRight);
	}
	
	//labels
	private void labels(){
		//subtotal
		lblSubTotal = new JLabel("SUBTOTAL :");
		lblSubTotal.setBounds(430,470, 130, 25);
		lblSubTotal.setFont(new Font("Arial",1,14));
		lblSubTotal.setForeground(Color.blue);
		lblSubTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblSubTotal);
		//tax
		lblTax = new JLabel("IVA :");
		lblTax.setBounds(430,490, 130, 25);
		lblTax.setFont(new Font("Arial",1,14));
		lblTax.setForeground(Color.blue);
		lblTax.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblTax);
		//discount
		lblDiscount = new JLabel("DESCUENTO :");
		lblDiscount.setBounds(430,510, 130, 25);
		lblDiscount.setFont(new Font("Arial",1,14));
		lblDiscount.setForeground(Color.blue);
		lblDiscount.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblDiscount);
		//total
		lblTotal= new JLabel("TOTAL :");
		lblTotal.setBounds(430,550, 130, 25);
		lblTotal.setFont(new Font("Arial",1,14));
		lblTotal.setForeground(Color.blue);
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblTotal);
	}
	//show messages & alerts
	private void messages(){
		scrollMsgs = new JScrollPane();
		scrollMsgs.setBounds(15, 470, 420, 120);
		scrollMsgs.setBorder(BorderFactory.createTitledBorder("Mensajes y Alertas"));
		add(scrollMsgs);
		String columnNames[] = { "MENSAJE","DETALLES"};
		String dataValues[][] =
			{
				{ "00001", "Coca Cola 600 ml"},
				{ "00001", "Coca Cola 600 ml"},
				{ "00001", "Coca Cola 600 ml"}
			};
		JTable table = new JTable(dataValues, columnNames);
		scrollMsgs.setViewportView(table);
	}
	
	//panel buttons
	private void panelButtons(){
		//first row
		panButtons = new JPanel();
		panButtons.setBounds(660, 100, 290, 110);
		panButtons.setLayout(null);
		panButtons.setBorder(BorderFactory.createTitledBorder("Panel"));
		add(panButtons);
		ImageButton imgBtnIn = new ImageButton("/res/receipt.png",15,30,65,65);
		imgBtnIn.setToolTipText("Recibir Mercancia");
		imgBtnIn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ReceiptGoods receipt = new ReceiptGoods();
				receipt.setVisible(true);
			}
			
		});
		panButtons.add(imgBtnIn);
		ImageButton BtnCash = new ImageButton("/res/caja52x52.png",80,30,65,65);
		BtnCash.setToolTipText("Caja Chica");
		panButtons.add(BtnCash);
		ImageButton BtnPay = new ImageButton("/res/pay52x52.png",145,30,65,65);
		BtnPay.setToolTipText("Pagos");
		panButtons.add(BtnPay);
		//second row
		ImageButton BtnStock = new ImageButton("/res/stock.png",210,30,65,65);
		BtnStock.setToolTipText("Almacen");
		panButtons.add(BtnStock);
		
	}
	
	//buttons service
	private void buttonsService(){
		panService = new JPanel();
		panService.setBounds(660, 480, 290, 100);
		panService.setLayout(null);
		panService.setBorder(BorderFactory.createTitledBorder("Acciones"));
		add(panService);
		
		ImageButton BtnPay = new ImageButton("/res/payment52x52.png",20,25,80,60);
		BtnPay.setToolTipText("Cobrar Orden");
		panService.add(BtnPay);
		
		//button clear
		ImageButton BtnClear = new ImageButton("/res/clear52x52.png",105,25,80,60);
		BtnClear.setToolTipText("Nueva Orden o Limpiar Orden");
		panService.add(BtnClear);
		
		ImageButton BtnHome = new ImageButton("/res/home52x52.png",190,25,80,60);
		BtnHome.setToolTipText("Servicio a Domicilio");
		panService.add(BtnHome);
	}

}
