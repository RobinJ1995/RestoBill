package be.robinj.restobill.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import be.robinj.restobill.OrderActivity;
import be.robinj.restobill.R;
import be.robinj.restobill.model.TableEntity;

/**
 * Created by robin on 03/11/15.
 */
public class TableGridViewOnItemClickListener
	implements AdapterView.OnItemClickListener
{
	private Activity parent;

	public TableGridViewOnItemClickListener (Activity parent)
	{
		this.parent = parent;
	}

	@Override
	public void onItemClick (AdapterView<?> adapterView, View view, int i, long l)
	{
		long id = (long) view.getTag ();

		Intent intent = new Intent (this.parent, OrderActivity.class);
		intent.putExtra ("tableId", id);

		this.parent.startActivityForResult (intent, 1);
	}
}
