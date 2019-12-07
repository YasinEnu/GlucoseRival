package com.example.glucoserival.activity.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.glucoserival.R;
import com.example.glucoserival.activity.AddSuggestionActivity;
import com.example.glucoserival.activity.AppointmentListActivity;
import com.example.glucoserival.activity.DoctorSearchActivity;
import com.example.glucoserival.activity.UpdateProfile;
import com.example.glucoserival.activity.login.LoginActivity;
import com.example.glucoserival.helper.AppData;
import com.example.glucoserival.helper.SessionManager;
import com.example.glucoserival.network.ConnApi;
import com.example.glucoserival.network.MyNetwork;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button logoutButton;
    private AppData appData;
//    private TextView userName,emailTV,totalAppointMentTV,nextAppointMent;
    private ImageView userTypeIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appData=new AppData(this);
//        userTypeIV=findViewById(R.id.userTypeIV);
//        userName=findViewById(R.id.userName);
//        emailTV=findViewById(R.id.emailTV);
//        totalAppointMentTV=findViewById(R.id.totalAppointmentTV);
        if (appData.getUserType().equalsIgnoreCase("P")){
//            userName.setText("Welcome "+appData.getUserName());
//            userTypeIV.setImageResource(R.drawable.girl);
//            nextAppointMent=findViewById(R.id.nextAppointmentDateTV);
//            emailTV.setText(appData.getUserEmail());
            replaceFragment(new PatientNewFragment());
        }else {
//            userName.setText("Welcome Doctor");
//            userTypeIV.setImageResource(R.drawable.doctor);
//            emailTV.setText(appData.getUserEmail());
            replaceFragment(new DoctorFragment());

        }
        if (!appData.isMobileVerified()){
            showVerificationNumberOrCodeDialog("Enter a valid number",true);
        }

        Log.e("EMAIL",appData.getUserPhone());

    }

    private void showVerificationNumberOrCodeDialog(String title, final boolean isMobile) {

        AlertDialog.Builder aD=new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.mobile_verification_dialog, null);
        final EditText editText=dialogView.findViewById(R.id.mobileNumET);

        aD.setView(dialogView);
        aD.setTitle(title);
        aD.setCancelable(false);
        aD.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (isMobile){
                    if (editText.getText().toString().trim().length()>10){

                        verifyMobileNum(editText.getText().toString().trim(),appData.getUserID());

                    }else {
                        Toast.makeText(MainActivity.this, "Number must be greater than 10 digit", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (editText.getText().toString().trim().length()>3){

                        sentVerificationCode(editText.getText().toString().trim(),appData.getUserID());

                    }else {
                        Toast.makeText(MainActivity.this, "Number must be greater than 3 digit", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
//        aD.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
        AlertDialog dialog = aD.create();
        dialog.show();
    }


    private void verifyMobileNum(final String mobile, String userId) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ConnApi connApi = MyNetwork.getRetrofit().create(ConnApi.class);
        connApi.verifyMobile(mobile, userId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String responseMsg="";
                if (response.isSuccessful()) {
                    try {
                        JsonObject responseBodyJSON = response.body();
                        responseMsg = responseBodyJSON.get("message").getAsString();
                        if (responseBodyJSON.get("status").getAsString().equals("success")) {
                            Toast.makeText(MainActivity.this, responseMsg, Toast.LENGTH_SHORT).show();
                            showVerificationNumberOrCodeDialog("Enter Verification Code",false);
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(MainActivity.this, responseMsg, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JsonParseException e) {
                        Toast.makeText(MainActivity.this, "Parsing Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Unknown Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } finally {
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("LoginConError", t.toString());
                Toast.makeText(MainActivity.this, "Network Error!!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }
    private void sentVerificationCode(final String code, String userId) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ConnApi connApi = MyNetwork.getRetrofit().create(ConnApi.class);
        connApi.sendVerificationCode(userId, code).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                String responseMsg="";
                if (response.isSuccessful()) {
                    try {
                        JsonObject responseBodyJSON = response.body();
                        responseMsg = responseBodyJSON.get("message").getAsString();
                        if (responseBodyJSON.get("status").getAsString().equals("success")) {
                            appData.setMobileVerificationStatus(true);
                            Toast.makeText(MainActivity.this, responseMsg, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, responseMsg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JsonParseException e) {
                        Toast.makeText(MainActivity.this, "Parsing Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Unknown Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } finally {
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("LoginConError", t.toString());
                Toast.makeText(MainActivity.this, "Network Error!!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if (appData.getUserType().equalsIgnoreCase("P")){
            getMenuInflater().inflate(R.menu.patient_dashboard, menu);
        }else {
            getMenuInflater().inflate(R.menu.doctor_dashboard, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_appointment) {
            startActivity(new Intent(MainActivity.this, DoctorSearchActivity.class));
            return true;
        }
        if (id == R.id.action_edit_profile) {
            startActivity(new Intent(MainActivity.this, UpdateProfile.class));
            return true;
        }
        if (id == R.id.action_appointment_list) {
            startActivity(new Intent(MainActivity.this, AppointmentListActivity.class));
            return true;
        }
        if (id == R.id.action_add_suggestion) {
            startActivity(new Intent(MainActivity.this, AddSuggestionActivity.class));
            return true;
        }
        if (id == R.id.action_logout) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            new SessionManager(MainActivity.this).setLogin(false);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

}
