package com.example.academy_intern.clouddime.MyFragmentClass;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.academy_intern.clouddime.Objects.Album;
import com.example.academy_intern.clouddime.Objects.AlbumsAdapter;
import com.example.academy_intern.clouddime.R;
import com.example.academy_intern.clouddime.classes.StorageSharedPreference;
import com.example.academy_intern.clouddime.classes.UserInfo;

import java.util.ArrayList;

/**
 * Home fragment to display all the events
 */
public class HomeFragment extends Fragment {

    TextView pointsText, nameText, spentText, dateText;
    View view;
    StorageSharedPreference storageSharedPreference;
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;

    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate layout
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //methods to initialize declared variables
        init();
        storageSharedPreference = new StorageSharedPreference(getActivity());
        //change the points of the user points txt
        getPoints();

        //update the adapter
        adapterSetUp();
    }

    //methods to initialize declared variables
    public void init() {
        pointsText = (TextView) view.findViewById(R.id.txtPoint);
        spentText = (TextView) view.findViewById(R.id.txtEvent_date);
        nameText = (TextView) view.findViewById(R.id.txtUser_name);
        dateText = (TextView) view.findViewById(R.id.txtEvent_date);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(5), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    //update the adapter
    public void adapterSetUp() {

        ArrayList<Album> albumList = storageSharedPreference.sharedPreferenceEvents();
        adapter = new AlbumsAdapter(getActivity(), albumList);
        recyclerView.setAdapter(adapter);
    }

    //change the points of the user points txt
    void getPoints() {
        UserInfo userInfo = storageSharedPreference.sharedPreferenceProfile();
        String name = userInfo.getName();
        double pointsSpent = userInfo.getPointsSpent();
        double pointsBalance = userInfo.getPointsBalance();

        pointsText.setText(String.valueOf(pointsBalance));
        nameText.setText(userInfo.getName());
        spentText.setText(String.valueOf(pointsSpent));
    }


    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}
