package com.example.bikini_android.ui.feeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bikini_android.R
import com.example.bikini_android.databinding.FragmentFeedsBinding
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.ui.feeds.adapter.FeedAdapter
import com.example.bikini_android.ui.feeds.adapter.FeedAdapter.Companion.FOURCOLUMN
import com.example.bikini_android.ui.holder.MainHolderFragment

/**
 * @author bsgreentea
 */

class FeedsFragment : MainHolderFragment() {

    private lateinit var binding: FragmentFeedsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentFeedsBinding>(
            inflater,
            R.layout.fragment_feeds,
            container,
            false
        )

        var adapter = FeedAdapter()

        var testFeeds = mutableListOf<Feed>()

        testFeeds.add(Feed(userId = "sample1"))
        testFeeds.add(Feed(userId = "sample2"))
        testFeeds.add(Feed(userId = "sample3"))
        testFeeds.add(Feed(userId = "sample4"))
        testFeeds.add(Feed(userId = "sample5"))
        testFeeds.add(Feed(userId = "sample6"))

        adapter.setItems(testFeeds)
        adapter.setViewType(FOURCOLUMN)

        binding.feeds.adapter = adapter
        binding.feeds.layoutManager = GridLayoutManager(activity, 4)

        return binding.root
    }

    companion object {
        fun newInstance(): FeedsFragment {
            return FeedsFragment()
        }
    }
}