package io.boringideas.utube;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SelectCountryActivity extends AppCompatActivity {

    TextView mCountryName;
    ImageView mCountryFlag;
    String [] countries;
    TypedArray flags;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_country);
        mCountryName = findViewById(R.id.country_name);
        mCountryFlag = findViewById(R.id.country_image);

        countries = getResources().getStringArray(R.array.countries);
        flags = getResources().obtainTypedArray(R.array.countries_flags);
    }

    public void onClickCountry(View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(R.string.select_country);
        alertDialog.setItems(countries, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),countries[i],Toast.LENGTH_LONG).show();
                mCountryName.setText(countries[i]);
                mCountryFlag.setImageResource(flags.getResourceId(i,-1));

            }
        });
        alertDialog.show();
    }

    public void onClickNext(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
