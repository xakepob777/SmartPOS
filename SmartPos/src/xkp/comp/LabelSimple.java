package xkp.comp;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class LabelSimple extends JLabel {
	
	//constructor
	public LabelSimple(String text, int x, int y, int width, int height,String color){
		setText(text);
		setForeground(Color.decode(color));
		setBounds(x, y, width, height);
		setFont(new Font("Arial",1,12));
	}
	
	

}
