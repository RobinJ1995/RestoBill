package be.robinj.restobill.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;

import java.util.List;

import be.robinj.restobill.R;
import be.robinj.restobill.TableActivity;
import be.robinj.restobill.model.BillEntity;
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
				List<BillEntity> bills = BillEntity.find (BillEntity.class, "table_entity = ? AND closed = 0", String.valueOf (table.getId ()));

				if (bills.size () > 0)
				{
					bills.get (0).startCheckout (this.parent);
				}
				else
				{
					AlertDialog.Builder dlgBuilder = new AlertDialog.Builder (this.parent);
					dlgBuilder
						.setMessage ("There is no open bill for this table.")
						.setPositiveButton (this.parent.getString (android.R.string.ok), new DialogDismissListener ());

					dlgBuilder.create ().show ();
				}

				break;
			case 1: // Reset //
				//TODO//
				break;
			/*case 2: // Remove //
				table.delete ();
				((TableActivity) this.parent).refreshTables ();

				Snackbar.make (this.parent.findViewById (R.id.colaTables), "Table removed", Snackbar.LENGTH_SHORT).show ();

				break;*/
		}
	}
}
