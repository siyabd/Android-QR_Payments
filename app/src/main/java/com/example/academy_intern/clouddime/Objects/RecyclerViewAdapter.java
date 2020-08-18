package com.example.academy_intern.clouddime.Objects;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.academy_intern.clouddime.R;
import com.example.academy_intern.clouddime.classes.Events;

import java.text.BreakIterator;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<TransactionalHistory> mData;


    public RecyclerViewAdapter(Context mContext, List<TransactionalHistory> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //return null;
        View view= LayoutInflater.from(mContext).inflate(R.layout.transactionlist,viewGroup,false);
        MyViewHolder vHolder = new MyViewHolder(view);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder  holder, int position) {
        holder.txt_name.setText(mData.get(position).getLocation());
        holder.txt_day.setText(mData.get(position).getDate().toString());
        holder.txt_points.setText(mData.get(position).getPoints());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position, @NonNull List<Object> payloads) {
        holder.txt_name.setText(mData.get(position).getLocation());
        holder.txt_day.setText(mData.get(position).getDate().toString());
        holder.txt_points.setText(mData.get(position).getPoints());
       // holder.img.setImageResource(mData.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        // public EditText tv_name;
        private TextView txt_name;
        private TextView txt_day;
        private TextView txt_month;
        private TextView txt_points;
        private ImageView img;


        public MyViewHolder(View itemView) {

            super(itemView);
            txt_name = (TextView) itemView.findViewById(R.id.name_contact);
            txt_day = (TextView) itemView.findViewById(R.id.occupation_name);
            txt_points=(TextView) itemView.findViewById(R.id.txtPoints);
           // img = (ImageView) itemView.findViewById(R.id.image_contact);

        }

    }
}
