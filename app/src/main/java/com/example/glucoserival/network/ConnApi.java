package com.example.glucoserival.network;

import com.example.glucoserival.model.AddGlucose;
import com.example.glucoserival.model.ApplyAppointmentResponse;
import com.example.glucoserival.model.AppointmentStatusModel;
import com.example.glucoserival.model.DoctorCategory;
import com.example.glucoserival.model.DoctorDashboardData;
import com.example.glucoserival.model.DoctorList;
import com.example.glucoserival.model.GlucoseList;
import com.example.glucoserival.model.MedicineModel;
import com.example.glucoserival.model.PatientDashboard;
import com.example.glucoserival.model.SuggestionModel;
import com.example.glucoserival.model.SuggestionResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

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

    @FormUrlEncoded
    @POST("login/add_glucose_level")
    Call<AddGlucose> submitGlucoseData(@Field("user_id") String userId,
                                       @Field("value_of_glucose") String glucoseValue,
                                       @Field("date") String date);

    @FormUrlEncoded
    @POST("login/get_glucose_level_list")
    Call<GlucoseList> getGlucoseList(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("login/add_prescription")
    Call<JsonObject> addPrescription(@Field("user_id") String userId,
                                      @Field("appointment_id") String appointment_id,
                                      @Field("medicine_name") String medicine_name,
                                      @Field("before_or_after_eat") String before_or_after_eat,
                                      @Field("morning") String morning,
                                      @Field("noon") String noon,
                                      @Field("night") String night,
                                      @Field("how_many_days") String how_many_days);

    @FormUrlEncoded
    @POST("login/get_prescription")
    Call<MedicineModel> getPrescription(@Field("user_id") String userId,
                                        @Field("appointment_id") String appointment_id);

    @FormUrlEncoded
    @POST("login/get_suggestion")
    Call<SuggestionModel> get_suggestion(@Field("appointment_id") String appointment_id);

    @FormUrlEncoded
    @POST("login/add_suggestion")
    Call<SuggestionResponse> add_suggestion(@Field("appointment_id") String appointment_id,
                                            @Field("suggestion") String suggestion);


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
