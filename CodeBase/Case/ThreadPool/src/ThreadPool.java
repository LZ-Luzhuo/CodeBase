import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * �̳߳�ԭ��
 * @author Luzhuo
 */
public class ThreadPool {
	int maxCount = 3; // ��࿪�����ٸ��߳�
	AtomicInteger count = new AtomicInteger(0); // ��ǰ�����߳���   AtomicInteger:�߳�ͬ����Integer
	LinkedList<Runnable> runnables = new LinkedList<Runnable>();
	
	/**
	 * ִ���߳�
	 * @param runnable
	 */
	public void execute(Runnable runnable){
		runnables.add(runnable);
		if(count.incrementAndGet() <= maxCount){
			createThread();
		}
	}
	
	/**
	 * �����߳�
	 */
	private void createThread(){
		new Thread(){
			@Override
			public void run() {
				if(runnables.size() > 0){
					while(true){
						Runnable remove = runnables.remove(0); // ȡ��һ���첽����
						if(remove != null){
							remove.run();
						}
					}
				}else{
					// ���̵߳ȴ� wake();
				}
			}
		}.start();
	}
	
}
