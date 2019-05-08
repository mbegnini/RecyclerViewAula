package br.com.recyclerviewaula;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class EditFilmActivity extends AppCompatActivity {

    EditText tituloEditText;
    EditText generoEditText;
    EditText anoEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_film);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tituloEditText = (EditText) findViewById(R.id.tituloEditText);
        generoEditText = (EditText) findViewById(R.id.generoEditText);
        anoEditText = (EditText) findViewById(R.id.anoEditText);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnFilme();
            }
        });
    }

    public void returnFilme(){
        Filme filme = new Filme();
        filme.setTitulo(tituloEditText.getText().toString());
        filme.setGenero(generoEditText.getText().toString());
        filme.setAno(Integer.valueOf(anoEditText.getText().toString()));

        Bundle bundle = new Bundle();
        bundle.putSerializable("filme",filme);

        Intent returnIntent = new Intent();
        returnIntent.putExtras(bundle);
        setResult(Activity.RESULT_OK, returnIntent);

        finish();
    }

}
