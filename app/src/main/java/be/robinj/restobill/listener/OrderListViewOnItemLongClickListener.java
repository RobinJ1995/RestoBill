package be.robinj.restobill.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;

import be.robinj.restobill.R;
import be.robinj.restobill.model.OrderEntity;
import be.robinj.restobill.model.TableEntity;

/**
 * Created by robin on 03/11/15.
 */
public class OrderListViewOnItemLongClickListener
	implements AdapterView.OnItemLongClickListener
{
	private Activity parent;

	public OrderListViewOnItemLongClickListener (Activity parent)
	{
		this.parent = parent;
	}

	@Override
	public boolean onItemLongClick (AdapterView<?> adapterView, View view, int i, long l)
	{
		long id = (long) view.getTag ();
		OrderEntity order = OrderEntity.findById (OrderEntity.class, id);

		AlertDialog.Builder dlgBuilder = new AlertDialog.Builder (this.parent);
		dlgBuilder
			.setTitle (order.amount + "x " + order.productEntity.name)
			.setItems (R.array.order_listview_item_long_click_menu, new OrderListViewItemLongClickMenuOnClickListener (this.parent, id));

		dlgBuilder.create ().show ();

		return true;
	}
}
