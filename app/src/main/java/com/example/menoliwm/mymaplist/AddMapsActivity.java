package com.example.menoliwm.mymaplist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Menoliwm on 2017-05-31.
 */

public class AddMapsActivity extends Activity{

    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        Button edit_btn = (Button) findViewById(R.id.add_btn);
        edit_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText editTitle = (EditText) findViewById(R.id.add_title);
                EditText editAddress = (EditText) findViewById(R.id.add_address);
                EditText editLatitude = (EditText) findViewById(R.id.add_latitude);
                EditText editLongitude = (EditText) findViewById(R.id.add_longitude);

                String title = editTitle.getText().toString();
                String address = editAddress.getText().toString();
                String latitude = editLatitude.getText().toString();
                String longitude = editLongitude.getText().toString();

                intent.putExtra("title", title);
                intent.putExtra("address", address);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);

                setResult(0, intent);
                finish();
            }
        });
    }
}
