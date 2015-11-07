package be.robinj.restobill.model;

import com.orm.SugarRecord;

import be.robinj.restobill.API;

/**
 * Created by robin on 03/11/15.
 */
public class OrderEntity
	extends SugarRecord<OrderEntity>
{
	public BillEntity billEntity;
	public ProductEntity productEntity;
	public short amount = 1;

	public OrderEntity ()
	{
	}

	public OrderEntity (BillEntity bill, ProductEntity product)
	{
		this.billEntity = bill;
		this.productEntity = product;
	}

	public float getPrice ()
	{
		return this.amount * this.productEntity.price;
	}

	@Override
	public void save ()
	{
		super.save ();

		final OrderEntity order = this;

		Runnable runnable = new Runnable ()
		{
			@Override
			public void run ()
			{
				(new API ("http://10.0.2.2:8000/")).saveOrder (order);
			}
		};
		Thread thread = new Thread (runnable);

		thread.start ();
	}
}
