package be.robinj.restobill.listener;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import be.robinj.restobill.ProductActivity;
import be.robinj.restobill.adapter.ProductAdapter;
import be.robinj.restobill.model.ProductEntity;

/**
 * Created by robin on 03/11/15.
 */
public class ProductSearchTextChangedListener
	implements TextWatcher
{
	private List<ProductEntity> products;
	private ProductAdapter adapter;

	public ProductSearchTextChangedListener (List<ProductEntity> products, ProductAdapter adapter)
	{
		this.products = products;
		this.adapter = adapter;
	}

	@Override
	public void beforeTextChanged (CharSequence charSequence, int i, int i1, int i2)
	{
	}

	@Override
	public void onTextChanged (CharSequence charSequence, int i, int i1, int i2)
	{
		this.products.clear ();
		this.products.addAll (ProductEntity.find (ProductEntity.class, "name LIKE ?", charSequence.toString () + "%")); // I first want the results that start with the given String... //

		for (ProductEntity product : ProductEntity.find (ProductEntity.class, "name LIKE ?", "%" + charSequence.toString () + "%")) // And only after that the ones that contain it somewhere else. //
		{
			if (! this.products.contains (product))
				this.products.add (product);
		}

		this.adapter.notifyDataSetChanged ();
	}

	@Override
	public void afterTextChanged (Editable editable)
	{
	}
}
