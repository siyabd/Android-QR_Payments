package com.example.academy_intern.clouddime.classes;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.academy_intern.clouddime.Objects.Album;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Class stores the currently logged in user details in the phone memory,as long as the user is
currently logged in,values are stored in the shared preference and cleared when the user logs out

 */

public class StorageSharedPreference {

    public static  String jsonObj;

    public static final String USER_INFO_PREFERENCES = "userInfoPrefs" ;
    public static final String userInfoObject = "profileKey";

    public static final String EVENTS_PREFERENCES = "businessEventsPrefs" ;
    public static final String eventsList = "eventsKey";

    public static final String BUSINES_PROFILE_PREFERENCES = "businessProfilePrefs" ;
    public static final String BuinessProfileObject = "businessProfileKey";

    public static final String CONNECTIONS_PREFERENCES = "connectionsPrefs" ;
    public static final String connectionObject = "connectionsKey";

    public static final String ACHIEVEMENTS_PREFERENCES = "achievementsPrefs" ;
    public static final String achievementsObject = "achievementsKey";

    public static final String POINTS_PREFERENCES = "pointsPrefs" ;
    public static final String pointsObject = "pointsKey";

    /*
        writeSharedpreferenceObjectType() methods receives Java object/List of objects belonging to the user who is currently online.
         The unique key gives reference for the sharepreference that stores a specific object,from the Json request
        and then stores them in the shared preference for the rest of the logged in session.

        SharedPrefeenceObjectType() e.g public UserInfo sharedPreferenceProfile(),gets the stored sharedPrefernce of that
        specific object and returns them as a Java Object or List of Object of that specific type
     */


    SharedPreferences sharedpreferences;

    public StorageSharedPreference(Context context) {
        //receives the context of the acivity/fragment from the which the sharedpreference is invoked
        //and send the values as Java objects to that acitviyty/fragment
        sharedpreferences = context.getSharedPreferences(EVENTS_PREFERENCES, Context.MODE_PRIVATE);
    }

    public UserInfo sharedPreferenceProfile(){

        Gson gson = new Gson();


        UserInfo userInfo =null;

        if(sharedpreferences.getString(userInfoObject,"ProfileObject").trim().isEmpty())
        {
            userInfo =null;
        }else
        {
            String jsonObj = sharedpreferences.getString(userInfoObject,"ProfileObject");
            userInfo = gson.fromJson(jsonObj,UserInfo.class);
        }

        return userInfo;
    }

    public void writeSharedPreferenceProfile(UserInfo userInfo){
        Gson gson = new Gson();
        String json = gson.toJson(userInfo);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(userInfoObject, json);

        editor.commit();
    }


//Events shared prefereceeeeeee

    public ArrayList<Album> sharedPreferenceEvents(){

        Gson gson = new Gson();
        String json = sharedpreferences.getString("eventsList",null);
        Type type = new TypeToken<ArrayList<Album>>(){}.getType();

        ArrayList<Album> listOfEvents = gson.fromJson(json,type);

     return listOfEvents;


    }

    public void writeSharedPreferenceEvents(ArrayList<Album> eventsList){

        Gson gson = new Gson();
        String json = gson.toJson(eventsList);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("eventsList",json);
        editor.apply();

    }



    public int getsharedPreferenceTour(){

        String tourKey = sharedpreferences.getString("TOUR_KEY",null);

        return Integer.parseInt(tourKey);
    }

    public void writeSharedPreferenceTour( int key){

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("TOUR_KEY",String.valueOf(key));
        editor.apply();
    }



/*
    //business profile shared preference

    public BusinessProfile sharedPreferenceBusinessProfile(Context context){
        sharedpreferences = context.getSharedPreferences(BUSINES_PROFILE_PREFERENCES, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String jsonObj = sharedpreferences.getString(BuinessProfileObject,"BusinessProfileObject");
        //System.out.println(jsonObj.toString());
        BusinessProfile businessProfile = gson.fromJson(jsonObj,BusinessProfile.class);
        return businessProfile;
    }

    public void writeSharedPreferenceBusinessProfile(Context context,BusinessProfile businessProfile){
        sharedpreferences = context.getSharedPreferences(BUSINES_PROFILE_PREFERENCES, Context.MODE_PRIVATE);

        Gson gson = new Gson();


        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(BuinessProfileObject, gson.toJson(businessProfile));

        editor.commit();

    }
 //connection shared preference

    public Connections sharedPreferenceConnections(Context context){
        sharedpreferences = context.getSharedPreferences(CONNECTIONS_PREFERENCES, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String jsonObj = sharedpreferences.getString(connectionObject,"connectionObject");
        Connections connections = gson.fromJson(jsonObj,Connections.class);
        return connections;
    }

    public void writeSharedPreferenceConnections(Context context,Connections connections){
        sharedpreferences = context.getSharedPreferences(CONNECTIONS_PREFERENCES, Context.MODE_PRIVATE);

        Gson gson = new Gson();

        String json = gson.toJson(connections);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(connectionObject, json);

        editor.commit();

    }

//achievements

    public Achievements sharedPreferenceAchievements(Context context){
        sharedpreferences = context.getSharedPreferences(ACHIEVEMENTS_PREFERENCES, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String jsonObj = sharedpreferences.getString(achievementsObject,"connectionObject");
        Achievements achivements = gson.fromJson(jsonObj,Achievements.class);
        return achivements;
    }

    public void writeSharedPreferenceAchievements(Context context,Connections connections){
        sharedpreferences = context.getSharedPreferences(ACHIEVEMENTS_PREFERENCES, Context.MODE_PRIVATE);

        Gson gson = new Gson();

        String json = gson.toJson(connections);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(achievementsObject, json);

        editor.commit();

    }


    public  Points sharedPreferencePoints(Context context){
        sharedpreferences = context.getSharedPreferences(ACHIEVEMENTS_PREFERENCES, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String jsonObj = sharedpreferences.getString(achievementsObject,"connectionObject");
         Points points = gson.fromJson(jsonObj,Points.class);
        return points;
    }

    public void writeSharedPreferencePoints(Context context,Points points){
        sharedpreferences = context.getSharedPreferences(ACHIEVEMENTS_PREFERENCES, Context.MODE_PRIVATE);

        Gson gson = new Gson();

        String json = gson.toJson(points);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(achievementsObject, json);

        editor.commit();

    }*/

}
