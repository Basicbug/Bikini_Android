package com.example.bikini_android.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.bikini_android.R
import com.example.bikini_android.databinding.FragmentProfileBinding
import com.example.bikini_android.ui.base.BaseFragment
import com.example.bikini_android.ui.board.BoardActivity
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author bsgreentea
 */
class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var viewModel: ProfileViewModel
    private lateinit var itemEventRelay: Relay<RxAction>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )

        viewModel = ViewModelProvider(requireActivity())[ProfileViewModel::class.java]
        itemEventRelay = viewModel.itemEventRelay

        binding.apply {
            viewmodel = viewModel
        }

        observeEvent()

        return binding.root
    }

    private fun observeEvent() {
        itemEventRelay
            .ofType(ProfileViewModel.EventType::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    ProfileViewModel.EventType.OPEN_BOARD ->
                        openBoard()
                    else -> Unit
                }
            }.addTo(disposables)
    }

    private fun openBoard() {
        startActivity(Intent(activity, BoardActivity::class.java))
    }

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}