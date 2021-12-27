/*
 * AccountInfoFragment.kt 2021. 10. 23
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.account.info

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.basicbug.core.rx.addTo
import com.basicbug.core.ui.list.DefaultDiffCallback
import com.basicbug.core.ui.list.DefaultListAdapter
import com.basicbug.core.ui.list.RecyclerViewLayoutType
import com.basicbug.core.util.bus.RxAction
import com.basicbug.core.util.bus.RxActionBus
import com.basicbug.core.util.ktx.autoCleared
import com.example.bikini_android.R
import com.example.bikini_android.databinding.FragmentAccountInfoBinding
import com.example.bikini_android.ui.account.setting.AccountInitItemViewModel
import com.example.bikini_android.ui.account.setting.AccountSettingEvent
import com.example.bikini_android.ui.account.viewmodel.AccountViewModelFactory
import com.example.bikini_android.ui.base.BikiniBaseFragment
import com.example.bikini_android.ui.board.BoardActivity
import com.example.bikini_android.ui.feeds.FeedAdapterHelper
import com.example.bikini_android.ui.feeds.FeedGridItemViewModel
import com.example.bikini_android.ui.feeds.FeedsEvent
import com.example.bikini_android.ui.feeds.FeedsFragment
import com.example.bikini_android.ui.feeds.FeedsSortType
import com.example.bikini_android.ui.feeds.FeedsType
import com.example.bikini_android.ui.feeds.viewmodel.FeedsViewModel
import com.example.bikini_android.ui.feeds.viewmodel.FeedsViewModelFactoryProvider
import com.example.bikini_android.util.bus.event.ReloadFeedEvent
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author bsgreentea
 */
class AccountInfoFragment : BikiniBaseFragment() {

    private var binding by autoCleared<FragmentAccountInfoBinding>()
    private var feedAdapterHelper by autoCleared<FeedAdapterHelper>()

    private lateinit var feedsViewModel: FeedsViewModel
    private lateinit var accountInfoViewModel: AccountInfoViewModel
    private lateinit var feedsEventRelay: Relay<RxAction>
    private lateinit var accountInfoEventRelay: Relay<RxAction>

    private val feedsType: FeedsType = FeedsType.MY_FEEDS
    private val sortType: FeedsSortType = FeedsSortType.LATEST_DATE
    private val recyclerViewLayoutType: RecyclerViewLayoutType = RecyclerViewLayoutType.GRID

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil.inflate<FragmentAccountInfoBinding>(
        inflater,
        R.layout.fragment_account_info,
        container,
        false
    ).also {
        super.onCreateView(inflater, container, savedInstanceState)

        feedsViewModel = ViewModelProvider(
            requireActivity(),
            FeedsViewModelFactoryProvider(requireActivity(), savedInstanceState)
        )[FeedsViewModelFactoryProvider.getFeedViewModelClazz(feedsType)]

        accountInfoViewModel = ViewModelProvider(
            requireActivity(),
            AccountViewModelFactory(AccountInitItemViewModel())
        )[AccountInfoViewModel::class.java]

        feedsEventRelay = feedsViewModel.itemEventRelay
        accountInfoEventRelay = accountInfoViewModel.itemEventRelay

        feedAdapterHelper = FeedAdapterHelper(
            DefaultListAdapter(DefaultDiffCallback()),
            recyclerViewLayoutType,
            feedsEventRelay
        )

        binding = it.apply {
            viewmodel = accountInfoViewModel
            myFeeds.adapter = feedAdapterHelper.feedsAdapter
            myFeeds.layoutManager = feedAdapterHelper.getLayoutManger(requireContext())
        }
        observeEvent()

        setHasOptionsMenu(true)

    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        feedsViewModel.loadFeeds()
        accountInfoViewModel.updateAccountInfoUi()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_account, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.account_detail_menu -> {
                getNavigationHelper()?.navigateToProfileDetail()
                false
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeEvent() {
        accountInfoEventRelay
            .ofType(AccountInfoViewModel.EventType::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    AccountInfoViewModel.EventType.OPEN_BOARD -> {
                        openBoard()
                    }
                    else -> Unit
                }
            }.addTo(disposables)

        accountInfoEventRelay
            .ofType(AccountInfoItemViewModel.EventType::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    AccountInfoItemViewModel.EventType.OPEN_EDIT_NICKNAME -> {
                        getNavigationHelper()?.navigateToAccountSetting()
                    }
                    else -> Unit
                }
            }.addTo(disposables)

        feedsEventRelay
            .ofType(FeedsEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .filter { it.feedsType == this.feedsType }
            .subscribe { event ->
                feedAdapterHelper.bindFeeds(event.feeds)
                accountInfoViewModel.updateAccountInfoUi()
            }.addTo(disposables)

        feedsEventRelay
            .ofType(FeedGridItemViewModel.ImageClickEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                getNavigationHelper()?.navigateToFeeds(
                    FeedsFragment.makeBundle(
                        RecyclerViewLayoutType.VERTICAL,
                        feedsType,
                        sortType,
                        event.feed
                    )
                )
            }.addTo(disposables)

        RxActionBus
            .toObservable(ReloadFeedEvent::class.java)
            .subscribe {
                feedsViewModel.reloadFeeds()
            }
            .addTo(disposables)

        RxActionBus
            .toObservable(AccountSettingEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                accountInfoViewModel.updateAccountInfoUi()
            }
            .addTo(disposables)

    }

    private fun openBoard() {
        startActivity(Intent(activity, BoardActivity::class.java))
    }
}