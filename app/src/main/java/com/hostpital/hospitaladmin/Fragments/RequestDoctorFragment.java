package com.hostpital.hospitaladmin.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


//import com.android.volley.AuthFailureError;
//import com.android.volley.NetworkError;
//import com.android.volley.NetworkResponse;
//import com.android.volley.NoConnectionError;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.TimeoutError;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.NetworkError;
import com.android.volley.error.NoConnectionError;
import com.android.volley.error.TimeoutError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;

import com.hostpital.hospitaladmin.Activities.Login1Activity;
import com.hostpital.hospitaladmin.Activities.MainActivity;
import com.hostpital.hospitaladmin.Activities.MapsActivity;
import com.hostpital.hospitaladmin.Helpers.ServiceHandler;
import com.hostpital.hospitaladmin.Models.Category2;
import com.hostpital.hospitaladmin.Models.Category3;
import com.hostpital.hospitaladmin.R;
import com.hostpital.hospitaladmin.Utils.CustomDialog;
import com.hostpital.hospitaladmin.Utils.InternetConnection;
import com.hostpital.hospitaladmin.Utils.SharedPreferenceUtil;
import com.hostpital.hospitaladmin.Utils.URLHelper;
import com.hostpital.hospitaladmin.Utils.Utilities;
import com.hostpital.hospitaladmin.Utils.XuberApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by Arbab on 7/24/2019.
 */

public class RequestDoctorFragment extends Fragment implements View.OnClickListener {

    private ArrayList<Category2> categoriesList;
    private ArrayList<Category3> subCategoriesList;

    private View mMainView;
    Utilities utils = new Utilities();
    CustomDialog customDialog;
    final Calendar myCalendar = Calendar.getInstance();
    private static final String KEY_CATEGORY = "name";
    private static final String KEY_SUB_CATEGORY = "medical";
    private Spinner categorySpinner, sub_categorySpinner;
    private Button request;
    private EditText ed_hospital, ed_price, startDate, endDate, ed_address, hours;
    private String token;
    //    String medical_id;
//    ArrayList<String> _ids;
    ArrayList<String> _ids = new ArrayList<String>();
    //    int medical_id;
    //    private AutoCompleteTextView search_location;
//    private ProgressDialog pDialog;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog startDatePickerDialog;
    private DatePickerDialog endDatePickerDialog;
    private TextView getLocation;

    SweetAlertDialog pDialog, pDialog2;
    //    Category category1;
    Category2 cat;
    Category3 subCat;
    private Spinner PartSpinner;
//    List<String> PartName;
//    List<String> PartId;

    private final int REQUEST_CODE_PERMISSION = 55, PLACE_PICKER_REQUEST = 66, MY_PERMISSIONS_REQUEST_LOCATION = 44;
    private Double latitude, longitude;

    private String id;

    int currCategoryId, currSubCategoryId;
    ArrayAdapter<String> spinnerAdapter;
//     Category categoryDetails ;
//     List<String> sub_categoryList ;
//     ArrayAdapter sub_categoryAdapter ;

    public RequestDoctorFragment() {
    }

    public static RequestDoctorFragment newInstance() {
        RequestDoctorFragment fragment = new RequestDoctorFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_request_doctor, container, false);

        mMainView = inflater.inflate(R.layout.fragment_request_doctor, container, false);

//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add Request");
//        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        deleteCache(getContext());
        initViews();
        token = SharedPreferenceUtil.getStringValue(getActivity(), "access_token");
//        checkUserToken();

//        PartName=new ArrayList<>();
//        PartId=new ArrayList<>();

        categoriesList = new ArrayList<Category2>();
        subCategoriesList = new ArrayList<Category3>();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

//        startDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar c = Calendar.getInstance();
//                int mYear = c.get(Calendar.YEAR); // current year
//                int mMonth = c.get(Calendar.MONTH); // current month
//                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
//                // date picker dialog
//                startDatePickerDialog = new DatePickerDialog(getActivity(),
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
//                                // set day of month , month and year value in the edit text
//                                updateGraduateLabel();
//
//                            }
//                        }, mYear, mMonth, mDay);
//                startDatePickerDialog.show();
//            }
//        });
//
//
//        endDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar c = Calendar.getInstance();
//                int mYear = c.get(Calendar.YEAR); // current year
//                int mMonth = c.get(Calendar.MONTH); // current month
//                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
//                // date picker dialog
//                endDatePickerDialog = new DatePickerDialog(getActivity(),
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
//                                // set day of month , month and year value in the edit text
//                                endDate.setText(dayOfMonth + "/"
//                                        + (monthOfYear + 1) + "/" + year);
//
//                            }
//                        }, mYear, mMonth, mDay);
//                endDatePickerDialog.show();
//
//            }
//        });

//        displayLoader();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateStartDateLabel();
            }

        };

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEndDateLabel();
            }

        };

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
//        loadCategorySubCategoryDetails2();
        new GetCategories().execute();
