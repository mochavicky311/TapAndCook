package com.example.user.tapandcook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//adapter called by recycler view to create new item in the forms of viewHolders
public class bookmarkadapter extends RecyclerView.Adapter<bookmarkadapter.myViewHolder> {

    private ArrayList<Recipe> favouriteList;
    private ItemClickListener listener;

    public bookmarkadapter(ArrayList<Recipe> favouriteList, ItemClickListener listener){

        this.favouriteList = favouriteList;
        this.listener = listener;
    }


    @Override
    public bookmarkadapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view by inflate the bookmark_item layout

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookmarkitem, parent, false);
        myViewHolder viewHolder = new myViewHolder(v);

        return viewHolder;
    }

    //binding data to the view holder
    @Override
    public void onBindViewHolder( myViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }

    public interface ItemClickListener{
        void onListenItemClick(int clickedItemPosition);
    }


    //view holder cache references to the views that will be modified in the adapter
    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView title;

        public myViewHolder(View v){
            super(v);


            image=v.findViewById(R.id.image2);
            title = v.findViewById(R.id.title2);
            v.setOnClickListener(this); //data is bind into the bookmark item view
        }

        //load image and set title
        void bind(int position){
            Picasso.get().load(favouriteList.get(position).getImageURL()).into(image);
            title.setText(favouriteList.get(position).getTitle());
        }

        @Override
        public void onClick(View v){
            int clickedPosition = getAdapterPosition();
            listener.onListenItemClick(clickedPosition);
        }
    }

}
