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

public class formulaireclient extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6,e7;
    Button b;
    ProgressDialog p;

    JSONParser j=new JSONParser();
    int success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulaireclient);
        e1=findViewById(R.id.nom);
        e2=findViewById(R.id.prenom);
        e3=findViewById(R.id.ddn);
        e4=findViewById(R.id.tel);
        e5=findViewById(R.id.email);
        e6=findViewById(R.id.mdp);
        e7=findViewById(R.id.cin);
        b=findViewById(R.id.valider);

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
            p=new ProgressDialog(formulaireclient.this);
            p.setMessage("Chargement...");
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            HashMap<String,String> map=new HashMap<String,String>();
            map.put("nom",e1.getText().toString());
            map.put("prenom",e2.getText().toString());
            map.put("date",e3.getText().toString());
            map.put("tel",e4.getText().toString());
            map.put("email",e5.getText().toString());
            map.put("mdp",e6.getText().toString());
            map.put("cin",e7.getText().toString());





            JSONObject o=j.makeHttpRequest(ipconfig.ip+"/location/ajout_client2.php","GET",map);

            try {
                success=o.getInt("success");

                if(success==1)
                {
                    Intent i=new Intent(formulaireclient.this,Login.class);
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
                AlertDialog.Builder a=new AlertDialog.Builder(formulaireclient.this);
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
