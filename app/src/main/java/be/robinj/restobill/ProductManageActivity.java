package be.robinj.restobill;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.HashSet;
import java.util.List;

import be.robinj.restobill.adapter.ProductAdapter;
import be.robinj.restobill.listener.ManageProductAddOnClickListener;
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
		gvProducts.setOnItemLongClickListener
		(
			new AdapterView.OnItemLongClickListener ()
			{
				@Override
				public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id)
				{
					AlertDialog.Builder adb = new AlertDialog.Builder (ProductManageActivity.this);
					adb.setTitle ("Remove product?");
					final long prodId = (long) view.getTag ();
					adb.setPositiveButton (getString (android.R.string.yes),
						new DialogInterface.OnClickListener ()
						{
							@Override
							public void onClick (DialogInterface dialog, int which)
							{
								ProductEntity product = ProductEntity.findById (ProductEntity.class, prodId);
								product.delete ();
								refreshProducts ();
							}
						});
					adb.setNegativeButton (getString (android.R.string.no), new DialogInterface.OnClickListener ()
					{
						@Override
						public void onClick (DialogInterface dialog, int which)
						{
							dialog.dismiss ();
						}
					});
					adb.show ();

					return true;
				}

			}
		);
	}

	public void refreshProducts ()
	{

		GridView gvProducts = (GridView) this.findViewById (R.id.gvManageProducts);
		ProductAdapter adapter = (ProductAdapter) gvProducts.getAdapter ();

		adapter.clear ();
		adapter.addAll (ProductEntity.listAll (ProductEntity.class));
		adapter.notifyDataSetChanged ();
	}
}
