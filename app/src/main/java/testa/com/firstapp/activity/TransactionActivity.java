package testa.com.firstapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import testa.com.firstapp.R;

/**
 * Created by Phael on 27/03/2021.
 */

public class TransactionActivity extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        Button transaction = (Button) findViewById(R.id.transaction);
        transaction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent monIntent = new Intent(TransactionActivity.this, ProfilActivity.class);
                startActivity(monIntent);
            }
        });

    }



}
