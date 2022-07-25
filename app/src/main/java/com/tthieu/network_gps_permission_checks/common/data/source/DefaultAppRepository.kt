package com.tthieu.network_gps_permission_checks.common.data.source

import com.tthieu.network_gps_permission_checks.common.utils.listener.GpsStatusListener
import com.tthieu.network_gps_permission_checks.common.utils.listener.NetworkStatusListener
import com.tthieu.network_gps_permission_checks.common.utils.listener.PermissionStatusListener

class DefaultAppRepository(
    private val appDataSource: AppDataSource,
): AppRepository {
    override fun getNetworkStatusListener() = appDataSource.getNetworkStatusListener()

    override fun getGpsStatusListener() = appDataSource.getGpsStatusListener()

    override fun getGpsPermissionStatusListener() = appDataSource.getGpsPermissionStatusListener()
}