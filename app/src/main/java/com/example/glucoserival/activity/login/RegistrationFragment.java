package com.example.glucoserival.activity.login;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glucoserival.R;
import com.example.glucoserival.activity.home.MainActivity;
import com.example.glucoserival.helper.AppData;
import com.example.glucoserival.helper.SQLiteHandler;
import com.example.glucoserival.helper.SessionManager;
import com.example.glucoserival.network.ConnApi;
import com.example.glucoserival.network.MyNetwork;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationFragment extends Fragment {

    private EditText userName;
    private TextView dateOfBirth;
    private EditText mobile;
    private EditText address;
    private EditText userEmail;
    private EditText password;
    private EditText repeatPassword;
    private Button registration;
    private SQLiteHandler myDB;
    private SessionManager sessionManager;
    private AppData appData;
    private RadioGroup userTypeRG;
    private Calendar dateOfBirthCal;
    private SimpleDateFormat simpleDateFormat;

    String userNameString,userTypeString,userEmailString,passwordString,repeatPasswordString,birthDayString,mobileNumString,addresString;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view=inflater.inflate(R.layout.fragment_registration, container, false);

        userName= (EditText) view.findViewById(R.id.nameET);
        userEmail= (EditText) view.findViewById(R.id.emailET);
        password= (EditText) view.findViewById(R.id.passET);
        dateOfBirth= (TextView) view.findViewById(R.id.birthdayTV);
        mobile= (EditText) view.findViewById(R.id.mobileET);
        address= (EditText) view.findViewById(R.id.addressET);
        repeatPassword= (EditText) view.findViewById(R.id.repeatPassET);
        registration= (Button) view.findViewById(R.id.registrationBTN);
        userTypeRG=view.findViewById(R.id.userTypeRG);
        myDB =new SQLiteHandler(getActivity().getApplicationContext());
        sessionManager=new SessionManager(getActivity().getApplicationContext());
        appData=new AppData(getContext());
        dateOfBirthCal=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("dd MMM yyyy");

        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateOfBirthCal.set(year,month,dayOfMonth);
                        dateOfBirth.setText(simpleDateFormat.format(dateOfBirthCal.getTime()));
                    }
                },dateOfBirthCal.get(Calendar.YEAR),dateOfBirthCal.get(Calendar.MONTH),dateOfBirthCal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();

            }
        });


        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                userNameString=userName.getText().toString().trim();
                userEmailString=userEmail.getText().toString().trim();
                birthDayString=getFormattedDateForPrentSystem(dateOfBirthCal);
                mobileNumString=mobile.getText().toString().trim();
                addresString=address.getText().toString().trim();
                passwordString=password.getText().toString().trim();
                repeatPasswordString=repeatPassword.getText().toString().trim();
                userTypeString=getUserTypeShortCode(((RadioButton)view.findViewById(userTypeRG.getCheckedRadioButtonId())).getText().toString());


                if (!userNameString.isEmpty() && !userEmailString.isEmpty() && !passwordString.isEmpty()
                        && !repeatPasswordString.isEmpty() && !userTypeString.isEmpty() && !userTypeString.equals("null")
                        && !birthDayString.isEmpty()&& !mobileNumString.isEmpty()&& !addresString.isEmpty()){

                        if(passwordString.equals(repeatPasswordString)){

                            Calendar calendar= Calendar.getInstance();
                            final String userId= String.valueOf((int)calendar.getTimeInMillis());

                            final ProgressDialog progressDialog = new ProgressDialog(getContext());
                            progressDialog.setMessage("Please wait...");
                            progressDialog.show();

                            MyNetwork.getRetrofit().create(ConnApi.class).userRegistration(userNameString,userEmailString,birthDayString,userTypeString,mobileNumString,addresString,passwordString).enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    String responseMsg="";
                                    if (response.isSuccessful()) {

                                        try {
                                            JsonObject responseBodyJSON = response.body();
                                            responseMsg = String.valueOf(responseBodyJSON.get("message"));
                                            if (responseBodyJSON.get("status").getAsString().equals("success")) {
                                                String userIdString = responseBodyJSON.getAsJsonPrimitive("user_id").getAsString();
                                                JsonArray userInfoArray = responseBodyJSON.getAsJsonArray("user_info");
                                                JsonObject jsonObject = (JsonObject) userInfoArray.get(0);
                                                String userTypeString = jsonObject.getAsJsonPrimitive("user_type").getAsString();
                                                String userNameString = jsonObject.getAsJsonPrimitive("name").getAsString();
                                                String userAddressString = jsonObject.getAsJsonPrimitive("address").getAsString();
                                                String userPhoneString = jsonObject.getAsJsonPrimitive("mobile").getAsString();
                                                String emailString = jsonObject.getAsJsonPrimitive("email").getAsString();
                                                String isMobileVerified = jsonObject.getAsJsonPrimitive("mobile_varify_status").getAsString();
                                                if (isMobileVerified.equals("0")){
                                                    appData.setMobileVerificationStatus(false);
                                                }else {
                                                    appData.setMobileVerificationStatus(true);
                                                }
                                                sessionManager.setLogin(true);
                                                appData.setUserID(userIdString);
                                                appData.setUserType(userTypeString);
                                                appData.setUserName(userNameString);
                                                appData.setUserAddress(userAddressString);
                                                appData.setUserPhone(userPhoneString);
                                                appData.setUserEmail(emailString);
                                                Toast.makeText(getContext(), responseMsg, Toast.LENGTH_SHORT).show();
                                                getActivity().startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                                                getActivity().finish();
                                                progressDialog.dismiss();
                                            } else {
                                                Toast.makeText(getContext(), responseMsg, Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                        }
                                        } catch (JsonParseException e) {
                                            Toast.makeText(getContext(), "Parsing Error!!", Toast.LENGTH_SHORT).show();
                                            e.printStackTrace();
                                        }catch (Exception e) {
                                            Toast.makeText(getContext(), "Unknown Error!!", Toast.LENGTH_SHORT).show();
                                            e.printStackTrace();
                                        } finally {
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Log.d("LoginConError", t.toString());
                                    Toast.makeText(getContext(), "Network Error!!!", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });

//                            myDB.addUserToAccountRegistration(userId,userNameString,userTypeString,userEmailString,passwordString);
//                            getActivity().startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
//                            sessionManager.setLogin(true);
//                            appData.setUserId(userId);
//                            getActivity().finish();
//                            Toast.makeText(getActivity().getApplicationContext(),"Welcome!!! "+ userNameString +" \n Registration successfully", Toast.LENGTH_LONG).show();

                        }else {

                            Toast.makeText(getActivity().getApplicationContext(),"Password don't matched!!!", Toast.LENGTH_LONG).show();

                        }

                    }else {

                        Toast.makeText(getActivity().getApplicationContext(),"Please enter all data first!!!", Toast.LENGTH_LONG).show();

                    }
                }
        });





        return view;
    }


    String getUserTypeShortCode(String userType){
        switch (userType){
            case "Patient":
                return "P";
            case "Doctor":
                return "D";
            default:
                return "null";
        }
    }

    public String getFormattedDateForPrentSystem(Calendar dateCalendar) {
        return new SimpleDateFormat("yyyy-MM-dd").format(dateCalendar.getTime());
    }
}
