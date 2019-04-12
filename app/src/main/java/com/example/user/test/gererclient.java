package com.example.user.test;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
public class gererclient extends AppCompatActivity {
    Button b;
    EditText edt1,edt2;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    /** url pour s'authentifier ,si vous utiliser EasyPHP ou
     Wamp et vous tester avec l'émulateur
     vous devez mettre 10.0.2.2 au lieu de localhost
     ou 127.0.0.1 dans l'url ,
     sinon si vous êtes connecté à un ordinateur distant,
     mettez l'url comme il est **/

    private static String url_create_product =ipconfig.ip+"/location/supprimerclient.php";
    //JSON Node names
    private static final String TAG_SUCCESS = "success";
    AlertDialog.Builder alert;
    JSONObject json;
    int success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gererclient);
        b=findViewById(R.id.button3);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(gererclient.this,seconnecteremployee.class);
                startActivity(i);
            }
        });
        edt1=(EditText) findViewById(R.id.editText6);
        edt2=(EditText) findViewById(R.id.editText3);


    }

    class CreateNewProduct extends AsyncTask <String,String,String>  {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(gererclient.this);
            pDialog.setMessage("CHARGEMENT...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            String nom = edt1.getText().toString();
            String prenom = edt2.getText().toString();


            HashMap<String,String> params = new HashMap<String,String>();
            params.put("nom", nom);
            params.put("prenom",prenom);

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "GET", params);
            // check log cat fro response
            Log.d("Create Response", json.toString());
            // check for success tag
            try {
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // on suppose qu'une activité portant le nom EspaceActivity existe


                    Intent i = new Intent(getApplicationContext(), Second.class);
                    i.putExtra("nom", nom);

                    startActivity(i);


                } else {
                    alert=new AlertDialog.Builder(gererclient.this);
                    alert.setMessage("email ou mot de passe incorrecte.Réessayer");
                    alert.setNeutralButton("ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();

            if (success==0)
            {
                alert.show();
            }
        }
    }
}