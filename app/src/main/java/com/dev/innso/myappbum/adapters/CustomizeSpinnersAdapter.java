package com.dev.innso.myappbum.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.utils.tags.KeyValuePair;

import java.util.List;

public class CustomizeSpinnersAdapter implements SpinnerAdapter {

    private Context mContext;
    private List<KeyValuePair> mItems;
    private String mMainItemTitle;

    public CustomizeSpinnersAdapter(Context context, List<KeyValuePair> items, String mainTitle) {
        mContext = context;
        mItems = items;
        mMainItemTitle = mainTitle;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.spinner_item_dropdown, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textViewTitle.setText(mItems.get(position).getKey());
        return convertView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.spinner_item_main, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textViewTitle.setText(mMainItemTitle);
        holder.subtitle.setText(mItems.get(position).getKey());
        return convertView;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public enum OptionTypes {
        BLACK,
        EMPTY,
        INSET
    }

    static class ViewHolder {

        private TextView textViewTitle;

        private TextView subtitle;

        public ViewHolder(View view) {
            textViewTitle = (TextView) view.findViewById(R.id.title);
            subtitle = (TextView) view.findViewById(R.id.subtitle);
        }
    }
}
