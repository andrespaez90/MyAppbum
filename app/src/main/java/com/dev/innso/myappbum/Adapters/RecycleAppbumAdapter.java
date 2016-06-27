package com.dev.innso.myappbum.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.models.Appbum;
import com.dev.innso.myappbum.models.Intender;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecycleAppbumAdapter extends RecyclerView.Adapter<RecycleAppbumAdapter.DataViewHolder> {


    private ArrayList<Appbum> visibleItems;
    private ArrayList<Appbum> allItems;
    private Context mContext;

    public RecycleAppbumAdapter(ArrayList<Appbum> data, Context context){

        super();
        mContext = context;

        allItems = data;

        visibleItems = new ArrayList<>();

        visibleItems.addAll(allItems);
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_appbum_list, viewGroup, false);

        DataViewHolder tvh = new DataViewHolder(itemView,mContext);

        itemView.setOnClickListener(tvh);

        return tvh;
    }

    @Override
    public void onBindViewHolder(DataViewHolder data, int i) {
        Appbum item = visibleItems.get(i);
        data.bindItem(item);
    }

    @Override
    public int getItemCount() {
        return visibleItems.size();
    }

    public void setFilter(String queryText) {

        visibleItems = new ArrayList<Appbum>();
        for (Appbum item: allItems) {
            if (item.getName().toLowerCase().contains(queryText))
                visibleItems.add(item);
        }
        notifyDataSetChanged();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        private ImageView imageView;
        private TextView txtTitle;
        private int mRowHeight;

        private Context mContext;
        private Appbum appbum;

        public DataViewHolder(View itemView, Context context) {
            super(itemView);
            mRowHeight = 100;
            mContext = context;
            imageView = (ImageView)itemView.findViewById(R.id.imagelist_img);
            txtTitle = (TextView)itemView.findViewById(R.id.imagelist_title);
        }

        public void bindItem(Appbum item) {
            appbum = item;
            txtTitle.setText(appbum.getName().toUpperCase());
            Picasso.with(mContext).load(appbum.getUrlCover()).placeholder(R.drawable.bg_appbum_load).into(imageView);
        }

        @Override
        public void onClick(View v) {
                Intent intent = Intender.createIntent(appbum, mContext);
                intent.putExtra("isPrivate",appbum.isPrivate());
                intent.putExtra("Pass",appbum.getPassNumber());
                mContext.startActivity(intent);
        }
    }

}
