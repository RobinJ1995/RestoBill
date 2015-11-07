package be.robinj.restobill.model;

import com.orm.SugarRecord;

import be.robinj.restobill.API;

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

	@Override
	public boolean equals (Object product)
	{
		try
		{
			if (this.getId () == ((ProductEntity) product).getId ())
				return true;
		}
		catch (Exception ex)
		{
			// No time to handle this properly. If something goes wrong they're probably not equal. //
		}

		return false;
	}

	@Override
	public void save ()
	{
		super.save ();

		final ProductEntity product = this;

		Runnable runnable = new Runnable ()
		{
			@Override
			public void run ()
			{
				(new API ("http://10.0.2.2:8000/")).saveProduct (product);
			}
		};
		Thread thread = new Thread (runnable);

		thread.start ();
	}
}
