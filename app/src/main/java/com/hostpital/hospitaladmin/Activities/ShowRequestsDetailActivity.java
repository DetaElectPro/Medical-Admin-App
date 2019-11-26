package com.hostpital.hospitaladmin.Activities;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.NetworkError;
import com.android.volley.error.NoConnectionError;
import com.android.volley.error.TimeoutError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;

import com.hostpital.hospitaladmin.R;
import com.hostpital.hospitaladmin.Utils.ConnectionHelper;
import com.hostpital.hospitaladmin.Utils.CustomDialog;
import com.hostpital.hospitaladmin.Utils.InternetConnection;
import com.hostpital.hospitaladmin.Utils.SharedPreferenceUtil;
import com.hostpital.hospitaladmin.Utils.URLHelper;
import com.hostpital.hospitaladmin.Utils.Utilities;
import com.hostpital.hospitaladmin.Utils.XuberApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShowRequestsDetailActivity extends AppCompatActivity {

    private View mMainView;
    private String token;

    TextView medical_name;
    TextView medical_name_specialties;
    TextView address;
    TextView price;
    TextView start_time;
    TextView end_time;
    TextView request_admin_name;
    TextView request_admin_phone;
    String id, stringCV;


    TextView user_address, job_title, field, registration_number, graduation_date, years_of_experience, cv;

    ConnectionHelper helper;
    CustomDialog customDialog;
    Boolean isInternet;
    Utilities utils = new Utilities();

    Button accept_request, cancel_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_requests_detail);
        initViews();
        token = SharedPreferenceUtil.getStringValue(this, "access_token");
//        token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xNjUuMjIuODYuMTkzXC9NZWRpY2FsXC9wdWJsaWNcL2FwaVwvbG9naW4iLCJpYXQiOjE1NjQ5MzMyMTUsImV4cCI6MTU2NjE0MjgxNSwibmJmIjoxNTY0OTMzMjE1LCJqdGkiOiJJQzNYZkRZZ05WWmJJMHRmIiwic3ViIjo0NCwicHJ2IjoiODdlMGFmMWVmOWZkMTU4MTJmZGVjOTcxNTNhMTRlMGIwNDc1NDZhYSJ9.vX8XCQ3WeJvMedhcOHeq-v1e2JOaZ7RB1Zgz2KoZDPY";
        checkUserToken();

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
//            int myInt = bundle.getInt(key, defaultValue);
            medical_name.setText(mBundle.getString("medical_name"));
            medical_name_specialties.setText(mBundle.getString("medical_name_specialties"));
            address.setText(mBundle.getString("address"));
            price.setText(mBundle.getString("price"));
            start_time.setText(mBundle.getString("start_time"));
            end_time.setText(mBundle.getString("end_time"));
            request_admin_name.setText(mBundle.getString("request_admin_name"));
            request_admin_phone.setText(mBundle.getString("request_admin_phone"));
            id = (mBundle.getString("id"));

            user_address.setText(mBundle.getString("user_address"));
            job_title.setText(mBundle.getString("job_title"));
            field.setText(mBundle.getString("field"));
            registration_number.setText(mBundle.getString("registration_number"));
            graduation_date.setText(mBundle.getString("graduation_date"));
            years_of_experience.setText(mBundle.getString("years_of_experience"));
            stringCV = (mBundle.getString("cv"));
        }

        accept_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept_request2();
            }
        });

        cancel_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_request();

            }
        });

        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShowRequestsDetailActivity.this, ShowPDFActivity.class);
//                Intent.ACTION_VIEW, Uri.parse(pdf_url);
                i.putExtra("show_cv", stringCV);
                ShowRequestsDetailActivity.this.startActivity(i);
            }
        });

//        Bundle mBundle = getIntent().getExtras();
//        if (mBundle != null) {
//            mEmailTitle.setText(mBundle.getString("title"));
//            mSource.setText(mBundle.getString("source"));
//            mEmailDetails.setText(mBundle.getString("details"));
//            mEmailTime.setText(mBundle.getString("time"));
//            mImage.setImageResource(mBundle.getInt("icon"));

