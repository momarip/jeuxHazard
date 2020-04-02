package com.example.jeuhazard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.*;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonOK;
    private EditText editTextNumber;
    private ListView listViewHisto;
    private TextView textViewIndication;
    private ProgressBar progressBarScore;
    private TextView textViewScoreCumul;
    private TextView textViewScore;
    private int secret;
    private int nombreEssais=1;	private int nombreMaxEssais=6;
    private  List<String> historique=new ArrayList<>();	private ArrayAdapter<String> adapter;
    private int scoreCumule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextNumber = findViewById(R.id.editTextNumber);
        listViewHisto = findViewById(R.id.lisViewHisto);
        textViewIndication = findViewById(R.id.textViewIndication);
        textViewScore = findViewById(R.id.textViewScore);
        progressBarScore = findViewById(R.id.progressBarScore);
        buttonOK = findViewById(R.id.buttonOK);
        textViewScoreCumul = findViewById(R.id.textViewScoreCumul);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, historique);
        listViewHisto.setAdapter(adapter);
        initialisation();

        buttonOK.setOnClickListener((evt)->{
            String str=editTextNumber.getText().toString();
            int number=0;
            try{
                number=Integer.parseInt(str);
            }catch (NumberFormatException ex){  ex.printStackTrace();
                return;
            }
            historique.add(nombreEssais+" =>:"+number);  adapter.notifyDataSetChanged();  Log.i("MyInfos",getString(R.string.essai_numero)+
                    String.valueOf(nombreEssais)+"=>"+String.valueOf(number));


            textViewScore.setText(String.valueOf(nombreEssais));
            progressBarScore.setProgress(nombreEssais);
            if(number>secret){
                textViewIndication.setText(getString(R.string.nombre_plus_grand));
            }
            else if(number<secret){
                textViewIndication.setText(getString(R.string.nombre_plus_petit));
            }
            else{
                textViewIndication.setText(R.string.bravo);
                scoreCumule+=5;
                retry();
            }
            editTextNumber.setText("");
            if((number!=secret)&&(nombreEssais>nombreMaxEssais)){
                retry();
            }
            ++nombreEssais;
        });
    }
    private void retry(){
        AlertDialog alertDialog=new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.str_nouvel_essai));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initialisation();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Finish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.show();
    }



    private void initialisation() {
        this.nombreEssais=1;
        this.secret=1+((int)(Math.random()*100));
        this.editTextNumber.requestFocus();
        this.progressBarScore.setProgress(nombreEssais);
        this.textViewIndication.setText("");  this.editTextNumber.setText("");
        this.textViewScore.setText(String.valueOf(nombreEssais));
        textViewScoreCumul.setText(String.valueOf(scoreCumule));
        historique.clear();
        adapter.notifyDataSetChanged();
    }

}

