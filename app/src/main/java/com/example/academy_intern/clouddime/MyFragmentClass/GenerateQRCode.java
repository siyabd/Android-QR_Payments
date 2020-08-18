package com.example.academy_intern.clouddime.MyFragmentClass;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.academy_intern.clouddime.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

//import android.app.Fragment;

/**
 * Fragment to generate QR codes on the app
 */
public class GenerateQRCode extends Fragment {
    //Layouts used
    ImageView image;
    EditText txtName;
    EditText txtPrice;
    Spinner spinner;
    TextView priceDescr;
    Button button;
    Button buttonSend;
    View view;

    //strings used to generate QR Codes
    String serviceType = "";
    String product = "";
    String price = "";
    String[] items;
    ArrayAdapter<String> adapter;

    public GenerateQRCode() {
        // Required empty public constructor
    }

    public void init() {
//
//        priceDescr = view.findViewById(R.id.txtPriceDescr);
//        spinner = view.findViewById(R.id.qrSpinner);
//        txtName = view.findViewById(R.id.txtName);
//        txtPrice = view.findViewById(R.id.txtDescr);
//        button = view.findViewById(R.id.btnGenerate);
//        buttonSend = view.findViewById(R.id.btnSend);
//        image = view.findViewById(R.id.imgView);
//        items = new String[]{"Product", "Event"};
//        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, items);
    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        // Inflate the layout for this fragment
//        view = inflater.inflate(R.layout.generate_qrcode, container, false);
//        return view;
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String type = adapterView.getItemAtPosition(i).toString();
                itemSelected(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                generateQR();

            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareVia();
            }
        });


    }

    //method used to generate QR codes,it is called when you click a button and filling in the required fields
    public void generateQR() {
        try {
            String text = serviceType + "tsixe%" + price + "@tcudorp/" + product;
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder Encoder = new BarcodeEncoder();
            Bitmap bitmap = Encoder.createBitmap(bitMatrix);
            image.setImageBitmap(bitmap);
            buttonSend.setVisibility(View.VISIBLE);
        } catch (WriterException e) {

            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //this method is used to share the QR code once it it generated,opens up a share as option in the fragment
    public void shareVia() {
        try {
            if (image.getDrawable() != null) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "qrcode";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            } else {
                //this message will display when an image is not generated
                String message = "Make sure that an image is generated";
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //Action based on a selected item from the spinner
    public void itemSelected(String type) {
        if (type == "Event") {
            serviceType = "ndetta";
            price = "00";
            txtName.setText(null);
            txtName.setHint("Enter Event Name");
            priceDescr.setVisibility(View.INVISIBLE);
            txtPrice.setVisibility(View.INVISIBLE);
            buttonSend.setVisibility(View.INVISIBLE);
            image.setImageDrawable(null);
            //if  product is selected
        } else if (type == "Product") {
            serviceType = "yub";
            price = txtPrice.getText().toString();
            txtName.setText(null);
            txtPrice.setText(null);
            txtName.setHint("Enter Product Name");
            txtPrice.setVisibility(View.VISIBLE);
            priceDescr.setVisibility(View.VISIBLE);
            buttonSend.setVisibility(View.INVISIBLE);
            image.setImageDrawable(null);
        }

    }

}
