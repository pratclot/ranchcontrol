package com.pratclot.ranchcontrol.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.pratclot.ranchcontrol.R
import com.pratclot.ranchcontrol.RanchControl
import com.pratclot.ranchcontrol.databinding.ControlFragmentBinding
import com.pratclot.ranchcontrol.viewmodels.ControlViewModel
import com.pratclot.ranchcontrol.viewmodels.ControlViewModelFactory
import com.pratclot.ranchcontrol.viewmodels.TAG
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ControlFragment : Fragment() {

    companion object {
        fun newInstance() = ControlFragment()
    }

    private lateinit var binding: ControlFragmentBinding

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: ControlViewModelFactory
    private val viewModel by activityViewModels<ControlViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
        viewModel.testVal = 2

//        viewModel.tempCauldron.observe(viewLifecycleOwner, Observer {
//            binding.currentTemp1.text = it
//        })
//

        binding.heatingButton.setOnClickListener {
            addDisposable()
        }

        return binding.root
    }

    private fun addDisposable() {
        Log.e("FRAGMENT", "clicked!")
        disposable.add(
            viewModel.socketService.getTemperatures()
                .subscribe({
                    Log.e(TAG, "Logging ${it.tempCauldron}")
                }, {
                    Log.e(TAG, it.cause.toString())
                })
        )
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