//        PartList();
//        setDateTimeField();
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String cat_id = _ids.get(position);
                Category2 selectedCategory = categoriesList.get(position);
//                Category2 selectedCategory2 = (Category2) parent.getItemAtPosition(position);
                currCategoryId = selectedCategory.getId();
//                Toast.makeText(getActivity(), String.valueOf(currCategoryId), Toast.LENGTH_LONG).show();

                new GetSubCategories().execute();

//                Toast.makeText(
//                        getActivity(),
//                        parent.getItemAtPosition(position).toString() + " Selected" ,
//                        Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sub_categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String cat_id = _ids.get(position);
                Category3 selectedSubCategory = subCategoriesList.get(position);
//                Category2 selectedCategory2 = (Category2) parent.getItemAtPosition(position);
                currSubCategoryId = selectedSubCategory.getId();
//                Toast.makeText(getActivity(), String.valueOf(currSubCategoryId), Toast.LENGTH_LONG).show();

//                new GetSubCategories().execute();

//                Toast.makeText(
//                        getActivity(),
//                        parent.getItemAtPosition(position).toString() + " Selected" ,
//                        Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startAutocompleteActivity();
            }
        });

        return mMainView;
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();

//        txtCategory.setText("");

        for (int i = 0; i < categoriesList.size(); i++) {
            lables.add(categoriesList.get(i).getName());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.category_list, R.id.spinnerText, lables);

        // Drop down layout style - list view with radio button
//        spinnerAdapter
//                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        categorySpinner.setAdapter(spinnerAdapter);
    }

    private void populateSpinner2() {
        List<String> lables2 = new ArrayList<String>();

//        txtCategory.setText("");
//        lables2.clear();
        for (int i = 0; i < subCategoriesList.size(); i++) {
            lables2.add(subCategoriesList.get(i).getName());
        }

        // Creating adapter for spinner
        spinnerAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.sub_category_list, R.id.sub_spinnerText, lables2);

        // Drop down layout style - list view with radio button
//        spinnerAdapter
//                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        sub_categorySpinner.setAdapter(spinnerAdapter);
//        spinnerAdapter.notifyDataSetChanged();
//        spinnerAdapter.setNotifyOnChange(true);
//        spinnerAdapter.notifyDataSetChanged();
    }

    /**
     * Async task to get all food categories
     */
    private class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URLHelper.category, ServiceHandler.GET);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj.getJSONArray("data");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            cat = new Category2(
                                    catObj.getString("name"),
                                    catObj.getInt("id"));
                            categoriesList.add(cat);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            populateSpinner();
        }

    }

    private class GetSubCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            subCategoriesList.clear();
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URLHelper.by_category + currCategoryId, ServiceHandler.GET);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
//                    JSONObject jsonObj = new JSONObject(json);
                    JSONArray subCategories = new JSONArray(json);
//                    if (jsonObj != null) {
//                        JSONArray categories = jsonObj.getJSONArray();
                    for (int i = 0; i < subCategories.length(); i++) {
                        JSONObject catObj = (JSONObject) subCategories.get(i);

                        subCat = new Category3(
                                catObj.getString("name"),
                                catObj.getInt("id"));
                        subCategoriesList.add(subCat);
                    }
