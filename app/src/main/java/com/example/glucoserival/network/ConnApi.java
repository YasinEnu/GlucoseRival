package com.example.glucoserival.network;

import com.example.glucoserival.model.ApplyAppointmentResponse;
import com.example.glucoserival.model.AppointmentStatusModel;
import com.example.glucoserival.model.DoctorCategory;
import com.example.glucoserival.model.DoctorDashboardData;
import com.example.glucoserival.model.DoctorList;
import com.example.glucoserival.model.PatientDashboard;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ConnApi {

    @FormUrlEncoded
    @POST("login/login_authentication")
    Call<JsonObject> authUser(@Field("mobile") String mobile
            , @Field("password") String password);
    @FormUrlEncoded
    @POST("login/mobile_number_varification")
    Call<JsonObject> verifyMobile(@Field("mobile") String mobile
            , @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("login/get_doctor_category")
    Call<DoctorCategory> getDocCat(@Field("Id") String id);

    @FormUrlEncoded
    @POST("login/get_patient_dashboard_data")
    Call<PatientDashboard> getPatientDashboardData(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("login/get_doctor_dashboard_data")
    Call<DoctorDashboardData> getDoctorDashboardData(@Field("doctor_id") String user_id);

    @FormUrlEncoded
    @POST("login/status_change_doctor_appointment")
    Call<AppointmentStatusModel> setAppointmentStatus(@Field("appointment_id") String user_id, @Field("status") String status);


    @FormUrlEncoded
    @POST("login/get_varification_code")
    Call<JsonObject> sendVerificationCode(@Field("user_id") String user_id
            , @Field("code") String code);

    @FormUrlEncoded
    @POST("login/get_doctor_list")
    Call<DoctorList> getDocList(@Field("name") String name
            , @Field("category_id") String category_id, @Field("present_hospital") String present_hospital,
                                @Field("post") String post);

    @FormUrlEncoded
    @POST("login/send_doctor_appointment")
    Call<ApplyAppointmentResponse> applyForDocAppointment(@Field("user_id") String userId,
                                                          @Field("doctor_id") String doctorId,
                                                          @Field("date") String appointmentDate);


//    @Multipart
//    @POST("login/add_user")
//    Call<JsonObject> userRegistration(
//            @Part("name") String fullName,
//            @Part("email") String email,
//            @Part("date_of_birth") String date_of_birth,
//            @Part("user_type") String user_type,
//            @Part("mobile") String userPhoneNumber,
//            @Part("address") String address,
//            @Part("password") String password,
//            @Part MultipartBody.Part userImage);
    @FormUrlEncoded
    @POST("login/add_user")
    Call<JsonObject> userRegistration(
            @Field("name") String fullName,
            @Field("email") String email,
            @Field("date_of_birth") String date_of_birth,
            @Field("user_type") String user_type,
            @Field("mobile") String userPhoneNumber,
            @Field("address") String address,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("login/update_user")
    Call<JsonObject> userUpdate(
            @Field("id") String id,
            @Field("name") String fullName,
            @Field("email") String email,
            @Field("date_of_birth") String date_of_birth,
            @Field("address") String address,
            @Field("edu_qua") String edu_qua,
            @Field("present_hospital") String present_hospital,
            @Field("post") String post,
            @Field("chamber") String chamber,
            @Field("chamber_address") String chamber_address,
            @Field("appointment_mobile") String appointment_mobile,
            @Field("Chamber_days") String chamber_days,
            @Field("category_id") String category_id);

//    userId,user_type,userPhoneNumber,fullName,address,image(file)

}
