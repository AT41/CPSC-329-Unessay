
public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AppView view = new AppView();
		AppModel model = new AppModel();
		AppController controller = new AppController(view, model);
	}

}
