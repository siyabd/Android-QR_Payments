package com.example.academy_intern.clouddime.MyFragmentClass;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.academy_intern.clouddime.Objects.AdminLoader;
import com.example.academy_intern.clouddime.R;

import com.example.academy_intern.clouddime.classes.ApiUrls;
import com.example.academy_intern.clouddime.classes.StorageSharedPreference;
import com.example.academy_intern.clouddime.classes.UserInfo;

import com.example.academy_intern.clouddime.HomeLoader;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

//User login fragment contains the procedures of login in

public class UserLogin extends Fragment {

    //Declarations
    View view;
    EditText etusername;
    EditText etpassword;
    TextView txtSignup;
    StorageSharedPreference storageShared;

    Button btLogin;

    public UserLogin() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_userlogin, container, false);
        return view;
    }

    public void init() {
        etusername = (EditText) view.findViewById(R.id.etUserName);
        etpassword = (EditText) view.findViewById(R.id.etPassword);
        btLogin = (Button) view.findViewById(R.id.btnLogin);
        txtSignup = (TextView) view.findViewById(R.id.txtSignUp);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

        storageShared = new StorageSharedPreference(getActivity());


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = etusername.getText().toString();
                String password = etpassword.getText().toString();

                if (username.isEmpty()) {

                    etusername.setError("Cannot be Empty");

                } else if (password.isEmpty()) {

                    etpassword.setError("Cannot be Empty");

                } else {

                    JSONObject user = new JSONObject();

                    try {
                        user.put("email", username);
                        user.put("password", password);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, ApiUrls.LOGIN_PI, user,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    if (response == null) {

                                        Toast.makeText(getActivity(), "Wrong email or password", Toast.LENGTH_LONG).show();

                                    } else {

                                        final UserInfo userInfo = new Gson().fromJson(response.toString(),UserInfo.class);
                                        storageShared.writeSharedPreferenceProfile(userInfo);
                                       // getFragmentManager().beginTransaction().replace(R.id.show,new AdminLoader()).commit();

                                        getFragmentManager().beginTransaction().replace(R.id.show,new HomeLoader()).commit();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    if (error instanceof ParseError) {
                                        Toast.makeText(getActivity(), "User not registered", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                    );

                    requestQueue.add(objectRequest);
                }
            }
        });

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            }
        });
    }
}



