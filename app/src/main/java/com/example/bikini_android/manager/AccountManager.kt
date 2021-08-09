package com.example.bikini_android.manager

import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources

/**
 * @author bsgreentea
 */
object AccountManager {

    var userName: String = ""
        get() {
            if (field.isEmpty()) {
                field = PreferenceManager.getString(AppResources.getString(R.string.user_name_key))
            }
            return field
        }
        set(value) {
            value.let {
                field = it
                PreferenceManager.setString(AppResources.getString(R.string.user_name_key), it)
            }
        }

}