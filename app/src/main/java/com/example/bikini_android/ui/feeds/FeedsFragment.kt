package com.example.bikini_android.ui.feeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.basicbug.core.rx.addTo
import com.basicbug.core.ui.list.CacheListAdapter
import com.basicbug.core.ui.list.DefaultDiffCallback
import com.basicbug.core.ui.list.RecyclerViewLayoutType
import com.basicbug.core.util.bus.RxAction
import com.basicbug.core.util.bus.RxActionBus
import com.basicbug.core.util.ktx.autoCleared
import com.example.bikini_android.R
import com.example.bikini_android.databinding.FragmentFeedsBinding
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.ui.base.BikiniBaseFragment
import com.example.bikini_android.ui.feeds.viewmodel.FeedsViewModel
import com.example.bikini_android.ui.feeds.viewmodel.FeedsViewModelFactoryProvider
import com.example.bikini_android.ui.map.BikiniMapFragment
import com.example.bikini_android.util.bus.event.ReloadFeedEvent
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author bsgreentea
 */

class FeedsFragment : BikiniBaseFragment() {
    private var binding by autoCleared<FragmentFeedsBinding>()
    private var feedAdapterHelper by autoCleared<FeedAdapterHelper>()
    private var pivotFeed: Feed? = null
    private var sortType: FeedsSortType = FeedsSortType.POPULAR
    private var feedsType: FeedsType = FeedsType.RANKING_FEEDS
    private var recyclerViewLayoutType: RecyclerViewLayoutType = RecyclerViewLayoutType.VERTICAL
    private var feedsReceived: List<Feed>? = null

    var posOfPivot = IDX_NOT_INITIATED

    private lateinit var viewModel: FeedsViewModel
    private lateinit var itemEventRelay: Relay<RxAction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recyclerViewLayoutType = RecyclerViewLayoutType.valueOf(
                it.getString(KEY_LAYOUT_TYPE_NAME) ?: RecyclerViewLayoutType.VERTICAL.name
            )
            pivotFeed = it.getParcelable(KEY_PIVOT_FEED) as Feed?
            feedsType =
                FeedsType.valueOf(it.getString(KEY_FEEDS_TYPE) ?: FeedsType.RANKING_FEEDS.name)
            sortType = FeedsSortType.valueOf(
                it.getString(KEY_SORT_TYPE_NAME) ?: FeedsSortType.NEAR_DISTANCE.name
            )

            val feedsParcelableArray = it.getParcelableArray(KEY_FEEDS)
            feedsParcelableArray?.let {
                val tempFeeds = mutableListOf<Feed>()
                feedsParcelableArray.forEachIndexed { idx, feedParcelable ->
                    (feedParcelable as? Feed)?.let { feed ->
                        tempFeeds.add(feed)

                        pivotFeed?.let { pivot ->
                            if (pivot.feedId == feed.feedId) posOfPivot = idx
                        }
                    }
                }
                feedsReceived = tempFeeds
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil.inflate<FragmentFeedsBinding>(
        inflater,
        R.layout.fragment_feeds,
        container,
        false
    ).also {
        super.onCreateView(inflater, container, savedInstanceState)
        viewModel = ViewModelProvider(
            requireActivity(),
            FeedsViewModelFactoryProvider(requireActivity(), savedInstanceState)
        )[FeedsViewModelFactoryProvider.getFeedViewModelClazz(feedsType)]
        itemEventRelay = viewModel.itemEventRelay
        feedAdapterHelper = FeedAdapterHelper(
            CacheListAdapter(DefaultDiffCallback()),
            recyclerViewLayoutType,
            itemEventRelay
        )

        binding = it.apply {
            feeds.adapter = feedAdapterHelper.feedsAdapter
            feeds.layoutManager = feedAdapterHelper.getLayoutManger(requireContext())
        }
        observeEvent()
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadFeeds(feedsReceived)
    }

    private fun observeEvent() {
        itemEventRelay
            .ofType(FeedsEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .filter { it.feedsType == this.feedsType }
            .subscribe { event ->
                feedAdapterHelper.bindFeeds(event.feeds)
                showPivotFeed()
            }.addTo(disposables)

        itemEventRelay
            .ofType(FeedGridItemViewModel.ImageClickEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                getNavigationHelper()?.navigateToFeeds(
                    makeBundle(
                        RecyclerViewLayoutType.VERTICAL,
                        feedsType,
                        sortType,
                        event.feed,
                        feedsReceived
                    )
                )
            }.addTo(disposables)

        itemEventRelay
            .ofType(FeedVerticalItemViewModel.LocationClickEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                event.feed.locationInfo?.let {
                    val bundle = BikiniMapFragment.makeBundle(it)
                    getNavigationHelper()?.navigateToFeedsMap(bundle)
                }

            }.addTo(disposables)
        RxActionBus.toObservable(ReloadFeedEvent::class.java).subscribe {
            viewModel.reloadFeeds()
        }.addTo(disposables)
    }

    private fun showPivotFeed() {
        if (recyclerViewLayoutType == RecyclerViewLayoutType.VERTICAL && posOfPivot != IDX_NOT_INITIATED) {
            binding.feeds.layoutManager?.scrollToPosition(posOfPivot)
        }
    }

    companion object {

        private const val IDX_NOT_INITIATED = -1

        private const val KEY_LAYOUT_TYPE_NAME = "keyLayoutType"
        private const val KEY_SORT_TYPE_NAME = "sortType"
        private const val KEY_PIVOT_FEED = "pivotFeed"
        private const val KEY_FEEDS_TYPE = "viewType"
        private const val KEY_FEEDS = "keyFeeds"

        fun makeBundle(
            layoutType: RecyclerViewLayoutType,
            feedsType: FeedsType = FeedsType.RANKING_FEEDS,
            sortType: FeedsSortType = FeedsSortType.POPULAR,
            pivotFeed: Feed? = null,
            feeds: List<Feed>? = null
        ): Bundle {
            return Bundle().apply {
                putString(KEY_LAYOUT_TYPE_NAME, layoutType.name)
                putString(KEY_SORT_TYPE_NAME, sortType.name)
                putString(KEY_FEEDS_TYPE, feedsType.name)
                putParcelable(KEY_PIVOT_FEED, pivotFeed)
                putParcelableArray(KEY_FEEDS, feeds?.toTypedArray())
            }
        }
    }
}
