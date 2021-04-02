package testa.com.firstapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import testa.com.firstapp.models.Utilisateur;
import testa.com.firstapp.models.wsModel.LoginResponseWs;
import testa.com.firstapp.tools.UserPreferenceHelper;

/**
 * Created by Phael on 20/03/2021.
 */

public class LoginActivity extends AppCompatActivity {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    EditText login;
    EditText mdp;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login= (EditText) findViewById (R.id.login);
        mdp= (EditText) findViewById (R.id.pwd);
        progressBar=  findViewById (R.id.progressBar);
        initListener();

    }

    private void initListener() {
        Button BtnLogin = (Button) findViewById(R.id.btnLogin);
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String loginText=login.getText().toString();
                if(loginText==null || loginText.trim().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Veillez saisir votre identifiant", Toast.LENGTH_SHORT).show();
                    return;
                }
                String mdpText=mdp.getText().toString();
                if(mdpText==null || mdpText.trim().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Veillez saisir votre mot de passe", Toast.LENGTH_SHORT).show();
                    return;
                }
                login(loginText, mdpText);
            }


        });
        Button inscription = (Button) findViewById(R.id.inscription);
        inscription.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent monIntent = new Intent(LoginActivity.this, InscriptionActivity.class);
                startActivity(monIntent);
            }
        });

    }


    public void login(String login,String mdp){

        progressBar.setVisibility(View.VISIBLE);
        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        MediaType mediaType =  MediaType.parse("multipart/form-data");

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                 .addFormDataPart("etat","login")
                 .addFormDataPart("mdp",mdp)
                 .addFormDataPart("login",login)
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
                                    LoginResponseWs loginResponse=gson.fromJson(responseBody, LoginResponseWs.class);
                                    Utilisateur utilisateur=null;
                                    if(!loginResponse.isError()){
                                        utilisateur =(Utilisateur) loginResponse.getData();
                                        UserPreferenceHelper userPreferenceHelper= UserPreferenceHelper.getInstance(LoginActivity.this);
                                        userPreferenceHelper.saveString("nom",utilisateur.getNom());
                                        userPreferenceHelper.saveString("idUtilisateur",String.valueOf(utilisateur.getId()));
                                        userPreferenceHelper.saveString("credit",String.valueOf(utilisateur.getCredit()));
                                        //if (utilisateur.g) {
                                          //  userPreferenceHelper.saveString("compte",String.valueOf(utilisateur.getCredit()));
                                        //}
                                        progressBar.setVisibility(View.GONE);
                                        Intent accueil=new Intent(LoginActivity.this,ProfilActivity.class);

                                        startActivity(accueil);

                                    }
                                    else
                                    {
                                        showMessageError("Mot de passe ou login invalide");
                                    }

                                }
                                else
                                {
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
                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
