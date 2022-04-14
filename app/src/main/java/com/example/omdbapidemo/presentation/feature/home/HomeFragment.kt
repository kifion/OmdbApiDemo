package com.example.omdbapidemo.presentation.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omdbapidemo.R
import com.example.omdbapidemo.domain.model.Movie
import com.example.omdbapidemo.presentation.core.BaseFragment
import com.example.omdbapidemo.presentation.core.Response
import com.example.omdbapidemo.presentation.view.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private val viewModel: HomeViewModel by viewModels()

    private var searchListAdapter: SearchListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()

        viewModel.searchList.observe(viewLifecycleOwner, { eventSearchList ->
            when (eventSearchList.status) {
                Response.Status.LOADING -> showProgress(true)
                Response.Status.SUCCESS -> updateUi(eventSearchList.data)
                Response.Status.ERROR -> showError(eventSearchList.error)
            }
        })

        viewModel.lastSearchText.observe(viewLifecycleOwner, {
            it?.let {
                searchInputLayout.editText?.setText(it)
            }
        })
    }

    private fun setupUi() {
        val layoutManager = LinearLayoutManager(context)

        searchList.addItemDecoration(VerticalSpaceItemDecoration(20))
        searchList.layoutManager = layoutManager
        searchListAdapter = SearchListAdapter(object : SearchListAdapter.ClickListener {
            override fun onItemClicked(searchItem: Movie) {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(searchItem)
                )
            }
        })
        searchList.adapter = searchListAdapter

        searchInputLayout.editText?.doOnTextChanged { text, start, before, _ ->
            text?.let {
                if(viewModel.lastSearchText.value as String != text.toString()) {
                    viewModel.searchByText(it.toString())
                }
            }
        }
    }

    private fun showError(error: String?) {
        showProgress(false)
        error?.let {
            Snackbar.make(detailScrollView, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun updateUi(data: List<Movie>?) {
        data?.let {
            searchListAdapter?.items = it
        }
        showProgress(false)
    }

    private fun showProgress(progress: Boolean) {
        if (progress) {
            searchContentGroup.visibility = View.GONE
            searchProgressBar.visibility = View.VISIBLE
        } else {
            searchContentGroup.visibility = View.VISIBLE
            searchProgressBar.visibility = View.GONE
        }
    }
}