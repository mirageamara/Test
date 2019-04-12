package com.example.user.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class accueil extends AppCompatActivity {
ImageButton b1,b2;
Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);
        b1=findViewById(R.id.imageButton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Intent i=new Intent(accueil.this,Login.class);
               startActivity(i);
            }
        });
        b2=findViewById(R.id.imageButton3);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(accueil.this,loginemployee.class);
                startActivity(i);
            }
        });
        b=findViewById(R.id.button4);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(accueil.this,contact.class);
                startActivity(i);
            }
        });
    }

}
