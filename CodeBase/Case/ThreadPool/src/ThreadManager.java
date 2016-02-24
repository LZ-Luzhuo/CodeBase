import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 管理线程池
 * 联网比较耗时,读取本件文件,分开放
 * 使用: ThreadManager.getInstance().createLongPool().execute(new Runnable());
 * @author Luzhuo
 */
public class ThreadManager {
	private ThreadManager(){ }
	private static ThreadManager instance = new ThreadManager();
	private ThreadPoolProxy longPool;
	private ThreadPoolProxy shortPool;
	
	public static ThreadManager getInstance(){
		return instance;
	}
	
	public synchronized ThreadPoolProxy createLongPool(){ // 联网用
		if(longPool == null)
			longPool = new ThreadPoolProxy(5, 5, 5000L);
		return longPool;
	}
	
	public synchronized ThreadPoolProxy createShortPool(){ // 读取本地文件用
		if(shortPool == null)
			shortPool = new ThreadPoolProxy(5, 5, 5000L);
		return shortPool;
	}
	
	public class ThreadPoolProxy{
		private ThreadPoolExecutor pool; // 线程池
		private int corePoolSize; // 线程池里面管理多少个线程
		private int maximumPoolSize; // 如果排队满了 额外开的线程数
		private long time; // 如果线程池没有要执行的任务 存活多久
		public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long time){
			this.corePoolSize = corePoolSize;
			this.maximumPoolSize = maximumPoolSize;
			this.time = time;
		}
		public void execute(Runnable runnable){
			if(pool == null){
				// 创建线程池
				/* 1.线程池里面管理多少个线程
				 * 2.如果排队满了 额外开的线程数
				 * 3.如果线程池没有要执行的任务 存活多久
				 * 4.时间单位
				 * 5.如果 线程池里管理的线程都已经用了,剩下的任务 临时存到LinkedBlockingQueue对象中 排队
				 */
				pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, time, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10));
			}
			pool.execute(runnable); //调用线程池 执行异步任务
		}
		
		public void cancel(Runnable runnable){
			if(pool != null && !pool.isShutdown() && !pool.isTerminated()){
				pool.remove(runnable); // 移除异步任务
			}
		}
	}
}
