package be.robinj.restobill;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import be.robinj.restobill.adapter.OrderAdapter;
import be.robinj.restobill.listener.OrderAddOnClickListener;
import be.robinj.restobill.model.BillEntity;
import be.robinj.restobill.model.OrderEntity;
import be.robinj.restobill.model.ProductEntity;
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
		ListView lvOrders = (ListView) this.findViewById (R.id.lvOrders);

		Intent intent = this.getIntent ();
		long tableId = intent.getLongExtra ("tableId", -1);

		TableEntity table = TableEntity.findById (TableEntity.class, tableId);

		if (tableId == -1) // This isn't supposed to happen //
			this.finish ();

		List<BillEntity> bills = BillEntity.find (BillEntity.class, "table_entity = ? AND closed = 0", String.valueOf (tableId));
		
		if (bills.size () > 0)
		{
			this.bill = bills.get (0);
		}
		else
		{

			this.bill = new BillEntity (table);

			this.bill.save ();

			Snackbar.make (this.findViewById (R.id.colaOrders), "New bill opened for " + table.name, Snackbar.LENGTH_SHORT).show ();
		}

		List<OrderEntity> orders = OrderEntity.find (OrderEntity.class, "bill_entity = ?", String.valueOf (this.bill.getId ()));

		btnAddOrder.setOnClickListener (new OrderAddOnClickListener (this, bill.getId ()));
		lvOrders.setAdapter (new OrderAdapter (this, orders));
	}

	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent intent)
	{
		if (resultCode == RESULT_OK)
		{
			if (requestCode == 1)
			{
				int n = intent.getIntExtra ("nProducts", 0);

				Snackbar.make (this.findViewById (R.id.colaOrders), "Added " + n + " order" + (n == 1 ? "" : "s"), Snackbar.LENGTH_SHORT).show ();
			}
		}
	}
}
