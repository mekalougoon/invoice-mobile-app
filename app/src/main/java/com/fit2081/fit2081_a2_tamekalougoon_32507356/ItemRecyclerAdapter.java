package com.fit2081.fit2081_a2_tamekalougoon_32507356;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// creates the view holders and binds the data to them
public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder>{

    ArrayList<Item> itemDataList;

    private final int HEADER_CARD_TYPE = 0;
    private final int VALUE_CARD_TYPE = 1;

    // receives the data from outside
    public void setItemData(ArrayList<Item> itemData) {
        itemDataList = itemData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == VALUE_CARD_TYPE) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false); //CardView inflated as RecyclerView list item
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_header, parent, false); //CardView inflated as RecyclerView list item
        }

        // create a view holder and pass the view
        ViewHolder viewHolder = new ViewHolder(v);

        // return to the parent (android) which is responsible for how many and when to create
        return viewHolder;
    }

    // sets a header for the recycler by determining if it is the first one shown
    public int getItemViewType(int position) {
        if (position == 0)
            return HEADER_CARD_TYPE;
        else return VALUE_CARD_TYPE;
    }

    // return of the view data becomes the input for the bind data
    // sets the data
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (position != 0) {

            // set the values

            holder.itemNameTV.setText(itemDataList.get(position-1).getName());
            holder.itemQuantityTV.setText(String.valueOf(itemDataList.get(position-1).getQuantity()));
            holder.itemCostTV.setText(String.valueOf(itemDataList.get(position-1).getCost()));
        }

    }

    // determines the number of items
    @Override
    public int getItemCount() {
        return itemDataList.size()+1;
    }

    // creates object that holds references to the views
    public class ViewHolder extends RecyclerView.ViewHolder {

        // contains the elements
        TextView itemNameTV;
        TextView itemQuantityTV;
        TextView itemCostTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameTV = itemView.findViewById(R.id.item_card_name_id);
            itemQuantityTV = itemView.findViewById(R.id.item_card_quantity_id);
            itemCostTV = itemView.findViewById(R.id.item_card_cost_id);
        }
    }
}
