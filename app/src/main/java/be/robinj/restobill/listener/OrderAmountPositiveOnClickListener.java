package be.robinj.restobill.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import be.robinj.restobill.OrderActivity;
import be.robinj.restobill.R;
import be.robinj.restobill.TableActivity;
import be.robinj.restobill.model.OrderEntity;
import be.robinj.restobill.model.TableEntity;

/**
 * Created by robin on 03/11/15.
 */
public class OrderAmountPositiveOnClickListener
	implements DialogInterface.OnClickListener
{
	private Activity parent;
	private View dlgView;
	private OrderEntity order;

	public OrderAmountPositiveOnClickListener (Activity parent, View dlgView, OrderEntity order)
	{
		this.parent = parent;
		this.dlgView = dlgView;
		this.order = order;
	}

	@Override
	public void onClick (DialogInterface dialogInterface, int i)
	{
		NumberPicker npOrderAmount = (NumberPicker) this.dlgView.findViewById (R.id.npOrderAmount);

		if (this.order.amount != npOrderAmount.getValue ())
		{
			this.order.amount = (short) npOrderAmount.getValue ();
			this.order.save ();

			((OrderActivity) this.parent).refreshOrders ();

			Snackbar.make (this.parent.findViewById (R.id.colaOrders), "Amount changed", Snackbar.LENGTH_SHORT).show ();
		}
	}
}
