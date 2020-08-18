package com.example.academy_intern.clouddime;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.academy_intern.clouddime.MyFragmentClass.CreateEventsFragment;
import com.example.academy_intern.clouddime.MyFragmentClass.HistoryFragment;
import com.example.academy_intern.clouddime.MyFragmentClass.HomeFragment;
import com.example.academy_intern.clouddime.MyFragmentClass.ScanQRCodes;
import com.example.academy_intern.clouddime.MyFragmentClass.UserProfileTabs;


/*
    This fragment displays an overview of the user profile including name,points balance,number of points spent,and the upcoming events.
    The button navigation provides direct access to all the main screens of the app such as transactional history,QR scanner,create
    events,and full user profile

 */

public class HomeLoader extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BottomNavigationView bottomNav = view.findViewById(R.id.bottom_navigation);

       // navBar.inflateMenu(R.menu.bottom_navigation_view);

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getFragmentManager().beginTransaction().add(R.id.bks,new HomeFragment()).commit();

    }


   private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
       @Override
       public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()){
                case R.id.nav_home:
                    getFragmentManager().beginTransaction().replace(R.id.bks,new HomeFragment()).commit();
                    break;
                case R.id.nav_history:
                    getFragmentManager().beginTransaction().replace(R.id.bks,new HistoryFragment()).commit();
                    break;
                case R.id.nav_camera:
                    getFragmentManager().beginTransaction().replace(R.id.bks,new ScanQRCodes()).commit();

                   break;
                case R.id.nav_events:
                    getFragmentManager().beginTransaction().replace(R.id.bks,new CreateEventsFragment()).commit();

                    break;
                case R.id.nav_settings:
                    getFragmentManager().beginTransaction().replace(R.id.bks,new UserProfileTabs()).commit();

                    break;

           }

            return false;
        }
   };
}
