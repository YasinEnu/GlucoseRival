package com.example.glucoserival.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glucoserival.R;
import com.example.glucoserival.activity.home.MainActivity;
import com.example.glucoserival.helper.AppData;
import com.example.glucoserival.helper.Utility;
import com.example.glucoserival.model.Category;
import com.example.glucoserival.model.DoctorCategory;
import com.example.glucoserival.network.ConnApi;
import com.example.glucoserival.network.MyNetwork;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfile extends AppCompatActivity {

    private static final int CODE_GALLERY = 100;
    private EditText name,email,address,edu,hospita,post,chamber,chamberAddress,appoinmentMobile,chamberDays;
    private TextView dateOfBirth;
    private Spinner category;
    private Button submit;
    private Calendar dateOfBirthCal;
    private SimpleDateFormat simpleDateFormat;
    private ArrayList<String> doctorCatAdapterList=new ArrayList<>();
    private ArrayList<Category> doctorCatList=new ArrayList<>();
    private ArrayAdapter<String> categoryAdapter;
    private AppData appData;
    private CircleImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        profileImage=findViewById(R.id.profile_image);
        appData=new AppData(this);
        name=findViewById(R.id.nameET);
        email=findViewById(R.id.emailET);
        address=findViewById(R.id.addressET);
        edu=findViewById(R.id.educationET);
        hospita=findViewById(R.id.hospitalET);
        post=findViewById(R.id.postET);
        chamber=findViewById(R.id.chamberET);
        chamberAddress=findViewById(R.id.chamberAddressET);
        appoinmentMobile=findViewById(R.id.appointmentMobileET);
        chamberDays=findViewById(R.id.chamberDaysET);
        dateOfBirth=findViewById(R.id.birthdayTV);
        category=findViewById(R.id.categorySpinner);
        submit=findViewById(R.id.updateBTN);
        setUserData(appData.getUserType(),appData.getUserName(),appData.getUserDOB(),
                appData.getUserAddress(),appData.getUserEmail(),appData.getUserPhone());
        dateOfBirthCal=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("dd MMM yyyy");
        categoryAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,doctorCatAdapterList);
        category.setAdapter(categoryAdapter);


        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(UpdateProfile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateOfBirthCal.set(year,month,dayOfMonth);
                        dateOfBirth.setText(simpleDateFormat.format(dateOfBirthCal.getTime()));
                    }
                },dateOfBirthCal.get(Calendar.YEAR),dateOfBirthCal.get(Calendar.MONTH),dateOfBirthCal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String nameString=name.getText().toString();
                String birthDayString= Utility.getFormattedDateForPrentSystem(dateOfBirthCal);
                String addressString=address.getText().toString();
                String emailString=email.getText().toString();
                if (appData.getUserType().equals("D")){
                    String eduString=edu.getText().toString();
                    String hospitalString=hospita.getText().toString();
                    String postString=post.getText().toString();
                    String chamberString=chamber.getText().toString();
                    String chamberAddressString=chamberAddress.getText().toString();
                    String appointmentMobileString=appoinmentMobile.getText().toString();
                    String chamberDaysString=chamberDays.getText().toString();
                    String categoryIdString=getCategoryIdString();
                    sendUpdatedDataToServer(appData.getUserID(),nameString,emailString,birthDayString,addressString,eduString,hospitalString,
                            postString,chamberString,chamberAddressString,appointmentMobileString,chamberDaysString,categoryIdString);

                }else {
                    sendUpdatedDataToServer(appData.getUserID(),nameString,emailString,birthDayString,addressString,"","",
                            "","","","","","");
                }

            }
        });

        if (appData.getUserType().equals("D")){
            ((LinearLayout)findViewById(R.id.doctorsPanel)).setVisibility(View.VISIBLE);
            getDocCat();
        }
        if (appData.getProfileImage()!=null && !appData.getProfileImage().isEmpty()&&!appData.getProfileImage().equals("null")){
            profileImage.setImageBitmap(getPicture(Uri.parse(appData.getProfileImage())));
        }
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(UpdateProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    ActivityCompat.requestPermissions(UpdateProfile.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            1);
                }else {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, CODE_GALLERY);
                }
            }
        });
    }

    private void setUserData(String userType, String userName, String userDOB, String userAddress,
                             String userEmail, String userPhone) {
        name.setText(userName);
        dateOfBirth.setText(userDOB);
        address.setText(userAddress);
        email.setText(userEmail);
    }

    private String getCategoryIdString() {
        return doctorCatList.get(category.getSelectedItemPosition()).getId();
    }

    private void getDocCat() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        ConnApi connApi = MyNetwork.getRetrofit().create(ConnApi.class);
        connApi.getDocCat("").enqueue(new Callback<DoctorCategory>() {
            @Override
            public void onResponse(Call<DoctorCategory> call, Response<DoctorCategory> response) {

                if (response.isSuccessful()) {

                    try {
                        DoctorCategory doctorCategory=response.body();
                        if (doctorCategory.getStatus().equals("success")) {
                            doctorCatList= (ArrayList<Category>) doctorCategory.getCategorys();
                            for (Category s:doctorCatList) {
                                doctorCatAdapterList.add(s.getName());
                            }
                            categoryAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(UpdateProfile.this, "Server Error!!!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JsonParseException e) {
                        Toast.makeText(UpdateProfile.this, "Parsing Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }catch (Exception e) {
                        Toast.makeText(UpdateProfile.this, "Unknown Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } finally {
                    }
                }

            }

            @Override
            public void onFailure(Call<DoctorCategory> call, Throwable t) {
                Log.d("LoginConError", t.toString());
                Toast.makeText(UpdateProfile.this, "Network Error!!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }
    private void sendUpdatedDataToServer(String id,String fullName,String email,String date_of_birth,String address,String edu_qua,
            String present_hospital,String post,String chamber,String chamber_address,String appointment_mobile,String chamber_days,
            String category_id) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        ConnApi connApi = MyNetwork.getRetrofit().create(ConnApi.class);
        connApi.userUpdate(id,fullName,email,date_of_birth,address,edu_qua,present_hospital,post,chamber,chamber_address,appointment_mobile,chamber_days,category_id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String responseMsg="";
                if (response.isSuccessful()) {

                    try {
                        JsonObject responseObject=response.body();
                        responseMsg=responseObject.get("message").getAsString();
                        if (responseObject.get("status").getAsString().equals("success")) {
//                            "id": "29",
//                                    "name": "Yasin Hosain",
//                                    "mobile": "01303215205",
//                                    "email": "",
//                                    "date_of_birth": "2019-05-11",
//                                    "address": "",
                            JsonArray userInfoArray = responseObject.getAsJsonArray("user_info");
                            JsonObject jsonObject = (JsonObject) userInfoArray.get(0);
                            String userIdString = jsonObject.getAsJsonPrimitive("id").getAsString();
                            String userTypeString = jsonObject.getAsJsonPrimitive("user_type").getAsString();
                            String userNameString = jsonObject.getAsJsonPrimitive("name").getAsString();
                            String userAddressString = jsonObject.getAsJsonPrimitive("address").getAsString();
                            String userPhoneString = jsonObject.getAsJsonPrimitive("mobile").getAsString();
                            String emailString = jsonObject.getAsJsonPrimitive("email").getAsString();

                            appData.setUserID(userIdString);
                            appData.setUserType(userTypeString);
                            appData.setUserName(userNameString);
                            appData.setUserAddress(userAddressString);
                            appData.setUserPhone(userPhoneString);
                            appData.setUserEmail(emailString);
                            startActivity(new Intent(UpdateProfile.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            finish();
                            Toast.makeText(UpdateProfile.this, responseMsg, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(UpdateProfile.this, responseMsg, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }catch (JsonParseException e) {
                        Toast.makeText(UpdateProfile.this, "Parsing Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }catch (Exception e) {
                        Toast.makeText(UpdateProfile.this, "Unknown Error!!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } finally {
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("LoginConError", t.toString());
                Toast.makeText(UpdateProfile.this, "Network Error!!!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == CODE_GALLERY) {
            appData.setPRofileImage(data.getDataString());
            profileImage.setImageBitmap(getPicture(data.getData()));
        }
    }

    public Bitmap getPicture(Uri selectedImage) {
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return BitmapFactory.decodeFile(picturePath);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    profileImage.callOnClick();

                } else {
                    Toast.makeText(UpdateProfile.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
