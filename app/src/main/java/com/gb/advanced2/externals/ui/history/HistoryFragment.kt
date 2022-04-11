package com.gb.advanced2.externals.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gb.advanced2.app.Contract
import com.gb.advanced2.databinding.HistoryFragmentBinding
import com.gb.advanced2.externals.ui.navigation.NavigatorHolder
import org.koin.android.ext.android.inject

class HistoryFragment : Fragment() {
    private var _binding: HistoryFragmentBinding? = null
    private val binding: HistoryFragmentBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val adapter = HistoryListAdapter()
    private val viewModel by inject<Contract.ViewModel>()
    private val navigatorHolder by inject<NavigatorHolder>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.historyList.adapter = adapter
        binding.historyList.layoutManager = LinearLayoutManager(requireContext())
        super.onViewCreated(view, savedInstanceState)
    }
}