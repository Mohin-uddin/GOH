package com.ewnbd.goh.data.remote

import com.ewnbd.goh.data.model.request_all_class.*
import com.ewnbd.goh.data.model.request_all_class.basic_profile.BasicProfileRequest
import com.ewnbd.goh.data.model.request_all_class.forget.ForgetpasswordRequest
import com.ewnbd.goh.data.model.response_all_class.*
import com.ewnbd.goh.data.model.response_all_class.acceptActivityRequest.ActivityAcceptRequestResponse
import com.ewnbd.goh.data.model.response_all_class.acceptRequest.AcceptRequestResponse
import com.ewnbd.goh.data.model.response_all_class.activities.ActivitiesListResponse
import com.ewnbd.goh.data.model.response_all_class.activityRequestSend.ActivtyRequestSendResponse
import com.ewnbd.goh.data.model.response_all_class.activity_categorylist.ActivityCategoryListResponse
import com.ewnbd.goh.data.model.response_all_class.activity_rating.ActivityRatingResponse
import com.ewnbd.goh.data.model.response_all_class.actvityDetails.ActvityDetailsResponse
import com.ewnbd.goh.data.model.response_all_class.age.AgeListResponse
import com.ewnbd.goh.data.model.response_all_class.basicProfile.BasicProfileResponse
import com.ewnbd.goh.data.model.response_all_class.cancelActivity.CancelActivityResponse
import com.ewnbd.goh.data.model.response_all_class.chat.ChatListResponse
import com.ewnbd.goh.data.model.response_all_class.chatDetails.ChatDetailsResponse
import com.ewnbd.goh.data.model.response_all_class.codeMatch.CodeMatchResponse
import com.ewnbd.goh.data.model.response_all_class.createActivty.CreateActivityResponse
import com.ewnbd.goh.data.model.response_all_class.friendList.FriendListResponse
import com.ewnbd.goh.data.model.response_all_class.friendRequestList.FriendRequestListresponse
import com.ewnbd.goh.data.model.response_all_class.friendRequestWithdraw.FriendRequestWithdrawResponse
import com.ewnbd.goh.data.model.response_all_class.interestList.InterestListResponse
import com.ewnbd.goh.data.model.response_all_class.interstAddResposne.InterestAddResponse
import com.ewnbd.goh.data.model.response_all_class.issue.IssueResponse
import com.ewnbd.goh.data.model.response_all_class.login.LoginRequest
import com.ewnbd.goh.data.model.response_all_class.login.LoginResponse
import com.ewnbd.goh.data.model.response_all_class.mapSearch.MapSearchResponse
import com.ewnbd.goh.data.model.response_all_class.name_update.NameUpdateResponse
import com.ewnbd.goh.data.model.response_all_class.nearActivitys.NearActivitiesResponse
import com.ewnbd.goh.data.model.response_all_class.notification.SentFriendRequestResponse
import com.ewnbd.goh.data.model.response_all_class.notificationRe.NotifcationResponse
import com.ewnbd.goh.data.model.response_all_class.organigationActivities.OrganizedActivitiesResponse
import com.ewnbd.goh.data.model.response_all_class.organizationParticipetorList.OrganizationParticipetorListResponse
import com.ewnbd.goh.data.model.response_all_class.organizationRequestList.OrganizationJoinRequestListResponse
import com.ewnbd.goh.data.model.response_all_class.profile.ProfileDetailsResponse
import com.ewnbd.goh.data.model.response_all_class.profilePicUpdate.ProfilePicUpdateResponse
import com.ewnbd.goh.data.model.response_all_class.promotion.PromotionResponse
import com.ewnbd.goh.data.model.response_all_class.receatCode.RecetGenerateCodeResponse
import com.ewnbd.goh.data.model.response_all_class.signup.SignUpRequest
import com.ewnbd.goh.data.model.response_all_class.signup.SignUpResponse
import com.ewnbd.goh.data.model.response_all_class.timeListResponse.TimeListResponse
import com.ewnbd.goh.data.model.response_all_class.trackingissue.TrackingIssueListResponse
import com.ewnbd.goh.data.model.response_all_class.verification.VeificationResponse
import com.ewnbd.goh.data.model.response_all_class.verification.VerifiactionRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {
    @POST("login/")
    suspend fun getLogin(
       @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("register/")
    fun createUser(
        @Body signUpRequest: SignUpRequest
    ): Call<SignUpResponse>
    @POST("reset_pass_generate_code/")
    suspend fun resetPassCodeGenerate(
        @Body username: ResetPassGenareteRequest
    ): RecetGenerateCodeResponse
    @POST("reset_password_set/{mobile_number}/{code}/")
    suspend fun forgetPassword(
        @Body forgetpasswordRequest: ForgetpasswordRequest,
        @Path ("mobile_number") mobileNumber: String,
        @Path ("code") code: String
    ): ForgetPasswordResponse

    @POST("reset_password_match_code/{mobile_number}/")
    suspend fun codeMatch(
        @Body codeMatchRequest: CodeMatchingRequest,
        @Path ("mobile_number") mobileNumber: String,
    ): CodeMatchResponse

    @PUT("verify_number/{userId}/")
    suspend fun getVerification(
        @Body verifiactionRequest: VerifiactionRequest,
        @Path ("userId") userId: String
    ): VeificationResponse

    //ActivityList
    @GET("activities/list/")
    suspend fun activitiesList(
        @HeaderMap headers: Map<String, String>
    ): ActivitiesListResponse

    //TimeList
    @GET("activity_time/list/")
    suspend fun timeList(
        @HeaderMap headers: Map<String, String>
    ): TimeListResponse

    // Prefarence
    @PUT("add_update_basic_profile/")
    suspend fun addBasicProfile(
        @Header("Authorization")  authHeader:String,
        @Body basicProfileRequest: BasicProfileRequest
    ): BasicProfileResponse

    // Filter
    @GET("multifilter_activity/")
    suspend fun filterActivity(
        @HeaderMap header: Map<String, String>,
        @Query("category_id") category_id: String,
        @Query("gender")gender: String,
        @Query("activity_date")activity_date: String,
        @Query("latitude")latitude: String,
        @Query("longitude")longitude: String,
        @Query("ageRange")ageRange: Int,
        @Query("start_end_time") activity_time_id: Int
    ): NearActivitiesResponse

    //Near
    @GET("nearby_activities/")
    suspend fun getNearActivities(
        @HeaderMap headers: Map<String, String>,
    ): NearActivitiesResponse

    //Profile Pic Update
    @Multipart
    @POST("change_profile_pic/")
    suspend fun profilePicUpdate(
        @HeaderMap headers: Map<String, String>,
        @Part file: MultipartBody.Part
        ): ProfilePicUpdateResponse

    //Activity Create
    @Multipart
    @POST("add_activity/{userId}/")
    suspend fun activityCreate(
        @HeaderMap headers: Map<String, String>,
        @Part file: MultipartBody.Part,
        @Part("name")name: RequestBody,
        @Part("desc")desc: RequestBody,
        @Part("min_participate")min_participate: RequestBody,
        @Part("gender")gender: RequestBody,
        @Part("privacy")privacy: RequestBody,
        @Part("latitude")latitude: RequestBody,
        @Part("longitude")longitude: RequestBody,
        @Part("place")place: RequestBody,
        @Part("activity_date")activity_date: RequestBody,
        @Part("category_id")category_id: RequestBody,
        @Part("age_range_id")age_range_id: RequestBody,
        @Part("start_time")start_time: RequestBody,
        @Part("end_time")end_time: RequestBody,
        @Path ("userId") userId: String
    ): CreateActivityResponse
    //Category
    @GET("activity_category_list/")
    suspend fun categoryList(
        @HeaderMap headers: Map<String, String>
    ): ActivityCategoryListResponse

    @GET("user_profile/{userId}/")
    suspend fun getProfileDetails(
        @HeaderMap headers: Map<String, String>,
        @Path ("userId") userId: String
    ): ProfileDetailsResponse

    //Friend Request Send
    @POST("sent_friend_request_to/{userId}/")
    suspend fun getFriendRequest(
        @HeaderMap headers: Map<String, String>,
        @Path ("userId") userId: String
    ): SentFriendRequestResponse

    //Friend Remove
    @POST("remove_friend/{userId}/")
    suspend fun getFriendRemove(
        @HeaderMap headers: Map<String, String>,
        @Path ("userId") userId: String
    ): FriendRequestWithdrawResponse
    //Friend Request withdraw
    @POST("withdraw_friend_request/{userId}/")
    suspend fun getFriendRequestWithdraw(
        @HeaderMap headers: Map<String, String>,
        @Path ("userId") userId: String
    ): FriendRequestWithdrawResponse
    //Friend Request Accept
    @POST("accept_friend_request_from_profile/{userId}/")
    suspend fun getFriendAccept(
        @HeaderMap headers: Map<String, String>,
        @Path ("userId") userId: String
    ): AcceptRequestResponse
    //FriendList
    @GET("friend/list/{userId}/")
    suspend fun getFriendLIst(
        @HeaderMap headers: Map<String, String>,
        @Path ("userId") userId: String
    ): FriendListResponse

    //Request LIst
    @GET("individual_friend_request_received_list/")
    suspend fun requestList(
        @HeaderMap headers: Map<String, String>
    ): FriendRequestListresponse

    @GET("get_participated_activities/{userId}/")
    suspend fun getParticipatedRequest(
        @HeaderMap headers: Map<String, String>,
        @Path ("userId") userId: String
    ): OrganizedActivitiesResponse

    @GET("activity/join_requests/list/{activityId}/")
    suspend fun getOrganizationRequestListRequest(
        @HeaderMap headers: Map<String, String>,
        @Path ("activityId") activityId: String
    ): OrganizationJoinRequestListResponse

    @GET("cancel_activity/{activityId}/")
    suspend fun getActivityDeleteRequest(
        @HeaderMap headers: Map<String, String>,
        @Path ("activityId") activityId: String
    ): CancelActivityResponse

    @POST("accept_join_request/{activityId}/{userId}/")
    suspend fun acceptActivityRequest(
        @HeaderMap headers: Map<String, String>,
        @Path ("activityId") activityId: String,
        @Path ("userId") userId: String
    ): ActivityAcceptRequestResponse

    @GET("get_organized_activities/{userId}/")
    suspend fun getOrganizedRequest(
        @HeaderMap headers: Map<String, String>,
        @Path ("userId") userId: String
    ): OrganizedActivitiesResponse

    @GET("activity/participators/list/{userId}/")
    suspend fun getOrganizedParticipators(
        @HeaderMap headers: Map<String, String>,
        @Path ("userId") userId: String
    ): OrganizationParticipetorListResponse

    @GET("search_activity_via_category/{categoryID}/")
    suspend fun getCategoryWiseNear(
        @HeaderMap headers: Map<String, String>,
        @Path ("categoryID") categoryID: String
    ): NearActivitiesResponse

    @POST("sent_joint_request_activity/{activityID}/")
    suspend fun joinRequestSend(
        @HeaderMap headers: Map<String, String>,
        @Path ("activityID") activityID: String
    ): ActivtyRequestSendResponse

    @POST("add_interests/{userId}/")
    suspend fun addInterest(
        @HeaderMap headers: Map<String, String>,
        @Path ("userId") userId: String,
        @Body interserAddRequest: InterserAddRequest
    ): InterestAddResponse
    //NameUpdate
    @PUT("update_full_name/")
    suspend fun nameUpdate(
        @HeaderMap headers: Map<String, String>,
        @Body updateNameRequest: UpdateNameRequest
    ): NameUpdateResponse

    @GET("agerange/list/")
    suspend fun ageList(
        @HeaderMap headers: Map<String, String>
    ): AgeListResponse

    //NameUpdate
    @PUT("change_password/{activityId}/")
    suspend fun changePassword(
        @HeaderMap headers: Map<String, String>,
        @Body changePasswordResponse: ChangePasswordRequest,
        @Path ("activityId") userId: String
    ): ForgetPasswordResponse

    @GET
    suspend fun mapApi(
        @Url url: String,
        @Query("input") input: String,
        @Query("key") map_key: String
    ): MapSearchResponse

    @POST("withdraw_join_request_activity/{activityId}/")
    suspend fun withDrawRequest(
        @HeaderMap headers: Map<String, String>,
        @Path ("activityId") userId: String,
    ): WithdrawResponse

    @GET("notifications/")
    suspend fun notification(
        @HeaderMap headers: Map<String, String>,
    ): NotifcationResponse
    @PUT("update_user_about/")
    suspend fun updateAbout(
        @HeaderMap headers: Map<String, String>,
        @Body aboutResponse: AboutResponse
    ): AboutResponse

    @GET("rating_list_for_activity/{activityId}/")
    suspend fun ratingActivityList(
        @HeaderMap headers: Map<String, String>,
        @Path ("activityId") activityId: String,
    ): ActivityRatingResponse

    @PUT("update_nick_name/")
    suspend fun nickNameUpdate(
        @HeaderMap headers: Map<String, String>,
        @Body nickNameUpdate: NickNameUpdate
    ): NickNameUpdate

    @GET
    suspend fun notificationNextPage(
        @HeaderMap headers: Map<String, String>,
        @Url url: String
    ): NotifcationResponse

    @POST("create_issue/")
    suspend fun reportIssue(
        @HeaderMap headers: Map<String, String>,
        @Body reportIssueRequest: ReportIssueRequest
    ): IssueResponse
    @GET("issue_list/")
    suspend fun trackingIssue(
        @HeaderMap headers: Map<String, String>,
    ): TrackingIssueListResponse

    @GET("interests_list/")
    suspend fun interestList(
        @HeaderMap headers: Map<String, String>,
    ): InterestListResponse

    @POST("promote/{activityId}/")
    suspend fun promotionRequest(
        @HeaderMap headers: Map<String, String>,
        @Body promotionRequest: PromotionRequest,
        @Path ("activityId") activityId: String,
    ): PromotionResponse

    @GET("dialogs/")
    suspend fun chatList(
        @HeaderMap headers: Map<String, String>,
    ): ChatListResponse

    @GET("convo_messages/{chatId}/")
    suspend fun chatDetails(
        @HeaderMap headers: Map<String, String>,
        @Path ("chatId") chatId: String,
    ): ChatDetailsResponse

    @POST("decline_activity_join_request/{userId}/{activityId}/")
    suspend fun activityRequestCancel(
        @HeaderMap headers: Map<String, String>,
        @Path ("activityId") activityId: String,
        @Path("userId") userId: String
    ): ShareActivityResponse

    @POST("share/activity/{userId}/")
    suspend fun activityShare(
        @HeaderMap headers: Map<String, String>,
        @Path("userId") userId: String
    ): ShareActivityResponse

    @POST("rate/{activityId}/")
    suspend fun ratingActivity(
        @HeaderMap headers: Map<String, String>,
        @Path("activityId") userId: String,
        @Body personRating : RatingActivityRequest

    ): RatingPersonResponse

    @GET("activity_details/{activityId}/")
    suspend fun activityDetails(
        @HeaderMap headers: Map<String, String>,
        @Path("activityId") userId: String,
    ): ActvityDetailsResponse

    @POST("block_chat/{userId}/")
    suspend fun chatBlock(
        @HeaderMap headers: Map<String, String>,
        @Path("userId") userId: String
    ): ShareActivityResponse


    @Multipart
    @PUT("update_activity/{userId}/")
    suspend fun activityUpdate(
        @HeaderMap headers: Map<String, String>,
        @Part file: MultipartBody.Part,
        @Part("name")name: RequestBody,
        @Part("desc")desc: RequestBody,
        @Part("min_participate")min_participate: RequestBody,
        @Part("gender")gender: RequestBody,
        @Part("privacy")privacy: RequestBody,
        @Part("latitude")latitude: RequestBody,
        @Part("longitude")longitude: RequestBody,
        @Part("place")place: RequestBody,
        @Part("activity_date")activity_date: RequestBody,
        @Part("category_id")category_id: RequestBody,
        @Part("age_range_id")age_range_id: RequestBody,
        @Part("start_time")start_time: RequestBody,
        @Part("end_time")end_time: RequestBody,
        @Path ("userId") userId: String
    ): CreateActivityResponse

    @POST("remove_participant/participator_id_{participatorId}/activity_id_{activityId}/")
    suspend fun participatorRemove(
        @HeaderMap headers: Map<String, String>,
        @Path ("participatorId") participator: String,
        @Path ("activityId") activityId: String
    ): ShareActivityResponse

    @GET("issue-category-list/")
    suspend fun categoryIssueList(
        @HeaderMap headers: Map<String, String>,
    ): CategoryIssueListResponse
}