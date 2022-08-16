package com.ewnbd.goh.di

import com.ewnbd.goh.data.model.response_all_class.CategoryIssueListResponse
import com.ewnbd.goh.data.remote.ApiInterface
import com.ewnbd.goh.repository.MapRepository
import com.ewnbd.goh.repository.MapRepositoryImp
import com.ewnbd.goh.repository.about.AboutUpdateRepoImpl
import com.ewnbd.goh.repository.about.AboutUpdateRepository
import com.ewnbd.goh.repository.acceptFreindRequest.AcceptFriendRequestRepo
import com.ewnbd.goh.repository.acceptFreindRequest.AcceptFriendRequestRepoImp
import com.ewnbd.goh.repository.acceptRequestActivity.AcceptActivityRequestRepo
import com.ewnbd.goh.repository.acceptRequestActivity.AcceptActivityRequestRepoImp
import com.ewnbd.goh.repository.activityFilter.ActivityFilterRepository
import com.ewnbd.goh.repository.activityFilter.ActivityFilterRepositoryImp
import com.ewnbd.goh.repository.activityList.ActivityListRepository
import com.ewnbd.goh.repository.activityList.ActivityListrepositoryImp
import com.ewnbd.goh.repository.activityRequestCancel.ActivityRequestCancelRepository
import com.ewnbd.goh.repository.activityRequestCancel.ActivtyRequestCancelRepoImp
import com.ewnbd.goh.repository.activityRequestSent.ActivitySentRequestRepository
import com.ewnbd.goh.repository.activityRequestSent.ActivitySentRequestRepositoryImp
import com.ewnbd.goh.repository.activityShare.ActivityShareRepository
import com.ewnbd.goh.repository.activityShare.ActivityShareRepositoryImp
import com.ewnbd.goh.repository.activityShare.ChatBlockRepository
import com.ewnbd.goh.repository.actvityDetails.ActvityDetailsRepository
import com.ewnbd.goh.repository.actvityDetails.ActvityDetailsRepositoryImp
import com.ewnbd.goh.repository.addBasicProfile.AddBasicProfileRepository
import com.ewnbd.goh.repository.addBasicProfile.AddBasicProfileRepositoryImp
import com.ewnbd.goh.repository.age.AgeListRepository
import com.ewnbd.goh.repository.age.AgeListRepositoryImp
import com.ewnbd.goh.repository.caht.ChatRepository
import com.ewnbd.goh.repository.caht.ChatRepositoryImp
import com.ewnbd.goh.repository.cancelActivity.CancelActivityRepository
import com.ewnbd.goh.repository.cancelActivity.CancelActivityRepositoryImp
import com.ewnbd.goh.repository.categories.CategoryRepository
import com.ewnbd.goh.repository.categories.CategoryRepositoryImp
import com.ewnbd.goh.repository.categoryIssueList.CategoryIsssueListRepoImp
import com.ewnbd.goh.repository.categoryIssueList.CategoryIssueListRepository
import com.ewnbd.goh.repository.categoryWiseSearch.CategoryWiseActivitySearchRepo
import com.ewnbd.goh.repository.categoryWiseSearch.CategoryWiseActivitySearchRepoImp
import com.ewnbd.goh.repository.changePassword.ChangePasswordRepoImp
import com.ewnbd.goh.repository.changePassword.ChangepasswordRepository
import com.ewnbd.goh.repository.chatBlock.ChatBlockRepositoryImp
import com.ewnbd.goh.repository.chatDetails.ChatDetailsRepositoryImp
import com.ewnbd.goh.repository.chatDetails.ChatDetailsRespository
import com.ewnbd.goh.repository.codeMtach.CodeMatchRepository
import com.ewnbd.goh.repository.codeMtach.CodeMatchingRepositoryImp
import com.ewnbd.goh.repository.createActivity.CreateActivityRepository
import com.ewnbd.goh.repository.createActivity.CreateActivityRepositoryImp
import com.ewnbd.goh.repository.forgetpassword.ForgetPasswordRepository
import com.ewnbd.goh.repository.forgetpassword.ForgetPasswordRepositoryImp
import com.ewnbd.goh.repository.forgetpassword.recetCodeGenerate.RecetCodeGenerateRepoImp
import com.ewnbd.goh.repository.forgetpassword.recetCodeGenerate.RecetCodeGenerateRepository
import com.ewnbd.goh.repository.friendList.FriendListRepository
import com.ewnbd.goh.repository.friendList.FriendListRepositoryImp
import com.ewnbd.goh.repository.friendRemove.FriendRemoveRepoImp
import com.ewnbd.goh.repository.friendRemove.FriendRemoveRepository
import com.ewnbd.goh.repository.friendRequestList.FriendRequestListRepo
import com.ewnbd.goh.repository.friendRequestList.FriendRequestListRepoImp
import com.ewnbd.goh.repository.friendRequestRemove.FriendRequestRemoveRepo
import com.ewnbd.goh.repository.friendRequestRemove.FriendRequestRemoveRepoImp
import com.ewnbd.goh.repository.friendRequestSend.FriendRequestSendRepo
import com.ewnbd.goh.repository.friendRequestSend.FriendRequestSendRepoImp
import com.ewnbd.goh.repository.interestAdd.InterestAddRepoImp
import com.ewnbd.goh.repository.interestAdd.InterestAddRepository
import com.ewnbd.goh.repository.interestList.InterestListRepository
import com.ewnbd.goh.repository.interestList.InterestListRepositoryImp
import com.ewnbd.goh.repository.login.LoginRepository
import com.ewnbd.goh.utils.ConstValue.BASE_URL
import com.ewnbd.goh.utils.DispatcherProvider
import com.ewnbd.goh.repository.login.LoginRepositoryImp
import com.ewnbd.goh.repository.nearActivity.NearActivitiesListRepoImp
import com.ewnbd.goh.repository.nearActivity.NearActivitysListRepository
import com.ewnbd.goh.repository.nickName.NickNameUpdateRepoImp
import com.ewnbd.goh.repository.nickName.NickNameUpdateRepository
import com.ewnbd.goh.repository.notifcation.NotifcationResponseImp
import com.ewnbd.goh.repository.notifcation.NotificationRepository
import com.ewnbd.goh.repository.notification_next.NotifcationNextRepoImp
import com.ewnbd.goh.repository.notification_next.NotificationNextRepository
import com.ewnbd.goh.repository.organization.OrganizationRepository
import com.ewnbd.goh.repository.organization.OrganizationRepositoryImp
import com.ewnbd.goh.repository.organizationParticipetor.OrganizationParticipetorListRepoImpl
import com.ewnbd.goh.repository.organizationParticipetor.OrganizationParticipetorRepo
import com.ewnbd.goh.repository.organizationRequestList.OrganizationRequestListRepoImpl
import com.ewnbd.goh.repository.organizationRequestList.OrganizationRequestListRepository
import com.ewnbd.goh.repository.participated.ParticipatedActivityRepo
import com.ewnbd.goh.repository.participated.ParticipatedActivityRepoImp
import com.ewnbd.goh.repository.participatorDelete.ParticipatorDeleteRepoImp
import com.ewnbd.goh.repository.participatorDelete.ParticipatorDeleteRepository
import com.ewnbd.goh.repository.profile.ProfileRepository
import com.ewnbd.goh.repository.profile.ProfileRepositoryImp
import com.ewnbd.goh.repository.profilePicUpdate.ProfilePicUpdateRepoImp
import com.ewnbd.goh.repository.profilePicUpdate.ProfilePicUpdateRepository
import com.ewnbd.goh.repository.promotion.PromotionRepository
import com.ewnbd.goh.repository.promotion.PromotionRepositoryImp
import com.ewnbd.goh.repository.ratingActivtyList.ActivityRatingRepositoryImp
import com.ewnbd.goh.repository.ratingActivtyList.ActvityRatingListRepository
import com.ewnbd.goh.repository.ratingPersonActivity.RatingPersonRepositoryImp
import com.ewnbd.goh.repository.ratingPersonActivity.RatingRatingActivityRepository
import com.ewnbd.goh.repository.reportIssue.ReportIssueRepositoryImp
import com.ewnbd.goh.repository.reportIssue.ReportissueRepository
import com.ewnbd.goh.repository.sentFreindRequest.SentFriendRequestRepository
import com.ewnbd.goh.repository.sentFreindRequest.SentFriendRequestRepositoryImp
import com.ewnbd.goh.repository.signup.SignUpRepository
import com.ewnbd.goh.repository.signup.SignUpRepositoryImp
import com.ewnbd.goh.repository.timeList.TimeListRepository
import com.ewnbd.goh.repository.timeList.TimeListRepositoryImpImp
import com.ewnbd.goh.repository.trackingList.TrackingListRepository
import com.ewnbd.goh.repository.trackingList.TrackingListRepositoryImp
import com.ewnbd.goh.repository.updateActvity.UpdateActvityRepository
import com.ewnbd.goh.repository.updateActvity.UpdateActvityRepositoryImp
import com.ewnbd.goh.repository.updateName.UpdateNameRepositoryImp
import com.ewnbd.goh.repository.updateName.UpdatenameRepository
import com.ewnbd.goh.repository.verification.VerificationRepository
import com.ewnbd.goh.repository.verification.VerificationRepositoryImp
import com.ewnbd.goh.repository.withdrawActivityRequest.WithdrawActivityRequest
import com.ewnbd.goh.repository.withdrawActivityRequest.WithdrawRequestRepoImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Singleton
    @Provides
    fun provideCategoryIssue (
        api: ApiInterface
    ): CategoryIssueListRepository = CategoryIsssueListRepoImp(api)
    @Singleton
    @Provides
    fun provideParticipatorDeleteRepo (
        api: ApiInterface
    ): ParticipatorDeleteRepository = ParticipatorDeleteRepoImp(api)
    @Singleton
    @Provides
    fun provideUpdateActvityRepo (
        api: ApiInterface
    ): UpdateActvityRepository = UpdateActvityRepositoryImp(api)
    @Singleton
    @Provides
    fun provideChatBlockRepo (
        api: ApiInterface
    ): ChatBlockRepository = ChatBlockRepositoryImp(api)
    @Singleton
    @Provides
    fun provideActvityDetailsRepo (
        api: ApiInterface
    ): ActvityDetailsRepository = ActvityDetailsRepositoryImp(api)
    @Singleton
    @Provides
    fun provideRatingPersonRepo (
        api: ApiInterface
    ): RatingRatingActivityRepository = RatingPersonRepositoryImp(api)
    @Singleton
    @Provides
    fun provideActivitySendRequestCancelRepo (
        api: ApiInterface
    ): ActivityRequestCancelRepository = ActivtyRequestCancelRepoImp(api)
    @Singleton
    @Provides
    fun provideActivityShareRepo (
        api: ApiInterface
    ): ActivityShareRepository = ActivityShareRepositoryImp(api)
    @Singleton
    @Provides
    fun provideChatDetailsRepo (
        api: ApiInterface
    ): ChatDetailsRespository = ChatDetailsRepositoryImp(api)
    @Singleton
    @Provides
    fun provideChatRepo (
        api: ApiInterface
    ): ChatRepository = ChatRepositoryImp(api)
    @Singleton
    @Provides
    fun providePromotion (
        api: ApiInterface
    ): PromotionRepository = PromotionRepositoryImp(api)
    @Singleton
    @Provides
    fun provideInterestList (
        api: ApiInterface
    ): InterestListRepository = InterestListRepositoryImp(api)
    @Singleton
    @Provides
    fun provideTrackingRepo (
        api: ApiInterface
    ): TrackingListRepository = TrackingListRepositoryImp(api)
    @Singleton
    @Provides
    fun provideReportIssueRepo (
        api: ApiInterface
    ): ReportissueRepository = ReportIssueRepositoryImp(api)
    @Singleton
    @Provides
    fun provideNotificationNextRepo (
        api: ApiInterface
    ): NotificationNextRepository = NotifcationNextRepoImp(api)
    @Singleton
    @Provides
    fun provideNIckNameRepo (
        api: ApiInterface
    ): NickNameUpdateRepository = NickNameUpdateRepoImp(api)
    @Singleton
    @Provides
    fun provideActivityRatingRepo (
        api: ApiInterface
    ): ActvityRatingListRepository = ActivityRatingRepositoryImp(api)
    @Singleton
    @Provides
    fun provideChangePasswordRepo (
        api: ApiInterface
    ): ChangepasswordRepository = ChangePasswordRepoImp(api)
    @Singleton
    @Provides
    fun provideAboutRepository (
        api: ApiInterface
    ): AboutUpdateRepository = AboutUpdateRepoImpl(api)
    @Singleton
    @Provides
    fun provideNotificationRepository (
        api: ApiInterface
    ): NotificationRepository = NotifcationResponseImp(api)
    @Singleton
    @Provides
    fun provideWithdrawRequestRepository (
        api: ApiInterface
    ): WithdrawActivityRequest = WithdrawRequestRepoImp(api)
    @Singleton
    @Provides
    fun provideInterestAddRepository (
        api: ApiInterface
    ): InterestAddRepository = InterestAddRepoImp(api)
    @Singleton
    @Provides
    fun provideMapSearchResultRepository (
        api: ApiInterface
    ): MapRepository = MapRepositoryImp(api)
    //AcceptActivityRequest
    @Singleton
    @Provides
    fun provideOrganizationParticipetorRepository (
        api: ApiInterface
    ): OrganizationParticipetorRepo = OrganizationParticipetorListRepoImpl(api)
    //AcceptActivityRequest
    @Singleton
    @Provides
    fun provideAcceptActivityRequestRepository (
        api: ApiInterface
    ): AcceptActivityRequestRepo = AcceptActivityRequestRepoImp(api)
    //FriendList
    @Singleton
    @Provides
    fun provideFriendAcceptRepository (
        api: ApiInterface
    ): AcceptFriendRequestRepo = AcceptFriendRequestRepoImp(api)
    //FriendList
    @Singleton
    @Provides
    fun provideFriendListRepository (
        api: ApiInterface
    ): FriendListRepository = FriendListRepositoryImp(api)
    //FriendRequestList
    @Singleton
    @Provides
    fun provideFriendRequestListRepository (
        api: ApiInterface
    ): FriendRequestListRepo = FriendRequestListRepoImp(api)

    //FriendRequestSend
    @Singleton
    @Provides
    fun provideFriendRequestSendRepository (
        api: ApiInterface
    ): FriendRequestSendRepo = FriendRequestSendRepoImp(api)
    //FriendRequestWithdraw
    @Singleton
    @Provides
    fun provideFriendRequestWithdrawRepository (
        api: ApiInterface
    ): FriendRequestRemoveRepo = FriendRequestRemoveRepoImp(api)
    //Friend Remove
    @Singleton
    @Provides
    fun provideFriendRemoveRepository (
        api: ApiInterface
    ): FriendRemoveRepository = FriendRemoveRepoImp(api)
    //SignUp
    @Singleton
    @Provides
    fun provideSignUpRepository (
        api: ApiInterface
    ): SignUpRepository = SignUpRepositoryImp(api)

    //SignUp
    @Singleton
    @Provides
    fun provideForgetPasswordRepository (
        api: ApiInterface
    ): ForgetPasswordRepository = ForgetPasswordRepositoryImp(api)
    //ActivityRequestSend
    @Singleton
    @Provides
    fun activityRequestSendRepository (
        api: ApiInterface
    ): ActivitySentRequestRepository = ActivitySentRequestRepositoryImp(api)

    //ActivityRequestSend
    @Singleton
    @Provides
    fun friendRequestSendRepository (
        api: ApiInterface
    ): SentFriendRequestRepository = SentFriendRequestRepositoryImp(api)
    //RecentGenerateCode
    @Singleton
    @Provides
    fun provideReceatGenerateCodeRepository (
        api: ApiInterface
    ): RecetCodeGenerateRepository = RecetCodeGenerateRepoImp(api)
    //Organization
    @Singleton
    @Provides
    fun provideOrganizationRepository (
        api: ApiInterface
    ): OrganizationRepository = OrganizationRepositoryImp(api)

    //TimeList
    @Singleton
    @Provides
    fun provideTimeRepository (
        api: ApiInterface
    ): TimeListRepository = TimeListRepositoryImpImp(api)
    //Participated
    @Singleton
    @Provides
    fun provideParticipatedRepository (
        api: ApiInterface
    ): ParticipatedActivityRepo = ParticipatedActivityRepoImp(api)

    //CategoryWiseNear
    @Singleton
    @Provides
    fun provideCategoryWiseNearRepository (
        api: ApiInterface
    ): CategoryWiseActivitySearchRepo = CategoryWiseActivitySearchRepoImp(api)
    //AddBasicProfile
    @Singleton
    @Provides
    fun providePrefarenceRepository (
        api: ApiInterface
    ): AddBasicProfileRepository = AddBasicProfileRepositoryImp(api)
    //Code Match
    @Singleton
    @Provides
    fun provideCodeMatchRepository (
        api: ApiInterface
    ): CodeMatchRepository = CodeMatchingRepositoryImp(api)

    //Code Match
    @Singleton
    @Provides
    fun provideCancelActivityRepository (
        api: ApiInterface
    ): CancelActivityRepository = CancelActivityRepositoryImp(api)

    //Code Match
    @Singleton
    @Provides
    fun provideCreateActivityRepository (
        api: ApiInterface
    ): CreateActivityRepository = CreateActivityRepositoryImp(api)

    //Login
    @Singleton
    @Provides
    fun provideLoginRepository (
        api: ApiInterface
    ): LoginRepository = LoginRepositoryImp(api)
    //Filter
    @Singleton
    @Provides
    fun provideFilterRepository (
        api: ApiInterface
    ): ActivityFilterRepository = ActivityFilterRepositoryImp(api)
    //Organization Request list
    @Singleton
    @Provides
    fun provideOrganizationRequestListRepository (
        api: ApiInterface
    ): OrganizationRequestListRepository = OrganizationRequestListRepoImpl(api)
    //Age
    @Singleton
    @Provides
    fun provideAgeListRepository (
        api: ApiInterface
    ): AgeListRepository = AgeListRepositoryImp(api)
    //Profile
    @Singleton
    @Provides
    fun provideProfileRepository (
        api: ApiInterface
    ): ProfileRepository = ProfileRepositoryImp(api)
    //Profile
    @Singleton
    @Provides
    fun provideUpdateNameRepository (
        api: ApiInterface
    ): UpdatenameRepository = UpdateNameRepositoryImp(api)
    //Verification
    @Singleton
    @Provides
    fun provideVerificationRepository (
        api: ApiInterface
    ): VerificationRepository = VerificationRepositoryImp(api)

    //ActivityList
    @Singleton
    @Provides
    fun provideActivityListRepository (
        api: ApiInterface
    ): ActivityListRepository = ActivityListrepositoryImp(api)

    //NearList
    @Singleton
    @Provides
    fun nearActivityListRepository (
        api: ApiInterface
    ): NearActivitysListRepository = NearActivitiesListRepoImp(api)

    @Singleton
    @Provides
    fun provideCategoryRepository (
        api: ApiInterface
    ): CategoryRepository = CategoryRepositoryImp(api)

    @Singleton
    @Provides
    fun provideProfileUpdateRepository (
        api: ApiInterface
    ): ProfilePicUpdateRepository = ProfilePicUpdateRepoImp(api)
    @Singleton
    @Provides
    fun OkHttpClient():OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()
    @Singleton
    @Provides
    fun provideRetrofit(): ApiInterface = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(OkHttpClient())
        .build()
        .create()

    @Singleton
    @Provides
    fun provideDispatcher() : DispatcherProvider = object : DispatcherProvider{
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }



}