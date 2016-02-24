import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池原理
 * @author Luzhuo
 */
public class ThreadPool {
	int maxCount = 3; // 最多开启多少个线程
	AtomicInteger count = new AtomicInteger(0); // 当前开的线程数   AtomicInteger:线程同步的Integer
	LinkedList<Runnable> runnables = new LinkedList<Runnable>();
	
	/**
	 * 执行线程
	 * @param runnable
	 */
	public void execute(Runnable runnable){
		runnables.add(runnable);
		if(count.incrementAndGet() <= maxCount){
			createThread();
		}
	}
	
	/**
	 * 创建线程
	 */
	private void createThread(){
		new Thread(){
			@Override
			public void run() {
				if(runnables.size() > 0){
					while(true){
						Runnable remove = runnables.remove(0); // 取出一个异步任务
						if(remove != null){
							remove.run();
						}
					}
				}else{
					// 让线程等待 wake();
				}
			}
		}.start();
	}
	
}
