package be.robinj.restobill.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import be.robinj.restobill.ProductActivity;
import be.robinj.restobill.R;

/**
 * Created by robin on 03/11/15.
 */
public class OrderAddOnClickListener
	implements View.OnClickListener
{
	private Activity parent;
	private long billId;

	public OrderAddOnClickListener (Activity parent, long billId)
	{
		this.parent = parent;
		this.billId = billId;
	}

	@Override
	public void onClick (View view)
	{
		Intent intent = new Intent (this.parent, ProductActivity.class);
		intent.putExtra ("billId", this.billId);

		this.parent.startActivityForResult (intent, 1);
	}
}
