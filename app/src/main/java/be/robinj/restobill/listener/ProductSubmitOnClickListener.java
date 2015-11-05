package be.robinj.restobill.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import java.util.HashSet;
import java.util.List;

import be.robinj.restobill.ProductActivity;
import be.robinj.restobill.model.BillEntity;
import be.robinj.restobill.model.OrderEntity;
import be.robinj.restobill.model.ProductEntity;

/**
 * Created by robin on 03/11/15.
 */
public class ProductSubmitOnClickListener
	implements View.OnClickListener
{
	private Activity parent;
	private HashSet<Long> selected;
	private long billId;

	public ProductSubmitOnClickListener (Activity parent, HashSet<Long> selected, long billId)
	{
		this.parent = parent;
		this.selected = selected;
		this.billId = billId;
	}

	@Override
	public void onClick (View view)
	{
		BillEntity bill = BillEntity.findById (BillEntity.class, this.billId);

		for (Long productId : this.selected)
		{
			ProductEntity product = ProductEntity.findById (ProductEntity.class, productId);

			List<OrderEntity> orders = OrderEntity.find (OrderEntity.class, "bill_entity = ? AND product_entity = ?", String.valueOf (this.billId), String.valueOf (productId));
			OrderEntity order;

			if (orders.size () > 0)
			{
				order = orders.get (0);
				order.amount++;
			}
			else
			{
				order = new OrderEntity (bill, product);
			}

			order.save ();
		}

		Intent intent = new Intent ();
		intent.putExtra ("nProducts", this.selected.size ());
		this.parent.setResult (Activity.RESULT_OK, intent);

		this.parent.finish ();
	}
}
