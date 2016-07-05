package com.dev.innso.myappbum.ui.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.dev.innso.myappbum.R;
import com.dev.innso.myappbum.adapters.CircularAdapter;
import com.dev.innso.myappbum.models.ui.DetailPhotoActivity;
import com.dev.innso.myappbum.providers.BuddiesImages;
import com.dev.innso.myappbum.utils.ExtraArgumentKeys;
import com.jpardogo.listbuddies.lib.views.ListBuddiesLayout;


public class ListBuddiesFragment extends Fragment implements ListBuddiesLayout.OnBuddyItemClickListener {

    private static final String TAG = ListBuddiesFragment.class.getSimpleName();

    private ListBuddiesLayout mListBuddies;

    int mMarginDefault;

    int[] mScrollConfig;

    private boolean isOpenActivities;

    public static ListBuddiesFragment newInstance(boolean isOpenActivitiesActivated) {

        ListBuddiesFragment fragment = new ListBuddiesFragment();

        Bundle bundle = new Bundle();

        bundle.putBoolean(ExtraArgumentKeys.OPEN_ACTIVITES.toString(), isOpenActivitiesActivated);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isOpenActivities = getArguments().getBoolean(ExtraArgumentKeys.OPEN_ACTIVITES.toString(), false);
        mMarginDefault = getResources().getDimensionPixelSize(com.jpardogo.listbuddies.lib.R.dimen.default_margin_between_lists);
        mScrollConfig = getResources().getIntArray(R.attr.scrollFaster);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_listbuddies, container, false);

        initViews(rootView);

        initList();

        mListBuddies.setOnItemClickListener(this);

        return rootView;
    }

    private void initViews(View view) {
        mListBuddies = (ListBuddiesLayout) view.findViewById(R.id.listbuddies);
    }

    private void initList() {

        CircularAdapter mAdapterLeft = new CircularAdapter(getActivity(), getResources().getDimensionPixelSize(R.dimen.item_height_small), BuddiesImages.imageUrls_left);

        CircularAdapter mAdapterRight = new CircularAdapter(getActivity(), getResources().getDimensionPixelSize(R.dimen.item_height_tall), BuddiesImages.imageUrls_right);

        mListBuddies.setAdapters(mAdapterLeft, mAdapterRight);
    }

    @Override
    public void onBuddyItemClicked(AdapterView<?> parent, View view, int buddy, int position, long id) {
        if (isOpenActivities) {
            Intent intent = new Intent(getActivity(), DetailPhotoActivity.class);
            intent.putExtra(DetailPhotoActivity.EXTRA_URL, getImage(buddy, position));
            startActivity(intent);
        } else {
            Resources resources = getResources();
            Toast.makeText(getActivity(), resources.getString(R.string.list) + ": " + buddy + " " + resources.getString(R.string.position) + ": " + position, Toast.LENGTH_SHORT).show();
        }
    }

    private String getImage(int buddy, int position) {
        return buddy == 0 ? BuddiesImages.imageUrls_left.get(position) : BuddiesImages.imageUrls_right.get(position);
    }

    public void setGap(int value) {
        mListBuddies.setGap(value);
    }


    public void setDivider(Drawable drawable) {
        mListBuddies.setDivider(drawable);
    }
}