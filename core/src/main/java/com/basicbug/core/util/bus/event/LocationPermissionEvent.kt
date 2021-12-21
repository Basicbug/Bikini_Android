/*
 * LocationPermissionEvent.kt 2020. 11. 24
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.util.bus.event

import com.basicbug.core.util.bus.RxAction

/**
 * @author MyeongKi
 */

class LocationPermissionEvent(val isAccept: Boolean) : RxAction