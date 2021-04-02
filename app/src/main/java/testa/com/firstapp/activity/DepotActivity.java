package testa.com.firstapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
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
import testa.com.firstapp.models.Depot;
import testa.com.firstapp.models.Utilisateur;
import testa.com.firstapp.models.wsModel.DepoResponseWs;
import testa.com.firstapp.tools.UserPreferenceHelper;

public class DepotActivity extends AppCompatActivity {

    EditText editMontant;
    EditText editCodeSecret;
    ProgressBar progressBar;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depot);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_logout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editMontant= (EditText) findViewById (R.id.editMontant);
        editCodeSecret= (EditText) findViewById (R.id.editCodeSecret);
        progressBar=  findViewById (R.id.progressBar);
        btnBack =  findViewById (R.id.btnBack);
        initListener();
    }
    private void initListener() {
        Button btnValidate = (Button) findViewById(R.id.btnValidate);
        btnValidate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String montant=editMontant.getText().toString();
                if(montant==null || montant.trim().isEmpty()){
                    Toast.makeText(DepotActivity.this, "Veillez saisir le montant", Toast.LENGTH_SHORT).show();
                    return;
                }
                String codetSecret=editCodeSecret.getText().toString();
                if(codetSecret==null || codetSecret.trim().isEmpty()){
                    Toast.makeText(DepotActivity.this, "Veillez saisir votre code secret", Toast.LENGTH_SHORT).show();
                    return;
                }
                addDepot(montant, codetSecret);
            }


        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent monIntent = new Intent(DepotActivity.this, ProfilActivity.class);
                startActivity(monIntent);
            }
        });

    }

    private void addDepot(String montant, String codetSecret) {
        progressBar.setVisibility(View.VISIBLE);
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        UserPreferenceHelper userPreferenceHelper = UserPreferenceHelper.getInstance(DepotActivity.this);
        String idUtilisateur = userPreferenceHelper.getString("idUtilisateur");

        MediaType mediaType = MediaType.parse("multipart/form-data");

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("etat", "ajout")
                .addFormDataPart("id", idUtilisateur)
                .addFormDataPart("code", codetSecret)
                .addFormDataPart("montant", montant)
                .build();

        final Request request = new Request.Builder()
                .url("https://mobilemoney2021.herokuapp.com/depot")
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
                    Log.e("TAG", "Unexpected code from server : " + response.code());
                    //listener.requestError(response.code(), response.message());
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String responseBody = response.body().string();
                                if (responseBody != null) {
                                    Gson gson = new Gson();
                                    DepoResponseWs depoResponseWs = gson.fromJson(responseBody, DepoResponseWs.class);

                                    if (!depoResponseWs.isError()) {
                                       // UserPreferenceHelper userPreferenceHelper = UserPreferenceHelper.getInstance(DepotActivity.this);
                                        //userPreferenceHelper.saveString("nom", depot.getMontant());

                                        progressBar.setVisibility(View.GONE);
                                        Intent accueil = new Intent(DepotActivity.this, ProfilActivity.class);

                                        startActivity(accueil);

                                    } else {
                                        showMessageError(depoResponseWs.getMessage());
                                    }

                                } else {
                                    showMessageError("Une erreur est survenue");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                showMessageError("Une erreur est survenue");
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
                    Toast.makeText(DepotActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                UserPreferenceHelper userPreferenceHelper= UserPreferenceHelper.getInstance(DepotActivity.this);
                userPreferenceHelper.saveString("nom",null);
                userPreferenceHelper.saveString("idUtilisateur",null);

                Intent monIntent = new Intent(DepotActivity.this, LoginActivity.class);
                startActivity(monIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    }

