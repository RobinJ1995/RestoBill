package be.robinj.restobill.listener;

import android.content.DialogInterface;

/**
 * Created by robin on 03/11/15.
 */
public class OrderAmountNegativeOnClickListener
	implements DialogInterface.OnClickListener
{

	public OrderAmountNegativeOnClickListener ()
	{
	}

	@Override
	public void onClick (DialogInterface dialogInterface, int i)
	{
		dialogInterface.cancel ();
	}
}