//                    spinnerAdapter.setNotifyOnChange(true);
//                    spinnerAdapter.notifyDataSetChanged();
//                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
//            adapter2.notifyDataSetChanged();
            populateSpinner2();
        }

    }

    private void updateStartDateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        startDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateEndDateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        endDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void startAutocompleteActivity() {
//        List<com.google.android.libraries.places.api.model.Place.Field> placeFields = new ArrayList<>(Arrays.asList(com.google.android.libraries.places.api.model.Place.Field.values()));
//        List<TypeFilter> typeFilters = new ArrayList<>(Arrays.asList(TypeFilter.values()));
// Create a RectangularBounds object.
//        RectangularBounds bounds = RectangularBounds.newInstance(
//                new LatLng(-33.880490, 151.184363),
//                new LatLng(-33.858754, 151.229596));
//        Intent autocompleteIntent = new  com.google.android.libraries.places.widget.
//                new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, placeFields)
//                        .setLocationBias(bounds)
//                        .setTypeFilter(typeFilters.get(0))
//                        .build(this);
//        startActivityForResult(autocompleteIntent, PLACE_PICKER_REQUEST);
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        startActivityForResult(intent, PLACE_PICKER_REQUEST);
//        startActivity(intent);
//        overridePendingTransition(R.anim.slide_up,R.anim.slide_up);

    }

    private void request() {
        if (new InternetConnection().isInternetOn(getActivity())) {
//            customDialog = new CustomDialog(getActivity());
//            customDialog.setCancelable(false);
//            customDialog.show();

            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();

            JSONObject object = new JSONObject();
            try {
                object.put("name", ed_hospital.getText().toString());
                object.put("start_time", startDate.getText().toString());
                object.put("end_time", endDate.getText().toString());
                object.put("price", ed_price.getText().toString());
                object.put("address", ed_address.getText().toString());
                object.put("status", 1);
                object.put("medical_id", currSubCategoryId);
                object.put("latitude", latitude);
                object.put("longitude", longitude);
                object.put("hours", hours.getText().toString());

                utils.print("Request Doctor Request", "" + object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLHelper.request_specialist, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    pDialog.dismiss();
                    utils.print("Request Doctor Response", String.valueOf(response));
                    Log.w("Request Doctor Response", String.valueOf(response));

                    pDialog2 = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                    pDialog2.setTitleText("Successful!!");
                    pDialog2.setContentText(getString(R.string.add_request));
                    pDialog2.setCancelable(false);
                    pDialog2.show();

                    pDialog2.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            pDialog2.dismiss();
                            goToMainActivity();
                        }
                    });

//                    displayMessage(getString(R.string.add_request));

//                    goToMainActivity();

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                    String json = null;
                    String Message;
                    NetworkResponse response = error.networkResponse;
                    utils.print("MyTest", "" + error);
                    utils.print("MyTestError", "" + error.networkResponse);

                    String body;
                    //get status code here
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    if(error.networkResponse.data!=null) {
                        try {
                            body = new String(error.networkResponse.data,"UTF-8");
                            utils.print("Doctor Body", "" + body);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }

                    if (error instanceof NoConnectionError) {
                        displayMessage(getString(R.string.oops_connect_your_internet));
                    } else if (error instanceof NetworkError) {
                        displayMessage(getString(R.string.oops_connect_your_internet));
                    } else if (error instanceof TimeoutError) {
                        request();
                    }

                }
            })
//                    ;
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json");
//                    headers.put("Content-Type", "multipart/form-data");
                    headers.put("Authorization", "Bearer " + token);
                    utils.print("Authorization", "" + token);
                    return headers;
                }
            };
            Log.i("Body", "Request body: " + new String(jsonObjectRequest.getBody()));

            XuberApplication.getInstance().addToRequestQueue(jsonObjectRequest);

        }
    }

    private void checkUserToken() {
        if (new InternetConnection().isInternetOn(getActivity())) {
//            customDialog = new CustomDialog(getActivity());
//            customDialog.setCancelable(false);
//            customDialog.show();

            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLHelper.check_token, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    pDialog.dismiss();
                    utils.print("Check Token Response", response.toString());

                    try {
                        if (response.getBoolean("status")) {
                            SharedPreferenceUtil.storeStringValue(getActivity(), "access_token", response.optString("access_token"));
                        } else {
                            SharedPreferenceUtil.storeStringValue(getActivity(), "access_token", "");
                            displayMessage(getString(R.string.please_try_login_again));
                            goToLogin1Activity();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                    String json = null;
                    String Message;
                    NetworkResponse response = error.networkResponse;
                    utils.print("MyTest", "" + error);
                    utils.print("MyTestError", "" + error.networkResponse);

                    if (error instanceof NoConnectionError) {
                        displayMessage(getString(R.string.oops_connect_your_internet));
                    } else if (error instanceof NetworkError) {
                        displayMessage(getString(R.string.oops_connect_your_internet));
                    } else if (error instanceof TimeoutError) {
                        checkUserToken();
                    }

                }
            })
//                    ;
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("X-Requested-With", "XMLHttpRequest");
                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }
            };

            XuberApplication.getInstance().addToRequestQueue(jsonObjectRequest);

        }
    }

    private void initViews() {

//        viewGroup = (ViewGroup) ((ViewGroup) mMainView
//                .findViewById(android.R.id.content)).getChildAt(0);

        categorySpinner = mMainView.findViewById(R.id.doctorFragment_category_spinner);
        sub_categorySpinner = mMainView.findViewById(R.id.doctorFragment_subcategory_spinner);
        request = mMainView.findViewById(R.id.doctorFragment_request_btn);
        ed_hospital = mMainView.findViewById(R.id.doctorFragment_keyword);
        ed_price = mMainView.findViewById(R.id.doctorFragment_price);
        getLocation = mMainView.findViewById(R.id.getLocation);
        ed_address = mMainView.findViewById(R.id.doctorFragment_address);
        hours = mMainView.findViewById(R.id.hours);

        startDate = mMainView.findViewById(R.id.doctorFragment_startDate);
//        startDate.setInputType(InputType.TYPE_NULL);
//        startDate.requestFocus();

        endDate = mMainView.findViewById(R.id.doctorFragment_endtDate);
//        PartSpinner = mMainView.findViewById(R.id.spinnerPart);

//        endDate.setInputType(InputType.TYPE_NULL);


//        search_location = mMainView.findViewById(R.id.doctorFragment_search_location);

    }

    private void displayLoader() {
//        pDialog = new ProgressDialog(getActivity());
//        pDialog.setMessage("Loading Data.. Please wait...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        pDialog.show();

    }

