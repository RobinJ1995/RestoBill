package be.robinj.restobill.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import be.robinj.restobill.R;

/**
 * Created by robin on 03/11/15.
 */
public class TableAddOnClickListener
	implements View.OnClickListener
{
	private Activity parent;

	public TableAddOnClickListener (Activity parent)
	{
		this.parent = parent;
	}

	@Override
	public void onClick (View view)
	{
		AlertDialog.Builder dlgBuilder = new AlertDialog.Builder (this.parent);
		LayoutInflater inflater = this.parent.getLayoutInflater ();

		View dlgView = inflater.inflate (R.layout.dialog_table_add, null);
		dlgBuilder
			.setView (dlgView)
			.setTitle (this.parent.getString (R.string.dialog_tables_add_title))
			.setPositiveButton (R.string.dialog_tables_add_positive, new TableAddPositiveOnClickListener (this.parent, dlgView))
			.setNegativeButton (R.string.dialog_tables_add_negative, new TableAddNegativeOnClickListener ());

		AlertDialog dialog = dlgBuilder.create ();
		dialog.show ();
	}
}
