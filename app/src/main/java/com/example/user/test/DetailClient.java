package com.example.user.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailClient extends AppCompatActivity {
String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_client);

        Bundle b=getIntent().getExtras();
        id=b.getString("id");

    }
}
