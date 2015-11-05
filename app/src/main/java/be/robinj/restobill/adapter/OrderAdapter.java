package be.robinj.restobill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import be.robinj.restobill.R;
import be.robinj.restobill.model.OrderEntity;

/**
 * Created by robin on 03/11/15.
 */
public class OrderAdapter
	extends ArrayAdapter<OrderEntity>
{
	public OrderAdapter (Context context, List<OrderEntity> orders)
	{
		super (context, R.layout.order_listview_item, orders);
	}

	@Override
	public View getView (int position, View view, ViewGroup parent)
	{
		OrderEntity order = this.getItem (position);

		if (view == null)
			view = LayoutInflater.from (this.getContext ()).inflate (R.layout.order_listview_item, parent, false);

		TextView tvOrderListViewItemProductName = (TextView) view.findViewById (R.id.tvOrderListViewItemProductName);
		TextView tvOrderListViewItemProductPrice = (TextView) view.findViewById (R.id.tvOrderListViewItemProductPrice);
		TextView tvOrderListViewItemAmount = (TextView) view.findViewById (R.id.tvOrderListViewItemAmount);

		tvOrderListViewItemProductName.setText (order.productEntity.name);
		tvOrderListViewItemProductPrice.setText ("€" + order.productEntity.price);
		tvOrderListViewItemAmount.setText (order.amount == 1 ? "" : "x" + order.amount);

		view.setTag (order.getId ());

		return view;
	}
}
