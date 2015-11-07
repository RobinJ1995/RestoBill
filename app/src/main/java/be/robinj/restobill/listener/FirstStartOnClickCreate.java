package be.robinj.restobill.listener;

import android.app.Activity;
import android.view.View;

import be.robinj.restobill.FirstStartActivity;
import be.robinj.restobill.model.TableEntity;

/**
 * Created by Afaci on 07/11/15.
 */
public class FirstStartOnClickCreate implements View.OnClickListener {
    private Activity parent;

    public FirstStartOnClickCreate(Activity _parent) {
        parent = _parent;
    }

    @Override
    public void onClick(View v) {
        int numTables = 0;
        if(parent instanceof FirstStartActivity) {
            numTables = ((FirstStartActivity)parent).getNumberTables();
            for(int i = 1; i <= numTables; i++) {
                TableEntity te = new TableEntity();
                te.name = "Table " + i;
                te.save();
            }
            ((FirstStartActivity)parent).finish();
        }


    }
}
