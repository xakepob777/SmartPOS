package xkp;

import java.awt.Font;

import javax.swing.JLabel;

public class Label extends JLabel {
	
	//CONSTRUCT
	public Label(String text, int x, int y, int width, int height){
		setText(text);
		setBounds(x, y, width, height);
	}
	
	public Label(int size, String text, int x, int y, int width, int height){
		setText(text);
		setBounds(x, y, width, height);
		setFont(new Font("Arial", Font.BOLD, size));
	}

}
