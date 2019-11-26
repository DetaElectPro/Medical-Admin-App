package com.hostpital.hospitaladmin.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


//import com.android.volley.AuthFailureError;
//import com.android.volley.NetworkError;
//import com.android.volley.NetworkResponse;
//import com.android.volley.NoConnectionError;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.TimeoutError;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.NetworkError;
import com.android.volley.error.NoConnectionError;
import com.android.volley.error.TimeoutError;
import com.android.volley.error.VolleyError;

import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.hostpital.hospitaladmin.Activities.EmergencyServicesActivity;
import com.hostpital.hospitaladmin.R;
import com.hostpital.hospitaladmin.Utils.CustomDialog;
import com.hostpital.hospitaladmin.Utils.InternetConnection;
import com.hostpital.hospitaladmin.Utils.SharedPreferenceUtil;
import com.hostpital.hospitaladmin.Utils.URLHelper;
import com.hostpital.hospitaladmin.Utils.Utilities;
//import com.labters.lottiealertdialoglibrary.DialogTypes;
//import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Arbab on 8/24/2019.
 */

public class RequestEmergencyFragment extends Fragment implements View.OnClickListener {
    private View mMainView;
    Utilities utils = new Utilities();
    CustomDialog customDialog;
    private String token, user_id, selectedType;

    private String filepath;
    private File myFile;

    Button request, selectImg;
    EditText ed_hospital, ed_price, ed_address, available_bed, report;
    Spinner typeSpinner;

    private static final int STORAGE_PERMISSION_CODE = 123;

    SweetAlertDialog pDialog, pDialog2;

    String[] type = {"ICU Bed", "Mechanical Ventilation", "Hemodialysis"};

    public RequestEmergencyFragment() {
    }

    public static RequestEmergencyFragment newInstance() {
        RequestEmergencyFragment fragment = new RequestEmergencyFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_request_doctor, container, false);

