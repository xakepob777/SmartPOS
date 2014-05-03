package xkp.comp;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class TableData extends JTable {
	
	//PROPERTIES
	JScrollPane scrollTable;
	
	
	//CONSTRUCTOR
	public TableData(String[] columns, ResultSet rs) throws SQLException{
		//super(data, columns);
		DefaultTableModel model = new DefaultTableModel()
	      {
	        public boolean isCellEditable(int row, int column)
	        {
	          return false;
	        }
	      };
	      
	      for (int c = 0; c < columns.length; c++) {
	    	   model.addColumn(columns[c]);
	    }
	      
	      while (rs.next())
	           {
	             Object[] fila = new Object[columns.length];
	       
	             for (int i = 0; i < columns.length; i++){
	               fila[i] = rs.getObject(i + 1);
	             //this.subtotal += Double.parseDouble(rs.getObject("total").toString());
	               	System.out.println(fila[i].toString());
	             }
	             model.addRow(fila);
	           }
	         
	           rs = null;
	       
	       setModel(model);
	   //   this.setModel(model);
	      
	      
	//	scrollTable = new JScrollPane();
	//	scrollTable.setLayout(null);
	//	scrollTable.setViewportView(this);
	}
	
	//METHODS

}
