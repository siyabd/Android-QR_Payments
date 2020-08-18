package com.example.academy_intern.clouddime.MyFragmentClass;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.academy_intern.clouddime.Objects.RecyclerViewAdapter;
import com.example.academy_intern.clouddime.Objects.TransactionalHistory;
import com.example.academy_intern.clouddime.R;
import com.example.academy_intern.clouddime.classes.ApiUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/*
* Fragment is created for users to view previous achievements they created*/

public class ViewAchievements extends Fragment {

    //Declarations
    private RecyclerView myrecyclerView;
    private List<TransactionalHistory> lstContact;
    View view;
    RequestQueue requestQueue;

    //Constructor
    public ViewAchievements() {

    }

    //Overriding on create
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    //Overriding on create view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_achievement_list,container,false);
        lstContact = new ArrayList<>();
        getRecent();
        myrecyclerView = (RecyclerView) view.findViewById(R.id.contact_recyclerview);
        return view;
    }

    //method to get achievements of the current logged in user
    public void getRecent(){

        int userID = 2;
        Timestamp date = new Timestamp(System.currentTimeMillis());
        requestQueue = Volley.newRequestQueue(this.getActivity());

        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(ApiUrls.VIEW_ACH_API +"?userID="+userID, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Toast.makeText(getActivity(), rep, Toast.LENGTH_SHORT).show();
                for (int i = 0; i < response.length(); i++) {

                    try {

                        JSONObject jsonObject = response.getJSONObject(i);
                        // User user = new User(jsonObject.getString("email"), jsonObj
                        //Timestamp date = new Timestamp(System.currentTimeMillis());
                        String points=jsonObject.getString("spentPoints");
                        String date=jsonObject.getString("createDate");
                        String location=jsonObject.getString("place");
                        lstContact.add(new TransactionalHistory("-"+points+"pts",date,location));

                        //String text = "Points Spent"+jsonObject.getString("spentPoints");;


                        //  Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                RecyclerViewAdapter recyclerAdapter = new RecyclerViewAdapter(getContext(), lstContact);
                myrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                myrecyclerView.setAdapter(recyclerAdapter);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        //requestQueue.add(jsonArrayRequest);
        requestQueue.add(jsonArrayRequest);

    }


}



