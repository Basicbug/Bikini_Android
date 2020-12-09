package com.example.bikini_android.ui.feeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
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
            feedAdapterHelper = FeedAdapterHelper(
                RecyclerViewLayoutType.valueOf(it.getString(KEY_LAYOUT_TYPE_NAME) ?: RecyclerViewLayoutType.LINEAR.name)
            )
            pivotFeed = it.getParcelable(KEY_PIVOT_FEED) as Feed?
            sortType = FeedSortType.valueOf(it.getString(KEY_SORT_TYPE_NAME) ?: FeedSortType.NEAR.name)

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
        private const val KEY_LAYOUT_TYPE_NAME = "keyLayoutType"
        private const val KEY_SORT_TYPE_NAME = "sortType"
        private const val KEY_PIVOT_FEED = "pivotFeed"
        fun newInstance(
            layoutType: RecyclerViewLayoutType,
            sortType: FeedSortType = FeedSortType.POPULAR,
            pivotFeed: Feed? = null
        ): FeedsFragment {
            return FeedsFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_LAYOUT_TYPE_NAME, layoutType.name)
                    putString(KEY_SORT_TYPE_NAME, sortType.name)
                    putParcelable(KEY_PIVOT_FEED, pivotFeed)
                }
            }
        }
    }
}