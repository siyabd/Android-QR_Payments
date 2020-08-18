package com.example.academy_intern.clouddime.MyFragmentClass;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.academy_intern.clouddime.HomeLoader;
import com.example.academy_intern.clouddime.Objects.AdminLoader;
import com.example.academy_intern.clouddime.Objects.Album;
import com.example.academy_intern.clouddime.R;
import com.example.academy_intern.clouddime.classes.ApiUrls;
import com.example.academy_intern.clouddime.classes.StorageSharedPreference;
import com.example.academy_intern.clouddime.classes.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Splash extends Fragment {

    View view;
   // Animation fromtop;
    ImageView logo;

    ImageView logocloud,logod,logoc;
    Animation fromthebotton,fromtop;


    StorageSharedPreference storageShared;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_splash, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        storageShared = new StorageSharedPreference(getActivity());
        getEvents();
        top();
        bottom();

        new CountDownTimer(3500,1000){
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {

              //  getFragmentManager().beginTransaction().replace(R.id.show,new tabfrag()).commit();
                getFragmentManager().beginTransaction().replace(R.id.show,new AdminLoader()).commit();
                //checkSharedPreference();
            }
        }.start();

    }


    public void top(){

        logocloud =(ImageView)view.findViewById(R.id.logocloud);
        fromtop=AnimationUtils.loadAnimation(getActivity(),R.anim.fromtop);
        logocloud.setAnimation(fromtop);


        ImageView contlogoc =  (ImageView) view.findViewById(R.id.logoc);

        final Animation zoomAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom);
        contlogoc.startAnimation(zoomAnimation);
        contlogoc.setVisibility(View.VISIBLE);
    }


    public void bottom(){

        logod =(ImageView)view.findViewById(R.id.logod);
        fromthebotton=AnimationUtils.loadAnimation(getActivity(),R.anim.frombottom);
        logod.setAnimation(fromthebotton);

    }

    public void checkSharedPreference(){
        try {
            //checking whether the sahred preference is not null and mpving to the right fragment
            if (storageShared.sharedPreferenceProfile().getName().trim().length() > 0) {

                UserInfo userInfoObj = storageShared.sharedPreferenceProfile();

                String userName = userInfoObj.getName();
                String surname = userInfoObj.getSurname();

                if (userName != null && surname != null) {
                    Toast.makeText(getActivity(), "Welcome back " + userInfoObj.getName(), Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.show, new HomeLoader()).commit();
                } else {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.show, new tabfrag()).commit();
                }
            } else {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.show, new tabfrag()).commit();
            }

        }catch(Exception e){
            Toast.makeText(getActivity(), "Welcome back "+ e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    //getting all the events
    public void getEvents(){
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

            //shared preference
            storageShared.writeSharedPreferenceEvents(albumList);

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    });
    requestQueue.add(jsonArrayRequest);

    }

    //Gets the month as a int and converts it to a string value
    public String getMonth(int monthNum){

        String months[] = {"","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

        return months[monthNum];
    }
}
