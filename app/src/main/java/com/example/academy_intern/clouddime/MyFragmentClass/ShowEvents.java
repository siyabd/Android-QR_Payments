package com.example.academy_intern.clouddime.MyFragmentClass;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.academy_intern.clouddime.Objects.Album;
import com.example.academy_intern.clouddime.Objects.AlbumsAdapter;
import com.example.academy_intern.clouddime.R;
import com.example.academy_intern.clouddime.classes.ApiUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowEvents extends Fragment {

    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    String name,date,location;
    TextView pointsText,nameText,spentText ,dateText;
    Spinner branchSpinner;
    String[] items;
    ArrayAdapter<String> spinnerAdapter;
    View view;


    public ShowEvents() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_show, container, false);
        return view;
    }




    public void init(){

        spentText= ( TextView )view.findViewById(R.id.txtEvent_date);
        dateText= ( TextView )view.findViewById(R.id.txtEvent_date);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        branchSpinner = view.findViewById(R.id.filter_spinner);



        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        items = new String[]{"Product", "Event"};
        spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, items);


    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        branchSpinner.setAdapter(spinnerAdapter);
        getEventss();
    }




    public void getEvents(){

        final ArrayList<Album> albumList = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        String url = "http://10.1.0.60:8080/cloudDime/User/getevents";

        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject jsonObject = response.getJSONObject(i);

                        name=jsonObject.getString("name");
                        date=jsonObject.getString("date");
                        location=jsonObject.getString("location");

                        String displayDate = date.substring(date.lastIndexOf('-') + 1, date.length());
                        int monthNum =Integer.parseInt(date.substring(date.indexOf('-') + 1, date.lastIndexOf('-')));
                        String month = String.valueOf(getMonth(monthNum));
                        //Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();

                        int[] covers = new int[]{
                                R.drawable.album2,
                                R.drawable.album2};


//                        if(location.equals("South Tower"))
//                        {

                            //albumList.add(new Album(name, covers[0], displayDate, location, month));

                            albumList.add(new Album(name, covers[0],displayDate,location,month,"22","2"));
                        //}


                    } catch (JSONException e) {
                        e.printStackTrace();

                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }


                adapter = new AlbumsAdapter(getActivity(), albumList);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    //getting all the events
    public void getEventss(){
        //array list of type Album which will store all the events
        final ArrayList<Album> albumList = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(ApiUrls.GET_EVENTS_IP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject jsonObject = response.getJSONObject(i);

                        //what will be added to the array list
                        String name=jsonObject.getString("name");
                        String date=jsonObject.getString("date");
                        String location=jsonObject.getString("location");
                        String points = jsonObject.getString("points");

                        //user within the response
                        String user = jsonObject.getString("user");


                        String displayDate = date.substring(date.lastIndexOf('-') + 1, date.length());
                        int monthNum =Integer.parseInt(date.substring(date.indexOf('-') + 1, date.lastIndexOf('-')));
                        String userId = user.substring(user.indexOf(':') + 1, user.indexOf(',') );
                        String month = String.valueOf(getMonth(monthNum));

                        int[] covers = new int[]{R.drawable.album2};

                        albumList.add(new Album(name, covers[0],displayDate,location,month,points,userId));


                    } catch (JSONException e) {
                        e.printStackTrace();

                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }


                adapter = new AlbumsAdapter(getActivity(), albumList);
                recyclerView.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);

    }


    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    public String getMonth(int monthNum){

        String months[] = {"","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

        return months[monthNum];
    }
}
