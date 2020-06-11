package types;

public class Main {

	public static void Main(String[] args) {
		// TODO Auto-generated method stub
		AppView view = new AppView();
		AppModel model = new AppModel();
		AppController controller = new AppController(view, model);
	}

}
