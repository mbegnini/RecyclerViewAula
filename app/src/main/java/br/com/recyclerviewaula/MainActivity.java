package br.com.recyclerviewaula;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Actions {

    private List<Filme> listaFilmes;
    private FilmeAdapter adapter;
    private RecyclerView recyclerView;

    private static final int REQUEST_EDIT = 1;
    private static final int REQUEST_INSERT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listaFilmes = new ArrayList<Filme>();
        listaFilmes.add(new Filme("Pulp Fiction","Ação",1999));
        listaFilmes.add(new Filme("Harry Potter","Fantasia",2001));
        listaFilmes.add(new Filme("Forma da Agua","Fantasia",2018));
        listaFilmes.add(new Filme("Senhor dos Anéis","Fantasia",2003));
        listaFilmes.add(new Filme("Três anuncios de um crime","Ação",2017));
        listaFilmes.add(new Filme("Vingadores End Game","Aventura",2019));

        recyclerView = (RecyclerView) findViewById(R.id.itemRecyclerView);

        adapter = new FilmeAdapter(listaFilmes, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new TouchHelp(adapter));
        touchHelper.attachToRecyclerView(recyclerView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserirFilme();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this,adapter.getListaFilmes().get(0).getTitulo()+" "+adapter.getListaFilmes().get(1).getTitulo()+" "+adapter.getListaFilmes().get(2).getTitulo(),Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void inserirFilme(){
        Intent intent = new Intent(this, EditFilmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("request_code", REQUEST_INSERT);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_INSERT);
    }


    @Override
    public void undo() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.constraintLayout),"Item removido.",Snackbar.LENGTH_LONG);

        snackbar.setAction("Desfazer", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.restaurar();
            }
        });
        snackbar.show();
    }

    @Override
    public void toast(Filme filme) {
        Toast.makeText(this,filme.getTitulo()+" "+filme.getGenero()+" "+filme.getAno(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void edit(int position) {
        Intent intent = new Intent(this, EditFilmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("request_code", REQUEST_EDIT);
        bundle.putSerializable("filme", adapter.getListaFilmes().get(position));
        bundle.putInt("position", position);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_EDIT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                Filme f = (Filme) bundle.getSerializable("filme");
                int position = bundle.getInt("position");
                adapter.update(f, position);
            }
        }
        if (requestCode == REQUEST_INSERT) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                Filme f = (Filme) bundle.getSerializable("filme");
                adapter.inserir(f);
            }
        }
    }
}
