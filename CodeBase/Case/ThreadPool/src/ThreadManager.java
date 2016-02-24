import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * �����̳߳�
 * �����ȽϺ�ʱ,��ȡ�����ļ�,�ֿ���
 * ʹ��: ThreadManager.getInstance().createLongPool().execute(new Runnable());
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
	
	public synchronized ThreadPoolProxy createLongPool(){ // ������
		if(longPool == null)
			longPool = new ThreadPoolProxy(5, 5, 5000L);
		return longPool;
	}
	
	public synchronized ThreadPoolProxy createShortPool(){ // ��ȡ�����ļ���
		if(shortPool == null)
			shortPool = new ThreadPoolProxy(5, 5, 5000L);
		return shortPool;
	}
	
	public class ThreadPoolProxy{
		private ThreadPoolExecutor pool; // �̳߳�
		private int corePoolSize; // �̳߳����������ٸ��߳�
		private int maximumPoolSize; // ����Ŷ����� ���⿪���߳���
		private long time; // ����̳߳�û��Ҫִ�е����� �����
		public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long time){
			this.corePoolSize = corePoolSize;
			this.maximumPoolSize = maximumPoolSize;
			this.time = time;
		}
		public void execute(Runnable runnable){
			if(pool == null){
				// �����̳߳�
				/* 1.�̳߳����������ٸ��߳�
				 * 2.����Ŷ����� ���⿪���߳���
				 * 3.����̳߳�û��Ҫִ�е����� �����
				 * 4.ʱ�䵥λ
				 * 5.��� �̳߳��������̶߳��Ѿ�����,ʣ�µ����� ��ʱ�浽LinkedBlockingQueue������ �Ŷ�
				 */
				pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, time, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10));
			}
			pool.execute(runnable); //�����̳߳� ִ���첽����
		}
		
		public void cancel(Runnable runnable){
			if(pool != null && !pool.isShutdown() && !pool.isTerminated()){
				pool.remove(runnable); // �Ƴ��첽����
			}
		}
	}
}
