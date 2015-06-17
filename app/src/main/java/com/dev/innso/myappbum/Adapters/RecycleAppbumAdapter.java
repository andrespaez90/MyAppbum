package com.dev.innso.myappbum.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.innso.myappbum.Models.ItemImageList;
import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.UI.BuddiesActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by INNSO SAS on 13/05/2015.
 */
public class RecycleAppbumAdapter extends RecyclerView.Adapter<RecycleAppbumAdapter.DataViewHolder>
                            implements  View.OnClickListener{


    private ArrayList<ItemImageList> visibleItems;
    private ArrayList<ItemImageList> allItems;
    private Context mContext;

    public RecycleAppbumAdapter(ArrayList<ItemImageList> data, Context context){
        mContext = context;
        allItems = data;
        flushFilter();
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.image_list, viewGroup, false);

        itemView.setOnClickListener(this);
        DataViewHolder tvh = new DataViewHolder(itemView,mContext);

        return tvh;
    }

    @Override
    public void onBindViewHolder(DataViewHolder data, int i) {
        ItemImageList item = visibleItems.get(i);
        data.bindItem(item);
    }

    @Override
    public int getItemCount() {
        return visibleItems.size();
    }

    public void flushFilter(){
        visibleItems = new ArrayList<ItemImageList>();
        visibleItems.addAll(allItems);
        notifyDataSetChanged();
    }

    public void setFilter(String queryText) {

        visibleItems = new ArrayList<ItemImageList>();
        for (ItemImageList item: allItems) {
            if (item.getNameAlbum().toLowerCase().contains(queryText))
                visibleItems.add(item);
        }
        notifyDataSetChanged();
    }



    @Override
    public void onClick(View v) {
        TextView tv = (TextView)v.findViewById(R.id.imagelist_title);
        Intent intent = new Intent(mContext, BuddiesActivity.class);
        intent.putExtra("APPBUM_NAME", tv.getText().toString());
        mContext.startActivity(intent);
    }


    public static class DataViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView txtTitle;
        private int mRowHeight;
        private Context mContext;

        public DataViewHolder(View itemView, Context context) {
            super(itemView);
            mRowHeight = 100;
            mContext = context;
            imageView = (ImageView)itemView.findViewById(R.id.imagelist_img);
            txtTitle = (TextView)itemView.findViewById(R.id.imagelist_title);
        }

        public void bindItem(ItemImageList item) {
            txtTitle.setText(item.getNameAlbum());
            Picasso.with(mContext).load(item.getUrlImg()).placeholder(R.drawable.bg_appbum_load).into(imageView);
        }
    }


}
