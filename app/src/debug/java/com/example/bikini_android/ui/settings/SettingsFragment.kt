/*
 * SettingsFragment.kt 2020. 11. 1
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings

import android.os.Bundle
import android.view.View
import androidx.preference.ListPreference
import androidx.preference.Preference
import com.example.bikini_android.ui.base.BaseSettingsFragment
import com.example.bikini_android.util.ktx.pref
import com.example.bikini_android.util.ktx.put

/**
 * @author MyeongKi
 */

class SettingsFragment : BaseSettingsFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findPreference<ListPreference>(PREF_SERVER_TYPE)?.apply {
            onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { preference, serverType ->
                    logger.debug { "changed serverType $serverType" }
                    with(requireActivity().pref()) {
                        put(PREF_SERVER_TYPE, serverType)
                    }
                    preference.summary = serverType.toString()
                    true
                }
            summary = value
        }
    }

    companion object {
        const val PREF_SERVER_TYPE = "pref.server.type"
    }
}
