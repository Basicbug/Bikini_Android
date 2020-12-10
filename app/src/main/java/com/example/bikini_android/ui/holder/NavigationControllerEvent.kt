/*
 * NavigationControllerEvent.kt 2020. 12. 11
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.holder

import androidx.navigation.NavController
import com.example.bikini_android.util.bus.RxAction

/**
 * @author MyeongKi
 */

class NavigationControllerEvent(val controller: NavController) : RxAction