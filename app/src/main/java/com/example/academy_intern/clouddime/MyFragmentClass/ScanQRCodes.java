package com.example.academy_intern.clouddime.MyFragmentClass;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.academy_intern.clouddime.R;
import com.example.academy_intern.clouddime.classes.StorageSharedPreference;
import com.example.academy_intern.clouddime.classes.UserInfo;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Fragment to scan QR Codes on the app
 */

public class ScanQRCodes extends Fragment {

    View view;
    String value = "";
    String eventCreatorId = "";
    String product = "";
    Boolean isBuying = false;
    Boolean isAttending = false;
    boolean dialogShown = false;
    double balance=0.0;
    double cost =0.0;
    int eventId;
    UserInfo userInfo ;
    TextView txtResult;
    AlertDialog alertDialog;
    SurfaceView cameraPreview;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    final int RequestCameraPermissionID = 100;
    StorageSharedPreference storageSharedPreference;

    //Default constructor
    public ScanQRCodes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.scan_qrcodes, container, false);
        return view;
    }
    public void init(){
        cameraPreview = (SurfaceView) view.findViewById(R.id.cameraPreview);
        txtResult = (TextView) view.findViewById(R.id.txtResult);
        storageSharedPreference =new StorageSharedPreference(getActivity());
        userInfo = storageSharedPreference.sharedPreferenceProfile();

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

        final Context context = view.getContext().getApplicationContext();
        barcodeDetector = new BarcodeDetector.Builder(context)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource
                .Builder(getActivity(), barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //Request permission to use the camera
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                //checking whether you are purchasing something

                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if(qrcodes.size() != 0)
                {
                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {
                            //when the dialog is displaying,the scanner must stop
                            if(!dialogShown){
                                //Value received from the QR code
                                String value = qrcodes.valueAt(0).displayValue;

                                //example of QR codes Generated from the app
                                //https://ndetta tsixe %20@ tcudorp/ digitalacademy
                                //https://ndettatsixe%2@Out/DigitalAcademy

                                //way to check whether the QR code is generated in the App
                                if (value.contains("tsixe")) {
                                    try {
                                        //To check whether you are buying or attending an event
                                        if (value.contains("yub")) {

                                            String price = value.substring(value.indexOf('%') + 1, value.lastIndexOf('@'));
                                            product = value.substring(value.lastIndexOf('/') + 1, value.length());
                                            cost= Double.parseDouble(price);
                                            buying();

                                        } else if (value.contains("ndetta")) {
                                            //QR codes Generated in the App example
                                            //https://ndettatsixe% EVENTID @ TYPE / userID

                                            //getting values from the QR code string
                                            eventCreatorId = value.substring(value.lastIndexOf('/') + 1, value.length());
                                            eventId = Integer.parseInt(value.substring(value.indexOf('%') + 1, value.lastIndexOf('@')));
                                            String type = value.substring(value.indexOf('@') + 1, value.lastIndexOf('/'));


                                            attending(type);


                                        }
                                    }
                                    catch(Exception e){
                                        Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    //if QR code is not generated from the App
                                    String text = "Scan a valid QR code";
                                    txtResult.setText(text);
                                }
                            } else{

                            }
                        }
                    });
                }

            };
        });
    }
    //Alert dialog to purchase something from the App
    public String alertDialog(){

        try {
            //Way to show only one dialog at a time
            if(!dialogShown) {
                dialogShown = true;
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                //Confirming what you are purchasing

                String message = "Are you sure that you want to buy ? Balance:"+balance+"/Cost:"+cost;
                alert.setMessage(message);
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        value = "No";
                        dialogShown = false;
                    }
                });

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isBuying = true;
                        //checking whether you have enough points to purchase something
                        if(balance >=cost) {
                            buying();
                        }else{
                            isBuying = false;
                            Toast.makeText(getActivity(),"You do not have enough points", Toast.LENGTH_LONG).show();
                        }
                        value = "Yes";
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }

        }catch(Exception e){
            Toast.makeText(getActivity(),e.toString(), Toast.LENGTH_LONG).show();
        }
        return value;
    }
    //getting the current points of the user ,stored in a shared preference
    double getPoints(){
        double points = userInfo.getPointsBalance();
        return points;
    }

    //Purchasing something from the App
    public void buying(){
        //Avoiding calling multiple dialogs
        if(!isBuying){

            try {
                //change the global balance
                balance = getPoints();
                if (alertDialog() == "Yes") {

                } else if (alertDialog() == "No") {
                    isBuying = false;
                }
            }catch(Exception e){

            }

        }else{
            //deduct the points from the user account
            deductPoints(cost);
        }
    }

    //This method is called when a user is scanning a QR code to attend an event
    public void attending(String type){

        //Checking whether a user is attending or leaving an event
        if(type.contains("In")){

            ///when you a entering
            attend();

        }else if(type.contains("Out")){

            //after attending an event
            doneAttending();

        }
    }

    //Spring connection to record attendance
    public void attend(){

        if(!isAttending) {
            String url = "http://10.1.0.97:8080/cloudDime/attendin";
            RequestQueue queue = Volley.newRequestQueue(getContext());

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response != " ") {
                        try {
                            //when the attendance is added in the database ,show a dialog to the user
                            attendInDialog();
                            isAttending=false;
                        } catch (Exception error) {
                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Already recorded", Toast.LENGTH_LONG).show();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error", error.toString());
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    //params.put("userId", String.valueOf(userInfo.getUserId()));
                    //parameter that are needed when making a connection
                    params.put("userId", String.valueOf(String.valueOf(2)));
                    params.put("eventId", String.valueOf(eventId));
                    return params;
                }
            };
            queue.add(stringRequest);
            isAttending = true;
        }

    }
    //spring connection after attending
    public void doneAttending(){

        if(!isAttending ) {

            isAttending = true;
            Toast.makeText(getActivity(), "Done attending", Toast.LENGTH_LONG).show();
            String url = "http://10.1.0.60:8080/cloudDime/attendout";
            RequestQueue queue = Volley.newRequestQueue(getContext());

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (response != " ") {
                        try {
                            //when the attendance is added in the database ,show a dialog to the user
                            attendOutDialog();

                        } catch (Exception error) {
                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "Already recorded", Toast.LENGTH_LONG).show();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.d("Error", error.toString());
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    //params.put("userId", String.valueOf(userInfo.getUserId()));
                    params.put("userId", String.valueOf(String.valueOf(3)));
                    params.put("eventId", String.valueOf(eventId));
                    return params;
                }
            };
            queue.add(stringRequest);

        }

    }

    //This method displays a dialog when a user scans a QR code before attending an event
    public void attendInDialog(){

        if(!dialogShown) {
            dialogShown = true;
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

            String message = "Attendance is registered make sure that you scan the QR code when you leave";

            alert.setMessage(message);

            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialogShown = false;

                }
            });

            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        }

    }
    //This method displays a dialog when a user scans a QR code after attending an event
    public void attendOutDialog(){

        //checks whether a dialog is shown before displaying

        //Boolean variable to shows dialogs once at a time
        if(!dialogShown) {
            dialogShown = true;

            LayoutInflater alertDisplay = LayoutInflater.from(getContext());

            //layout which is displayed when the dialog is showing
            View promptView = alertDisplay.inflate(R.layout.alert, null);

            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());


            //sets what will be displayed
            alert.setIcon(android.R.drawable.btn_star_big_on);
            alert.setTitle("Attendance registered");
            alert.setView(promptView);

            //From the  dialog interface
            Button btnThumbsUp = (Button) promptView.findViewById(R.id.btnThumbsUp);
            Button btnThumbsDown = (Button) promptView.findViewById(R.id.btnThumbsDown);

            //once the thumbs up button is clicked
            btnThumbsUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thumbsUp();
                    isAttending = false;
                    dialogShown = false;
                    alertDialog.dismiss();
                }
            });

            btnThumbsDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thumbsDown();
                    isAttending = false;
                    dialogShown = false;
                    alertDialog.dismiss();

                }
            });
            alertDialog = alert.create();
            //displays the dialog
            alertDialog.show();
        }
    }
    //Method to change event score in the database
    public void thumbsUp(){

        String url = "http://10.1.0.60:8080/cloudDime/thumbsUp";
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response!= " "){

                    Toast.makeText(getActivity(), "Thumbs Up", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",error.toString());
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("userId", eventCreatorId);
                return params;
            }
        };
        queue.add(stringRequest);

    }
    //Method to add an event Score when a user clicks the thumbs down button
    public void thumbsDown(){

        String url = "http://10.1.0.60:8080/cloudDime/thumbsDown";
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response!= " "){
                    Toast.makeText(getActivity(),"Thumbs Down",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            //if an error is encountered
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",error.toString());
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("userId", eventCreatorId);
                return params;
            }
        };
        queue.add(stringRequest);
    }
    public void deductPoints(final double total){

        String url = "http://10.1.0.60:8080/cloudDime/User/deductpoints";
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response!= "Deducted"){
                    try {
                        double userBalance = userInfo.getPointsBalance()-total;
                        userInfo.setPointsBalance(userBalance);
                        //When your transaction is successful
                        getFragmentManager().beginTransaction().replace(R.id.show, new SuccessfulPayFragment()).commit();

                    }catch(Exception error){
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",error.toString());
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("userID", String.valueOf(userInfo.getUserId()));
                params.put("product", product);
                params.put("total", String.valueOf(total));
                return params;
            }
        };
        queue.add(stringRequest);
    }


    //camera permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }
}
