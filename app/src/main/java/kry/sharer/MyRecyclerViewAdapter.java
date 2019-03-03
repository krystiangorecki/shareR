package kry.sharer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context ctx;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.ctx = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = mData.get(position);
        holder.labelNumber.setText(animal);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addItem(String number) {
        mData.add(number);
        notifyDataSetChanged();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView labelNumber;
        Button buttonR;
        Button buttonG;

        ViewHolder(View itemView) {
            super(itemView);
            labelNumber = itemView.findViewById(R.id.labelNumber);
            buttonR = itemView.findViewById(R.id.buttonR);
            buttonG = itemView.findViewById(R.id.buttonG);
            buttonR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String number = mData.get(getAdapterPosition());
                    new PageVisitor(ctx).visitSearchPageR(number);
                }
            });
            buttonG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String number = mData.get(getAdapterPosition());
                    new PageVisitor(ctx).visitSearchPageG(number);
                }
            });
            labelNumber.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}