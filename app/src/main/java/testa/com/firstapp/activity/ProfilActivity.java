package testa.com.firstapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import testa.com.firstapp.R;
import testa.com.firstapp.tools.UserPreferenceHelper;

/**
 * Created by Phael on 20/03/2021.
 */

public class ProfilActivity extends AppCompatActivity {
    TextView credit;
    TextView compte;
    TextView offerMenu;
    TextView callMenu;
    TextView profilMenu;
    ImageView postinView;
    ImageView postoutView;
    TextView name;
    ImageView creditView;
    RelativeLayout relativeDepot;
    RelativeLayout relativeRetrait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_logout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        callMenu = (TextView) findViewById(R.id.callMenu);
        offerMenu = (TextView) findViewById(R.id.offerMenu);
        credit = (TextView) findViewById(R.id.credit);
        compte= (TextView) findViewById(R.id.compte);
        profilMenu=(TextView) findViewById(R.id.profilMenu);
        name=(TextView) findViewById(R.id.name);
        postinView=(ImageView) findViewById(R.id.imgPostin);
        postoutView=(ImageView) findViewById(R.id.imgPostout);
        creditView=(ImageView) findViewById(R.id.imgCredit);
        relativeDepot= findViewById(R.id.relativeDepot);
        relativeRetrait= findViewById(R.id.relativeRetrait);
        UserPreferenceHelper userPreferenceHelper= UserPreferenceHelper.getInstance(this);
        String nomInPreferences = userPreferenceHelper.getString("nom");
        String ncreditInPreferences = userPreferenceHelper.getString("credit");
        credit.setText("Mon credit est:"+ncreditInPreferences);

        name.setText(nomInPreferences);
        callMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent monIntent = new Intent(ProfilActivity.this, CallHistoryActivity.class);
                startActivity(monIntent);
            }
        });



        offerMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent offerIntent = new Intent(ProfilActivity.this, OfferActivity.class);
                startActivity(offerIntent);
            }
        });



        postinView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent monIntent = new Intent(ProfilActivity.this, RetraitActivity.class);
                startActivity(monIntent);
            }
        });

        postoutView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent monIntent = new Intent(ProfilActivity.this, TransactionActivity.class);
                startActivity(monIntent);
            }
        });
        relativeDepot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent monIntent = new Intent(ProfilActivity.this, DepotActivity.class);
                startActivity(monIntent);
            }
        });

        relativeRetrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent monIntent = new Intent(ProfilActivity.this, RetraitActivity.class);
                startActivity(monIntent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                UserPreferenceHelper userPreferenceHelper= UserPreferenceHelper.getInstance(ProfilActivity.this);
                userPreferenceHelper.saveString("nom",null);
                userPreferenceHelper.saveString("idUtilisateur",null);

                Intent monIntent = new Intent(ProfilActivity.this, LoginActivity.class);
                startActivity(monIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
