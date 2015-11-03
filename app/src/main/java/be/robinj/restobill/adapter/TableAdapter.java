package be.robinj.restobill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import be.robinj.restobill.R;
import be.robinj.restobill.listener.TableListViewOnItemLongClickListener;
import be.robinj.restobill.model.TableEntity;

/**
 * Created by robin on 03/11/15.
 */
public class TableAdapter
	extends ArrayAdapter<TableEntity>
{
	public TableAdapter (Context context, List<TableEntity> tables)
	{
		super (context, R.layout.table_listview_item, tables);
	}

	@Override
	public View getView (int position, View view, ViewGroup parent)
	{
		TableEntity table = this.getItem (position);

		if (view == null)
			view = LayoutInflater.from (this.getContext ()).inflate (R.layout.table_listview_item, parent, false);

		TextView tvTableListViewItemName = (TextView) view.findViewById (R.id.tvTableListViewItemName);
		tvTableListViewItemName.setText (table.name);

		view.setTag (table.getId ());

		return view;
	}
}
