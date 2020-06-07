package com.example.starwapi.presentation.view;
import java.util.List;

//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.starwapi.R;
import com.example.starwapi.presentation.model.Personnage;


public class ListeAdapter extends RecyclerView.Adapter<ListeAdapter.ViewHolder> {
    private List<Personnage> values;
    private OnItemClickListener listener;


    public ListeAdapter(List<Personnage> myDataset, OnItemClickListener listener) {
        this.values = myDataset;
        this.listener = listener;
    }
    public void setListener(OnItemClickListener listener){
        this.listener =listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Personnage perso);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
        }
    }

    public void add(int position, Personnage item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }



    // Create new views (invoked by the layout manager)
    @Override
    public ListeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Personnage perso = values.get(position);
        holder.txtHeader.setText(perso.getName());
        holder.txtFooter.setText(perso.resume());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
                listener.onItemClick(perso);
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

}