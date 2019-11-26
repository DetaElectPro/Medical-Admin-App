package com.hostpital.hospitaladmin.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;


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

public class AmbulanceActivity extends AppCompatActivity {

    public Context context = AmbulanceActivity.this;
    public Activity activity = AmbulanceActivity.this;

    private static final String TAG = "AmbulanceActivity";
    private static final int ACTIVITY_NUM = 2;
//    private BottomNavigationView bottomNavigationView;

    CustomDialog customDialog;
    Boolean isInternet;
    Utilities utils = new Utilities();

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);

//        // Hide ActionBar
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }

//        setupBottomNavigationView();

        findViewById();

        token = SharedPreferenceUtil.getStringValue(context, "access_token");
//        checkUserToken();

    }

//    private void setupBottomNavigationView() {
//        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
//        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
//        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
//        BottomNavigationViewHelper.enableNavigation(context, this, bottomNavigationViewEx);
//        Menu menu = bottomNavigationViewEx.getMenu();
//        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
//        menuItem.setChecked(true);
//    }

    private void findViewById() {

    }

    private void checkUserToken() {
        if (new InternetConnection().isInternetOn(AmbulanceActivity.this)) {
            customDialog = new CustomDialog(activity);
            customDialog.setCancelable(false);
            customDialog.show();


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLHelper.check_token, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    customDialog.dismiss();
                    utils.print("Check Token Response", response.toString());

                    try {
                        if (response != null && response.getBoolean("status")) {
                            SharedPreferenceUtil.storeStringValue(context, "access_token", response.optString("access_token"));
                        } else {
                            SharedPreferenceUtil.storeStringValue(context, "access_token", "");
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

    public void goToLogin1Activity() {
        Intent mainIntent = new Intent(activity, Login1Activity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        activity.finish();
    }

    public void displayMessage(String toastString) {
        utils.print("displayMessage", "" + toastString);
        Snackbar.make(getCurrentFocus(), toastString, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

}
