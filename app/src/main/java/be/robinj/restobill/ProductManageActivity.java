package be.robinj.restobill;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Switch;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import be.robinj.restobill.adapter.ProductAdapter;
import be.robinj.restobill.listener.ManageProductAddOnClickListener;
import be.robinj.restobill.listener.ProductAddPositiveListener;
import be.robinj.restobill.listener.TableAddNegativeOnClickListener;
import be.robinj.restobill.model.OrderEntity;
import be.robinj.restobill.model.ProductEntity;

public class ProductManageActivity
	extends AppCompatActivity
{
	private HashSet<Long> selected = new HashSet<Long> ();
	
	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_product_manage);
		Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
		setSupportActionBar (toolbar);
		getSupportActionBar ().setDisplayHomeAsUpEnabled (true);

		FloatingActionButton btnAddProduct = (FloatingActionButton) this.findViewById (R.id.btnAddProduct);
		btnAddProduct.setOnClickListener
			(
				new View.OnClickListener ()
				{
					@Override
					public void onClick (View v)
					{
						ManageProductAddOnClickListener pd = new ManageProductAddOnClickListener (ProductManageActivity.this);
						pd.show ();
					}
				}
			);

		GridView gvProducts = (GridView) this.findViewById (R.id.gvManageProducts);

		List<ProductEntity> products = ProductEntity.find (ProductEntity.class, null, new String[] {}, null, "name", null);
		//btnSubmitProducts.setOnClickListener (new ProductSubmitOnClickListener(this, this.selected, billId));
		gvProducts.setAdapter (new ProductAdapter (this, products, this.selected));
		gvProducts.setOnItemLongClickListener (new AdapterView.OnItemLongClickListener ()
		{
			@Override
			public boolean onItemLongClick (AdapterView<?> adapterView, View view, int i, long l)
			{
				AlertDialog.Builder adb = new AlertDialog.Builder (ProductManageActivity.this);
				final long prodId = (long) view.getTag ();
				adb.setTitle (ProductEntity.findById (ProductEntity.class, prodId).name);
				adb.setItems (new String[] {"Edit", "Remove"}, new DialogInterface.OnClickListener ()
				{
					@Override
					public void onClick (DialogInterface dialog, int which)
					{
						switch (which)
						{
							case 0:
								editProduct (prodId);
								break;
							case 1:
								deleteProduct (prodId);
								break;
						}
					}
				});
				adb.show ();

				return true;
			}
		});
	}

	public void editProduct (final long prodId)
	{
		final ProductEntity product = ProductEntity.findById (ProductEntity.class, prodId);
		android.app.AlertDialog.Builder dlgBuilder = new android.app.AlertDialog.Builder (this);
		LayoutInflater inflater = this.getLayoutInflater ();
		View dlgView = inflater.inflate (R.layout.dialog_product_manage_add, null);
		EditText etProdName = (EditText) dlgView.findViewById (R.id.etProdName);
		etProdName.setText (product.name);

		Switch swProdAvailable = (Switch) dlgView.findViewById (R.id.swProdAvailable);
		swProdAvailable.setChecked (product.available);

		EditText etProdPrice = (EditText) dlgView.findViewById (R.id.etProdPrice);
		etProdPrice.setText (String.valueOf (product.price));

		EditText etProdDesc = (EditText) dlgView.findViewById (R.id.etProdDesc);
		etProdDesc.setText (product.description);

		dlgBuilder
			.setView (dlgView)
			.setTitle ("Edit product")
			.setPositiveButton (R.string.dialog_tables_add_positive,
				new ProductAddPositiveListener (this, dlgView, product))
			.setNegativeButton ("Cancel", new TableAddNegativeOnClickListener ());

		android.app.AlertDialog dialog = dlgBuilder.create ();
		dialog.show ();
	}

	public void deleteProduct (final long prodId)
	{
		final ProductEntity product = ProductEntity.findById (ProductEntity.class, prodId);
		List<OrderEntity> orders = OrderEntity.listAll (OrderEntity.class);
		final List<OrderEntity> ordersUsingProduct = new LinkedList<OrderEntity> ();
		for (int i = 0; i < orders.size (); i++)
		{
			OrderEntity order = orders.get (i);
			if (order.productEntity != null)
			{
				if (order.productEntity.getId () == product.getId ())
					ordersUsingProduct.add (order);
			}
		}

		if (ordersUsingProduct.size () > 0)
		{
			AlertDialog.Builder adb = new AlertDialog.Builder (this);
			adb.setTitle ("Warning");
			adb.setMessage ("This product is used by " + ordersUsingProduct.size () + " bills\n" +
				"Do you really want to remove it?");
			adb.setPositiveButton ("Yes", new DialogInterface.OnClickListener ()
			{
				@Override
				public void onClick (DialogInterface dialog, int which)
				{
					for (int i = 0; i < ordersUsingProduct.size (); i++)
					{
						OrderEntity deleteOrder = ordersUsingProduct.get (i);
						deleteOrder.delete ();
					}

					product.delete ();
					refreshProducts ();
				}
			});
			adb.setNegativeButton ("No", new DialogInterface.OnClickListener ()
			{
				@Override
				public void onClick (DialogInterface dialog, int which)
				{
					dialog.dismiss ();
				}
			});
			adb.show ();
		}
		else
		{
			product.delete ();
			refreshProducts ();
		}

	}

	public void refreshProducts ()
	{
		GridView gvProducts = (GridView) this.findViewById (R.id.gvManageProducts);
		ProductAdapter adapter = (ProductAdapter) gvProducts.getAdapter ();

		adapter.clear ();
		adapter.addAll (ProductEntity.listAll (ProductEntity.class));
		adapter.notifyDataSetChanged ();
	}



	@Override
	public void onResume ()
	{
		super.onResume ();

		this.refreshProducts ();
	}
}
