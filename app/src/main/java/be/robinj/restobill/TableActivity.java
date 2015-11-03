package be.robinj.restobill;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import be.robinj.restobill.adapter.TableAdapter;
import be.robinj.restobill.listener.TableAddOnClickListener;
import be.robinj.restobill.listener.TableListViewOnItemClickListener;
import be.robinj.restobill.listener.TableListViewOnItemLongClickListener;
import be.robinj.restobill.model.TableEntity;

public class TableActivity
	extends AppCompatActivity
{
	
	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_table);
		Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
		setSupportActionBar (toolbar);
		
		FloatingActionButton btnAddTable = (FloatingActionButton) this.findViewById (R.id.btnAddTable);
		ListView lvTables = (ListView) this.findViewById (R.id.lvTables);

		btnAddTable.setOnClickListener (new TableAddOnClickListener (this));
		lvTables.setAdapter (new TableAdapter (this, TableEntity.listAll (TableEntity.class)));
		lvTables.setOnItemLongClickListener (new TableListViewOnItemLongClickListener (this));
		lvTables.setOnItemClickListener (new TableListViewOnItemClickListener (this));
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
		}
		
		return super.onOptionsItemSelected (item);
	}

	public void refreshTables ()
	{
		ListView lvTables = (ListView) this.findViewById (R.id.lvTables);
		TableAdapter adapter = (TableAdapter) lvTables.getAdapter ();

		adapter.clear ();
		adapter.addAll (TableEntity.listAll (TableEntity.class));
		adapter.notifyDataSetChanged ();
	}
}
