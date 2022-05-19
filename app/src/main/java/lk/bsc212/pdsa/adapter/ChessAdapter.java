package lk.bsc212.pdsa.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import lk.bsc212.pdsa.QueenPuzzle;
import lk.bsc212.pdsa.R;
import lk.bsc212.pdsa.utils.AlertDialog;

public class ChessAdapter extends RecyclerView.Adapter<ChessAdapter.ChessCellHolder> {

    ArrayList<String> selectedPlaces;

    Context context;

    public ChessAdapter(Context context,ArrayList<String> selectedPlaces) {
        this.context = context;
        this.selectedPlaces = selectedPlaces;
    }

    public void updatePlaces(ArrayList<String> selectedPlaces){
        this.selectedPlaces = selectedPlaces;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChessCellHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chess_cell, parent, false);

        return new ChessCellHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ChessCellHolder holder, @SuppressLint("RecyclerView") int position) {

        if (selectedPlaces.get(position).equals("1"))

            holder.queen.setVisibility(View.VISIBLE);

        else
            holder.queen.setVisibility(View.GONE);


        if ((position > 7 && position < 16) || (position > 23 && position < 32) || (position > 39 && position < 48) || (position > 55 && position < 64)) {
            if (position % 2 != 0)
                holder.root.setBackgroundColor(Color.parseColor("#FFFFFF"));
            else
                holder.root.setBackgroundColor(Color.parseColor("#B5000000"));

        } else {

            if (position % 2 == 0)
                holder.root.setBackgroundColor(Color.parseColor("#FFFFFF"));
            else
                holder.root.setBackgroundColor(Color.parseColor("#B5000000"));
        }


        holder.root.setOnClickListener(view -> {

            if (Collections.frequency(selectedPlaces, "1") == 8 && selectedPlaces.get(position).equals("0")) {

                new AlertDialog().negativeAlert(context, "Invalid Input", "Cannot select more than 8 places", "OK");
                return;

            }

            if (selectedPlaces.get(position).equals("0"))
                selectedPlaces.set(position, "1");
            else
                selectedPlaces.set(position, "0");

            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return 64;
    }

    class ChessCellHolder extends RecyclerView.ViewHolder {

        ImageView queen;
        RelativeLayout root;


        public ChessCellHolder(@NonNull View itemView) {
            super(itemView);
            queen = itemView.findViewById(R.id.queen);
            root = itemView.findViewById(R.id.root);
        }
    }

}
