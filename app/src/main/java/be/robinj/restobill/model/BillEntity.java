package be.robinj.restobill.model;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.orm.SugarRecord;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import be.robinj.restobill.API;
import be.robinj.restobill.OrderActivity;
import be.robinj.restobill.R;
import be.robinj.restobill.adapter.CheckoutProductAdapter;
import be.robinj.restobill.listener.CheckoutNegativeOnClickListener;
import be.robinj.restobill.listener.CheckoutPositiveOnClickListener;
import be.robinj.restobill.listener.OrderListViewItemLongClickMenuOnClickListener;

/**
 * Created by robin on 03/11/15.
 */
public class BillEntity
	extends SugarRecord<BillEntity>
{
	public Long syncId = null;
	public TableEntity tableEntity;
	public boolean closed = false;

	public BillEntity ()
	{
	}

	public BillEntity (TableEntity table)
	{
		this.tableEntity = table;
	}

	public void startCheckout (Activity activity)
	{
		AlertDialog.Builder dlgBuilder = new AlertDialog.Builder (activity);
		LayoutInflater inflater = activity.getLayoutInflater ();

		View dlgView = inflater.inflate (R.layout.dialog_checkout, null);
		dlgBuilder
			.setView (dlgView)
			.setTitle (activity.getString (R.string.dialog_checkout_title))
			.setPositiveButton (R.string.dialog_checkout_positive, new CheckoutPositiveOnClickListener (activity, this.getId ()))
			.setNegativeButton (R.string.dialog_checkout_negative, new CheckoutNegativeOnClickListener ());

		List<OrderEntity> orders = OrderEntity.find (OrderEntity.class, "bill_entity = ?", String.valueOf (this.getId ()));
;
		ListView lvCheckoutProducts = (ListView) dlgView.findViewById (R.id.lvCheckoutProducts);
		TextView tvCheckoutTotalPrice = (TextView) dlgView.findViewById (R.id.tvCheckoutTotalPrice);

		float totalPrice = 0F;
		for (OrderEntity order : OrderEntity.find (OrderEntity.class, "bill_entity = ?", String.valueOf (this.getId ())))
			totalPrice += order.getPrice ();

		lvCheckoutProducts.setAdapter (new CheckoutProductAdapter (activity, orders));
		tvCheckoutTotalPrice.setText ("€" + new DecimalFormat ("0.00").format (totalPrice));

		AlertDialog dialog = dlgBuilder.create ();
		dialog.show ();
	}

	@Override
	public void save ()
	{
		this.save (true);
	}

	public void save (boolean sync)
	{
		super.save ();

		if (this.syncId == null)
		{
			this.syncId = this.getId ();

			this.save (false);
		}

		if (sync)
		{
			final BillEntity bill = this;

			Runnable runnable = new Runnable ()
			{
				@Override
				public void run ()
				{
					(new API ("http://10.0.2.2:8000/")).saveBill (bill);
				}
			};
			Thread thread = new Thread (runnable);

			thread.start ();
		}
	}
}