        mMainView = inflater.inflate(R.layout.fragment_request_emergency, container, false);

//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Request");
//        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        initViews();
        token = SharedPreferenceUtil.getStringValue(getActivity(), "access_token");
        user_id = SharedPreferenceUtil.getStringValue(getActivity(), "user_id");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                selectedType = type[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_request();
            }
        });

        return mMainView;
    }

    private void initViews() {
        request = mMainView.findViewById(R.id.request_btn);
        ed_hospital = mMainView.findViewById(R.id.name);
        ed_price = mMainView.findViewById(R.id.price);
        ed_address = mMainView.findViewById(R.id.address);
        available_bed = mMainView.findViewById(R.id.bed);
        report = mMainView.findViewById(R.id.report);



        typeSpinner = mMainView.findViewById(R.id.type_spinner);
    }



    private void add_request() {
        if (new InternetConnection().isInternetOn(getActivity())) {
//            customDialog = new CustomDialog(getActivity());
//            customDialog.setCancelable(false);
//            customDialog.show();

//            pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE);
////            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
////            pDialog.setTitleText("Loading");
//            pDialog.setTitleText("Successful!!");
////            pDialog.setContentText(get);
//            pDialog.setContentText(getString(R.string.add_request));
//            pDialog.setCancelable(false);
//            pDialog.show();

            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();

//            SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
//                    .setTitleText("Good job!")
//                    .setContentText("You clicked the button!")
//                    .show();

            /*JSONObject object = new JSONObject();
            try {
                object.put("name", ed_hospital.getText().toString());
                object.put("address", ed_address.getText().toString());
                object.put("price_per_day", ed_price.getText().toString());
                object.put("type", typeSpinner.getSelectedItem().toString());
                object.put("available", available_bed.getText().toString());
                object.put("user_id", user_id);

                utils.print("EmergencyActivity Request", "" + object);
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, URLHelper.emergency_services, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

//                    pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//                            sweetAlertDialog.dismiss();
//                            Intent intent = new Intent(getActivity(),EmergencyServicesActivity.class);
//                            startActivity(intent);
//                            goToEmergencyServicesActivity();
//                            Intent intent = new Intent(getActivity(), EmergencyServicesActivity.class);
//                            startActivity(intent);
//                            getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
//                            goToEmergencyServicesActivity();
//                            getActivity().recreate();
//                            Fragment frg = null;
//                            frg = getFragmentManager().findFragmentByTag("Your_Fragment_TAG");
//                            final FragmentTransaction ft = getFragmentManager().beginTransaction();
//                            ft.detach(frg);
//                            ft.attach(frg);
//                            ft.commit();
//                        }
//                    });
                    pDialog.dismiss();

                    pDialog2 = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                    pDialog2.setTitleText("Successful!!");
                    pDialog2.setContentText(getString(R.string.add_request));
                    pDialog2.setCancelable(false);
                    pDialog2.show();

                    pDialog2.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            pDialog2.dismiss();
                            goToEmergencyServicesActivity();
                        }
                    });
//                    pDialog.dismiss();
                    utils.print("EmergencyActivity Response", response.toString());


//                    displayMessage(getString(R.string.add_request));

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                    String json = null;
                    String Message;
                    NetworkResponse response = error.networkResponse;
//                    String jsonObject = error.getMessage();
                    utils.print("MyTest", "" + error);
                    utils.print("MyTestError", "" + error.networkResponse);

                    Log.w("Emergency error", error.getMessage());
                    Log.w("Emergency Network error", String.valueOf(error.networkResponse));

                    if (error instanceof NoConnectionError) {
                        displayMessage(getString(R.string.oops_connect_your_internet));
                    } else if (error instanceof NetworkError) {
                        displayMessage(getString(R.string.oops_connect_your_internet));
                    } else if (error instanceof TimeoutError) {
                        add_request();
                    }

                }
            })
//                    ;
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
//                    headers.put("Accept", "application/json");
//                    headers.put("Accept", "multipart/form-data");
//                    headers.put("Content-Type", "multipart/form-data");
                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }
            };

            smr.addFile("image", filepath);
            smr.addStringParam("name", ed_hospital.getText().toString());
            smr.addStringParam("address", ed_address.getText().toString());
//            smr.addStringParam("report", report.getText().toString());
            smr.addStringParam("price_per_day", ed_price.getText().toString());
            smr.addStringParam("type", typeSpinner.getSelectedItem().toString());
            smr.addStringParam("available", available_bed.getText().toString());
            smr.addStringParam("user_id", user_id);

//        smr.addStringParam("userid", userid);
//        smr.addStringParam("caption", caption);
//        MyApplication.getInstance().addToRequestQueue(smr);

            //I used this because it was sending the file twice to the server
            smr.setRetryPolicy(
                    new DefaultRetryPolicy(
                            0,
                            -1,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                    )
            );
            RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
            mRequestQueue.add(smr);
            mRequestQueue.start();

//            XuberApplication.getInstance().addToRequestQueue(jsonObjectRequest);

        }
    }

    public void goToEmergencyServicesActivity() {
        Intent mainIntent = new Intent(getActivity(), EmergencyServicesActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        getActivity().finish();
    }

    public void displayMessage(String toastString) {
        utils.print("displayMessage", "" + toastString);
        Snackbar.make(getActivity().getCurrentFocus(), toastString, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    @Override
    public void onClick(View v) {

    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(getActivity(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        String realPath = "";
        if (cursor == null) {
            realPath = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            realPath = cursor.getString(idx);
        }
        if (cursor != null) {
            cursor.close();
        }

        return realPath;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
////        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null || requestCode == REQUEST_FILE_CODE) {
//        if (requestCode == IMAGE_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK && data != null) {
////            if (resultCode != RESULT_CANCELED) {
//            Uri uri = data.getData();
//
////            assert uri != null;
//            String uriString = getRealPathFromURIPath(uri, getActivity());
////            String uriString = uri.toString();
//            myFile = new File(uriString);
//            filepath = myFile.getAbsolutePath();
//
////            String[] filePathColumn = {MediaStore.Images.Media.DATA};
////            Cursor cursor = getContentResolver().query(filepath, filePathColumn, null, null, null);
////            cursor.moveToFirst();
////            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
////            String picturePath = cursor.getString(columnIndex);
////            cursor.close();
////            try {
////                InputStream inputStream = getContentResolver().openInputStream(filepath);
////                bitmap = BitmapFactory.decodeStream(inputStream);
////                img.setImageBitmap(bitmap);
////            } catch (FileNotFoundException e) {
////                e.printStackTrace();
////            }
////                }
//
//            /*galleryPhoto.setPhotoUri(data.getData());
//            photoPath = galleryPhoto.getPath();*/
//
////            try {
////                Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(256, 256).getBitmap();
////                img.setImageBitmap(bitmap);
////                ImagesAqar(count_image, photoPath);
////                    selectedImage = photoPath;
////            filePath = data.getData();
////            try {
////                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
////                imageView.setImageBitmap(bitmap);
//
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}
