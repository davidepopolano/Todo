package com.elis.ltm.todo.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elis.ltm.todo.R;
import com.elis.ltm.todo.activities.MainActivity;
import com.elis.ltm.todo.model.Nota;

import java.util.ArrayList;

/**
 * Created by davide on 04/03/17.
 */

public class NotaAdapter extends RecyclerView.Adapter<NotaAdapter.NotaVH> {

    ArrayList<Nota> dataset = new ArrayList<>();
    int position;

    public void setDataset(ArrayList<Nota> dataset) {
        this.dataset = dataset;
        notifyDataSetChanged();
    }
    public void addNota(Nota nota){
        dataset.add(nota);
        notifyDataSetChanged();
    }
    public Nota editNota(int position, String titolo, String corpo, String data, boolean bool) {
        Nota nota = getNota(position);
        nota.setTitle(titolo);
        nota.setBody(corpo);
        nota.setExpiryDate(data);
        nota.setStatus(bool);
        notifyItemChanged(position);
        return nota;
    }
    public void removeNota(int position){
        dataset.remove(position);
        notifyItemRemoved(position);
    }

    public int getPosition() {
        return position;
    }
    public Nota getNota(int posizione){
        return dataset.get(posizione);
    }

    @Override
    public NotaVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nota, parent, false);
        return new NotaVH(v);
    }

    @Override
    public void onBindViewHolder(NotaVH holder, int position) {
        holder.title.setText(dataset.get(position).getTitle());
        holder.body.setText(dataset.get(position).getBody());
        holder.date.setText(dataset.get(position).getExpiryDate());
        holder.creationDate.setText(dataset.get(position).getCreationDate());
        if(!dataset.get(position).isStatus()){
            holder.star.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public class NotaVH extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView title, body, date, creationDate;
        ImageView star;


        public NotaVH(View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_tv);
            body = (TextView) itemView.findViewById(R.id.body_tv);
            date = (TextView) itemView.findViewById(R.id.date_tv);
            creationDate = (TextView) itemView.findViewById(R.id.creation_date_tv);
            star = (ImageView) itemView.findViewById(R.id.star);
            itemView.setOnCreateContextMenuListener(this);
//            itemView.setOnLongClickListener(this);

        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MainActivity activity = (MainActivity) v.getContext();
            MenuInflater menuInflater =  activity.getMenuInflater();
            menuInflater.inflate(R.menu.context_menu, menu);
            position = getAdapterPosition();
        }

//        @Override
//        public boolean onLongClick(View v) {
//            MainActivity  activity = (MainActivity) v.getContext();
//            activity.pippo = activity.startSupportActionMode(activity.callBack);
//            position = getAdapterPosition();
//            v.setSelected(true);
//            return true;
//        }
    }
}
