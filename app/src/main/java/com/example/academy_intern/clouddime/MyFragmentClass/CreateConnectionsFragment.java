package com.example.academy_intern.clouddime.MyFragmentClass;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;


/*
    This fragment allows users to add the connections they have with other businesses, providing names of the connection and their specialisation
    The button connectionsbtn confirms the addition of the connection

 */


public class CreateConnectionsFragment extends Fragment {
    private View view;
    private EditText connectionName;
    private EditText specialization;
    private Button connectionsbtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_connections, container, false);

        return view;
    }


    public void init() {
        //assigning the elements to the one on the xml
        connectionName = view.findViewById(R.id.theirname);
        specialization = view.findViewById(R.id.specialization);
        connectionsbtn = view.findViewById(R.id.connection);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //call init() method
        init();


        //button click
        connectionsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String request
                StringRequest request = new StringRequest(Request.Method.POST, ApiUrls.ADD_CONNECTION_IP,
                        //Response listener
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //log.d to check the response on the logcat
                                Log.d("Response: ", response);
                                Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                            }
                        },
                        //Error listener
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //log.d to check the response on the logcat
                                Log.d("ErrorResponse: ", error.toString());

                                Toast.makeText(getActivity(), error.toString() + " wellness ", Toast.LENGTH_LONG).show();
                            }

                        })

                {
                    //getParams() method overriding
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();


                        //calling the shared preference that stored the user information
                        StorageSharedPreference storageSharedPreference = new StorageSharedPreference(getActivity());
                        UserInfo userInfo = storageSharedPreference.sharedPreferenceProfile();


                        //putting details of the connections to the server
                        params.put("userId", String.valueOf(userInfo.getUserId()));
                        // params.put("userId", String.valueOf(4));
                        params.put("name", connectionName.getText().toString());
                        params.put("specialization", specialization.getText().toString());


                        //String nm = params.get("name");
                        return params;
                    }
                };
                //Creating new request queue to store the request
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                //giving the created request the new request
                queue.add(request);
                //}

            }
        });


    }
}


