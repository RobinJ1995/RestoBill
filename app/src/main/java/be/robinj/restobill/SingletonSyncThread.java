package be.robinj.restobill;

/**
 * Created by robin on 07/11/15.
 */
public class SingletonSyncThread extends Thread
{
	private static SingletonSyncThread instance = null;

	public static SingletonSyncThread getInstance (Runnable runnable)
	{
		if (SingletonSyncThread.instance == null)
			SingletonSyncThread.instance = new SingletonSyncThread (runnable);

		return SingletonSyncThread.instance;
	}

	private SingletonSyncThread (Runnable runnable)
	{
		super (runnable);
	}
}
