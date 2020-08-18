package com.example.academy_intern.clouddime.Objects;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.academy_intern.clouddime.MyFragmentClass.AdminHomeFragment;
import com.example.academy_intern.clouddime.MyFragmentClass.AdminQRFragment;
import com.example.academy_intern.clouddime.MyFragmentClass.ApproveFragment;
import com.example.academy_intern.clouddime.MyFragmentClass.CreateEventsFragment;
import com.example.academy_intern.clouddime.MyFragmentClass.HistoryFragment;
import com.example.academy_intern.clouddime.MyFragmentClass.HomeFragment;
import com.example.academy_intern.clouddime.MyFragmentClass.ScanQRCodes;
import com.example.academy_intern.clouddime.MyFragmentClass.ShowEvents;
import com.example.academy_intern.clouddime.MyFragmentClass.UserProfileTabs;
import com.example.academy_intern.clouddime.MyFragmentClass.UserRegistration;
import com.example.academy_intern.clouddime.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminLoader extends Fragment {

    View view ;

    public AdminLoader() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank_admin, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        BottomNavigationView bottomNav = view.findViewById(R.id.bottom_navigation_admin);


        bottomNav.setOnNavigationItemSelectedListener(navListenerAdmin);

        getFragmentManager().beginTransaction().add(R.id.bks_admin,new AdminHomeFragment()).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListenerAdmin = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.nav_adminhome:
                    getFragmentManager().beginTransaction().replace(R.id.bks_admin,new AdminHomeFragment()).commit();
                    break;
                case R.id.nav_allEvents:
                    getFragmentManager().beginTransaction().replace(R.id.bks_admin,new ShowEvents()).commit();
                    break;
                case R.id.nav_ApproveUsers:
                    getFragmentManager().beginTransaction().replace(R.id.bks_admin,new ApproveFragment()).commit();
                    break;
                case R.id.nav_QRcode:
                    getFragmentManager().beginTransaction().replace(R.id.bks_admin,new AdminQRFragment()).commit();
                    break;
                case R.id.nav_registerAdmin:
                    getFragmentManager().beginTransaction().replace(R.id.bks_admin,new UserRegistration()).commit();
            }
            return false;
        }
    };
}
