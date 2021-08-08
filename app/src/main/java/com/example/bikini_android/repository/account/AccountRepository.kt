package com.example.bikini_android.repository.account

import com.example.bikini_android.network.response.MyInfoReponse
import io.reactivex.Single

/**
 * @author bsgreentea
 */
interface AccountRepository {

    fun getUserFromRemote(
        userInfo: UserInfo,
    ): Single<String?>

    fun getMyInfoFromRemote(): Single<MyInfoReponse?>
}