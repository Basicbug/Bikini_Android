package com.example.bikini_android.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bikini_android.R
import com.example.bikini_android.databinding.FragmentProfileBinding
import com.example.bikini_android.ui.base.BaseFragment
import com.example.bikini_android.ui.board.BoardActivity

/**
 * @author bsgreentea
 */
class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by lazy {
        ViewModelProvider(this)[ProfileViewModel::class.java]
    }

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

        binding.apply {
            profileViewModel = viewModel
        }

        setUpObservers()

        return binding.root
    }

    private fun setUpObservers() {
        viewModel.onMakeFeedClicked.observe(viewLifecycleOwner, Observer {
            startActivity(Intent(activity, BoardActivity::class.java))
        })
    }

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}