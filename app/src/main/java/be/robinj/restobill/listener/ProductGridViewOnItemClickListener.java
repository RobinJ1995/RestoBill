package be.robinj.restobill.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.util.HashSet;

import be.robinj.restobill.R;
import be.robinj.restobill.adapter.ProductAdapter;
import be.robinj.restobill.model.TableEntity;

/**
 * Created by robin on 03/11/15.
 */
public class ProductGridViewOnItemClickListener
	implements AdapterView.OnItemClickListener
{
	private HashSet<Long> selected;

	public ProductGridViewOnItemClickListener (HashSet<Long> selected)
	{
		this.selected = selected;
	}

	@Override
	public void onItemClick (AdapterView<?> adapterView, View view, int i, long l)
	{
		long id = (long) view.getTag ();

		if (this.selected.contains (id))
			this.selected.remove (id);
		else
			this.selected.add (id);

		((ProductAdapter) adapterView.getAdapter ()).notifyDataSetChanged ();
	}
}
