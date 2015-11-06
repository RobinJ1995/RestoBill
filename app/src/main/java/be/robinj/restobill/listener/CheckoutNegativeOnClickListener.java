package be.robinj.restobill.listener;

import android.content.DialogInterface;

/**
 * Created by robin on 03/11/15.
 */
public class CheckoutNegativeOnClickListener
	implements DialogInterface.OnClickListener
{

	public CheckoutNegativeOnClickListener ()
	{
	}

	@Override
	public void onClick (DialogInterface dialogInterface, int i)
	{
		dialogInterface.cancel ();
	}
}
