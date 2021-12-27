package com.example.bikini_android.repository.account

import io.reactivex.Single

/**
 * @author bsgreentea
 */
interface AccountRepository {
    fun updateUserInfo(userUpdateInfo: UserUpdateInfo): Single<UserInfo?>
    fun getMyInfo(): Single<UserInfo?>
}