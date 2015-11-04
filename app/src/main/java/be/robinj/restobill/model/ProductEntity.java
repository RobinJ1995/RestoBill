package be.robinj.restobill.model;

import com.orm.SugarRecord;

/**
 * Created by robin on 03/11/15.
 */
public class ProductEntity extends SugarRecord<ProductEntity>
{
	public String name;
	public float price;
	public String description;
	public boolean available;

	public ProductEntity ()
	{
	}

	public ProductEntity (String name, float price)
	{
		this.name = name;
		this.price = price;
	}

	public ProductEntity (String name, float price, String description)
	{
		this.name = name;
		this.price = price;
		this.description = description;
	}
}
