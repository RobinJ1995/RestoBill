package be.robinj.restobill.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;

import be.robinj.restobill.OrderActivity;
import be.robinj.restobill.R;
import be.robinj.restobill.TableActivity;
import be.robinj.restobill.model.BillEntity;

/**
 * Created by robin on 03/11/15.
 */
public class CheckoutConfirmPositiveOnClickListener
	implements DialogInterface.OnClickListener
{
	private Activity parent;
	private long billId;

	public CheckoutConfirmPositiveOnClickListener (Activity parent, long billId)
	{
		this.parent = parent;
		this.billId = billId;
	}

	@Override
	public void onClick (DialogInterface dialogInterface, int i)
	{
		BillEntity bill = BillEntity.findById (BillEntity.class, this.billId);
		bill.closed = true;

		bill.save ();

		View colaTables = this.parent.findViewById (R.id.colaTables);
		if (colaTables != null)
			Snackbar.make (colaTables, "Bill closed", Snackbar.LENGTH_SHORT).show ();

		if (this.parent instanceof TableActivity)
			((TableActivity) this.parent).refreshTables ();
		else
			this.parent.finish ();
	}
}
