package com.tthieu.network_gps_permission_checks.common.data.source

import com.tthieu.network_gps_permission_checks.common.utils.listener.GpsStatusListener
import com.tthieu.network_gps_permission_checks.common.utils.listener.NetworkStatusListener
import com.tthieu.network_gps_permission_checks.common.utils.listener.PermissionStatusListener

interface AppDataSource {

    fun getNetworkStatusListener(): NetworkStatusListener
    fun getGpsStatusListener(): GpsStatusListener
    fun getGpsPermissionStatusListener(): PermissionStatusListener
}