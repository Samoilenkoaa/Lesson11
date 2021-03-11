package com.example.lesson11.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson11.NoteDataClass;
import com.example.lesson11.R;

import java.util.ArrayList;
import java.util.List;


public class MyNoteDataClassRecyclerViewAdapter extends RecyclerView.Adapter<MyNoteDataClassRecyclerViewAdapter.ViewHolder> {

    private List<NoteDataClass> mValues;
    private OnItemClickListener itemClickListener;

    public MyNoteDataClassRecyclerViewAdapter(ArrayList<NoteDataClass> items) {
        mValues = items;
    }

    public NoteDataClass getNote(int position) {
        return mValues.get(position);
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public void submitList(List<NoteDataClass> items) {
        mValues = items;
        notifyDataSetChanged();
    }

    // Интерфейс для обработки нажатий, как в ListView
    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(mValues.get(position).getName());
        holder.date.setText(mValues.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView date;


        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.item_name);
            date = view.findViewById(R.id.item_date);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
            view.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
                menu.add("Delete");

            });

        }

    }
}