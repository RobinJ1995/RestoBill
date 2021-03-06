package be.robinj.restobill.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import be.robinj.restobill.ProductActivity;
import be.robinj.restobill.ProductManageActivity;
import be.robinj.restobill.R;
import be.robinj.restobill.TableActivity;
import be.robinj.restobill.model.ProductEntity;
import be.robinj.restobill.model.TableEntity;

/**
 * Created by Afaci on 05/11/15.
 */
public class ProductAddPositiveListener
	implements DialogInterface.OnClickListener
{
	private Activity parent;
	private View dlgView;
	private ProductEntity updateEntity;

	public ProductAddPositiveListener (Activity parent, View dlgView)
	{
		this.parent = parent;
		this.dlgView = dlgView;
	}

	public ProductAddPositiveListener (Activity parent, View dlgView, ProductEntity _updateEntiry)
	{
		this.parent = parent;
		this.dlgView = dlgView;
		this.updateEntity = _updateEntiry;
	}

	@Override
	public void onClick (DialogInterface dialogInterface, int i)
	{
		EditText etTableAddName = (EditText) this.dlgView.findViewById (R.id.etProdName);
		EditText etProdPrice = (EditText) this.dlgView.findViewById (R.id.etProdPrice);
		EditText etProdDesc = (EditText) this.dlgView.findViewById (R.id.etProdDesc);
		Switch swProdAvailable = (Switch) this.dlgView.findViewById(R.id.swProdAvailable);

		String name = etTableAddName.getText ().toString ();
		String price = etProdPrice.getText ().toString ();
		String desc = etProdDesc.getText ().toString ();
		boolean available = swProdAvailable.isChecked();

		if (name.isEmpty () || price.isEmpty ())
		{
			AlertDialog.Builder dlgBuilder = new AlertDialog.Builder (this.parent);

			dlgBuilder
				.setMessage ("You didn't specify a name or a price.")
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
		} else
		{
	    /*TableEntity table = new TableEntity (name);
            table.save ();

            ((TableActivity) this.parent).refreshTables ();

            Snackbar.make(this.parent.findViewById(R.id.colaTables), "Table added", Snackbar.LENGTH_SHORT).show ();*/
			ProductEntity prod = updateEntity;
			String message = "Product updated";
			if(prod == null) {
				prod = new ProductEntity ();
				message = "Product added";
			}

			prod.name = name;
			prod.price = (float) Double.parseDouble (price);
			prod.description = desc;
			prod.available = available;
			prod.save();

			((ProductManageActivity) this.parent).refreshProducts ();


			Snackbar.make (this.parent.findViewById (R.id.colaManageProducts), message, Snackbar.LENGTH_SHORT).show ();
		}
	}
}
