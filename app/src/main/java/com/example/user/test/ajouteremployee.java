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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ajouteremployee extends AppCompatActivity {
    EditText e1,e2,e3;
    Button b;
    ProgressDialog p;

    JSONParser j=new JSONParser();
    int success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouteremployee);
        e1=findViewById(R.id.nom);
        e2=findViewById(R.id.login);
        e3=findViewById(R.id.password);
        b=findViewById(R.id.ajouter);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e1.getText().toString().equals("")||e2.getText().toString().equals("")||e3.getText().toString().equals(""))
                {Toast.makeText(ajouteremployee.this,"Vous devez remplir tous les champs",Toast.LENGTH_LONG).show();

                }

             Intent i=new Intent(ajouteremployee.this,loginemployee.class);
               startActivity(i);
                new insert().execute();

            }
        });



    }

    class insert extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p=new ProgressDialog(ajouteremployee.this);
            p.setMessage("CHARGEMENT...");
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            HashMap<String,String> map=new HashMap<String,String>();
            map.put("nom",e1.getText().toString());
            map.put("login",e2.getText().toString());
            map.put("pass",e3.getText().toString());


            JSONObject o=j.makeHttpRequest(ipconfig.ip+"/location/ajoutemployee.php","GET",map);

            try {
                success=o.getInt("success");

                if(success==1)
                {
                    Intent i=new Intent(ajouteremployee.this,loginemployee.class);
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
                AlertDialog.Builder a=new AlertDialog.Builder(ajouteremployee.this);
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
