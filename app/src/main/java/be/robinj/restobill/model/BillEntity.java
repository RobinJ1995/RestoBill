package be.robinj.restobill.model;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robin on 03/11/15.
 */
public class BillEntity
	extends SugarRecord<BillEntity>
{
	public TableEntity tableEntity;
	public boolean closed = false;

	public BillEntity ()
	{
	}

	public BillEntity (TableEntity table)
	{
		this.tableEntity = table;
	}
}
