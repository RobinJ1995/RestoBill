package be.robinj.restobill;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    private String currency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        getSupportActionBar ().setDisplayHomeAsUpEnabled(true);

        Spinner spCurrency = (Spinner)findViewById(R.id.spSettingsCurrency);
        SharedPreferences sp = getSP();
        final SharedPreferences.Editor editor = sp.edit();
        String curr = sp.getString("currency", "€");
        final String[] currencies = getResources().getStringArray(R.array.currencies);

        spCurrency.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, currencies));
        int posCur = -1;
        for (int i = 0; i < currencies.length && posCur < 0; i++) {
            if(currencies[i].equals(curr)) {
                posCur = i;
            }
        }
        spCurrency.setSelection(posCur, true);
        spCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currency = currencies[position];
                save();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public SharedPreferences getSP() {
        return this.getSharedPreferences("prefs", MODE_PRIVATE);
    }

    public void save() {
        SharedPreferences sp = getSP();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("currency", currency);
        editor.apply();
    }
}
