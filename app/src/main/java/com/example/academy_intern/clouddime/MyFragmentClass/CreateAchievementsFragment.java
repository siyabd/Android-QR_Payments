package com.example.academy_intern.clouddime.MyFragmentClass;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.example.academy_intern.clouddime.classes.StorageSharedPreference;
import com.example.academy_intern.clouddime.classes.UserInfo;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/*
    This fragment allows users to add their achievements that can be personal or business based, providing description, dates and achievement type.
    The button achievementbtn confirms the addition of achievement

 */


public class CreateAchievementsFragment extends Fragment {
    private View view;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private EditText userId;
    private EditText description;
    private EditText date;
    private Spinner achievementType;
    private Button achievementsbtn;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_achievements, container, false);

        return view;
    }

    //Method to initialize elements
    public void init() {
        //userId = view.findViewById(R.id.userid);
        description = view.findViewById(R.id.description);
        date = view.findViewById(R.id.date);
        achievementsbtn = view.findViewById(R.id.achievements);
        achievementType = view.findViewById(R.id.achType);
    }


    //Overriding the on activity created method
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //call the elements declaration method
        init();


        //creating an adapter for the spinner element
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.achievements_types));
        //setting the drop down
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        achievementType.setAdapter(spinnerAdapter);


        //Calling the on clicker of a of a spinner
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //initialization
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                //creating new DataPickerDialogue
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth,
                        dateSetListener, year, month, day);

                //Setting a color property of the dialogue
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        //giving click listener for the Edit text of the start time
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                //Formatting the dates
                String dateOfAchievement = year + "-" + month + "-" + dayOfMonth;
                date.setText(dateOfAchievement);
            }
        };


        //Calling the on clicker of a button
        achievementsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creating a new String request
                StringRequest requestAchievements = new StringRequest(Request.Method.POST, ApiUrls.ADD_ACHIE_IP,
                        new Response.Listener<String>() {
                            //Overriding the response listener
                            @Override
                            public void onResponse(String response) {
                                //log.d to check the response on the logcat


                                Log.d("Response: ", response);
                                Toast.makeText(getActivity(), response.toString() + " wellness ", Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                    //Overriding the error response listener
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        //log.d to check the response on the logcat
                        Log.d("ErrorResponse: ", error.toString());
                    }
                })

                {

                    //getParams() method overriding
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();


                        //calling the shared preference that stored the logged in user details
                        StorageSharedPreference storageSharedPreference = new StorageSharedPreference(getActivity());
                        UserInfo userInfo = storageSharedPreference.sharedPreferenceProfile();


                        //putting details of the achievements to the database
                        params.put("userId", String.valueOf(userInfo.getUserId()));
                        //params.put("userId", String.valueOf(2));
                        params.put("description", description.getText().toString());
                        params.put("date", date.getText().toString());
                        params.put("achievementType", achievementType.getSelectedItem().toString());

                        return params;
                    }
                };
                //Creating new request queue to store the request
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                //giving the created request the new request
                queue.add(requestAchievements);

            }
        });

    }
}


