package lk.bsc212.pdsa.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

import lk.bsc212.pdsa.R;
import lk.bsc212.pdsa.model.PlacePredict;

public class ShortestPathDistanceAdapter extends RecyclerView.Adapter<ShortestPathDistanceAdapter.DistanceHolder> {

    LinkedList<PlacePredict> predictedDistance;

    Context context;

    public ShortestPathDistanceAdapter(Context context ) {
        this.context = context;
    }

    public void setPredictedDistance(LinkedList<PlacePredict> predictedDistance) {
        this.predictedDistance = predictedDistance;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DistanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);

        return new DistanceHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull DistanceHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.city.setText(String.valueOf(predictedDistance.get(position).getCityName()));

        holder.distance.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                predictedDistance.get(position).setPredictedDistance(Double.parseDouble(s.toString()));

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
        holder.root.setOnClickListener(view -> {


        });
    }

    @Override
    public int getItemCount() {
        return predictedDistance.size();
    }

    class DistanceHolder extends RecyclerView.ViewHolder {

        TextView city;
        EditText distance;
        LinearLayout root;


        public DistanceHolder(@NonNull View itemView) {
            super(itemView);
            city = itemView.findViewById(R.id.city);
            distance = itemView.findViewById(R.id.distance);
            root = itemView.findViewById(R.id.root);
        }
    }
}
