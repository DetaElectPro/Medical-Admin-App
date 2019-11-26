package com.hostpital.hospitaladmin.Activities;

import android.content.Intent;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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

public class ShowActiveRequestsDetailActivity extends AppCompatActivity {

    private View mMainView;
    private String token, id;

    TextView medical_name;
    TextView medical_name_specialties;
    TextView address;
    TextView price;
    TextView start_time;
    TextView end_time;
    TextView request_admin_name;
    TextView request_admin_phone;
    RatingBar simpleRatingBar;
    Float ratingBar;
    int intRatingBar;
    EditText note, recommendations;

    ConnectionHelper helper;
    CustomDialog customDialog;
    Boolean isInternet;
    Utilities utils = new Utilities();

    Button finish_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_active_requests_detail);

        initViews();
        token = SharedPreferenceUtil.getStringValue(this, "access_token");

//        checkUserToken();

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            medical_name.setText(mBundle.getString("medical_name"));
            medical_name_specialties.setText(mBundle.getString("medical_name_specialties"));
            address.setText(mBundle.getString("address"));
            price.setText(mBundle.getString("price"));
            start_time.setText(mBundle.getString("start_time"));
            end_time.setText(mBundle.getString("end_time"));
            request_admin_name.setText(mBundle.getString("request_admin_name"));
            request_admin_phone.setText(mBundle.getString("request_admin_phone"));
            id = (mBundle.getString("id"));
        }

        ratingBar = simpleRatingBar.getRating();
        intRatingBar = ratingBar.intValue();
//        simpleRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                ratingBar = simpleRatingBar.getRating(rating);
//            }
//        });

        finish_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish_request2();
            }
        });
    }

    private void finish_request() {
//        displayMessage(getString(R.string.accept_order));
        Toast.makeText(getApplicationContext(), "Order Accepted Successfully", Toast.LENGTH_SHORT).show();
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        this.finish();

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
        simpleRatingBar = (RatingBar) findViewById(R.id.simpleRatingBar);
        finish_request = (Button) findViewById(R.id.accept_request);

        note = (EditText) findViewById(R.id.note);
        recommendations = (EditText) findViewById(R.id.recommendations);
    }

    private void finish_request2() {
        if (new InternetConnection().isInternetOn(ShowActiveRequestsDetailActivity.this)) {
            customDialog = new CustomDialog(ShowActiveRequestsDetailActivity.this);
            customDialog.setCancelable(false);
            customDialog.show();
            JSONObject object = new JSONObject();
            try {
                object.put("id", id);
                object.put("status", 6);
                object.put("notes", note.getText().toString());
                object.put("recommendation", recommendations.getText().toString());
                object.put("rating", intRatingBar);

                utils.print("Show Active Request", "" + object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLHelper.accept_request, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    customDialog.dismiss();
                    utils.print("Show Active Request", response.toString());
                    //                        SharedPreferenceUtil.storeStringValue(ShowRequestsDetailActivity.this, "access_token", response.optString("access_token"));
//                    utils.print("Login Response", response.toString());
//                        JSONObject user = response.getJSONObject("user");
                    finish_request();
//                        if (user.getInt("status") == 1){
//                            goToLogin2Activity();
//                        }else if(user.getInt("status") == 2){
//                            goToLogin3Activity();
//                        } else if(user.getInt("status") == 3){
//                            goToMainActivity();
//                        }

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
                        finish_request2();
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
