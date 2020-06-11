package com.pratclot.ranchcontrol.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pratclot.ranchcontrol.R
import com.pratclot.ranchcontrol.RanchControl
import com.pratclot.ranchcontrol.databinding.ControlFragmentBinding
import com.pratclot.ranchcontrol.fragments.ui.login.LoginViewModel
import com.pratclot.ranchcontrol.viewmodels.ControlViewModel
import com.pratclot.ranchcontrol.viewmodels.ControlViewModelFactory
import javax.inject.Inject

const val TAG = "ControlFragment"

class ControlFragment : Fragment() {
    private lateinit var binding: ControlFragmentBinding

    @Inject
    lateinit var viewModelFactory: ControlViewModelFactory

    private val viewModel by viewModels<ControlViewModel> { viewModelFactory }
    private val loginViewModel by activityViewModels<LoginViewModel>()

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logOutMenuOption -> {
                signOut()
                true
            }
            else -> false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.control_fragment,
            container,
            false
        )
        setHasOptionsMenu(true)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.createJwtInterceptor(loginViewModel.jwtToken)
        viewModel.createSocketService(viewLifecycleOwner)
        viewModel.addDisposable()

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            temperatures = viewModel.temperatures
            heaterControl = viewModel.HeaterControl()
        }

        return binding.root
    }

    override fun onDestroyView() {
        viewModel.jwtInterceptor.client_token = ""
        super.onDestroyView()
    }

    private fun signOut() {
        loginViewModel.auth.signOut()
        findNavController().navigate(ControlFragmentDirections.actionControlFragmentToLoginFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.drawer_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as RanchControl).appComponent.inject(this)
    }
}
