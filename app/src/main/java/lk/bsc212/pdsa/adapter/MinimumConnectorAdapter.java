package lk.bsc212.pdsa.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import lk.bsc212.pdsa.R;
import lk.bsc212.pdsa.utils.AlertDialog;

public class MinimumConnectorAdapter extends RecyclerView.Adapter<MinimumConnectorAdapter.MinimumHolder> {


    List<String> cities = new LinkedList<>(Arrays.asList("", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));

    Context context;
    int systemSelectedCity;
    ArrayAdapter<String> fromDataAdapter;
    ArrayAdapter<String> toDataAdapter;
    boolean isSpinnerTouched = false;
    int[] selectedFromCities;
    int[] selectedToCities;
    int[] selectedDistance;

    public MinimumConnectorAdapter(Context context, int systemSelectedCity, int[] selectedFromCities, int[] selectedToCities, int[] selectedDistance) {
        this.context = context;
        this.systemSelectedCity = systemSelectedCity;
        this.selectedFromCities = selectedFromCities;
        this.selectedToCities = selectedToCities;
        this.selectedDistance = selectedDistance;


        fromDataAdapter = new ArrayAdapter<>(context, R.layout.item_spinner, cities);
        fromDataAdapter.setDropDownViewResource(R.layout.item_spinner);
        toDataAdapter = new ArrayAdapter<>(context, R.layout.item_spinner, cities);
        toDataAdapter.setDropDownViewResource(R.layout.item_spinner);

        selectedFromCities[0] = systemSelectedCity;

    }


    @NonNull
    @Override
    public MinimumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_minimum_connector, parent, false);

        return new MinimumHolder(view, new AnswerEditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull MinimumHolder holder, @SuppressLint("RecyclerView") int recyclerPosition) {
        if (recyclerPosition == 0) {
            holder.from.setSelection(systemSelectedCity + 1);
            holder.from.setEnabled(false);
        }
        holder.answerEditTextListener.updatePosition(holder.getAdapterPosition());

        holder.from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!isSpinnerTouched || position == 0) return;

                int selectedFromCity = Integer.parseInt(parent.getItemAtPosition(position).toString());

                if (selectedToCities[recyclerPosition] == selectedFromCity) {
                    parent.setSelection(0);
                    selectedFromCities[recyclerPosition] = -1;
                    new AlertDialog().negativeAlert(context, "Invalid Input", "This city selected as destination", "OK");
                } else
                    selectedFromCities[recyclerPosition] = selectedFromCity;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        holder.to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!isSpinnerTouched || position == 0) return;

                int selectedCity = Integer.parseInt(parent.getItemAtPosition(position).toString());

                if (systemSelectedCity == selectedCity) {
                    parent.setSelection(0);
                    selectedToCities[recyclerPosition] = -1;
                    new AlertDialog().negativeAlert(context, "Invalid Move!!!", "Sorry, The starting city cannot be selected again as choice", "OK");
                } else if (Arrays.stream(selectedToCities).anyMatch(i -> i == selectedCity)) {
                    parent.setSelection(0);
                    selectedToCities[recyclerPosition] = -1;
                    new AlertDialog().negativeAlert(context, "Invalid Move!!!", "This city is already selected as a reaching city!!!", "OK");
                } else if (selectedFromCities[recyclerPosition] == selectedCity) {
                    parent.setSelection(0);
                    selectedToCities[recyclerPosition] = -1;
                    new AlertDialog().negativeAlert(context, "Invalid Move!!!", "Sorry, The starting city cannot be selected again as choice", "OK");
                } else
                    selectedToCities[recyclerPosition] = selectedCity;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return 9;
    }

    class MinimumHolder extends RecyclerView.ViewHolder {

        Spinner from, to;
        EditText distance;
        AnswerEditTextListener answerEditTextListener;

        public MinimumHolder(@NonNull View itemView, AnswerEditTextListener answerEditTextListener) {
            super(itemView);
            from = itemView.findViewById(R.id.from);
            distance = itemView.findViewById(R.id.distance);
            to = itemView.findViewById(R.id.to);
            this.answerEditTextListener = answerEditTextListener;
            distance.addTextChangedListener(answerEditTextListener);


            to.setAdapter(toDataAdapter);
            from.setAdapter(fromDataAdapter);


            from.setOnTouchListener((v, event) -> {
                isSpinnerTouched = true;
                return false;
            });
            to.setOnTouchListener((v, event) -> {
                isSpinnerTouched = true;
                return false;
            });

        }
    }


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
            if (charSequence.length() > 0)
                selectedDistance[position] = Integer.parseInt(charSequence.toString());

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
