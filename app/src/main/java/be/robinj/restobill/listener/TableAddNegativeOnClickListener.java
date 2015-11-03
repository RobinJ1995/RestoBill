package be.robinj.restobill.listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import be.robinj.restobill.R;

/**
 * Created by robin on 03/11/15.
 */
public class TableAddNegativeOnClickListener
	implements DialogInterface.OnClickListener
{

	public TableAddNegativeOnClickListener ()
	{
	}

	@Override
	public void onClick (DialogInterface dialogInterface, int i)
	{
		dialogInterface.cancel ();
	}
}
