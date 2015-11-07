package be.robinj.restobill;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    private String currency;
    private String ip;
    private int port;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        getSupportActionBar ().setDisplayHomeAsUpEnabled(true);

        Spinner spCurrency = (Spinner)findViewById(R.id.spSettingsCurrency);
        EditText etIp = (EditText)findViewById(R.id.etSettingsHost);
        EditText etPort = (EditText)findViewById(R.id.etSettingsPort);

        SharedPreferences sp = getSP();
        final SharedPreferences.Editor editor = sp.edit();
        currency = sp.getString("currency", "€");
        ip = sp.getString("server_ip", "10.0.2.2");
        port = sp.getInt("server_port", 8000);

        etIp.setText(ip);
        etIp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ip = s.toString();
                save();
            }
        });
        etPort.setText(String.valueOf(port));
        etPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                port = Integer.parseInt(s.toString());
                save();
            }
        });
        final String[] currencies = getResources().getStringArray(R.array.currencies);

        spCurrency.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, currencies));
        int posCur = -1;
        for (int i = 0; i < currencies.length && posCur < 0; i++) {
            if(currencies[i].equals(currency)) {
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
        editor.putString("server_ip", ip);
        editor.putInt("server_port", port);
        editor.apply();
    }
}
