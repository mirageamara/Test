package com.example.user.test;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
EditText e,e1;
Button b;
ProgressDialog p;

JSONParser j=new JSONParser();
int success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e=findViewById(R.id.nom);
        e1=findViewById(R.id.prenom);
        b=findViewById(R.id.ok);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*  Intent i=new Intent(MainActivity.this,Second.class);
               startActivity(i); */
            new insert().execute();

            }
        });



    }

    class insert extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p=new ProgressDialog(MainActivity.this);
            p.setMessage("Loading");
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            HashMap<String,String> map=new HashMap<String,String>();
            map.put("nom",e.getText().toString());
            map.put("prenom",e1.getText().toString());


            JSONObject o=j.makeHttpRequest(ipconfig.ip+"/location/ajout_client.php","GET",map);

            try {
                success=o.getInt("success");

                if(success==1)
                {
                    Intent i=new Intent(MainActivity.this,Second.class);
                    startActivity(i);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            p.cancel();
            if(success==0)
            {
                AlertDialog.Builder a=new AlertDialog.Builder(MainActivity.this);
                a.setMessage("Ajout échoué");
                a.setNeutralButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                a.show();
            }
        }
    }

}
