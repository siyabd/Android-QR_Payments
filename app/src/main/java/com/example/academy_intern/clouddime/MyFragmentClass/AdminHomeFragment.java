package com.example.academy_intern.clouddime.MyFragmentClass;


import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.academy_intern.clouddime.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminHomeFragment extends Fragment {


    int index[] = {1, 2 };

    PieChart chart;
    public  int Active =1,InActive=2;
    int numOfUser[] = {Active, InActive};
    String Title[] = {"Active Users", "Inactive User"};
    TextToSpeech textToSpeech;
    int result;
    String appInfo = "please click graph for more information";
    View view;


    public AdminHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_home, container, false);
        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        setPieChart();
    }
    public void init() {
        chart = getView().findViewById(R.id.chart);
    }

    public void setPieChart() {
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < numOfUser.length; i++) {
            pieEntries.add(new PieEntry(numOfUser[i], Title[i]));

        }
        //Data set object that is used to set and initialize values that
        // are going to be set on the chart
        PieDataSet dataSet = new PieDataSet(pieEntries, "Admin Stats");
        PieData data = new PieData(dataSet);

        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setSelectionShift(3f);

        //Setting data to the chart
        chart.setData(data);
        chart.animateY(3000);
        chart.setHoleRadius(7);
        chart.invalidate();


        //Listener that will handle click events on the chart
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                PieEntry pe = (PieEntry) e;
                String na = pe.getLabel();

                if (na.contains("Active Users")) {

                    //getFragmentManager().beginTransaction().add(R.id.show, new ShowEvents()).commit();

                } else if (na.contains("Approvals")) {


                } else if (na.contains("registered")) {


                }

            }

            @Override
            public void onNothingSelected() {

            }
        });


    }

}
