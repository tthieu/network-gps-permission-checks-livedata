package com.tthieu.network_gps_permission_checks.ui.statuscheck

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.tthieu.network_gps_permission_checks.AppStateApplication
import com.tthieu.network_gps_permission_checks.R
import com.tthieu.network_gps_permission_checks.common.utils.EventObserver
import com.tthieu.network_gps_permission_checks.common.utils.listener.GpsStatus
import com.tthieu.network_gps_permission_checks.common.utils.listener.NetworkStatus
import com.tthieu.network_gps_permission_checks.common.utils.listener.PermissionStatus
import com.tthieu.network_gps_permission_checks.databinding.FragmentMainBinding

class MainFragment : androidx.fragment.app.Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by viewModels<MainViewModel> {
        MainViewModelFactory((requireContext().applicationContext as AppStateApplication).appRepository)
    }

    private lateinit var binding: FragmentMainBinding

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            updatePermissionCheckUi(PermissionStatus.Granted())
        } else {
            updatePermissionCheckUi(PermissionStatus.Denied())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->

            uiState.networkStatus?.let {
                updateNetworkCheckUi(it)
            }

            uiState.gpsStatus?.let {
                updateGpsCheckUi(it)
            }

            uiState.locPermission?.let {
                updatePermissionCheckUi(it)
            }

        }

        viewModel.requestPermission.observe(viewLifecycleOwner, EventObserver {
            locationPermissionRequest.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        })

        viewModel.openLocationSetting.observe(viewLifecycleOwner, EventObserver {
            // val intent = Intent().apply {
            //     action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
            // }
            // startActivity(intent)

            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri: Uri = Uri.fromParts("package", activity?.packageName, null)
            intent.data = uri
            startActivity(intent)
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun getColor(colorId: Int) =
        resources.getColor(colorId, null)

    private fun updatePermissionCheckUi(status: PermissionStatus) {
        binding.apply {

            when (status) {

                is PermissionStatus.Blocked -> {
                    tvPermission.text = getText(R.string.permission_status_blocked)
                    tvPermission.setTextColor(getColor(R.color.text_unactive))
                    btnPermission.text = getString(R.string.unblock_permission)
                }

                is PermissionStatus.Denied -> {
                    tvPermission.text = getText(R.string.permission_status_denied)
                    tvPermission.setTextColor(getColor(R.color.text_denied))
                    btnPermission.text = getString(R.string.request_permission)
                }

                is PermissionStatus.Granted -> {
                    tvPermission.text = getText(R.string.permission_status_granted)
                    tvPermission.setTextColor(getColor(R.color.text_active))
                    btnPermission.text = getString(R.string.revoke_permission)
                }
            }

        }
    }

    private fun updateGpsCheckUi(status: GpsStatus) {
        binding.tvGps.apply {
            when (status) {

                is GpsStatus.Enabled -> {
                    text = getText(R.string.gps_status_enabled)
                    setTextColor(getColor(R.color.text_active))
                }

                is GpsStatus.Disabled -> {
                    text = getText(R.string.gps_status_disabled)
                    setTextColor(getColor(R.color.text_denied))
                }
            }
        }
    }

    private fun updateNetworkCheckUi(status: NetworkStatus) {
        binding.tvNetwork.apply {

            when (status) {

                is NetworkStatus.Online -> {
                    this.text = getText(R.string.network_status_online)
                    this.setTextColor(getColor(R.color.text_active))
                }

                is NetworkStatus.Offline -> {
                    this.text = getText(R.string.network_status_offline)
                    this.setTextColor(getColor(R.color.text_denied))
                }
            }

        }
    }
}