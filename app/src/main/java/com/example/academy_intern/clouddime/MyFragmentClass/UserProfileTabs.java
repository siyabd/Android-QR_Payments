package com.example.academy_intern.clouddime.MyFragmentClass;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.academy_intern.clouddime.FragmentAchievementsActivity;
import com.example.academy_intern.clouddime.Objects.ViewPagerAdapter;
import com.example.academy_intern.clouddime.R;

/*This fragment is created to add the slides of the business profile, achievements and connections */

public class UserProfileTabs extends Fragment {

    //Declarations
    View view;
    private TabLayout tabLayout1;
    private ViewPager vPager1;
    private AppBarLayout appBarLayout1;
    private boolean isIt;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_tabview, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        tabLayout1 = (TabLayout) view.findViewById(R.id.appTab1);
        //appBarLayout1 = (AppBarLayout)view. findViewById(R.id.appBar1);
        vPager1 = (ViewPager)view. findViewById(R.id.appViewPager1);

//      Adding Fragments - Slider
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.AddFragment(new UserLogin(), "Business");
        adapter.AddFragment(new CreateAchievementsFragment(), "Achievements");
        adapter.AddFragment(new CreateConnectionsFragment(), "Connections");

//      Adapter Setup
        vPager1.setAdapter(adapter);
        tabLayout1.setupWithViewPager(vPager1);
    }
}