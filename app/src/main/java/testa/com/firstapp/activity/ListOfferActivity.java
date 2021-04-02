package testa.com.firstapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import testa.com.firstapp.adapter.OfferAdapter;
import testa.com.firstapp.models.Offre;
import testa.com.firstapp.models.wsModel.DataResponse;
import testa.com.firstapp.tools.UserPreferenceHelper;


public class ListOfferActivity extends AppCompatActivity {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    OfferAdapter adapter;
    ListView listView;
    TextView profilMenu;
    TextView callMenu;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_offer);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_logout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = findViewById(R.id.listview);
        callMenu = (TextView) findViewById(R.id.callMenu);
        progressBar = findViewById(R.id.progressBar);
        profilMenu = (TextView) findViewById(R.id.profilMenu);
        initDataFromWS();
    }

    private void initDataFromWS() {
        progressBar.setVisibility(View.VISIBLE);
        getListOffers();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ListOfferActivity.this, "offre postion " + i +" clicked", Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void getListOffers(){

        UserPreferenceHelper userPreferenceHelper= UserPreferenceHelper.getInstance(this);
        String idUser = userPreferenceHelper.getString("idUtilisateur");

        final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("type","liste")
                .addFormDataPart("id",idUser)
                .build();

        final Request request = new Request.Builder()
                .url("https://mobilemoney2021.herokuapp.com/offre")
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
                                String responseBody = response.body().
                                        string();
                                if(responseBody != null)
                                {
                                    Gson gson = new Gson();
                                    DataResponse offerResponse=gson.fromJson(responseBody, DataResponse.class);

                                    ArrayList<Offre> offres=null;
                                    if(offerResponse !=null && offerResponse.getData()!=null)
                                    {
                                        offres =(ArrayList<Offre>) offerResponse.getData();
                                        adapter = new OfferAdapter(offres,ListOfferActivity.this,1);
                                        listView.setAdapter(adapter);
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else
                                    {
                                        showMessageError("Aucune donnée");
                                    }
                                }
                                else
                                {
                                    showMessageError("null response");
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
                Toast.makeText(ListOfferActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                UserPreferenceHelper userPreferenceHelper= UserPreferenceHelper.getInstance(ListOfferActivity.this);
                userPreferenceHelper.saveString("nom",null);
                userPreferenceHelper.saveString("idUtilisateur",null);

                Intent monIntent = new Intent(ListOfferActivity.this, LoginActivity.class);
                startActivity(monIntent);
        }
        return super.onOptionsItemSelected(item);
    }


}
