package com.example.bikini_android.ui.feeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.bikini_android.ui.holder.NavigationController
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
    private var pivotFeed: Feed? = null
    private var sortType: FeedSortType = FeedSortType.POPULAR
    private val feedsAdapter = DefaultListAdapter(DefaultDiffCallback<FeedItemViewModel>())
    private lateinit var viewModel: FeedsViewModel
    private val itemEventRelay: Relay<RxAction> by lazy {
        viewModel.itemEventRelay
    }
    private lateinit var navigateController: NavigationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            feedAdapterHelper = FeedAdapterHelper(it[KEY_LAYOUT_TYPE] as RecyclerViewLayoutType)
            pivotFeed = it[KEY_PIVOT_FEED] as Feed?
            sortType = it[KEY_SORT_TYPE] as FeedSortType

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<FragmentFeedsBinding>(
        inflater,
        R.layout.fragment_feeds,
        container,
        false
    ).also {
        binding = it.apply {
            feeds.adapter = feedsAdapter
            feeds.layoutManager = feedAdapterHelper.getLayoutManger(requireContext())
        }
        viewModel = ViewModelProvider(requireActivity())[FeedsViewModel::class.java]
        navigateController = NavigationController(binding.contentFragmentHolder.id, parentFragmentManager)
        observeEvent()
    }.root

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
        itemEventRelay
            .ofType(FeedItemViewModel.ClickEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                //TODO 네비게이트 수정이 필요할 듯
                navigateController.navigateToLinearFeeds()
            }.addTo(disposables)
    }

    private fun bindFeeds(feeds: List<Feed>) {
        feedsAdapter.submitList(
            feeds.map {
                feedAdapterHelper.getFeedItemViewModel(it, itemEventRelay)
            }
        )
    }

    companion object {
        private const val KEY_LAYOUT_TYPE = "keyLayoutType"
        private const val KEY_SORT_TYPE = "sortType"
        private const val KEY_PIVOT_FEED = "pivotFeed"
        fun newInstance(
            layoutType: RecyclerViewLayoutType,
            sortType: FeedSortType = FeedSortType.POPULAR,
            pivotFeed: Feed? = null
        ): FeedsFragment {
            return FeedsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LAYOUT_TYPE, layoutType)
                    putParcelable(KEY_SORT_TYPE, sortType)
                    putParcelable(KEY_PIVOT_FEED, pivotFeed)
                }
            }
        }
    }
}