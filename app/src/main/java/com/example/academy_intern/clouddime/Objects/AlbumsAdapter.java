package com.example.academy_intern.clouddime.Objects;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.academy_intern.clouddime.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {
    private Context mContext;
    private List<Album> albumList;
    private String email,name,surname,contact;
    private String userId;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card, parent, false);
        return new MyViewHolder(itemView);
    }


    public AlbumsAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        Album album = albumList.get(position);
        holder.getContact();
        userId = album.getId();


        holder.title.setText(album.getName().toString());
        holder.descr.setText(album.getLocation());
        holder.date.setText(album.getDate().toString());
        holder.month.setText(album.getMonth().toString());
        holder.points.setText(album.getPoints()+"pt");
        holder.eventCreator.setText("by "+name);

        double eventScore =album.getEventScore();

        if(eventScore <20  ) {

            holder.eventScore.setImageResource(R.drawable.cloudnew);
        }
        else if(eventScore >20 && eventScore <50) {

            holder.eventScore.setImageResource(R.drawable.ic_man_user);
        }
        else if(eventScore >=50) {

            holder.eventScore.setImageResource(R.drawable.ic_man_user);
        }

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        public TextView title, descr,date,month,points,eventCreator;
        public ImageView eventScore;

        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.Title);
            descr = (TextView) view.findViewById(R.id.txtEvent_desctiption);
            date = (TextView) view.findViewById(R.id.txtEvent_date);
            month = (TextView) view.findViewById(R.id.txtMonth2);
            points = (TextView) view.findViewById(R.id.txtEventPoints);
            eventCreator = (TextView) view.findViewById(R.id.txtEventCreator);
            eventScore= (ImageView) view.findViewById(R.id.imgEventScore);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            infoDialog(itemView);
        }

        //getting the contact details of the event organiser when a use clicks on the adapter
        public void getContact (){

            RequestQueue requestQueue = Volley.newRequestQueue(itemView.getContext());

            String url = "http://10.1.0.60:8080/cloudDime/User/getEventOrganiser?userId="+userId;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        for (int i = 0; i < response.length(); i++){

                            JSONObject jsonUser = response.getJSONObject("user");


                            name =response.getString("name");
                            surname = response.getString("surname");
                            contact = response.getString("cellphone");
                            email = jsonUser.getString("email");
                            userId =jsonUser.getString("userId");
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(itemView.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(jsonObjectRequest);

        }
        //dialog that will be displayed with the contact details
        public void infoDialog(View view){

            AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
            alert.setTitle("Contact person");

            String message = "Name :"+name+ "\nSurname :"+ surname+"\nCell:"+contact+"\nEmail : "+email;
            alert.setMessage(message);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        }




    }

}
