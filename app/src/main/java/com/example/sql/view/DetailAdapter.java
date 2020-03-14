package com.example.sql.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sql.R;
import com.example.sql.model.Detail;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailHolder> {

    private List<Detail> details;
    private LayoutInflater layoutInflater;
    private OnDetailItemClickListener onDetailItemClickListener;

    public DetailAdapter(Context context,List<Detail> details) {
        layoutInflater = LayoutInflater.from(context);
        this.details = details;
    }

    @NonNull
    @Override
    public DetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.details_adapter,parent,false);
        return new DetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DetailHolder holder, final int position) {
        Detail detail = details.get(position);
        holder.txtDay.setText(detail.getDay());
        holder.txtNumber.setText(detail.getNumber());
        holder.txtContent.setText(detail.getContent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDetailItemClickListener.OnItemClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    class DetailHolder extends RecyclerView.ViewHolder {
        private TextView txtDay;
        private TextView txtNumber;
        private TextView txtContent;
        public DetailHolder(@NonNull View itemView) {
            super(itemView);
            txtDay = itemView.findViewById(R.id.txtDay);
            txtNumber = itemView.findViewById(R.id.txtNumber);
            txtContent = itemView.findViewById(R.id.txtContent);
        }
    }

    public void setOnDetailItemClickListener(OnDetailItemClickListener onDetailItemClickListener) {
        this.onDetailItemClickListener = onDetailItemClickListener;
    }

    public interface OnDetailItemClickListener {
        void OnItemClicked(int position);
    }
}
