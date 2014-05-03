package xkp.comp;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.text.InternationalFormatter;

public class FieldFloat extends JFormattedTextField {
	
	//construct class
	public FieldFloat(int x, int y, int width, int height, JComponent comp, float value){
		super(value);
		this.setBounds(x, y, width, height);
		this.setFormatterFactory(new AbstractFormatterFactory(){

			@Override
			public AbstractFormatter getFormatter(JFormattedTextField tf) {
				// TODO Auto-generated method stub
				 NumberFormat format = DecimalFormat.getInstance();
		         format.setMinimumFractionDigits(2);
		         format.setMaximumFractionDigits(2);
		         format.setRoundingMode(RoundingMode.HALF_UP);
		         InternationalFormatter formatter = new InternationalFormatter(format);
		         formatter.setAllowsInvalid(false);
		         //formatter.setMinimum(0.0);
		         //formatter.setMaximum(1000.00);
		      //   this.setFormatter(formatter);
		         return formatter;
			}
			
		});
		comp.add(this);
	}
}
