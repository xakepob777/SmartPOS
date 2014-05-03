package ops;

import java.awt.Color;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import xkp.Label;
import xkp.comp.ImageButton;
import xkp.textfield.TextField;

public class ReceiptGoods extends JDialog {
	
	JScrollPane scrollStock, scrollOrder;
	ImageButton btnClose;
	JLabel lblFolio, lblCode;
	JButton btnLeft, btnRight, btnAccept, btnCancel, btnReport;
	JTextField txtCode;
	
	
	//costructor class
	public ReceiptGoods(){
		initWindow();
		initComponents();
	}
	
	private void initWindow(){
		setTitle("RECEPCION DE MERCANCIA");
		setSize(660, 500);
		setModal(true);
		setLocationRelativeTo(null);
		setLayout(null);
		getContentPane().setBackground(Color.decode("#b2b2b2"));
	}
	
	private void initComponents(){
		loadStock();
		loadOrder();
		buttons();
		//report button
		btnReport = new ImageButton("/res/report32x32.png",20,425,200,40);
		btnReport.setText("Reporte XLS / PDF");
		btnReport.setToolTipText("Generar Reporte");
		add(btnReport);
		btnReport.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
			
		});
		//button close
		btnClose = new ImageButton("/res/close32x32.png",480,425,160,40);
		btnClose.setText("Cerrar Ventana");
		btnClose.setToolTipText("Cerrar Ventana");
		add(btnClose);
		btnClose.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
			
		});
		
	}
	
	//load Stock
		private void loadStock(){
			
			//LABELS
			lblCode = new Label(12,"Codigo / Producto", 20, 5, 100, 25);
			//lblProduct = new Label(12,"Productos",20,90,100,25);
			//TEXTFIELDS
			txtCode = new TextField(20,25,200,30,"#CC7A00",1);
			//txtProduct = new TextField(20,110,150,25,"#CC7A00",1);
			
			add(lblCode);
		//	add(lblProduct);
			add(txtCode);
		//	add(txtProduct);
			
			scrollStock = new JScrollPane();
			scrollStock.setBounds(20,60,280,300);
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
			lblFolio.setBounds(360, 15, 250, 30);
			lblFolio.setFont(new Font("Impact",1,14));
			lblFolio.setForeground(Color.red);
			add(lblFolio);
			
			scrollOrder = new JScrollPane();
			scrollOrder.setBounds(360,60,280,300);
			
			String columnNames[] = { "CODIGO","PRODUCTO","QTY"};
			String dataValues[][] =
				{
					{ "00001", "Coca Cola 600 ml", "4" },
					{ "00001", "Coca Cola 600 ml", "4" },
					{ "00001", "Coca Cola 600 ml", "4" }
				};
			JTable table = new JTable(dataValues, columnNames);
			scrollOrder.setViewportView(table);
			//table.setBounds(360, 60, 280, 300);
			add(scrollOrder);
			
			//button confirm order 
			btnAccept = new ImageButton("/res/confirm_order.png",280,370,180,40);
			btnAccept.setText("Confirmar Orden");
			btnAccept.setToolTipText("Confirmar Orden");
			add(btnAccept);
			btnAccept.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					//dispose();
				}
				
			});
			//cancel order
			btnCancel = new ImageButton("/res/cancel_order.png",460,370,180,40);
			btnCancel.setText("Cancelar / Limpiar");
			btnCancel.setToolTipText("Cancelar o Limpiar la Orden");
			add(btnCancel);
			btnCancel.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					//dispose();
				}
				
			});
		}
		
		//buttons
		private void buttons(){
			ImageIcon iconLeft = new ImageIcon(getClass().getResource("/res/left.png"));
			btnLeft = new JButton();
			btnLeft.setIcon(iconLeft);
			btnLeft.setToolTipText("Quitar Producto");
			btnLeft.setBounds(309, 160, 40, 40);
			add(btnLeft);
			ImageIcon iconRight = new ImageIcon(getClass().getResource("/res/right.png"));
			btnRight = new JButton();
			btnRight.setIcon(iconRight);
			btnRight.setToolTipText("Agregar Producto");
			btnRight.setBounds(309, 200, 40, 40);
			add(btnRight);
		}

}
