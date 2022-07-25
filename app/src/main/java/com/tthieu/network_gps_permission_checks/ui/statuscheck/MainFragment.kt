package com.tthieu.network_gps_permission_checks.ui.statuscheck

import android.os.Bundle
import android.telephony.CarrierConfigManager.Gps
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.tthieu.network_gps_permission_checks.AppStateApplication
import com.tthieu.network_gps_permission_checks.R
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.uiState.observe(this) { uiState ->

            uiState.networkStatus?.let {

                binding.tvNetwork.apply {

                    when (it) {

                        is NetworkStatus.Online -> {
                            this.text = getText(R.string.network_status_online)
                            this.setTextColor(R.color.text_active)
                        }

                        is NetworkStatus.Offline -> {
                            this.text = getText(R.string.network_status_offline)
                            this.setTextColor(R.color.text_denied)
                        }
                    }

                }

            }

            uiState.gpsStatus?.let {

                binding.tvGps.apply {
                    when (it) {

                        is GpsStatus.Enabled -> {
                            this.text = getText(R.string.gps_status_enabled)
                            this.setTextColor(R.color.text_active)
                        }

                        is GpsStatus.Disabled -> {
                            this.text = getText(R.string.gps_status_disabled)
                            this.setTextColor(R.color.text_denied)

                        }
                    }
                }
            }

            uiState.locPermission?.let {

                binding.tvPermission.apply {

                    when (it) {

                        is PermissionStatus.Blocked -> {
                            this.text = getText(R.string.permission_status_blocked)
                            this.setTextColor(R.color.text_unactive)
                        }

                        is PermissionStatus.Denied -> {
                            this.text = getText(R.string.permission_status_denied)
                            this.setTextColor(R.color.text_denied)
                        }

                        is PermissionStatus.Granted -> {
                            this.text = getText(R.string.permission_status_granted)
                            this.setTextColor(R.color.text_active)
                        }
                    }

                }
            }

        }
    }

}