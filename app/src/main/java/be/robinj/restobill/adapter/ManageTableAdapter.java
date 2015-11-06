package be.robinj.restobill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import be.robinj.restobill.R;
import be.robinj.restobill.model.BillEntity;
import be.robinj.restobill.model.TableEntity;

/**
 * Created by Afaci on 06/11/15.
 */
public class ManageTableAdapter extends ArrayAdapter<TableEntity>
{
    public ManageTableAdapter (Context context, List<TableEntity> tables)
    {
        super (context, R.layout.tablemanage_gridview_item, tables);
    }

    @Override
    public View getView (int position, View view, ViewGroup parent)
    {
        TableEntity table = this.getItem (position);

        if (view == null)
            view = LayoutInflater.from(this.getContext()).inflate (R.layout.tablemanage_gridview_item, parent, false);

        TextView tvTableListViewItemName = (TextView) view.findViewById (R.id.tvManageTableText);
        tvTableListViewItemName.setText(table.name);

        view.setTag(table.getId());

        return view;
    }
}
