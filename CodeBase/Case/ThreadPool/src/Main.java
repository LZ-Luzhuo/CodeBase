

public class Main {
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			ThreadManager.getInstance().createShortPool().execute(new Runnable() {
				@Override
				public void run() {
					while(true)
						System.out.println(Thread.currentThread().getId());
				}
			});
		}
	}
}
