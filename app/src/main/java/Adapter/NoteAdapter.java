package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectpam.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import Model.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{
    private List<Note> list;

    public NoteAdapter(List<Note> ItemList){
        this.list = ItemList;
    }

    public void setItemList(List<Note> ListItem){
        this.list = ListItem;
    }

    DatabaseReference dbReference;
    FirebaseDatabase database;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_data, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_judul.setText(list.get(position).getJudul());
        holder.tv_isi.setText(list.get(position).getIsi());
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_judul, tv_isi;

        Button bt_delete, bt_update, bt_bookmark;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            database = FirebaseDatabase.getInstance();
            tv_judul = itemView.findViewById(R.id.card_nama);
            bt_delete = itemView.findViewById(R.id.deleteButton);
            bt_update = itemView.findViewById(R.id.updateButton);
            bt_bookmark = itemView.findViewById(R.id.bookmark);
            bt_delete.setOnClickListener(this);
            bt_update.setOnClickListener(this);
            bt_bookmark.setOnClickListener(this);
            dbReference = database.getReference().child(Note.class.getSimpleName());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(bt_delete.getId()==v.getId()){
                dbReference.child(list.get(position).getKey()).removeValue();
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());
                notifyDataSetChanged();
            }
            else if (bt_update.getId()==v.getId()){
                Intent update = new Intent(v.getContext(), UpdateNote.class);

            }
        }
    }
}
