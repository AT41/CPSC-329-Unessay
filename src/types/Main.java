package types;

import gui.GUI;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GUI view = new gui.AppView();
		AppModel model = new AppModel();
		AppController controller = new AppController(view, model);
	}

}
