package be.robinj.restobill.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import be.robinj.restobill.R;
import be.robinj.restobill.model.BillEntity;
import be.robinj.restobill.model.OrderEntity;
import be.robinj.restobill.model.TableEntity;

/**
 * Created by robin on 03/11/15.
 */
public class TableAdapter
	extends ArrayAdapter<TableEntity>
{
	boolean active = false;
	public TableAdapter (Context context, List<TableEntity> tables)
	{
		super (context, R.layout.table_gridview_item, tables);
		active = false;
	}

	public TableAdapter (Context context, List<TableEntity> tables, boolean _active) {
		super(context, R.layout.table_gridview_item, tables);
		active = _active;
	}

	@Override
	public View getView (int position, View view, ViewGroup parent)
	{
		TableEntity table = this.getItem (position);

		if (view == null)
			view = LayoutInflater.from (this.getContext ()).inflate (R.layout.table_gridview_item, parent, false);

		TextView tvTableListViewItemName = (TextView) view.findViewById (R.id.tvTableListViewItemName);
		tvTableListViewItemName.setText(table.name);

		view.setTag (table.getId ());

		if(active) {
			if(checkBillActive(table)) {
				tvTableListViewItemName.setTextColor(Color.BLACK);
			} else {
				tvTableListViewItemName.setTextColor(Color.GRAY);
			}
		}

		return view;
	}

	public boolean checkBillActive(TableEntity _table) {
		long count = BillEntity.count(BillEntity.class, "table_entity = ? AND closed = 0", new String[]{String.valueOf(_table.getId())});
		return count > 0;
	}
}
