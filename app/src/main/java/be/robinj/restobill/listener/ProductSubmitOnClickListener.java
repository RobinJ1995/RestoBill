package be.robinj.restobill.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import java.util.HashSet;

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
			OrderEntity order = new OrderEntity (bill, product);

			order.save ();
		}

		this.parent.finish ();
	}
}
