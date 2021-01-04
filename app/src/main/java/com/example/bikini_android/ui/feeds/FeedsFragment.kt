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
import com.example.bikini_android.ui.map.BikiniMapFragment
import com.example.bikini_android.ui.map.FeedsEvent
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
    private var sortType: FeedsSortType = FeedsSortType.POPULAR
    private var feedsType: FeedsType = FeedsType.HOT_RANKING_FEEDS
    private val feedsAdapter = DefaultListAdapter(DefaultDiffCallback<FeedItemViewModel>())
    private lateinit var viewModel: FeedsViewModel
    private lateinit var itemEventRelay: Relay<RxAction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            feedAdapterHelper = FeedAdapterHelper(
                RecyclerViewLayoutType.valueOf(
                    it.getString(KEY_LAYOUT_TYPE_NAME) ?: RecyclerViewLayoutType.HORIZONTAL.name
                )
            )
            pivotFeed = it.getParcelable(KEY_PIVOT_FEED) as Feed?
            feedsType = FeedsType.valueOf(it.getString(KEY_FEEDS_TYPE) ?: FeedsType.HOT_RANKING_FEEDS.name)
            sortType = FeedsSortType.valueOf(it.getString(KEY_SORT_TYPE_NAME) ?: FeedsSortType.NEAR_DISTANCE.name)
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
        super.onCreateView(inflater, container, savedInstanceState)
        binding = it.apply {
            feeds.adapter = feedsAdapter
            feeds.layoutManager = feedAdapterHelper.getLayoutManger(requireContext())
        }

        viewModel = ViewModelProvider(requireActivity())[FeedsViewModel::class.java]
        itemEventRelay = viewModel.itemEventRelay
        observeEvent()
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initFeeds(feedsType)
    }

    private fun observeEvent() {
        itemEventRelay
            .ofType(FeedsEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                bindFeeds(event.feeds)
            }.addTo(disposables)

        itemEventRelay
            .ofType(FeedGridItemViewModel.ImageClickEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                getNavigationHelper()?.navigateToBikiniFeeds(
                    makeBundle(
                        RecyclerViewLayoutType.HORIZONTAL,
                        feedsType,
                        sortType,
                        event.feed
                    )
                )
            }.addTo(disposables)

        itemEventRelay
            .ofType(FeedLinearItemViewModel.LocationClickEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                event.feed.position?.let {
                    val bundle = BikiniMapFragment.makeBundle(it)
                    getNavigationHelper()?.navigateToBikiniMap(bundle)
                }

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
        private const val KEY_FEEDS_TYPE = "viewType"

        fun makeBundle(
            layoutType: RecyclerViewLayoutType,
            feedsType: FeedsType = FeedsType.HOT_RANKING_FEEDS,
            sortType: FeedsSortType = FeedsSortType.POPULAR,
            pivotFeed: Feed? = null
        ): Bundle {
            return Bundle().apply {
                putString(KEY_LAYOUT_TYPE_NAME, layoutType.name)
                putString(KEY_SORT_TYPE_NAME, sortType.name)
                putString(KEY_FEEDS_TYPE, feedsType.name)
                putParcelable(KEY_PIVOT_FEED, pivotFeed)
            }
        }
    }
}
