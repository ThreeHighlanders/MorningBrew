package com.example.morningbrew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseFile;

import java.util.List;
import java.util.Objects;

public class BrewAdapter extends RecyclerView.Adapter<BrewAdapter.ViewHolder> {

    private Context context;
    List<Brew> brews;

    public BrewAdapter(Context context, List<Brew> brews) {
        this.context = context;
        this.brews = brews;
    }

    @NonNull
    @Override
    public BrewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_brew, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Brew brew = brews.get(position);
        holder.bind(brew);
    }

    @Override
    public int getItemCount() {
        return brews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHigh;
        private TextView tvLow;
        private TextView tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHigh = itemView.findViewById(R.id.tvHigh);
            tvLow = itemView.findViewById(R.id.tvLow);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }

        public void bind(Brew brew) {
            tvDescription.setText(brew.getDescription());
            tvHigh.setText("High: "+Integer.toString(brew.getHigh())+"°F");
            tvLow.setText("Low: "+Integer.toString(brew.getLow())+"°F");
        }
    }
}
