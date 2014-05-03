package xkp.textfield;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;

public class TextField extends JTextField {
	
	//constructor
	public TextField(int x, int y, int width, int height){
		setBounds(x, y, width, height);
	}

	public TextField(int x, int y, int width, int height, Color color){
		setBounds(x, y, width, height);
		setForeground(color);
	}
	
	public TextField(int x, int y, int width, int height, String color, int style){
		setBounds(x, y, width, height);
		setForeground(Color.decode(color));
		setFont(new Font("Arial",style,12));
	}
}
