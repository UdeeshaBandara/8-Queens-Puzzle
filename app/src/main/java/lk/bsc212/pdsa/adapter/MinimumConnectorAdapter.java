package lk.bsc212.pdsa.adapter;

import android.content.Context;
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

import lk.bsc212.pdsa.R;

public class MinimumConnectorAdapter extends RecyclerView.Adapter<MinimumConnectorAdapter.MinimumHolder> {


    String[] cities = {"0", "1", "2", "3", "4", "5", "6", "7", "8"};
    Context context;

    public MinimumConnectorAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public MinimumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_minimum_connector, parent, false);
        return new MinimumHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MinimumHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 9;
    }

    class MinimumHolder extends RecyclerView.ViewHolder implements
            AdapterView.OnItemSelectedListener {

        Spinner from, to;
        EditText distance;


        public MinimumHolder(@NonNull View itemView) {
            super(itemView);
            from = itemView.findViewById(R.id.from);
            distance = itemView.findViewById(R.id.distance);
            to = itemView.findViewById(R.id.to);

            from.setOnItemSelectedListener(this);
            to.setOnItemSelectedListener(this);

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, cities);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            from.setAdapter(dataAdapter);
            to.setAdapter(dataAdapter);

        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            if (adapterView == from)
                Toast.makeText(context, "from ", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "to ", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
