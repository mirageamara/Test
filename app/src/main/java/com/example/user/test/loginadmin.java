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
public class loginadmin extends AppCompatActivity {
    Button b;
    EditText edt,edt2;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();


    private static String url_create_product =ipconfig.ip+"/location/loginadmin.php";
    //JSON Node names
    private static final String TAG_SUCCESS = "success";
    AlertDialog.Builder alert;
    JSONObject json;
    int success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginadmin);
        edt=(EditText) findViewById(R.id.editText1);
        edt2=(EditText) findViewById(R.id.editText2);
        b=(Button) findViewById(R.id.button1);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(edt.getText().toString().equals("")||edt2.getText().toString().equals(""))
                {Toast.makeText(loginadmin.this,"Vous devez remplir tous les champs",Toast.LENGTH_LONG).show();

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
            pDialog = new ProgressDialog(loginadmin.this);
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


                    Intent i = new Intent(getApplicationContext(), administrateur.class);
                    i.putExtra("mail", email);

                    startActivity(i);


                } else {
                    alert=new AlertDialog.Builder(loginadmin.this);
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