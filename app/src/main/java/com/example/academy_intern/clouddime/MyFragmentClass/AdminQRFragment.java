package com.example.academy_intern.clouddime.MyFragmentClass;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.academy_intern.clouddime.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A simple {@link Fragment} subclass.
 */

public class AdminQRFragment extends Fragment {

    Button buttonGenerate;
    EditText price,name;
    ImageView qrCodeImage;
    View view;
    public AdminQRFragment() {
        // Required empty public constructor
    }
    public void init(){
        buttonGenerate = view.findViewById(R.id.btnAdminGenerate);
        price = view.findViewById(R.id.txtPrice);
        name = view.findViewById(R.id.txtProductName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_admin_qr, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //when the name and price of a product are entered then a qr code can be generated
                    if(price.getText().toString()!= null && name.getText().toString()!=null){

                        try {
                            qrCodeDialog();
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }


                    }else{
                        Toast.makeText(getActivity(),"Fill in all the required fields",Toast.LENGTH_LONG).show();;
                    }
            }
        });


    }
    AlertDialog alertDialog;
    Bitmap bitmap;
    public void qrCodeDialog() throws WriterException {



            LayoutInflater alertDisplay = LayoutInflater.from(getContext());

            //layout which is displayed when the dialog is showing
            View promptView = alertDisplay.inflate(R.layout.qr_code_dialog, null);

            final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());


            //sets what will be displayed
            alert.setIcon(android.R.drawable.btn_star_big_on);
            alert.setTitle("Generated QR code");
            alert.setView(promptView);

            //From the  dialog interface
            Button btnCancel = (Button) promptView.findViewById(R.id.btnCancel);
            Button btnSendVia = (Button) promptView.findViewById(R.id.btnSendVia);
            ImageView qrCodeImage = promptView.findViewById(R.id.imgQRcode);


        try {
            //https://ndetta tsixe %20@ tcudorp/ digitalacademy
            /*Qr code generated from the app string for a product
            *
            * yub is buy ,used to show that you a are purchasing a product
            * tsixe - shows that the qr code is generated from the app
            * */

            String text = "yubtsixe"+price+"@tcudorp/"+name;
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder Encoder = new BarcodeEncoder();
            bitmap = Encoder.createBitmap(bitMatrix);
            qrCodeImage.setImageBitmap(bitmap);

        }catch (Exception e){
            Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();;

        }
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();

                }
            });

            btnSendVia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        //shareVia();
                        saveImage(bitmap);
                        send();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();;
                    }
                }
            });

            alertDialog = alert.create();
            //displays the dialog
            alertDialog.show();
        }
        public void shareVia() throws IOException {


            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
           // sharingIntent.setType("text/plain");
            sharingIntent.setType("image");
            String shareBody = "qrcode";
           // sharingIntent.putExtra(Intent.EXTRA_STREAM, saveImageExternal(bitmap) );
           // sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(bitmap));
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }

    private static void saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/saved_images");
        Log.i("Directory", "==" + myDir);

        //Toast.makeText(getContext(), "Directory =="+ myDir, Toast.LENGTH_SHORT).show();

        myDir.mkdirs();

        String fname = "Image-test" + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send() {
        try {
            File myFile = new File("/storage/emulated/0/saved_images/Image-test.jpg");
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String ext = myFile.getName().substring(myFile.getName().lastIndexOf(".") + 1);
            String type = mime.getMimeTypeFromExtension(ext);
            Intent sharingIntent = new Intent("android.intent.action.SEND");
            sharingIntent.setType(type);
            sharingIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(myFile));
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}

