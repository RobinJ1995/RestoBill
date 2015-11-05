package be.robinj.restobill.listener;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;

import be.robinj.restobill.R;
import be.robinj.restobill.TableActivity;
import be.robinj.restobill.model.TableEntity;

/**
 * Created by robin on 03/11/15.
 */
public class TableGridViewItemLongClickMenuOnClickListener
	implements DialogInterface.OnClickListener
{
	private Activity parent;
	private long id;

	public TableGridViewItemLongClickMenuOnClickListener (Activity parent, long id)
	{
		this.parent = parent;
		this.id = id;
	}

	@Override
	public void onClick (DialogInterface dialogInterface, int i)
	{
		TableEntity table = TableEntity.findById (TableEntity.class, this.id);

		switch (i)
		{
			case 0: // Check out //
				//TODO//
				break;
			case 1: // Reset //
				//TODO//
				break;
			case 2: // Remove //
				table.delete ();
				((TableActivity) this.parent).refreshTables ();

				Snackbar.make (this.parent.findViewById (R.id.colaTables), "Table removed", Snackbar.LENGTH_SHORT).show ();

				break;
		}
	}
}
