package xkp.comp;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ImageButton extends JButton {
	
	public ImageButton(String image_path, int x, int y, int width, int height){
		Image img = null;
		try {
			img = ImageIO.read(getClass().getResource(image_path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setIcon(new ImageIcon(img));
		setBounds(x, y, width, height);
	}

}
