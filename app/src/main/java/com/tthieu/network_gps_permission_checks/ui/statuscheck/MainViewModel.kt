package com.tthieu.network_gps_permission_checks.ui.statuscheck

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tthieu.network_gps_permission_checks.common.data.source.AppRepository
import com.tthieu.network_gps_permission_checks.common.utils.combine
import com.tthieu.network_gps_permission_checks.common.utils.listener.GpsStatus
import com.tthieu.network_gps_permission_checks.common.utils.listener.GpsStatusListener
import com.tthieu.network_gps_permission_checks.common.utils.listener.NetworkStatus
import com.tthieu.network_gps_permission_checks.common.utils.listener.NetworkStatusListener
import com.tthieu.network_gps_permission_checks.common.utils.listener.PermissionStatus
import com.tthieu.network_gps_permission_checks.common.utils.listener.PermissionStatusListener

class MainViewModel(
    private val appRepository: AppRepository
) : ViewModel() {

    private var locationUiInfo: GpsStatusListener = appRepository.getGpsStatusListener()
    private var locPermissionUiInfo: PermissionStatusListener = appRepository.getGpsPermissionStatusListener()
    private var networkUiInfo: NetworkStatusListener = appRepository.getNetworkStatusListener()

    val uiState = networkUiInfo.combine(locationUiInfo, locPermissionUiInfo) { network, location, permission ->
        MainViewUiState(
            network,
            location,
            permission
        )
    }
}

class MainViewModelFactory (
    private val appRepository: AppRepository
    ): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (MainViewModel(appRepository) as T)
}

data class MainViewUiState(
    val networkStatus: NetworkStatus?,
    val gpsStatus: GpsStatus?,
    val locPermission: PermissionStatus?
)