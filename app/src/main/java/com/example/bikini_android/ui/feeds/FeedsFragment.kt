package com.example.bikini_android.ui.feeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bikini_android.R
import com.example.bikini_android.databinding.FragmentFeedsBinding
import com.example.bikini_android.ui.common.list.DefaultListAdapter
import com.example.bikini_android.ui.feeds.adapter.FeedItemDiffCallback
import com.example.bikini_android.ui.feeds.adapter.FeedItemGridViewModel
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

        var adapter = DefaultListAdapter(FeedItemDiffCallback<FeedItemGridViewModel>())

        var testFeeds = mutableListOf<FeedItemGridViewModel>()

        testFeeds.add(FeedItemGridViewModel().apply {
            userId = "sample1"
        })
        testFeeds.add(FeedItemGridViewModel().apply {
            userId = "sample2"
        })
        testFeeds.add(FeedItemGridViewModel().apply {
            userId = "sample3"
        })
        testFeeds.add(FeedItemGridViewModel().apply {
            userId = "sample4"
        })
        testFeeds.add(FeedItemGridViewModel().apply {
            userId = "sample5"
        })
        testFeeds.add(FeedItemGridViewModel().apply {
            userId = "sample6"
        })
        adapter.submitList(testFeeds)

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