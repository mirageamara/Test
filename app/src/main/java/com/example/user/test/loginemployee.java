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
public class loginemployee extends AppCompatActivity {
    Button b1,b2;
    EditText edt,edt2;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    /** url pour s'authentifier ,si vous utiliser EasyPHP ou
     Wamp et vous tester avec l'émulateur
     vous devez mettre 10.0.2.2 au lieu de localhost
     ou 127.0.0.1 dans l'url ,
     sinon si vous êtes connecté à un ordinateur distant,
     mettez l'url comme il est **/

    private static String url_create_product =ipconfig.ip+"/location/loginemployee.php";
    //JSON Node names
    private static final String TAG_SUCCESS = "success";
    AlertDialog.Builder alert;
    JSONObject json;
    int success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginemployee);
        b2=findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(loginemployee.this,loginadmin.class);
                startActivity(i);
            }
        });
        edt=(EditText) findViewById(R.id.editText1);
        edt2=(EditText) findViewById(R.id.editText2);
        b1=(Button) findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(edt.getText().toString().equals("")||edt2.getText().toString().equals(""))
                {Toast.makeText(loginemployee.this,"Vous devez remplir tous les champs",Toast.LENGTH_LONG).show();

                }
                else
                {
                    CreateNewProduct user= new CreateNewProduct();
                    user.execute();
                }
            }
        });
    }

    class CreateNewProduct extends AsyncTask <String,String,String>  {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(loginemployee.this);
            pDialog.setMessage("CHARGEMENT");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            String email = edt.getText().toString();
            String pass = edt2.getText().toString();


            HashMap<String,String> params = new HashMap<String,String>();
            params.put("mail", email);
            params.put("pass",pass);

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


                    Intent i = new Intent(getApplicationContext(), EspaceEmploye.class);
                    i.putExtra("mail", email);

                    startActivity(i);


                } else {
                    alert=new AlertDialog.Builder(loginemployee.this);
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