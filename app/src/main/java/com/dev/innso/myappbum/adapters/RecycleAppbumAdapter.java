package com.dev.innso.myappbum.adapters;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.models.Appbum;
import com.dev.innso.myappbum.models.Intender;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecycleAppbumAdapter extends RecyclerView.Adapter<RecycleAppbumAdapter.DataViewHolder> {


    private ArrayList<Appbum> visibleItems;
    private ArrayList<Appbum> allItems;
    private int lastPosition = -1;
    private Context mContext;

    public RecycleAppbumAdapter(ArrayList<Appbum> data, Context context) {
        super();
        mContext = context;

        setData(data);
    }

    public void setData(ArrayList data) {
        allItems = data;

        visibleItems = new ArrayList<>();

        visibleItems.addAll(allItems);
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_appbum_list, viewGroup, false);

        DataViewHolder tvh = new DataViewHolder(itemView, mContext);

        itemView.setOnClickListener(tvh);

        return tvh;
    }

    @Override
    public void onBindViewHolder(DataViewHolder data, int i) {
        Appbum item = visibleItems.get(i);
        data.bindItem(item);
        setAnimation(data.getcontainer(), i);
    }


    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.enter_list_item);
            animation.setDuration(animation.getDuration() + (position * 33));
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return visibleItems.size();
    }

    public void setFilter(String queryText) {

        visibleItems = new ArrayList<Appbum>();

        for (Appbum item : allItems) {

            if (item.getName().toLowerCase().contains(queryText)) {
                visibleItems.add(item);
            }
        }
        notifyDataSetChanged();
    }


    public static class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RelativeLayout layoutContainer;

        private ImageView imageView;
        private TextView txtTitle;

        private Context mContext;
        private Appbum appbum;

        public DataViewHolder(View itemView, Context context) {
            super(itemView);
            mContext = context;
            layoutContainer = (RelativeLayout) itemView.findViewById(R.id.layout_item_appbum_container);
            imageView = (ImageView) itemView.findViewById(R.id.imagelist_img);
            txtTitle = (TextView) itemView.findViewById(R.id.imagelist_title);
        }

        public void bindItem(Appbum item) {
            appbum = item;
            txtTitle.setText(appbum.getName().toUpperCase());
            Picasso.with(mContext).load(appbum.getUrlCover()).placeholder(R.drawable.bg_appbum_load).into(imageView);
        }

        @Override
        public void onClick(View v) {
            Intent intent = Intender.createIntent(appbum, mContext);
            intent.putExtra("isPrivate", appbum.isPrivate());
            intent.putExtra("Pass", appbum.getPassNumber());
            mContext.startActivity(intent);
        }

        public ViewGroup getcontainer() {
            return layoutContainer;
        }
    }

}
