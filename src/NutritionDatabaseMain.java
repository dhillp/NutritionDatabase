import java.awt.EventQueue;

public final class NutritionDatabaseMain {
	
	private NutritionDatabaseMain() {
		//Prevents Instantiation
	}
	
	public static void main(final String[] theArgs) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new LoginWindow();
			}
		});
	}
}
