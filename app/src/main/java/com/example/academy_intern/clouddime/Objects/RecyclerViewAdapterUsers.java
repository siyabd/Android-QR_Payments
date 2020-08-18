package com.example.academy_intern.clouddime.Objects;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.academy_intern.clouddime.R;
import com.example.academy_intern.clouddime.classes.ApiUrls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RecyclerViewAdapterUsers extends RecyclerView.Adapter<RecyclerViewAdapterUsers.MyViewHolder> {

    Context mContext;
    List<Contact> userList;
    String email ;
    View view;
    //default controller
    public RecyclerViewAdapterUsers(Context mContext, List<Contact> userList) {
        this.mContext = mContext;
        this.userList = userList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userlist,viewGroup,false);

        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {

        holder.txt_name.setText(userList.get(i).getName());
        holder.txt_surname.setText(userList.get(i).getOccupation());
        email =userList.get(i).getName();
        holder.approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Button click",Toast.LENGTH_LONG).show();;
                approveUsers();
            }
        });

    }

    //approving users based on their email
    public void approveUsers(){

        String url = "http://10.1.0.60:8080/cloudDime/Admin/approve";
        StringRequest putRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response

                        Toast.makeText(view.getContext(),response.toString(),Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response",error.toString());
                        Toast.makeText(view.getContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        ) {

            @Override
            protected java.util.Map<String,String>  getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //checking that there are no empty fields

                //putting data to the server
                params.put("email", email);
                return params;
            }

        };

        //new request queue to add the request
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(putRequest);

    }
    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // public EditText tv_name;
        private TextView txt_name;
        private TextView txt_surname;
        private Button approveButton;

        public MyViewHolder(View itemView) {

            super(itemView);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name_contact);
            txt_surname = (TextView) itemView.findViewById(R.id.txt_surname);
            approveButton =  itemView.findViewById(R.id.btnApprove);

        }

    }
}
