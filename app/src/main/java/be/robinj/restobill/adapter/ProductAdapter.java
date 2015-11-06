package be.robinj.restobill.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashSet;
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
	private HashSet<Long> selected;

	public ProductAdapter (Context context, List<ProductEntity> products, HashSet<Long> selected)
	{
		super (context, R.layout.product_gridview_item, products);

		this.selected = selected;
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
		tvProductGridViewItemPrice.setText ("€" + (new DecimalFormat ("0.00")).format (product.price));
		tvProductGridViewItemDescription.setText (product.description);

		CardView cvProductGridViewItem = (CardView) view.findViewById (R.id.cvProductGridViewItem);

		if (this.selected.contains (product.getId ()))
			cvProductGridViewItem.setBackgroundResource (android.R.color.holo_blue_light);
		else
			cvProductGridViewItem.setBackgroundResource (android.R.color.white);

		view.setTag (product.getId ());

		return view;
	}
}
