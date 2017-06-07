package com.example.menoliwm.mymaplist;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Menoliwm on 2017-06-02.
 */

public class EditMapsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();

        final String id = intent.getStringExtra("id");
        final String userid = intent.getStringExtra("userid");
        final String title = intent.getStringExtra("title");
        final String address = intent.getStringExtra("address");
        final String latitude = intent.getStringExtra("latitude");
        final String longitude = intent.getStringExtra("longitude");
        final String isMapView = intent.getStringExtra("isMapView");

        final Boolean boolIsMapView = Boolean.parseBoolean(isMapView);

        final EditText editTitle = (EditText) findViewById(R.id.edit_title);
        final EditText editAddress = (EditText) findViewById(R.id.edit_address);
        final EditText editLatitude = (EditText) findViewById(R.id.edit_latitude);
        final EditText editLongitude = (EditText) findViewById(R.id.edit_longitude);

        final CheckBox edit_checkBox = (CheckBox) findViewById(R.id.edit_checkBox);

        editTitle.setText(title);
        editAddress.setText(address);
        editLatitude.setText(latitude);
        editLongitude.setText(longitude);

        if(boolIsMapView){
            edit_checkBox.setChecked(boolIsMapView);
    }

        edit_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                final Intent intent = new Intent();

                if (edit_checkBox.isChecked()){
                    intent.putExtra("isMapView", "true");
                } else {
                    intent.putExtra("isMapView", "false");
                }

                final Button edit_btn = (Button) findViewById(R.id.edit_btn);
                edit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        intent.putExtra("id", id);
                        intent.putExtra("userid", userid);
                        intent.putExtra("title", editTitle.getText().toString());
                        intent.putExtra("address", editAddress.getText().toString());
                        intent.putExtra("latitude", editLatitude.getText().toString());
                        intent.putExtra("longitude", editLongitude.getText().toString());

                        setResult(1, intent);
                        finish();
                    }
                });
            }
        });
    }
}
