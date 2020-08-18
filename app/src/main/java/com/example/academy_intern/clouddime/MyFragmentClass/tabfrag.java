package com.example.academy_intern.clouddime.MyFragmentClass;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.academy_intern.clouddime.Objects.ViewPagerAdapter;
import com.example.academy_intern.clouddime.R;

//This fragment is created to add the slides of login and sign in
public class tabfrag extends Fragment{

    //Declarations
    View view;
    private TabLayout tabLayout;
    private ViewPager vPager;
    private AppBarLayout appBarLayout;
    private boolean isIt;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tabview, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//      Assigning the elements to the ones on the xml
        tabLayout = (TabLayout) view.findViewById(R.id.appTab);
        appBarLayout = (AppBarLayout)view. findViewById(R.id.appBar);
        vPager = (ViewPager)view. findViewById(R.id.appViewPager);

//      Adding Fragments - Slider
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.AddFragment(new UserLogin(), "Sign In");
        adapter.AddFragment(new UserRegistration(), "Sign Up");

//      Adapter Setup
        vPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(vPager);
    }
}
