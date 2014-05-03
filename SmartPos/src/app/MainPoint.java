package app;

import ita.Sqlite;

public class MainPoint {
	public static Sqlite db;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		db = new Sqlite("pos.db");
		PanelScreen panel = new PanelScreen();
		panel.setVisible(true);

	}

}
