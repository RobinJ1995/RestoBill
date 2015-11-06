package be.robinj.restobill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import be.robinj.restobill.R;
import be.robinj.restobill.model.OrderEntity;

/**
 * Created by robin on 03/11/15.
 */
public class CheckoutProductAdapter
	extends ArrayAdapter<OrderEntity>
{
	public CheckoutProductAdapter (Context context, List<OrderEntity> orders)
	{
		super (context, R.layout.checkout_listview_item, orders);
	}

	@Override
	public View getView (int position, View view, ViewGroup parent)
	{
		OrderEntity order = this.getItem (position);

		if (view == null)
			view = LayoutInflater.from (this.getContext ()).inflate (R.layout.checkout_listview_item, parent, false);

		TextView tvCheckoutGridViewItemProductName = (TextView) view.findViewById (R.id.tvCheckoutGridViewItemProductName);
		TextView tvCheckoutGridViewItemAmount = (TextView) view.findViewById (R.id.tvCheckoutGridViewItemAmount);
		TextView tvCheckoutGridViewItemProductPrice = (TextView) view.findViewById (R.id.tvCheckoutGridViewItemProductPrice);
		TextView tvCheckoutGridViewItemProductTotalPrice = (TextView) view.findViewById (R.id.tvCheckoutGridViewItemProductTotalPrice);

		tvCheckoutGridViewItemProductName.setText (order.productEntity.name);
		tvCheckoutGridViewItemAmount.setText ("x" + order.amount);
		tvCheckoutGridViewItemProductPrice.setText ("€" + (new DecimalFormat ("0.00")).format (order.productEntity.price));
		tvCheckoutGridViewItemProductTotalPrice.setText ("€" + (new DecimalFormat ("0.00")).format (order.getPrice ()));

		view.setTag (order.getId ());

		return view;
	}
}
