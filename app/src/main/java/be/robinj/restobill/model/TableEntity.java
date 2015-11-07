package be.robinj.restobill.model;

import android.util.Log;

import com.orm.SugarRecord;

import be.robinj.restobill.API;

/**
 * Created by robin on 03/11/15.
 */
public class TableEntity
	extends SugarRecord<TableEntity>
{
	public String name;

	public TableEntity ()
	{
	}

	public TableEntity (String name)
	{
		this.name = name;
	}

	@Override
	public void save ()
	{
		super.save ();

		final TableEntity table = this;

		Runnable runnable = new Runnable ()
		{
			@Override
			public void run ()
			{
				(new API ("http://10.0.2.2:8000/")).saveTable (table);
			}
		};
		Thread thread = new Thread (runnable);

		thread.start ();
	}
}
