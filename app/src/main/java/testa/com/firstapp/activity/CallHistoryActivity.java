package testa.com.firstapp.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import testa.com.firstapp.models.Appel;
import testa.com.firstapp.models.wsModel.CallResponse;
import testa.com.firstapp.tools.UserPreferenceHelper;

public class CallHistoryActivity extends AppCompatActivity {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    ListView listView;
    CallHistoryAdapter adapter;
    TextView offerMenu;
    TextView profilMenu;
    TextView emptyData;
    ProgressBar progressBar;
    ArrayList<Appel> result;
    Button appel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_history);
        appel= findViewById(R.id.btnAppel);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_logout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = findViewById(R.id.listview);
        profilMenu = (TextView) findViewById(R.id.profilMenu);
        offerMenu = (TextView) findViewById(R.id.offerMenu);
        emptyData = (TextView) findViewById(R.id.emptyData);
        progressBar = findViewById(R.id.progressBar);
        initDataFromWS();
        appel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent appel = new Intent(CallHistoryActivity.this, CallActivity.class);
                startActivity(appel);
            }
        });
        profilMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent profilIntent = new Intent(CallHistoryActivity.this, ProfilActivity.class);
                startActivity(profilIntent);
            }
        });

        offerMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent offerIntent = new Intent(CallHistoryActivity.this, OfferActivity.class);
                startActivity(offerIntent);
            }
        });
    }

    private void initDataFromWS() {

        progressBar.setVisibility(View.VISIBLE);
        getListCallWs();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // Toast.makeText(CallHistoryActivity.this, "offre postion " + i +" clicked", Toast.LENGTH_SHORT).show();
                openDialog(result.get(i).getAppeler().getId());
            }
        });
    }

    private void openDialog(final int appeler) {
        final Dialog dialog=new Dialog(CallHistoryActivity.this);
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
                                        if (result != null && !result.isEmpty()) {
                                            adapter = new CallHistoryAdapter(CallHistoryActivity.this,result);
                                            listView.setAdapter(adapter);
                                            emptyData.setVisibility(View.GONE);
                                        } else {
                                            emptyData.setVisibility(View.VISIBLE);
                                        }
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



    public void getListCallWs(){

        //get userId in preference
        UserPreferenceHelper userPreferenceHelper= UserPreferenceHelper.getInstance(this);
        String idUser = userPreferenceHelper.getString("idUtilisateur");

               final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("etat","liste")
                .addFormDataPart("idU",idUser)
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

                                    if(callResponse !=null && callResponse.getData()!=null)
                                    {
                                        result = callResponse.getData();
                                        //call =(ArrayList<Appel>) callResponse.getData();
                                        if (result != null && !result.isEmpty()) {
                                            adapter = new CallHistoryAdapter(CallHistoryActivity.this,result);
                                            listView.setAdapter(adapter);
                                            emptyData.setVisibility(View.GONE);
                                        } else {
                                            emptyData.setVisibility(View.VISIBLE);
                                        }
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
                Toast.makeText(CallHistoryActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                UserPreferenceHelper userPreferenceHelper= UserPreferenceHelper.getInstance(CallHistoryActivity.this);
                userPreferenceHelper.saveString("nom",null);
                userPreferenceHelper.saveString("idUtilisateur",null);

                Intent monIntent = new Intent(CallHistoryActivity.this, LoginActivity.class);
                startActivity(monIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
