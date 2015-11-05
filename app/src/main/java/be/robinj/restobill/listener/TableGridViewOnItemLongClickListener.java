package be.robinj.restobill.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;

import be.robinj.restobill.R;
import be.robinj.restobill.model.TableEntity;

/**
 * Created by robin on 03/11/15.
 */
public class TableGridViewOnItemLongClickListener
	implements AdapterView.OnItemLongClickListener
{
	private Activity parent;

	public TableGridViewOnItemLongClickListener (Activity parent)
	{
		this.parent = parent;
	}

	@Override
	public boolean onItemLongClick (AdapterView<?> adapterView, View view, int i, long l)
	{
		long id = (long) view.getTag ();
		TableEntity table = TableEntity.findById (TableEntity.class, id);

		AlertDialog.Builder dlgBuilder = new AlertDialog.Builder (this.parent);
		dlgBuilder
			.setTitle (table.name)
			.setItems (R.array.tables_gridview_item_long_click_menu, new TableGridViewItemLongClickMenuOnClickListener (this.parent, id));

		dlgBuilder.create ().show ();

		return true;
	}
}
