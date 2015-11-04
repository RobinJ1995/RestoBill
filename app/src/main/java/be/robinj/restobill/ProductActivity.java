package be.robinj.restobill;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;

import java.util.List;

import be.robinj.restobill.adapter.ProductAdapter;
import be.robinj.restobill.model.ProductEntity;

public class ProductActivity
	extends AppCompatActivity
{
	
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

		/*(new ProductEntity ("Pizza", 10F, "Dough with tomato sauce and other stuff...")).save ();
		(new ProductEntity ("Gnocci", 8.5F, "Some kind of pasta thing")).save ();
		(new ProductEntity ("Bread", 1F)).save ();
		(new ProductEntity ("Water", 1.5F, "Fresh from the tap")).save ();
		(new ProductEntity ("Cookie", 2F)).save ();*/

		List<ProductEntity> products = ProductEntity.listAll (ProductEntity.class);

		gvProducts.setAdapter (new ProductAdapter (this, products));

	}
	
}
