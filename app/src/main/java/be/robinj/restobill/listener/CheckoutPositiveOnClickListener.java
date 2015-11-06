package be.robinj.restobill.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import be.robinj.restobill.R;
import be.robinj.restobill.TableActivity;
import be.robinj.restobill.model.TableEntity;

/**
 * Created by robin on 03/11/15.
 */
public class CheckoutPositiveOnClickListener
	implements DialogInterface.OnClickListener
{
	private Activity parent;
	private long billId;

	public CheckoutPositiveOnClickListener (Activity parent, long billId)
	{
		this.parent = parent;
		this.billId = billId;
	}

	@Override
	public void onClick (DialogInterface dialogInterface, int i)
	{
		AlertDialog.Builder dlgBuilder = new AlertDialog.Builder (this.parent);

		dlgBuilder
			.setMessage ("Are you sure you wish to close and archive the bill?")
			.setPositiveButton (android.R.string.yes, new CheckoutConfirmPositiveOnClickListener (this.parent, this.billId))
			.setNegativeButton (android.R.string.no, new CheckoutNegativeOnClickListener ());

		AlertDialog dialog = dlgBuilder.create ();
		dialog.show ();
	}
}
