package com.example.bikini_android.repository.account

import com.example.bikini_android.network.response.MyInfoReponse
import com.example.bikini_android.network.response.UserUpdateResponse
import io.reactivex.Single

/**
 * @author bsgreentea
 */
interface AccountRepository {

    fun getUserFromRemote(
        userInfo: UserInfo
    ): Single<UserUpdateResponse.Result?>

    fun getMyInfoFromRemote(): Single<MyInfoReponse.Result?>
}