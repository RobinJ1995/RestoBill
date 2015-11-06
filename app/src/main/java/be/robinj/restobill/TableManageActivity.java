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

import be.robinj.restobill.adapter.ManageTableAdapter;
import be.robinj.restobill.listener.TableAddOnClickListener;
import be.robinj.restobill.model.TableEntity;

public class TableManageActivity
	extends AppCompatActivity
{
	
	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate (savedInstanceState);
		setContentView (R.layout.activity_table_manage);
		Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
		setSupportActionBar (toolbar);
		getSupportActionBar ().setDisplayHomeAsUpEnabled (true);
		
		FloatingActionButton btnAddTable = (FloatingActionButton) findViewById (R.id.btnAddTable);
		btnAddTable.setOnClickListener (new TableAddOnClickListener (this));

		GridView gvTables = (GridView) this.findViewById (R.id.gvManageTables);

		gvTables.setAdapter (new ManageTableAdapter (this, TableEntity.listAll (TableEntity.class)));
		gvTables.setOnItemLongClickListener (new AdapterView.OnItemLongClickListener ()
		{
			@Override
			public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id)
			{
				AlertDialog.Builder adb = new AlertDialog.Builder (TableManageActivity.this);
				adb.setTitle ("Remove table?");
				final long tableId = (long) view.getTag ();
				adb.setPositiveButton (getString (android.R.string.yes), new DialogInterface.OnClickListener ()
				{
					@Override
					public void onClick (DialogInterface dialog, int which)
					{
						TableEntity table = TableEntity.findById (TableEntity.class, tableId);
						table.delete ();
						refreshTables ();
					}
				});
				adb.setNegativeButton (getString (android.R.string.no), new DialogInterface.OnClickListener ()
				{
					@Override
					public void onClick (DialogInterface dialog, int which)
					{
						dialog.cancel ();
					}
				});
				adb.show ();
				return true;
			}
		});
	}

	public void refreshTables ()
	{

		GridView gvTables = (GridView) this.findViewById (R.id.gvManageTables);
		ManageTableAdapter adapter = (ManageTableAdapter) gvTables.getAdapter ();

		adapter.clear ();
		adapter.addAll (TableEntity.listAll (TableEntity.class));
		adapter.notifyDataSetChanged ();
	}
}
