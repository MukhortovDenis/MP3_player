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
    final String LOG_TAG = "myLogs";
    private static int viewHolderCount;
    private int numberItems;


    public String dirPath = "/storage/self/primary/Music/";

    private final File[] files;

    {
        //Говна сьел, говнокод вышел
       // int i = 0;
        File file = new File(dirPath);
        File[] files = file.listFiles();
        music = files[]toString;

    }

    public NumbersAdapter(int numberOfItems) {
        numberItems = numberOfItems;
        viewHolderCount = 0;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.number_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        NumberViewHolder viewHolder = new NumberViewHolder(view);
        viewHolder.viewHolderIndex.setText("ViewHolder index" + viewHolderCount);

        viewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numberItems;
    }

    class NumberViewHolder extends RecyclerView.ViewHolder {

        TextView listItemNumberView;
        TextView viewHolderIndex;
            public NumberViewHolder(View itemView) {
                super(itemView);

                listItemNumberView = itemView.findViewById(R.id.number_item);
                viewHolderIndex = itemView.findViewById(R.id.viewHolderNumber);

            }

            void bind(int Tracks){
                listItemNumberView.setText(String.valueOf(Tracks));
            }

    }
}
