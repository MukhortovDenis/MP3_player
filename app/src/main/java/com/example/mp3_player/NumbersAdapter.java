package com.example.mp3_player;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public class NumbersAdapter extends RecyclerView.Adapter<NumbersAdapter.NumberViewHolder>{

    protected String[] listFiles;
    protected UpdateTrack updateTrack;

    public NumbersAdapter(String[] listFiles, UpdateTrack context) {
        this.listFiles = listFiles;
        this.updateTrack = context;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.number_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new NumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return listFiles.length;
    }

    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Статичные объекты
        TextView listItemNumberView;
        //Название трека
        TextView viewHolderIndex;
            NumberViewHolder(View itemView) {
                super(itemView);
                listItemNumberView = itemView.findViewById(R.id.number_item);
                viewHolderIndex = itemView.findViewById(R.id.viewHolderNumber);
                itemView.setOnClickListener(this);
            }

            void bind(int position){
                listItemNumberView.setText(position + 1);
                viewHolderIndex.setText(listFiles[position]);
            }

        @Override
        public void onClick(View v) {
                updateTrack.update(listFiles[getAdapterPosition()]);
        }
    }
}
