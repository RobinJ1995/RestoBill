package be.robinj.restobill.model;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import be.robinj.restobill.OrderActivity;
import be.robinj.restobill.R;
import be.robinj.restobill.adapter.CheckoutProductAdapter;
import be.robinj.restobill.listener.OrderListViewItemLongClickMenuOnClickListener;

/**
 * Created by robin on 03/11/15.
 */
public class BillEntity
	extends SugarRecord<BillEntity>
{
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
			.setTitle (activity.getString (R.string.dialog_checkout_title));
			//.setPositiveButton (R.string.dialog_tables_add_positive, new TableAddPositiveOnClickListener (this.parent, dlgView))
			//.setNegativeButton (R.string.dialog_tables_add_negative, new TableAddNegativeOnClickListener ());

		List<OrderEntity> orders = OrderEntity.find (OrderEntity.class, "bill_entity = ?", String.valueOf (this.getId ()));
;
		ListView lvCheckoutProducts = (ListView) dlgView.findViewById (R.id.lvCheckoutProducts);
		lvCheckoutProducts.setAdapter (new CheckoutProductAdapter (activity, orders));
;
		AlertDialog dialog = dlgBuilder.create ();
		dialog.show ();
	}
}
