package com.example.bikini_android.ui.feeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.bikini_android.R
import com.example.bikini_android.databinding.FragmentFeedsBinding
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.ui.base.BaseFragment
import com.example.bikini_android.ui.feeds.adapter.FeedAdapter

/**
 * @author bsgreentea
 */

class FeedsFragment : BaseFragment() {

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

        testFeeds.add(Feed())
        testFeeds.add(Feed())
        testFeeds.add(Feed())
        testFeeds.add(Feed())
        testFeeds.add(Feed())
        testFeeds.add(Feed())

        adapter.setItems(testFeeds)

        binding.feeds.adapter = adapter
        return binding.root
    }

    companion object {
        fun newInstance(): FeedsFragment {
            return FeedsFragment()
        }
    }
}