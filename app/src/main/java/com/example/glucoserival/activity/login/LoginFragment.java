package com.example.glucoserival.activity.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {



    private EditText userEmail;
    private EditText password;
    private Button login;
    private SQLiteHandler myDB;
    private SessionManager sessionManager;
    private AppData appData;
    private String userEmailString,passwordString;
//    private int classId=0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_login, container, false);
        userEmail= (EditText) v.findViewById(R.id.emailET);
        password= (EditText) v.findViewById(R.id.passET);
        login= (Button) v.findViewById(R.id.loginBTN);
        myDB=new SQLiteHandler(getActivity().getApplicationContext());
        sessionManager=new SessionManager(getActivity().getApplicationContext());
        appData=new AppData(getContext());


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEmailString=userEmail.getText().toString().trim();
                passwordString = password.getText().toString().trim();

                if (!userEmailString.isEmpty() && !passwordString.isEmpty()){

                    authenticateUserWithServer(userEmailString,passwordString);


                }else {

                    Toast.makeText(getActivity().getApplicationContext(),"All credentials are required", Toast.LENGTH_LONG).show();

                }



            }
        });

        return v;
    }


    private void authenticateUserWithServer(final String mobile, String pass) {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        ConnApi connApi = MyNetwork.getRetrofit().create(ConnApi.class);
        connApi.authUser(mobile, pass).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String responseMsg="";
                if (response.isSuccessful()) {

                    try {
                        JsonObject responseBodyJSON = response.body();
                        responseMsg = String.valueOf(responseBodyJSON.get("message"));
                        if (responseBodyJSON.get("status").getAsString().equals("success")) {
                            JsonArray userInfoArray = responseBodyJSON.getAsJsonArray("user_info");
                            JsonObject jsonObject = (JsonObject) userInfoArray.get(0);
                            String userIdString = jsonObject.getAsJsonPrimitive("id").getAsString();
                            String userTypeString = jsonObject.getAsJsonPrimitive("user_type").getAsString();
                            String userNameString = jsonObject.getAsJsonPrimitive("name").getAsString();
                            String userAddressString = jsonObject.getAsJsonPrimitive("address").getAsString();
                            String userPhoneString = jsonObject.getAsJsonPrimitive("mobile").getAsString();
                            String emailString = jsonObject.getAsJsonPrimitive("email").getAsString();
                            String isMobileVerified = jsonObject.getAsJsonPrimitive("mobile_varify_status").getAsString();
                            sessionManager.setLogin(true);
                            if (isMobileVerified.equals("0")){
                                appData.setMobileVerificationStatus(false);
                            }else {
                                appData.setMobileVerificationStatus(true);
                            }
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


    }



}
