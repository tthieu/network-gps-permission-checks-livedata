package com.tthieu.network_gps_permission_checks

import android.app.Application
import com.tthieu.network_gps_permission_checks.common.data.source.AppRepository

class StatusCheckApplication: Application() {

    val appRepository: AppRepository
        get() = ServiceLocator.provideAppRepository(this)
}