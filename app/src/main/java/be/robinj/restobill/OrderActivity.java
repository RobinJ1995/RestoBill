package be.robinj.restobill;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import be.robinj.restobill.model.BillEntity;
import be.robinj.restobill.model.TableEntity;

public class OrderActivity
	extends AppCompatActivity
{
	private BillEntity bill;

	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_order);
		Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
		setSupportActionBar (toolbar);
		getSupportActionBar ().setDisplayHomeAsUpEnabled (true);

		FloatingActionButton btnAddOrder = (FloatingActionButton) this.findViewById (R.id.btnAddOrder);

		Intent intent = this.getIntent ();
		long tableId = intent.getLongExtra ("tableId", -1);

		TableEntity table = TableEntity.findById (TableEntity.class, tableId);

		if (tableId == -1) // This isn't supposed to happen //
			this.finish ();

		List<BillEntity> bills = BillEntity.find (BillEntity.class, "table_entity = ? AND closed = 0", String.valueOf (tableId));
		boolean existingBill = false;
		
		if (bills.size () > 0)
		{
			this.bill = bills.get (0);
			existingBill = true;
		}
		else
		{

			BillEntity bill = new BillEntity (table);

			bill.save ();
		}

		Snackbar.make (this.findViewById (R.id.colaOrders), (existingBill ? "Existing" : "New") + " bill opened for " + table.name, Snackbar.LENGTH_SHORT).show ();
	}
}
