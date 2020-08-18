package com.example.academy_intern.clouddime.MyFragmentClass;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.academy_intern.clouddime.R;

/**
 * A simple {@link Fragment} subclass. Triggers an alert dialog
 */
public class SuccessfulPayFragment extends Fragment {


    public SuccessfulPayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_successful_transaction, container, false);
    }

}
