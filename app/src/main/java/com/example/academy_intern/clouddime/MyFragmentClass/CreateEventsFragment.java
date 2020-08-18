package com.example.academy_intern.clouddime.MyFragmentClass;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.academy_intern.clouddime.R;
import com.example.academy_intern.clouddime.classes.ApiUrls;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/*
    This fragment allows users to create new upcoming events, adding the event title, event dates, start time, end time, branch and points.
    The button buttonGenerate enables generation of the QR code of the event and button createEvent confirms the creation

 */
public class CreateEventsFragment extends Fragment {

    //Declarations
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog timeSetListener;
    private  TimePickerDialog timePickerDialog;
    private View view;
    private EditText userId;
    private EditText title;
    private EditText event_dates;
    private Spinner mySpinner;
    private Spinner start_time;
    private Spinner end_time;
    private SeekBar points_seekBar;
    private Button buttonGenerate;
    private Button createEvent;

    public CreateEventsFragment() {

    }

    //finding the components
    public void init()
    {

        //assigning the elements to the one on the xml
        mySpinner = view.findViewById(R.id.branch);
        title = view.findViewById(R.id.title);
        event_dates = view.findViewById(R.id.event_dates);
        start_time = view.findViewById(R.id.start_time);
        end_time = view.findViewById(R.id.end_time);
       // buttonGenerate = view.findViewById(R.id.generateQR);
        createEvent = view.findViewById(R.id.create_event);
        points_seekBar = view.findViewById(R.id.points_seekBar);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_events, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //calling the method
        init();

        //using sick bar to set the points
        points_seekBar.setProgress(10);//minimum possible points is 10
        points_seekBar.setMax(150); //maximum is 150
        points_seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progresschangedvalue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progresschangedvalue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getActivity(), "100% Time attended: "+progresschangedvalue+" points", Toast.LENGTH_LONG).show();
            }
        });



        //creating the adapter for the spinner
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.names));
        //setting the drop down
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);



        //creating the adapter for the spinner
        ArrayAdapter<String> start = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.start_times));
        //setting the drop down
        start.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        start_time.setAdapter(start);




        //creating the adapter for the spinner
        ArrayAdapter<String> end = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.end_time));
        //setting the drop down
        end.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        end_time.setAdapter(end);




        //setOnItemSelectedListener instead of onItemClickListener to make the end_time default by 1 hour greater than start time
        start_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //setting default here
                end_time.setSelection(Integer.parseInt(String.valueOf(start_time.getFirstVisiblePosition())) + 2);
                //Toast.makeText(getActivity(),seekBar.getProgress(),Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //setting default to be always one hour even without clicking
                end_time.setSelection(Integer.parseInt(String.valueOf(start_time.getFirstVisiblePosition())) + 2);
            }
        });



        //setting the click listener for the text edit of dates
        event_dates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialisations
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                //creating a new DatePickerDialogue
                DatePickerDialog dialog =  new DatePickerDialog(
                        getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth,
                        dateSetListener,year,month,day);

                //setting a color property of the dialogue
                dialog.getWindow().setBackgroundDrawable(
                        new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        //giving the lister a user selected data
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month +=1;
                String date = year +"-"+month+"-"+dayOfMonth;
                event_dates.setText(date);
            }
        };







        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (title.getText().length() ==0 || event_dates.getText().toString() == null || mySpinner.getSelectedItem().toString() == null
                        || start_time.getSelectedItem().toString() == null || end_time.getSelectedItem().toString() == null)
                {
                    Toast.makeText(getActivity(),"All fields should have data",Toast.LENGTH_LONG).show();
                }
                else {


                    StringRequest putRequest = new StringRequest(Request.Method.POST, ApiUrls.REGISTER_EVENT_IP,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response", response);
                                Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Log.d("Error.Response",error.toString());
                                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                            }
                        }
                ) {

                    @Override
                    protected java.util.Map<String,String>  getParams() {


                        Map<String, String> params = new HashMap<String, String>();
                        //checking that there are no empty fields



                        //putting data to the server
                       // params.put("userID", userId.getText().toString());
                        params.put("name", title.getText().toString());
                        params.put("date", event_dates.getText().toString());
                        params.put("points", String.valueOf(points_seekBar.getProgress()));
                        params.put("location", mySpinner.getSelectedItem().toString());
                        params.put("startTime", start_time.getSelectedItem().toString());
                        params.put("endTime", end_time.getSelectedItem().toString());
                        //Long duration = end_time - start_time


                        return params;
                    }

                };

                //new request queue to add the request
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(putRequest);

            }
            }

        });


//        buttonGenerate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                   // getFragmentManager().beginTransaction().replace(R.id.fragment_container, new GenerateQRCode()).commit();
//                }catch(Exception e){
//
//                }
//            }
//        });
    }
}
