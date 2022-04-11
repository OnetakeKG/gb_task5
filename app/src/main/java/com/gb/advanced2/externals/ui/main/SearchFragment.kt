package com.gb.advanced2.externals.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gb.advanced2.app.Contract
import com.gb.advanced2.databinding.SearchFragmentBinding
import com.gb.advanced2.externals.ui.SearchListAdapter
import com.gb.advanced2.externals.ui.navigation.Navigator
import com.gb.advanced2.externals.ui.navigation.NavigatorHolder
import org.koin.android.ext.android.inject

class SearchFragment : Fragment() {
    private var _binding: SearchFragmentBinding? = null
    private val binding: SearchFragmentBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val viewModel by inject<Contract.ViewModel>()
    private val navigatorHolder by inject<NavigatorHolder>()
    private val adapter = SearchListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.searchFab.setOnClickListener {
            val dialog = SearchDialogFragment()
            dialog.onSearchClickListener = { text -> viewModel.search(text) }
            dialog.show(parentFragmentManager, DIALOG_TAG)
        }
        binding.historyFab.setOnClickListener { navigatorHolder.getNavigator().goToHistory() }
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = adapter

        viewModel.getSearchScreenState().observe(viewLifecycleOwner) { renderState(it) }
    }

    companion object {
        const val DIALOG_TAG = "e52b6ab0-ca3e-4351-bbe1-3ad2c49840b7"
    }

    private fun setProgressVisible(visible: Boolean) {
        if (visible) {
            binding.progress.visibility = View.VISIBLE
            binding.recyclerview.visibility = View.GONE
        } else {
            binding.progress.visibility = View.GONE
            binding.recyclerview.visibility = View.VISIBLE
        }
    }

    private fun renderState(state: Contract.SearchScreenState) {
        when (state) {
            is Contract.SearchScreenState.Loading -> {
                setProgressVisible(true)
            }
            is Contract.SearchScreenState.Empty -> {
                setProgressVisible(false)
                adapter.submitList(null)
            }
            is Contract.SearchScreenState.DataLoaded -> {
                setProgressVisible(false)
                adapter.submitList(state.data)
            }
            is Contract.SearchScreenState.Error -> {
                setProgressVisible(false)
                adapter.submitList(null)
                Toast.makeText(requireContext(), state.error, Toast.LENGTH_LONG).show()
            }
        }
    }
}