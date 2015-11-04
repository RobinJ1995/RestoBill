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
import be.robinj.restobill.model.ProductEntity;

/**
 * Created by robin on 03/11/15.
 */
public class ProductAdapter
	extends ArrayAdapter<ProductEntity>
{
	public ProductAdapter (Context context, List<ProductEntity> products)
	{
		super (context, R.layout.product_gridview_item, products);
	}

	@Override
	public View getView (int position, View view, ViewGroup parent)
	{
		ProductEntity product = this.getItem (position);

		if (view == null)
			view = LayoutInflater.from (this.getContext ()).inflate (R.layout.product_gridview_item, parent, false);

		TextView tvProductGridViewItemName = (TextView) view.findViewById (R.id.tvProductGridViewItemName);
		TextView tvProductGridViewItemPrice = (TextView) view.findViewById (R.id.tvProductGridViewItemPrice);
		TextView tvProductGridViewItemDescription = (TextView) view.findViewById (R.id.tvProductGridViewItemDescription);

		tvProductGridViewItemName.setText (product.name);
		tvProductGridViewItemPrice.setText ("€" + product.price);
		tvProductGridViewItemDescription.setText (product.description);

		view.setTag (product.getId ());

		return view;
	}
}
