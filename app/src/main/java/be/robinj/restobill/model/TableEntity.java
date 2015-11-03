package be.robinj.restobill.model;

import com.orm.SugarRecord;

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
}
