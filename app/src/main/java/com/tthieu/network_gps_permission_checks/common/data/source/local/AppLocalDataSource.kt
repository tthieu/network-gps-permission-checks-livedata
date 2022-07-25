package com.tthieu.network_gps_permission_checks.common.data.source.local

import com.tthieu.network_gps_permission_checks.common.data.source.AppDataSource
import com.tthieu.network_gps_permission_checks.common.utils.listener.GpsStatusListener
import com.tthieu.network_gps_permission_checks.common.utils.listener.NetworkStatusListener
import com.tthieu.network_gps_permission_checks.common.utils.listener.PermissionStatusListener

class AppLocalDataSource(
    private val networkStatusListener: NetworkStatusListener,
    private val gpsStatusListener: GpsStatusListener,
    private val gpsPermissionListener: PermissionStatusListener,
): AppDataSource {
    override fun getNetworkStatusListener() = networkStatusListener

    override fun getGpsStatusListener() = gpsStatusListener

    override fun getGpsPermissionStatusListener() = gpsPermissionListener
}