//            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

//            Glide.with(this).load(mBundle.getString("icon")).apply(requestOptions).into(mImage);
//        }

    }

    private void cancel_request() {
        if (new InternetConnection().isInternetOn(ShowRequestsDetailActivity.this)) {
            customDialog = new CustomDialog(ShowRequestsDetailActivity.this);
            customDialog.setCancelable(false);
            customDialog.show();
            JSONObject object = new JSONObject();
            try {
                object.put("id", id);
                object.put("status", 4);
//                object.put("password", password.getText().toString());

                utils.print("Login Request", "" + object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLHelper.accept_request, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    customDialog.dismiss();
                    utils.print("Login Response", response.toString());
                    try {
//                        SharedPreferenceUtil.storeStringValue(ShowRequestsDetailActivity.this, "access_token", response.optString("access_token"));

                        JSONObject user = response.getJSONObject("user");

//                        if (user.getInt("status") == 1){
//                            goToLogin2Activity();
//                        }else if(user.getInt("status") == 2){
//                            goToLogin3Activity();
//                        } else if(user.getInt("status") == 3){
//                            goToMainActivity();
//                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    customDialog.dismiss();
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
                        accept_request();
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
                    return headers;
                }
            };

            XuberApplication.getInstance().addToRequestQueue(jsonObjectRequest);
        }
    }

    private void accept_request() {
//        displayMessage(getString(R.string.accept_order));
        Toast.makeText(getApplicationContext(), "Order Accepted Successfully", Toast.LENGTH_SHORT).show();
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        this.finish();

    }

    private void checkUserToken() {

    }

    private void initViews() {
        medical_name = (TextView) findViewById(R.id.medical_name);
        medical_name_specialties = (TextView) findViewById(R.id.medical_name_specialties);
        address = (TextView) findViewById(R.id.address);
        price = (TextView) findViewById(R.id.price);
        start_time = (TextView) findViewById(R.id.start_time);
        end_time = (TextView) findViewById(R.id.end_time);
        request_admin_name = (TextView) findViewById(R.id.request_admin_name);
        request_admin_phone = (TextView) findViewById(R.id.request_admin_phone);

        user_address = (TextView) findViewById(R.id.user_address);
        job_title = (TextView) findViewById(R.id.job_title);
        field = (TextView) findViewById(R.id.field);
        registration_number = (TextView) findViewById(R.id.registration_number);
        graduation_date = (TextView) findViewById(R.id.graduation_date);
        years_of_experience = (TextView) findViewById(R.id.years_of_experience);
        cv = (TextView) findViewById(R.id.cv);

        accept_request = (Button) findViewById(R.id.accept_request);
        cancel_request = (Button) findViewById(R.id.cancel_request);
    }

    private void accept_request2() {
        if (new InternetConnection().isInternetOn(ShowRequestsDetailActivity.this)) {
            customDialog = new CustomDialog(ShowRequestsDetailActivity.this);
            customDialog.setCancelable(false);
            customDialog.show();
            JSONObject object = new JSONObject();
            try {
                object.put("id", id);
                object.put("status", 3);
//                object.put("password", password.getText().toString());

                utils.print("Login Request", "" + object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLHelper.accept_request, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    customDialog.dismiss();
                    utils.print("Login Response", response.toString());
//                    try {
//                        SharedPreferenceUtil.storeStringValue(ShowRequestsDetailActivity.this, "access_token", response.optString("access_token"));
                    accept_request();
//                        JSONObject user = response.getJSONObject("user");

//                        if (user.getInt("status") == 1){
//                            goToLogin2Activity();
//                        }else if(user.getInt("status") == 2){
//                            goToLogin3Activity();
//                        } else if(user.getInt("status") == 3){
//                            goToMainActivity();
//                        }

//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    customDialog.dismiss();
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
                        accept_request();
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
                    return headers;
                }
            };

            XuberApplication.getInstance().addToRequestQueue(jsonObjectRequest);

        }

    }

    public void displayMessage(String toastString) {
        utils.print("displayMessage", "" + toastString);
        Snackbar.make(getCurrentFocus(), toastString, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }
}
