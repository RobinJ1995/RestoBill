package be.robinj.restobill.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import be.robinj.restobill.R;

/**
 * Created by Afaci on 05/11/15.
 */
public class ManageProductAddOnClickListener
{
	private Activity parent;

	public ManageProductAddOnClickListener (Activity parent)
	{
		this.parent = parent;
	}


	public void show ()
	{
		AlertDialog.Builder dlgBuilder = new AlertDialog.Builder (this.parent);
		LayoutInflater inflater = this.parent.getLayoutInflater ();

		View dlgView = inflater.inflate (R.layout.dialog_product_manage_add, null);
		dlgBuilder
			.setView (dlgView)
			.setTitle ("Add product")
			.setPositiveButton (R.string.dialog_tables_add_positive, new ProductAddPositiveListener (this.parent, dlgView))
			.setNegativeButton (R.string.dialog_tables_add_negative, new TableAddNegativeOnClickListener ());

		AlertDialog dialog = dlgBuilder.create ();
		dialog.show ();
	}
}
