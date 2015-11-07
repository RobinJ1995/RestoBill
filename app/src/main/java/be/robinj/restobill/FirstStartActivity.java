package be.robinj.restobill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import be.robinj.restobill.listener.FirstStartOnClickCreate;

public class FirstStartActivity extends AppCompatActivity {

    private NumberPicker np;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        np = (NumberPicker) findViewById(R.id.npFirstTables);
        np.setMinValue(1);
        np.setMaxValue(100);

        Button btCreateTables = (Button) findViewById(R.id.btFristCreate);
        btCreateTables.setOnClickListener(new FirstStartOnClickCreate(this));
    }

    public int getNumberTables() {
        return np.getValue();
    }
}
