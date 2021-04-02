package testa.com.firstapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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
import testa.com.firstapp.models.wsModel.SaveResponseWs;


public class InscriptionActivity extends AppCompatActivity {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    int operateur=0;
    EditText nom;
    EditText loginText;
    EditText mdp;
    RadioButton telma;
    RadioButton airtel;
    RadioButton orange;
    Button inscription;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        inscription = (Button) findViewById(R.id.inscription);
        nom= (EditText) findViewById (R.id.userName);
        telma= findViewById (R.id.telma);
        airtel=  findViewById (R.id.airtel);
        orange=  findViewById (R.id.orange);
        progressBar=  findViewById (R.id.progressBar);
        loginText= (EditText) findViewById (R.id.loginText);
        mdp= (EditText) findViewById (R.id.password);
        initListener();
    }



    private void initListener() {
        telma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operateur = 1;
            }
        });
        airtel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operateur = 2;
            }
        });
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operateur = 3;
            }
        });
        inscription.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                operateur=1;
                String name=nom.getText().toString();
                if(name==null || name.trim().isEmpty()){
                    Toast.makeText(InscriptionActivity.this, "Veillez saisir votre nom", Toast.LENGTH_SHORT).show();
                    return;
                }
                String login=loginText.getText().toString();
                if(login==null || login.trim().isEmpty()){
                    Toast.makeText(InscriptionActivity.this, "Veillez saisir votre identifiant", Toast.LENGTH_SHORT).show();
                    return;
                }
                String password=mdp.getText().toString();
                if(password==null || password.trim().isEmpty()){
                    Toast.makeText(InscriptionActivity.this, "Veillez saisir votre mot de passe", Toast.LENGTH_SHORT).show();
                    return;
                } if(operateur==0){
                    Toast.makeText(InscriptionActivity.this, "Veillez choisir un operateur", Toast.LENGTH_SHORT).show();
                    return;
                }

                inscription(name,login,password);
            }

        });
    }


    public void inscription(String nom,String login,String mdp){
        progressBar.setVisibility(View.VISIBLE);

        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        MediaType mediaTypeImage =  MediaType.parse("multipart/form-data");

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("etat","save")
                .addFormDataPart("nom",nom)
                .addFormDataPart("mdp",mdp)
                .addFormDataPart("login",login)
                .addFormDataPart("idOperateur",String.valueOf(operateur))
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
                                    SaveResponseWs saveResponse=gson.fromJson(responseBody, SaveResponseWs.class);
                                    if(!saveResponse.getError()){
                                        Intent accueil=new Intent(InscriptionActivity.this,LoginActivity.class);
                                        startActivity(accueil);
                                    }
                                    else
                                    {
                                        showMessageError("Une erreur est survenue , resultat erreur");
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
                Toast.makeText(InscriptionActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        return;
    }
}
