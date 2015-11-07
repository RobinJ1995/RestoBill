package be.robinj.restobill;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import java.text.DecimalFormat;
import java.util.List;

import be.robinj.restobill.adapter.OrderAdapter;
import be.robinj.restobill.listener.OrderAddOnClickListener;
import be.robinj.restobill.listener.OrderListViewOnItemLongClickListener;
import be.robinj.restobill.model.BillEntity;
import be.robinj.restobill.model.OrderEntity;
import be.robinj.restobill.model.ProductEntity;
import be.robinj.restobill.model.TableEntity;

public class OrderActivity
	extends AppCompatActivity
{
	private BillEntity bill;
	private Menu menu;

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

		List<OrderEntity> orders = this.getOrders ();

		btnAddOrder.setOnClickListener (new OrderAddOnClickListener (this, bill.getId ()));
		lvOrders.setAdapter (new OrderAdapter (this, orders));
		lvOrders.setOnItemLongClickListener (new OrderListViewOnItemLongClickListener (this));
	}

	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent intent)
	{
		if (resultCode == RESULT_OK)
		{
			if (requestCode == 1)
			{
				int n = intent.getIntExtra ("nProducts", 0);

				this.refreshOrders ();

				Snackbar.make (this.findViewById (R.id.colaOrders), "Added " + n + " order" + (n == 1 ? "" : "s"), Snackbar.LENGTH_SHORT).show ();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu (Menu menu)
	{
		this.getMenuInflater ().inflate (R.menu.menu_order, menu);

		this.menu = menu;
		this.refreshTotalPrice ();

		return true;
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem menuItem)
	{
		switch (menuItem.getItemId ())
		{
			case R.id.action_checkout:
				this.bill.startCheckout (this);

				return true;
		}

		return super.onOptionsItemSelected (menuItem);
	}

	private List<OrderEntity> getOrders ()
	{
		return OrderEntity.find (OrderEntity.class, "bill_entity = ?", String.valueOf (this.bill.getId ()));
	}

	public void refreshOrders ()
	{
		ListView lvOrders = (ListView) this.findViewById (R.id.lvOrders);
		OrderAdapter adapter = (OrderAdapter) lvOrders.getAdapter ();

		List<OrderEntity> orders = this.getOrders ();

		adapter.clear ();
		adapter.addAll (orders);
		adapter.notifyDataSetChanged ();

		this.refreshTotalPrice (orders);
	}

	public void refreshTotalPrice ()
	{
		this.refreshTotalPrice (this.getOrders ());
	}

	public void refreshTotalPrice (List<OrderEntity> orders)
	{
		if (this.menu != null)
		{
			MenuItem action_checkout = this.menu.findItem (R.id.action_checkout);

			float totalPrice = 0F;
			for (OrderEntity order : orders)
				totalPrice += order.getPrice ();

			action_checkout.setTitle ("€" + new DecimalFormat ("0.00").format (totalPrice));
		}
	}

	@Override
	public void onResume ()
	{
		super.onResume ();

		this.refreshOrders ();
		this.refreshTotalPrice ();
	}
}
