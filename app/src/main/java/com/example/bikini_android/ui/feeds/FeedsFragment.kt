package com.example.bikini_android.ui.feeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bikini_android.R
import com.example.bikini_android.databinding.FragmentFeedsBinding
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.ui.base.BaseFragment
import com.example.bikini_android.ui.common.RecyclerViewLayoutType
import com.example.bikini_android.ui.common.list.DefaultDiffCallback
import com.example.bikini_android.ui.common.list.DefaultListAdapter
import com.example.bikini_android.ui.map.FeedsLoadEvent
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author bsgreentea
 */

class FeedsFragment : BaseFragment() {
    private lateinit var binding: FragmentFeedsBinding
    private var feedAdapterHelper: FeedAdapterHelper = FeedAdapterHelper()
    private val feedsAdapter = DefaultListAdapter(DefaultDiffCallback<FeedItemViewModel>())
    private val viewModel: FeedsViewModel by lazy {
        ViewModelProvider(requireActivity())[FeedsViewModel::class.java]
    }
    private val itemEventRelay: Relay<RxAction> by lazy {
        viewModel.itemEventRelay
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            feedAdapterHelper = FeedAdapterHelper(it[KEY_LAYOUT_TYPE] as RecyclerViewLayoutType)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_feeds,
            container,
            false
        )
        binding.apply {
            feeds.adapter = feedsAdapter
            feeds.layoutManager = feedAdapterHelper.getLayoutManger(requireContext())
        }
        observeEvent()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadFeeds()
    }

    private fun observeEvent() {
        itemEventRelay
            .ofType(FeedsLoadEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                bindFeeds(event.feeds)
            }.addTo(disposables)
    }

    private fun bindFeeds(feeds: List<Feed>) {
        feedsAdapter.submitList(
            feeds.map {
                feedAdapterHelper.getFeedItemViewModel(it)
            }
        )
    }

    companion object {
        private const val KEY_LAYOUT_TYPE = "keyLayoutType"
        fun newInstance(layoutType: RecyclerViewLayoutType): FeedsFragment {
            return FeedsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LAYOUT_TYPE, layoutType)
                }
            }
        }
    }
}