package com.example.bikini_android.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.bikini_android.R
import com.example.bikini_android.databinding.FragmentProfileBinding
import com.example.bikini_android.ui.base.BaseFragment
import com.example.bikini_android.ui.board.BoardActivity
import com.example.bikini_android.ui.common.RecyclerViewLayoutType
import com.example.bikini_android.ui.common.list.DefaultDiffCallback
import com.example.bikini_android.ui.common.list.DefaultListAdapter
import com.example.bikini_android.ui.feeds.FeedAdapterHelper
import com.example.bikini_android.ui.feeds.FeedGridItemViewModel
import com.example.bikini_android.ui.feeds.FeedsFragment
import com.example.bikini_android.ui.feeds.FeedsSortType
import com.example.bikini_android.ui.feeds.FeedsType
import com.example.bikini_android.ui.feeds.viewmodel.FeedsViewModel
import com.example.bikini_android.ui.feeds.viewmodel.FeedsViewModelFactoryProvider
import com.example.bikini_android.ui.map.FeedsEvent
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.bus.RxActionBus
import com.example.bikini_android.util.bus.event.RefreshFeedEvent
import com.example.bikini_android.util.ktx.autoCleared
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author bsgreentea
 */
class ProfileFragment : BaseFragment() {

    private var binding by autoCleared<FragmentProfileBinding>()
    private var feedAdapterHelper by autoCleared<FeedAdapterHelper>()

    private lateinit var feedsViewModel: FeedsViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var feedsEventRelay: Relay<RxAction>
    private lateinit var profileEventRelay: Relay<RxAction>

    private val feedsType: FeedsType = FeedsType.MY_FEEDS
    private val sortType: FeedsSortType = FeedsSortType.LATEST_DATE
    private val recyclerViewLayoutType: RecyclerViewLayoutType = RecyclerViewLayoutType.GRID

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil.inflate<FragmentProfileBinding>(
        inflater,
        R.layout.fragment_profile,
        container,
        false
    ).also {
        super.onCreateView(inflater, container, savedInstanceState)
        feedsViewModel = ViewModelProvider(
            requireActivity(),
            FeedsViewModelFactoryProvider(requireActivity(), savedInstanceState)
        )[FeedsViewModelFactoryProvider.getFeedViewModelClazz(feedsType)]
        profileViewModel = ViewModelProvider(requireActivity())[ProfileViewModel::class.java]
        feedsEventRelay = feedsViewModel.itemEventRelay
        profileEventRelay = profileViewModel.itemEventRelay
        feedAdapterHelper = FeedAdapterHelper(
            DefaultListAdapter(DefaultDiffCallback()),
            recyclerViewLayoutType,
            feedsEventRelay
        )

        binding = it.apply {
            viewmodel = profileViewModel
            myFeeds.adapter = feedAdapterHelper.feedsAdapter
            myFeeds.layoutManager = feedAdapterHelper.getLayoutManger(requireContext())
        }
        observeEvent()

        setHasOptionsMenu(true)

    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feedsViewModel.loadFeeds()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile_detail_menu -> {
                getNavigationHelper()?.navigateToProfileDetail()
                false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeEvent() {
        profileEventRelay
            .ofType(ProfileViewModel.EventType::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    ProfileViewModel.EventType.OPEN_BOARD ->
                        openBoard()
                    else -> Unit
                }
            }.addTo(disposables)
        feedsEventRelay
            .ofType(FeedsEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .filter { it.feedsType == this.feedsType }
            .subscribe { event ->
                feedAdapterHelper.bindFeeds(event.feeds)
            }.addTo(disposables)

        feedsEventRelay
            .ofType(FeedGridItemViewModel.ImageClickEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                getNavigationHelper()?.navigateToProfileFeeds(
                    FeedsFragment.makeBundle(
                        RecyclerViewLayoutType.VERTICAL,
                        feedsType,
                        sortType,
                        event.feed
                    )
                )
            }.addTo(disposables)
        RxActionBus.toObservable(RefreshFeedEvent::class.java).subscribe {
            feedsViewModel.refreshFeeds()
        }.addTo(disposables)
    }

    private fun openBoard() {
        startActivity(Intent(activity, BoardActivity::class.java))
    }
}