package be.robinj.restobill.model;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import be.robinj.restobill.API;

/**
 * Created by robin on 03/11/15.
 */
public class TableEntity
	extends SugarRecord<TableEntity>
{
	public Long syncId = null;
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
		this.save (true);
	}

	public void save (boolean sync)
	{
		super.save ();

		if (this.syncId == null)
		{
			this.syncId = this.getId ();

			this.save (false);
		}

		if (sync)
		{
			final TableEntity table = this;

			Runnable runnable = new Runnable ()
			{
				@Override
				public void run ()
				{
					API.getInstance (null).saveTable (table);
				}
			};
			Thread thread = new Thread (runnable);

			thread.start ();
		}
	}

	@Override
	public void delete ()
	{
		this.delete (true);
	}

	public void delete (boolean sync)
	{
		super.delete ();

		if (sync)
		{
			final TableEntity table = this;

			Runnable runnable = new Runnable ()
			{
				@Override
				public void run ()
				{
					API.getInstance (null).removeTable (table);
				}
			};
			Thread thread = new Thread (runnable);

			thread.start ();
		}
	}
}