//    private void loadCategorySubCategoryDetails() {
////        pDialog = new ProgressDialog(getActivity());
////        pDialog.setMessage("Loading Data.. Please wait...");
////        pDialog.setIndeterminate(false);
////        pDialog.setCancelable(false);
////        pDialog.show();
//
//        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        pDialog.setTitleText("Loading");
//        pDialog.setCancelable(false);
//        pDialog.show();
//
//        if (new InternetConnection().isInternetOn(getActivity())) {
//            final List<Category> categoryList = new ArrayList<>();
//            final List<String> category = new ArrayList<>();
//
//
//
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                    (Request.Method.GET, URLHelper.category, null, new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject jsonObject) {
//                            pDialog.dismiss();
//                            try {
//                                JSONArray responseArray = jsonObject.getJSONArray("data");
//                                //Parse the JSON response array by iterating over it
//                                for (int i = 0; i < responseArray.length(); i++) {
//                                    JSONObject response = responseArray.getJSONObject(i);
////                                    String field_id = response.getString("id");
//                                    String string_category = response.getString(KEY_CATEGORY);
//                                    JSONArray sub_category = response.getJSONArray(KEY_SUB_CATEGORY);
//                                    List<String> subCategoryList = new ArrayList<>();
////                                    List<Integer> subCategoryList1 = new ArrayList<>();
//                                    for (int j = 0; j < sub_category.length(); j++) {
//                                        JSONObject sub_category_jsonObject = sub_category.getJSONObject(j);
//                                        String name = sub_category_jsonObject.getString("name");
//                                        medical_id = sub_category_jsonObject.getInt("id");
//                                        category1 = new Category();
//                                        medical_id = category1.getId();
//                                        subCategoryList.add(name);
////                                        subCategoryList.add(medical_id);
//                                    }
//                                    categoryList.add(new Category(string_category, medical_id, subCategoryList));
//                                    category.add(string_category);
//
//                                }
//
//                                final CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(),
//                                        R.layout.category_list, R.id.spinnerText, categoryList);
//                                categorySpinner.setAdapter(categoryAdapter);
//
//                                categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                    @Override
//                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                                        //Populate City list to the second spinner when
//                                        // a state is chosen from the first spinner
//                                        categoryDetails = categoryAdapter.getItem(position);
//                                        sub_categoryList = categoryDetails.getSubcategoty();
//                                       sub_categoryAdapter = new ArrayAdapter<>(getActivity(),
//                                                R.layout.sub_category_list, R.id.sub_spinnerText, sub_categoryList);
//                                        sub_categorySpinner.setAdapter(sub_categoryAdapter);
////                                        Toast.makeText(getActivity(),String.valueOf(medical_id),Toast.LENGTH_LONG).show();
////                                        categorySpinner = parent.getItemAtPosition(position).toString();
////                                        count = position; //this would give you the id of the selected item
//
//                                    }
//
//                                    @Override
//                                    public void onNothingSelected(AdapterView<?> parent) {
//
//                                    }
//                                });
//
//                                sub_categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                    @Override
//                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                        Category category2 = (Category) sub_categorySpinner.getSelectedItem();
//                                        int id2 = category2.getId();
////                                        int id2 = category1.getId();
//                                        Toast.makeText(getActivity(),String.valueOf(id2),Toast.LENGTH_LONG).show();
//                                    }
//
//                                    @Override
//                                    public void onNothingSelected(AdapterView<?> parent) {
//
//                                    }
//                                });
//
////                                categoryAdapter.notifyDataSetChanged();
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            pDialog.dismiss();
//                            String json = null;
//                            String Message;
//                            NetworkResponse response = error.networkResponse;
//                            utils.print("MyTest", "" + error);
//                            utils.print("MyTestError", "" + error.networkResponse);
//
//                            if (error instanceof NoConnectionError) {
//                                displayMessage(getString(R.string.oops_connect_your_internet));
//                            } else if (error instanceof NetworkError) {
//                                displayMessage(getString(R.string.oops_connect_your_internet));
//                            } else if (error instanceof TimeoutError) {
//                                loadCategorySubCategoryDetails();
//                            }
//
//                        }
//                    })
////                    ;
//            {
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("Content-Type", "application/json");
////                    headers.put("Accept", "application/json");
////                    headers.put("Content-Type", "multipart/form-data");
//                    headers.put("Authorization", "Bearer " + token);
//                    return headers;
//                }
//            };
//
//            XuberApplication.getInstance().addToRequestQueue(jsonObjectRequest);
//
//        }
//    }

    /*private void loadCategorySubCategoryDetails2() {

        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        if (new InternetConnection().isInternetOn(getActivity())) {
            final List<Category> categoryList = new ArrayList<>();
            final List<String> category = new ArrayList<>();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, URLHelper.category, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            pDialog.dismiss();
                            try {
                                JSONArray responseArray = jsonObject.getJSONArray("data");
                                //Parse the JSON response array by iterating over it
                                for (int i = 0; i < responseArray.length(); i++) {
                                    JSONObject response = responseArray.getJSONObject(i);
                                    String string_category = response.getString(KEY_CATEGORY);
                                    JSONArray sub_category = response.getJSONArray(KEY_SUB_CATEGORY);
                                    List<String> subCategoryList = new ArrayList<>();
//                                    List<String> _ids = new ArrayList<>();
//                                    _ids = new ArrayList<String>();
                                    for (int j = 0; j < sub_category.length(); j++) {
                                        JSONObject sub_category_jsonObject = sub_category.getJSONObject(j);
                                        String name = sub_category_jsonObject.getString("name");
//                                        String id = sub_category.getJSONObject(i).getString("id");
//                                        _ids.add(id);
//                                        medical_id = sub_category_jsonObject.getInt("id");
                                        subCategoryList.add(name);
                                    }
//                                    categoryList.add(new Category(string_category, subCategoryList, medical_id));
                                    category.add(string_category);

                                }
                                final CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(),
                                        R.layout.category_list, R.id.spinnerText, categoryList);
                                categorySpinner.setAdapter(categoryAdapter);

                                categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        //Populate City list to the second spinner when
                                        // a state is chosen from the first spinner
                                        Category categoryDetails = categoryAdapter.getItem(position);
                                        List<String> sub_categoryList = categoryDetails.getSubcategoty();
                                        ArrayAdapter sub_categoryAdapter = new ArrayAdapter<>(getActivity(),
                                                R.layout.sub_category_list, R.id.sub_spinnerText, sub_categoryList);
                                        sub_categorySpinner.setAdapter(sub_categoryAdapter);

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                                sub_categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                        Category category2 = (Category) sub_categorySpinner.getSelectedItem();
//                                        int id2 = category2.getId();
//                                        int id2 = category1.getId();
//                                        Toast.makeText(getActivity(),String.valueOf(id2),Toast.LENGTH_LONG).show();
                                        Category categoryDetails = categoryAdapter.getItem(position);
                                        int id2 =categoryDetails.getId();
                                        Toast.makeText(getActivity(),String.valueOf(id2),Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String json = null;
                            String Message;
                            NetworkResponse response = error.networkResponse;
                            utils.print("MyTest", "" + error);
                            utils.print("MyTestError", "" + error.networkResponse);

                            if (error instanceof NoConnectionError) {
                                displayMessage(getString(R.string.oops_connect_your_internet));
                            } else if (error instanceof NetworkError) {
                                displayMessage(getString(R.string.oops_connect_your_internet));
                            } else if (error instanceof TimeoutError) {
                                loadCategorySubCategoryDetails2();
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
//                    headers.put("Content-Type", "multipart/form-data");
                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }
            };

            XuberApplication.getInstance().addToRequestQueue(jsonObjectRequest);

        }
    }*/

    public void displayMessage(String toastString) {
        utils.print("displayMessage", "" + toastString);
        Snackbar.make(getActivity().getCurrentFocus(), toastString, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    private void setDateTimeField() {

        /*Calendar newCalendar = Calendar.getInstance();
        startDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                startDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));*/

        /*endDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                endDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));*/
    }

    public void goToLogin1Activity() {
        Intent mainIntent = new Intent(getActivity(), Login1Activity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        getActivity().finish();
    }

    public void goToMainActivity() {
        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        getActivity().finish();
    }

    @Override
    public void onClick(View view) {
        if (view == startDate) {
            startDatePickerDialog.show();
        } else if (view == endDate) {
            endDatePickerDialog.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        startAutocompleteActivity();
                    }
                }
                break;
        }
    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppCompatActivity.RESULT_OK) {
            switch (requestCode) {

                case PLACE_PICKER_REQUEST:

//                    Double result_latitude = data.getDoubleExtra("latitude", latitude);
//                    Double result_longitude = data.getDoubleExtra("longitude", longitude);
//                    utils.print("Request Doctor result_latitude", "" + result_latitude);
//                    utils.print("Request Doctor result_longitude", "" + result_longitude);
                    Double result_latitude = data.getDoubleExtra("latitude", 0.0d);
                    Double result_longitude = data.getDoubleExtra("longitude", 0.0d);
                    utils.print("Request Doctor result_latitude", "" + result_latitude);
                    utils.print("Request Doctor result_longitude", "" + result_longitude);

                    latitude = result_latitude;
                    longitude = result_longitude;


                    break;
            }
        }
    }

//    private void PartList() {
//        final ProgressDialog loading = new ProgressDialog(getActivity());
//        loading.setMessage("Please Wait...");
//        loading.show();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLHelper.sub_category,
//                new Response.Listener<String>() {
//
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            Log.d("JSON", response);
//                            loading.dismiss();
//                            JSONObject eventObject = new JSONObject(response);
//                            String error_status=eventObject.getString("error");
//                            if(error_status.equals("true")){
//                                String error_msg=eventObject.getString("msg");
//                                Toast.makeText(getActivity(), error_msg, Toast.LENGTH_SHORT).show();
//                            }
//                            else{
//                                Resouces(eventObject);
//                            }
//                        } catch (Exception e) {
//                            Log.d("Tag",e.getMessage());
//
//                        }
//                    }
//                },
//
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        loading.dismiss();
//                        Toast.makeText(getActivity(),error.toString(), Toast.LENGTH_LONG ).show();
//
//                    }
//                }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> map = new HashMap<String,String>();
//                return map;
//            }
//        };
//
////        RequestQueue requestQueue = Volley.newRequestQueue(this);
////        requestQueue.add(stringRequest);
//        XuberApplication.getInstance().addToRequestQueue(stringRequest);
//    }
//    /*hare to pares the json response in list and show to spener*/
//    public void Resouces(JSONObject Ward) {
//        String Resourc_Name;
//        String ResourceID;
//        PartName.clear();
//        PartId.clear();
//        PartName.add("Select Part");
//        PartId.add("Select Part");
//        try {
//            JSONArray projectNameArray = Ward.getJSONArray("Available Parts");
//            for (int i = 0; i <= projectNameArray.length(); i++) {
//                JSONObject obj = projectNameArray.getJSONObject(i);
//                Resourc_Name = obj.getString("name");
//                ResourceID = obj.getString("id");
//                Log.d("name", Resourc_Name);
//                PartName.add(Resourc_Name);
//                PartId.add(ResourceID);
//
//                //Log.d("Dropdown",Dropdown.get(i));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, PartName);
//        PartSpinner.setAdapter(adapter);
//        PartSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
//                // On selecting a spinner item
//                ((TextView) adapter.getChildAt(0)).setTextColor(Color.BLACK);
//                id = PartId.get(position);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//                // TODO Auto-generated method stub
//            }
//        });
//    }

}
