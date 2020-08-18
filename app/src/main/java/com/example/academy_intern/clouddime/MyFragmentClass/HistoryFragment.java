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


/*This fragment is created to retrieve the transactions the user made*/


public class HistoryFragment extends Fragment {

    //Declarations
    private RecyclerView myrecyclerview;
    private List<TransactionalHistory> lstContact;
    View view;
    RequestQueue requestQueue;

    //Constructor
    public HistoryFragment() {

    }

    //Overriding on create
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history,container,false);
        myrecyclerview = (RecyclerView) view.findViewById(R.id.contact_recyclerview);
        lstContact = new ArrayList<>();
        getRecent();

        return view;
    }

    void getRecent(){

        int userID = 2;
        requestQueue = Volley.newRequestQueue(this.getActivity());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ApiUrls.GET_HISTORY_IP + "?userID=" + userID, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                //looping through the response and adding to the list of type transactional history
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String points=jsonObject.getString("spentPoints");
                        String date=jsonObject.getString("createDate");
                        String location=jsonObject.getString("place");

                        lstContact.add(new TransactionalHistory("-"+points+"pts",date,location));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                if(lstContact==null)
                    lstContact.add(new TransactionalHistory("-00.00"+"pts","null","Null"));


                //adding the list to the adapter then setting myrecyclerview
                RecyclerViewAdapter recyclerAdapter = new RecyclerViewAdapter(getContext(), lstContact);
                myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                myrecyclerview.setAdapter(recyclerAdapter);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);

    }


}



