package be.robinj.restobill;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;

import java.util.HashSet;
import java.util.List;

import be.robinj.restobill.adapter.ProductAdapter;
import be.robinj.restobill.listener.ProductGridViewOnItemClickListener;
import be.robinj.restobill.listener.ProductSearchTextChangedListener;
import be.robinj.restobill.model.ProductEntity;

public class ProductActivity
	extends AppCompatActivity
{
	private List<ProductEntity> products;
	private HashSet<Long> selected = new HashSet<Long> ();

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_product);
		Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
		setSupportActionBar (toolbar);
		getSupportActionBar ().setDisplayHomeAsUpEnabled (true);
		
		FloatingActionButton btnSubmitProducts = (FloatingActionButton) this.findViewById (R.id.btnSubmitProducts);
		GridView gvProducts = (GridView) this.findViewById (R.id.gvProducts);
		EditText etProductSearch = (EditText) this.findViewById (R.id.etProductSearch);

		/*(new ProductEntity ("Pizza", 10F, "Dough with tomato sauce and other stuff...")).save ();
		(new ProductEntity ("Gnocci", 8.5F, "Some kind of pasta thing")).save ();
		(new ProductEntity ("Bread", 1F)).save ();
		(new ProductEntity ("Water", 1.5F, "Fresh from the tap")).save ();
		(new ProductEntity ("Cookie", 2F)).save ();*/

		this.products = ProductEntity.listAll (ProductEntity.class);

		gvProducts.setAdapter (new ProductAdapter (this, this.products, this.selected));
		gvProducts.setOnItemClickListener (new ProductGridViewOnItemClickListener (this.selected, btnSubmitProducts));
		etProductSearch.addTextChangedListener (new ProductSearchTextChangedListener (this.products, (ProductAdapter) gvProducts.getAdapter ()));
	}

	@Override
	public boolean onCreateOptionsMenu (Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater ().inflate (R.menu.menu_product, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem menuItem)
	{
		switch (menuItem.getItemId ())
		{
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask (this);

				return true;
			case R.id.action_search:
				EditText etProductSearch = (EditText) this.findViewById (R.id.etProductSearch);
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService (Context.INPUT_METHOD_SERVICE);

				if (etProductSearch.getVisibility () != View.VISIBLE)
				{
					etProductSearch.setVisibility (View.VISIBLE);
					etProductSearch.requestFocus ();

					inputMethodManager.showSoftInput (etProductSearch, InputMethodManager.SHOW_IMPLICIT); // Show the keyboard //
				}
				else
				{
					etProductSearch.setVisibility (View.GONE);

					inputMethodManager.hideSoftInputFromWindow (etProductSearch.getWindowToken (), InputMethodManager.HIDE_IMPLICIT_ONLY); // Hide the keyboard, but only if it was shown because of an implicit request //
				}

				return true;
		}

		return super.onOptionsItemSelected (menuItem);
	}
	
}
