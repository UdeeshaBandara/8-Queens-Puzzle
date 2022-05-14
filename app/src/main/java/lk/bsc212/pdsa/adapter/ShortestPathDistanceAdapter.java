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

import java.util.ArrayList;

import lk.bsc212.pdsa.R;
import lk.bsc212.pdsa.model.PlacePredict;

public class ShortestPathDistanceAdapter extends RecyclerView.Adapter<ShortestPathDistanceAdapter.DistanceHolder> {

    ArrayList<PlacePredict> predictedDistance;

    Context context;

    public ShortestPathDistanceAdapter(Context context) {
        this.context = context;
    }

    public void setPredictedDistance(ArrayList<PlacePredict> predictedDistance) {
        this.predictedDistance = predictedDistance;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DistanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);
        return new DistanceHolder(view, new AnswerEditTextListener());

    }

    @Override
    public void onBindViewHolder(@NonNull DistanceHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.answerEditTextListener.updatePosition(holder.getAdapterPosition());
        holder.city.setText(String.valueOf(predictedDistance.get(position).getCityName()));

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
        AnswerEditTextListener answerEditTextListener;


        public DistanceHolder(@NonNull View itemView, AnswerEditTextListener answerEditTextListener) {
            super(itemView);
            city = itemView.findViewById(R.id.city);
            distance = itemView.findViewById(R.id.distance);
            root = itemView.findViewById(R.id.root);
            this.answerEditTextListener = answerEditTextListener;
            distance.addTextChangedListener(answerEditTextListener);
        }
    }

    //Customize text change listener to enhance performance instead of adding inside the onBind
    private class AnswerEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            predictedDistance.get(position).setPredictedDistance(Integer.parseInt(charSequence.toString()));

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
