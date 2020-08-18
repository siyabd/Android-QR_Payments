package com.example.academy_intern.clouddime.classes;

public class ApiUrls {

    static String IP_ADDRESS = "http://10.1.0.60:8080/cloudDime/User/"; //MAIN SERVER IP ADDRESS

    public static String LOGIN_PI = IP_ADDRESS + "login";
    public static String REGISTER_IP = IP_ADDRESS +"register";   //Api to register users
    public static String VIEW_ACH_API =IP_ADDRESS + "gethistory";
    public static String GET_EVENTS_IP =IP_ADDRESS + "getevents";
    public static String ADD_CONNECTION_IP = IP_ADDRESS +"addConnect";
    public static String ADD_ACHIE_IP = IP_ADDRESS +"addAchievements";
    public static String REGISTER_EVENT_IP = IP_ADDRESS +"registerevents";
    public static String GET_HISTORY_IP = IP_ADDRESS +"gethistory";
    public static String DEDUCTIONPOINT_IP = IP_ADDRESS +"deductpoints";
    public static String THUMBS_DOWN_IP = IP_ADDRESS + "thumbsDown";

}
