package be.robinj.restobill.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;

import be.robinj.restobill.R;
import be.robinj.restobill.TableActivity;
import be.robinj.restobill.model.TableEntity;

/**
 * Created by robin on 03/11/15.
 */
public class TableAddPositiveOnClickListener
	implements DialogInterface.OnClickListener
{
	private Activity parent;
	private View dlgView;

	public TableAddPositiveOnClickListener (Activity parent, View dlgView)
	{
		this.parent = parent;
		this.dlgView = dlgView;
	}

	@Override
	public void onClick (DialogInterface dialogInterface, int i)
	{
		EditText etTableAddName = (EditText) this.dlgView.findViewById (R.id.etTableAddName);

		String name = etTableAddName.getText ().toString ();
		if (name.isEmpty ())
		{
			AlertDialog.Builder dlgBuilder = new AlertDialog.Builder (this.parent);

			dlgBuilder
				.setMessage ("You didn't specify a name.")
				.setTitle ("Problem")
				.setPositiveButton
				(
					android.R.string.ok, new DialogInterface.OnClickListener ()
					{
						@Override
						public void onClick (DialogInterface dialogInterface, int i)
						{
							dialogInterface.dismiss ();
						}
					}
				);

			dlgBuilder.create ().show ();
		}
		else
		{
			TableEntity table = new TableEntity (name);
			table.save ();

			((TableActivity) this.parent).refreshTables ();

			Snackbar.make (this.parent.findViewById (R.id.colaTables), "Table added", Snackbar.LENGTH_SHORT).show ();
		}
	}
}
