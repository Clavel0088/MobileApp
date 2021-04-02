package testa.com.firstapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import testa.com.firstapp.R;
import testa.com.firstapp.models.Operateur;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent monIntent = new Intent(MainActivity.this, LoginActivity.class);
        //startActivity(monIntent);


    }
   /* private View.OnClickListener handleClick = new View.OnClickListener(){
        public void onClick(View arg0) {
            Button btn = (Button)arg0;
            TextView tv = (TextView) findViewById(R.id.textview1);
            tv.setText("You pressed " + btn.getText());
        }
    };*/
   @Override
   public void onBackPressed() {
       return;
   }
}
