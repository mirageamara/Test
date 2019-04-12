package com.example.user.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class listeclient extends AppCompatActivity {
    ProgressDialog pDialog;
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    ListView lv;
    ArrayList<HashMap<String, String>>   productsList = new ArrayList<HashMap<String, String>>();
    // url to get all products list
    private static String url_all_products = ipconfig.ip+"/location/listeclient.php";
    // products JSONArray
    JSONArray products = null;
    int error=0;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "client";
    private static final String TAG_PID = "cin";
    private static final String TAG_NAME = "nomclient";
    private static final String TAG_PRENOM = "prenomclient";
    private static final String TAG_NATURE = "datenaissance";
    private static final String TAG_TEL = "telephone";
    private static final String TAG_MAIL= "email";
    @Override

    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listeclient);
        // Hashmap for ListView.

        // Get listview
        lv = (ListView)findViewById(R.id.listView);
        // Loading products in Background Thread
        new LoadAllProducts().execute();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                TextView t=view.findViewById(R.id.pid);
                Intent i=new Intent(getApplicationContext(),DetailClient.class);
                i.putExtra("id",t.getText().toString());
                startActivity(i);


            }
        });

    }
    class LoadAllProducts extends

            AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(listeclient.this);
            pDialog.setMessage("Chargement...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**         * getting All products from url         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            HashMap<String, String> params = new HashMap<String, String>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());
            // Checking for SUCCESS TAG
            int success = 0;
            try {
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);

                    HashMap map;
                    // looping through All Products
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        String cin = c.getString(TAG_PID);
                        String name = c.getString(TAG_NAME);
                        String prenom = c.getString(TAG_PRENOM);
                        String datenaissance= c.getString(TAG_NATURE);
                        String telephone = c.getString(TAG_TEL);
                        String email = c.getString(TAG_MAIL);


                        // creating new HashMap
                        map = new HashMap<String, String>();
                        map.put(TAG_PID, cin);
                        map.put(TAG_NAME, name);
                        map.put(TAG_PRENOM, prenom);
                        map.put(TAG_NATURE,datenaissance);
                        map.put(TAG_TEL, telephone);
                        map.put(TAG_MAIL, email);

                        // adding HashList to ArrayList
                        productsList.add(map);
                    }
                } else {
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**    * After completing

         background task Dismiss the progress dialog         * **/

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            ListAdapter adapter = new SimpleAdapter(
                    listeclient.this, productsList,
                    R.layout.item, new String[] { TAG_PID,TAG_NAME,TAG_NATURE},
                    new int[] { R.id.pid, R.id.nom,R.id.nature});
            lv.setAdapter(adapter);


        }
    }
}