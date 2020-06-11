package types;

import gui.MainGUI;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainGUI view = new gui.Main();
		AppModel model = new AppModel();
		AppController controller = new AppController(view, model);
	}

}
