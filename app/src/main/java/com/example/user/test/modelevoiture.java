package com.example.user.test;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class modelevoiture extends AppCompatActivity {
    Button b;
    Spinner s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modelevoiture);
        s = (Spinner) findViewById(R.id.spinner3);

        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this,
                R.array.voiture, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (s.getSelectedItem().toString().equals("Fiat"))

                {
                    Intent i = new Intent(modelevoiture.this, second1.class);
                    startActivity(i);

                } else if (s.getSelectedItem().toString().equals("Fiat"))

                {
                    Intent i = new Intent(modelevoiture.this, second1.class);
                    startActivity(i);

                } else if (s.getSelectedItem().toString().equals("Clio"))

                {
                    Intent i = new Intent(modelevoiture.this, second1.class);
                    startActivity(i);

                } else if (s.getSelectedItem().toString().equals("Audi"))

                {
                    Intent i = new Intent(modelevoiture.this, second1.class);
                    startActivity(i);

                } else if (s.getSelectedItem().toString().equals("Hyundai"))

                {
                    Intent i = new Intent(modelevoiture.this, second1.class);
                    startActivity(i);

                } else if (s.getSelectedItem().toString().equals("Mercedes"))

                {
                    Intent i = new Intent(modelevoiture.this, second1.class);
                    startActivity(i);

                } else if (s.getSelectedItem().toString().equals("Volkswagen"))

                {
                    Intent i = new Intent(modelevoiture.this, second1.class);
                    startActivity(i);

                } else {
                    Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();

                }
            }
        });


    }
}