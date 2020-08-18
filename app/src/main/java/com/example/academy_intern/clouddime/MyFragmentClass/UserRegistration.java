package com.example.academy_intern.clouddime.MyFragmentClass;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.academy_intern.clouddime.R;
import com.example.academy_intern.clouddime.classes.ApiUrls;
import com.example.academy_intern.clouddime.classes.StorageSharedPreference;
import com.example.academy_intern.clouddime.classes.UserInfo;
//import com.example.academy_intern.clouddime.fragtab;
import com.google.gson.Gson;


/*
* This fragment allows new users/Entepreneures to register on the applications */

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegistration extends android.support.v4.app.Fragment {

    //Declarations
    View view;
    EditText etName,etSurname,etEmail,etContact,etPassword;
    Button btnRegister;

    //Constructor
    public UserRegistration(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //inflating layout
        view = inflater.inflate(R.layout.fragment_register, container, false);
        btnRegister = view.findViewById(R.id.btnRegister);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getting data from the elements to the declared variables
                String name = (etName = view.findViewById(R.id.etName)).getText().toString();
                String surname =   (etSurname = view.findViewById(R.id.etSurname)).getText().toString();
                String email =   (etEmail = view.findViewById(R.id.etEmail)).getText().toString();
                String password = (etPassword = view.findViewById(R.id.etPassword)).getText().toString();
                String cellphone = (etContact = view.findViewById(R.id.etCellPhone)).getText().toString();

                //checking empty fields
                if (name.isEmpty()) {
                    etName.setError("Cannot be Empty");

                } else if (surname.isEmpty()) {
                    etSurname.setError("Cannot be Empty");
                }else if(password.isEmpty()){
                    etPassword.setError("Cannot be Empty");
                }else if(email.isEmpty()){
                    etEmail.setError("Cannot be Empty");
                }else if(!emailValidation(email)){
                    etEmail.setError("Incorrect format");
                }
                else if(cellphone.isEmpty()){
                    etContact.setError("Cannot be empty");
                }
                else {

                    JSONObject user = new JSONObject();
                    JSONObject profile = new JSONObject();

                    try {

                        user.put("email",email);
                        user.put("password",password);
                        profile.put("name",name);
                        profile.put("surname",surname);
                        profile.put("cellphone",new Integer(cellphone));
                        profile.put("user",user);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //New requestqueue
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplication());
                    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, ApiUrls.REGISTER_IP, profile,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    System.out.println("*******************"+response.toString());
                                    if(!response.toString().isEmpty()){
                                        final UserInfo userInfo = new Gson().fromJson(response.toString(),UserInfo.class);

                                        Toast.makeText(getContext(),"Welcome "+userInfo.getName(),Toast.LENGTH_LONG).show();
                                        AlertDialog.Builder dialog  = new AlertDialog.Builder(getActivity());
                                        dialog.setMessage("Congratulations you just earned 50 points!");
                                        dialog.setTitle("CloudDime");


                                        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                StorageSharedPreference sp = new StorageSharedPreference(getActivity());
                                                sp.writeSharedPreferenceProfile(userInfo);
                                               // getFragmentManager().beginTransaction().remove(new fragtab());
                                            }
                                        });
                                        dialog.create().show();

                                    }else{
                                        Toast.makeText(getContext(),"Email already registered ",Toast.LENGTH_LONG).show();
                                        System.out.println("*******************");
                                    }

                                }


                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    if(error instanceof ServerError){

                                        Toast.makeText(getActivity(),"Currently down for maintenace,We'll be back shortly",Toast.LENGTH_LONG).show();
                                    }
                                    else if(error instanceof NetworkError){
                                        Toast.makeText(getActivity(),"Check your internet connection",Toast.LENGTH_LONG).show();

                                    }
                                }
                            }

                    );

                    //adding the new request
                    requestQueue.add(objectRequest);

                }///here

            }
        });


    }

    public boolean emailValidation(String email){
        Pattern patern;
        Matcher matcher;

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}
