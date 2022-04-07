package com.example.omdbapidemo.presentation.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.omdbapidemo.R
import com.example.omdbapidemo.presentation.core.Status
import com.example.omdbapidemo.domain.model.Movie
import com.example.omdbapidemo.presentation.view.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()

    private var searchListAdapter: SearchListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun setupUi() {
        println("ZZZ 1")

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

        viewModel.setupLastSearchTextRequest()

        searchInputLayout.editText?.doOnTextChanged { text, _, _, _ ->
            text?.let {
                viewModel.searchByText(it.toString())
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()

        viewModel.searchList.observe(viewLifecycleOwner, { eventSearchList ->
            when (eventSearchList.status) {
                Status.LOADING -> showProgress(true)
                Status.SUCCESS -> updateUi(eventSearchList.data)
                Status.ERROR -> showError(eventSearchList.error)
            }
        })

        viewModel.searchText.observe(viewLifecycleOwner, {
            it?.let {
                searchInputLayout.editText?.setText(it)
            }
        })
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