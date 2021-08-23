package com.example.bikini_android.repository.account

import io.reactivex.Single

/**
 * @author bsgreentea
 */
interface AccountRepository {

    fun getUserFromRemote(
        userInfo: UserInfo,
    ): Single<String?>

    fun getMyInfoFromRemote(): Single<Pair<UserInfo?, String>>
}