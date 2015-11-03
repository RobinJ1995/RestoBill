package be.robinj.restobill.model;

import com.orm.SugarRecord;

/**
 * Created by robin on 03/11/15.
 */
public class OrderEntity
	extends SugarRecord<OrderEntity>
{
	public BillEntity billEntity;
	public ProductEntity productEntity;

	public OrderEntity ()
	{
	}

	public OrderEntity (BillEntity bill, ProductEntity product)
	{
		this.billEntity = bill;
		this.productEntity = product;
	}
}
