package com.example.ansha.bgms;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class BirthdayRecyclerViewAdapter extends RecyclerView.Adapter<BirthdayRecyclerViewAdapter.BirthdayRecyclerViewHolder> {

    private String[] data;
    public BirthdayRecyclerViewAdapter(String[] data) {
        this.data = data;
    }
    @NonNull
    @Override
    public BirthdayRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.list_item_layout, viewGroup, false);
        return new BirthdayRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BirthdayRecyclerViewHolder birthdayRecyclerViewHolder, int i) {

        String title = data[i];
        birthdayRecyclerViewHolder._birthday_name_text.setText(title);

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class BirthdayRecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView _birthday_profile_img;
        TextView _birthday_name_text;
        TextView _birthday_date_text;

        public BirthdayRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            _birthday_profile_img = itemView.findViewById(R.id.birthday_profile_img);
            _birthday_date_text = itemView.findViewById(R.id.birthday_date_text);
            _birthday_name_text = itemView.findViewById(R.id.birthday_name_text);
        }
    }
}
