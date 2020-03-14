package com.example.sql.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sql.R;
import com.example.sql.model.User;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListHolder> {

    private List<User> users;
    private LayoutInflater layoutInflater;
    private OnUserItemClickListener onUserItemClickListener;

    public UserListAdapter(Context context,List<User> users) {
        layoutInflater = LayoutInflater.from(context);
        this.users = users;
    }

    @NonNull
    @Override
    public UserListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.user_list_adapter,parent,false);
        return new UserListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserListHolder holder, int position) {
        User user = users.get(position);
        holder.txtName.setText(user.getUserName());
        holder.txtPhone.setText(user.getPhone());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserItemClickListener.OnItemClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserListHolder extends RecyclerView.ViewHolder{
        private TextView txtName;
        private TextView txtPhone;
        public UserListHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtPhone = itemView.findViewById(R.id.txtPhone);
        }
    }

    public void setOnUserItemClickListener(OnUserItemClickListener onUserItemClickListener) {
        this.onUserItemClickListener = onUserItemClickListener;
    }

    public interface OnUserItemClickListener {
        void OnItemClicked(int position);
    }
}
