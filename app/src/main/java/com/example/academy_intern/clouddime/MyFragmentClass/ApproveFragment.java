package com.example.academy_intern.clouddime.MyFragmentClass;


import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.academy_intern.clouddime.Objects.Contact;
import com.example.academy_intern.clouddime.Objects.RecyclerViewAdapter;
import com.example.academy_intern.clouddime.Objects.RecyclerViewAdapterUsers;
import com.example.academy_intern.clouddime.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ApproveFragment extends Fragment {
    View view;
    private RecyclerViewAdapterUsers adapter;
    private RecyclerView myrecyclerview;

    public ApproveFragment() {
        // Required empty public constructor
    }
    public void init()
    {
        myrecyclerview = (RecyclerView) view.findViewById(R.id.contact_recyclerview);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_approve, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        addToList();
    }

    public void addToList() {

        final ArrayList<Contact> lstContact = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        String url = "http://10.1.0.60:8080/cloudDime/Admin/notapproved";

        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {

                    try {

                        JSONObject jsonObject = response.getJSONObject(i);
                        String name=jsonObject.getString("email");
                        String Occupation=jsonObject.getString("createDate");

                        int[] covers = new int[]{R.drawable.cloudnew};

                        lstContact.add(new Contact(name,Occupation,covers[0]));

                    } catch (JSONException e) {
                        e.printStackTrace();

                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                adapter = new RecyclerViewAdapterUsers(getActivity(), lstContact);
                myrecyclerview.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);

    }


}
