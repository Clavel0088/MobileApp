package testa.com.firstapp.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import testa.com.firstapp.R;
import testa.com.firstapp.adapter.CallHistoryAdapter;
import testa.com.firstapp.adapter.UserAdapter;
import testa.com.firstapp.models.Appel;
import testa.com.firstapp.models.Utilisateur;
import testa.com.firstapp.models.wsModel.CallResponse;
import testa.com.firstapp.models.wsModel.UserResponse;
import testa.com.firstapp.tools.UserPreferenceHelper;

/**
 * Created by Phael on 02/04/2021.
 */

public class CallActivity extends AppCompatActivity {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    ListView listView;
    UserAdapter adapter;
    TextView offerMenu;
    TextView profilMenu;
    ProgressBar progressBar;
    ArrayList<Appel> result;
    ArrayList<Utilisateur> utilisateurArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_logout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = findViewById(R.id.listview);
        profilMenu = (TextView) findViewById(R.id.profilMenu);
        offerMenu = (TextView) findViewById(R.id.offerMenu);
        progressBar = findViewById(R.id.progressBar);
        initDataFromWS();
        profilMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent profilIntent = new Intent(CallActivity.this, ProfilActivity.class);
                startActivity(profilIntent);
            }
        });

        offerMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent offerIntent = new Intent(CallActivity.this, OfferActivity.class);
                startActivity(offerIntent);
            }
        });
    }

    private void initDataFromWS() {

        progressBar.setVisibility(View.VISIBLE);
        getListUtilisateur();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(CallActivity.this, "offre postion " + i +" clicked", Toast.LENGTH_SHORT).show();
                openDialog(utilisateurArrayList.get(i).getId());
            }
        });
    }

    private void openDialog(final int appeler) {
        final Dialog dialog=new Dialog(CallActivity.this);
        dialog.setContentView(R.layout.dialog_appel);
        final EditText duree=dialog.findViewById(R.id.editDuree);

        Button btnOk=dialog.findViewById(R.id.ok);
        Button btnAnnuler=dialog.findViewById(R.id.annuler);
        dialog.setTitle("");
        dialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallWs(appeler,Integer.valueOf(duree.getText().toString()));
                dialog.cancel();
            }
        });
        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

    }


    public void CallWs(int appeler,int duree){

        //get userId in preference
        progressBar.setVisibility(View.VISIBLE);
        UserPreferenceHelper userPreferenceHelper= UserPreferenceHelper.getInstance(this);
        String idUser = userPreferenceHelper.getString("idUtilisateur");

        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("etat","ajout")
                .addFormDataPart("appelant",idUser)
                .addFormDataPart("appeler",String.valueOf(appeler))
                .addFormDataPart("duree",String.valueOf(duree))
                .build();

        final Request request = new Request.Builder()
                .url("https://mobilemoney2021.herokuapp.com/appel")
                .post(requestBody)
                //.get()
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showMessageError("Une erreur est survenue");
                Log.e("on failure", "Failed to execute : " + request.url().toString() + "\n" + e.getLocalizedMessage());
                //listener.requestFailure(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if (!response.isSuccessful()) {
                    showMessageError("Une erreur est survenue");
                    Log.e("TAG",  "Unexpected code from server : " + response.code());
                    //listener.requestError(response.code(), response.message());
                }
                else
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String responseBody = response.body().string();
                                if(responseBody != null)
                                {
                                    Gson gson = new Gson();
                                    CallResponse callResponse=gson.fromJson(responseBody, CallResponse.class);
                                    if(callResponse !=null )
                                    {
                                        //call =(ArrayList<Appel>) callResponse.getData();
                                       // adapter = new UserAdapter(CallActivity.this,result);
                                        //listView.setAdapter(adapter);
                                        Intent callHistory = new Intent(CallActivity.this, CallHistoryActivity.class);
                                        startActivity(callHistory);
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else {
                                        showMessageError(callResponse.getMessage());
                                    }
                                }
                                else
                                {
                                    showMessageError("Response null");
                                }
                            } catch (IOException e) {
                                showMessageError("Une erreur est survenue");
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

        });
    }



    public void getListUtilisateur(){

        //get userId in preference

        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("etat","all")
                .build();

        final Request request = new Request.Builder()
                .url("https://mobilemoney2021.herokuapp.com/utilisateur")
                .post(requestBody)
                //.get()
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showMessageError("Une erreur est survenue");
                Log.e("on failure", "Failed to execute : " + request.url().toString() + "\n" + e.getLocalizedMessage());
                //listener.requestFailure(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                if (!response.isSuccessful()) {
                    showMessageError("Une erreur est survenue");
                    Log.e("TAG",  "Unexpected code from server : " + response.code());
                    //listener.requestError(response.code(), response.message());
                }
                else
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String responseBody = response.body().string();
                                if(responseBody != null)
                                {
                                    Gson gson = new Gson();
                                    UserResponse userResponse=gson.fromJson(responseBody, UserResponse.class);

                                    if(userResponse !=null && userResponse.getData()!=null)
                                    {
                                        utilisateurArrayList = userResponse.getData();
                                        adapter = new UserAdapter(CallActivity.this,utilisateurArrayList);
                                        listView.setAdapter(adapter);
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else {
                                        showMessageError("Aucune donnée");
                                    }
                                }
                                else
                                {
                                    showMessageError("Response null");
                                }
                            } catch (IOException e) {
                                showMessageError("Une erreur est survenue");
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

        });
    }
    public void showMessageError(final String errorMessage)
    {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CallActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                UserPreferenceHelper userPreferenceHelper= UserPreferenceHelper.getInstance(CallActivity.this);
                userPreferenceHelper.saveString("nom",null);
                userPreferenceHelper.saveString("idUtilisateur",null);

                Intent monIntent = new Intent(CallActivity.this, LoginActivity.class);
                startActivity(monIntent);
        }
        return super.onOptionsItemSelected(item);
    }
    
}
