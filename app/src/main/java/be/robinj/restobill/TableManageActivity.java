package be.robinj.restobill;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Switch;

import be.robinj.restobill.adapter.ManageTableAdapter;
import be.robinj.restobill.listener.ProductAddPositiveListener;
import be.robinj.restobill.listener.TableAddNegativeOnClickListener;
import be.robinj.restobill.listener.TableAddOnClickListener;
import be.robinj.restobill.listener.TableAddPositiveOnClickListener;
import be.robinj.restobill.model.ProductEntity;
import be.robinj.restobill.model.TableEntity;

public class TableManageActivity
	extends AppCompatActivity
{
	
	@Override
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table_manage);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar (toolbar);
		getSupportActionBar ().setDisplayHomeAsUpEnabled(true);
		
		FloatingActionButton btnAddTable = (FloatingActionButton) findViewById(R.id.btnAddTable);
		btnAddTable.setOnClickListener(new TableAddOnClickListener(this));

		GridView gvTables = (GridView) this.findViewById (R.id.gvManageTables);

		gvTables.setAdapter(new ManageTableAdapter(this, TableEntity.listAll(TableEntity.class)));
		gvTables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				AlertDialog.Builder adb = new AlertDialog.Builder(TableManageActivity.this);
				adb.setTitle("What do want to do?");
				final long tableId = (long) view.getTag();
				adb.setItems(new String[]{"Edit table", "Delete table"}, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case 0:
								editTable(tableId);
								break;
							case 1:
								deleteTable(tableId);
								break;
						}
					}
				});
				adb.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				adb.show();
			}
		});
		/*gvTables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				AlertDialog.Builder adb = new AlertDialog.Builder(TableManageActivity.this);
				adb.setTitle("Remove table?");
				final long tableId = (long) view.getTag();
				adb.setPositiveButton(getString(android.R.string.yes), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
				adb.setNegativeButton(getString(android.R.string.no), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				adb.show();
			}
		});*/
	}

	public void editTable(final long _tableId) {
		TableEntity table = TableEntity.findById(TableEntity.class, _tableId);
		android.app.AlertDialog.Builder dlgBuilder = new android.app.AlertDialog.Builder (this);
		LayoutInflater inflater = this.getLayoutInflater();
		View dlgView = inflater.inflate(R.layout.dialog_table_add, null);

		EditText etTableName = (EditText)dlgView.findViewById(R.id.etTableAddName);
		etTableName.setText(table.name);


		dlgBuilder
				.setView(dlgView)
				.setTitle("Edit table")
				.setPositiveButton(R.string.dialog_tables_add_positive,
						new TableAddPositiveOnClickListener(this, dlgView, table))
				.setNegativeButton("Cancel", new TableAddNegativeOnClickListener());

		android.app.AlertDialog dialog = dlgBuilder.create ();
		dialog.show ();
	}

	public void deleteTable(final long _tableId) {
		TableEntity table = TableEntity.findById(TableEntity.class, _tableId);
		table.delete();
		refreshTables();
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
