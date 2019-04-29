package br.com.recyclerviewaula;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;

public class FilmeAdapter extends RecyclerView.Adapter {

    private List<Filme> listaFilmes;
    private Actions actions;
    private int posicaoRemovidoRecentemente;
    private Filme filmeRemovidoRecentemente;

    public FilmeAdapter(List<Filme> listaFilmes, Actions actions) {
        this.listaFilmes = listaFilmes;
        this.actions = actions;
    }

    public List<Filme> getListaFilmes() {
        return listaFilmes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);

        FilmeViewHolder holder = new FilmeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        FilmeViewHolder holder = (FilmeViewHolder) viewHolder;
        holder.tituloTextView.setText(listaFilmes.get(i).getTitulo());
        holder.generoTextView.setText(listaFilmes.get(i).getGenero());
        holder.anoTextView.setText(String.valueOf(listaFilmes.get(i).getAno()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),"Você clicou no filme "+listaFilmes.get(i).getTitulo(),Toast.LENGTH_SHORT).show();
                //click.edit(i);
                actions.toast(listaFilmes.get(viewHolder.getAdapterPosition()));
            }
        });

        /*holder.anoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remover(i);
            }
        });

        holder.generoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGenero("Terror",i);
            }
        });*/
    }

    public void remover(int position){
        posicaoRemovidoRecentemente = position;
        filmeRemovidoRecentemente = listaFilmes.get(position);


        listaFilmes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,this.getItemCount());
        actions.undo();
    }

    public void restaurar(){
        listaFilmes.add(posicaoRemovidoRecentemente,filmeRemovidoRecentemente);
        notifyItemInserted(posicaoRemovidoRecentemente);
    }

    public void inserir(Filme filme){
        listaFilmes.add(filme);
        notifyItemInserted(getItemCount());
    }

    public void mover(int fromPosition, int toPosition){
        if (fromPosition < toPosition)
            for (int i = fromPosition; i < toPosition; i++)
                Collections.swap(listaFilmes, i, i+1);
        else
            for (int i = fromPosition; i > toPosition; i--)
                Collections.swap(listaFilmes, i, i-1);
        notifyItemMoved(fromPosition,toPosition);
    }

    public void updateTitle(String newTitle, int position){
        listaFilmes.get(position).setTitulo(newTitle);
        notifyItemChanged(position);
    }

    public void updateGenero(String newGenero, int position){
        listaFilmes.get(position).setGenero(newGenero);
        notifyItemChanged(position);
    }

    public void updateAno (int newAno, int position){
        listaFilmes.get(position).setAno(newAno);
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return listaFilmes.size();
    }

    public static class FilmeViewHolder extends RecyclerView.ViewHolder {

        TextView tituloTextView;
        TextView generoTextView;
        TextView anoTextView;

        public FilmeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setTag(this);
            tituloTextView = (TextView) itemView.findViewById(R.id.tituloTextView);
            generoTextView = (TextView) itemView.findViewById(R.id.generoTextView);
            anoTextView = (TextView) itemView.findViewById(R.id.anoTextView);
        }

    }

}
