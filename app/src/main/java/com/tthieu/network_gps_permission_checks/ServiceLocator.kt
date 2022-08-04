package com.tthieu.network_gps_permission_checks

import android.Manifest
import android.content.Context
import androidx.annotation.VisibleForTesting
import com.tthieu.network_gps_permission_checks.common.data.source.AppDataSource
import com.tthieu.network_gps_permission_checks.common.data.source.AppRepository
import com.tthieu.network_gps_permission_checks.common.data.source.DefaultAppRepository
import com.tthieu.network_gps_permission_checks.common.data.source.local.AppLocalDataSource
import com.tthieu.network_gps_permission_checks.common.utils.listener.GpsStatusListener
import com.tthieu.network_gps_permission_checks.common.utils.listener.NetworkStatusListener
import com.tthieu.network_gps_permission_checks.common.utils.listener.PermissionStatusListener

object ServiceLocator {

    var appRepository: AppRepository? = null
        @VisibleForTesting set

    fun provideAppRepository(context: Context): AppRepository {
        synchronized(this) {
            return appRepository ?: createAppRepository(context)
        }
    }

    private fun createAppRepository(context: Context): AppRepository {
        val newRepo = DefaultAppRepository(createAppLocalDataSource(context))
        appRepository = newRepo

        return newRepo
    }

    private fun createAppLocalDataSource(context: Context): AppDataSource {
        val gpsStatusListener = GpsStatusListener(context)
        val networkStatusListener = NetworkStatusListener(context)
        val gpsPermissionStatusListener = PermissionStatusListener(context, Manifest.permission.ACCESS_COARSE_LOCATION)

        return AppLocalDataSource(networkStatusListener, gpsStatusListener, gpsPermissionStatusListener)
    }

}