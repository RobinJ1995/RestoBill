package be.robinj.restobill;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import be.robinj.restobill.adapter.TableAdapter;
import be.robinj.restobill.listener.TableAddOnClickListener;
import be.robinj.restobill.listener.TableGridViewOnItemClickListener;
import be.robinj.restobill.listener.TableGridViewOnItemLongClickListener;
import be.robinj.restobill.model.TableEntity;

public class TableActivity
	extends AppCompatActivity
{
	
	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);

		this.setContentView (R.layout.activity_table);
		Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
		this.setSupportActionBar (toolbar);

		if (toolbar != null)
			toolbar.setTitle (this.getString (R.string.activity_tables_title));
		if (this.getActionBar () != null)
			this.getActionBar ().setTitle (this.getString (R.string.activity_tables_title));
		if (this.getSupportActionBar () != null)
			this.getSupportActionBar ().setTitle (R.string.activity_tables_title);
		
		FloatingActionButton btnAddTable = (FloatingActionButton) this.findViewById (R.id.btnAddTable);
		GridView gvTables = (GridView) this.findViewById (R.id.gvTables);

		btnAddTable.setOnClickListener (new TableAddOnClickListener (this));
		gvTables.setAdapter (new TableAdapter (this, TableEntity.listAll (TableEntity.class)));
		gvTables.setOnItemLongClickListener (new TableGridViewOnItemLongClickListener (this));
		gvTables.setOnItemClickListener (new TableGridViewOnItemClickListener (this));
	}
	
	@Override
	public boolean onCreateOptionsMenu (Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater ().inflate (R.menu.menu_table, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected (MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId ();
		
		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings)
		{
			return true;
		} else if (id == R.id.action_manage)
		{
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
			alertDialog.setItems(new String[]{"Manage Tables", "Manage Products"}, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch(which) {
						case 0:
							Intent in = new Intent(TableActivity.this, TableManageActivity.class);
							startActivity(in);
							break;
						case 1:
							in = new Intent(TableActivity.this, ProductManageActivity.class);
							startActivity(in);
							break;
					}
				}
			});
			alertDialog.show();
		}
		
		return super.onOptionsItemSelected (item);
	}

	public void refreshTables ()
	{
		GridView gvTables = (GridView) this.findViewById (R.id.gvTables);
		TableAdapter adapter = (TableAdapter) gvTables.getAdapter ();

		adapter.clear ();
		adapter.addAll (TableEntity.listAll (TableEntity.class));
		adapter.notifyDataSetChanged ();
	}
}
