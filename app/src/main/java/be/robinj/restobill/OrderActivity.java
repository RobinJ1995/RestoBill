package be.robinj.restobill;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class OrderActivity
	extends AppCompatActivity
{
	
	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_order);
		Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
		setSupportActionBar (toolbar);
		getSupportActionBar ().setDisplayHomeAsUpEnabled (true);

		FloatingActionButton btnAddOrder = (FloatingActionButton) this.findViewById (R.id.btnAddOrder);
	}
	
}
