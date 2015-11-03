package be.robinj.restobill.model;

import com.orm.SugarRecord;

/**
 * Created by robin on 03/11/15.
 */
public class OrderEntity
	extends SugarRecord<OrderEntity>
{
	public BillEntity bill;
	public ProductEntity product;

	public OrderEntity ()
	{
	}

	public OrderEntity (BillEntity bill, ProductEntity product)
	{
		this.bill = bill;
		this.product = product;
	}
}
