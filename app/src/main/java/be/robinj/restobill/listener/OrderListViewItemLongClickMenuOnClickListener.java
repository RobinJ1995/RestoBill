package be.robinj.restobill.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import be.robinj.restobill.OrderActivity;
import be.robinj.restobill.R;
import be.robinj.restobill.TableActivity;
import be.robinj.restobill.model.OrderEntity;
import be.robinj.restobill.model.TableEntity;

/**
 * Created by robin on 03/11/15.
 */
public class OrderListViewItemLongClickMenuOnClickListener
	implements DialogInterface.OnClickListener
{
	private Activity parent;
	private long id;

	public OrderListViewItemLongClickMenuOnClickListener (Activity parent, long id)
	{
		this.parent = parent;
		this.id = id;
	}

	@Override
	public void onClick (DialogInterface dialogInterface, int i)
	{
		OrderEntity order = OrderEntity.findById (OrderEntity.class, this.id);

		switch (i)
		{
			case 0: // Change amount //
				AlertDialog.Builder dlgBuilder = new AlertDialog.Builder (this.parent);
				LayoutInflater inflater = this.parent.getLayoutInflater ();

				View dlgView = inflater.inflate (R.layout.dialog_order_amount, null);
				dlgBuilder
					.setView (dlgView)
					.setTitle (this.parent.getString (R.string.dialog_order_amount_title))
					.setPositiveButton (R.string.dialog_tables_add_positive, new OrderAmountPositiveOnClickListener (this.parent, dlgView, order))
					.setNegativeButton (R.string.dialog_tables_add_negative, new TableAddNegativeOnClickListener ());

				NumberPicker npOrderAmount = (NumberPicker) dlgView.findViewById (R.id.npOrderAmount);
				npOrderAmount.setMinValue (1);
				npOrderAmount.setMaxValue (25); // Not sure why I can't set these values straight from the XML file //
				npOrderAmount.setValue (order.amount);

				AlertDialog dialog = dlgBuilder.create ();
				dialog.show ();

				break;
			case 1: // Remove //
				order.delete ();
				((OrderActivity) this.parent).refreshOrders ();

				Snackbar.make (this.parent.findViewById (R.id.colaOrders), "Order removed", Snackbar.LENGTH_SHORT).show ();

				break;
		}
	}
}
