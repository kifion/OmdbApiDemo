package com.example.omdbapidemo.presentation.feature.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.omdbapidemo.R
import com.example.omdbapidemo.domain.model.MovieDetail
import com.example.omdbapidemo.domain.model.Status
import com.example.omdbapidemo.presentation.core.BaseFragment
import com.example.omdbapidemo.presentation.core.Response
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*

@AndroidEntryPoint
class DetailFragment : BaseFragment() {

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arguments = DetailFragmentArgs.fromBundle(requireArguments())
        viewModel.searchById(arguments.searchItem.imdbId)

        viewModel.detail.observe(viewLifecycleOwner, { eventDetail ->
            when (eventDetail) {
                is Status.Loading  -> showProgress(true)
                is Status.Error -> showError(eventDetail.error)
                is Status.Success-> updateUi(eventDetail.data)
            }
        })
    }

    private fun showError(error: String?) {
        showProgress(false)
        error?.let {
            Snackbar
                .make(detailScrollView, it, Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun updateUi(detail: MovieDetail?) {
        detail?.let {
            title.text = it.title
            country.text = it.country
            year.text = it.year
            plot.text = it.plot
            rating.text = it.imdbRating
            actors.text = it.actors
            awards.text = it.awards
            genre.text = it.genre

            Glide
                .with(requireContext())
                .load(it.poster)
                .centerCrop()
                .into(poster)
        }
        showProgress(false)
    }

    private fun showProgress(progress: Boolean) {
        if (progress) {
            progressBar.visibility = View.VISIBLE
            detailScrollView.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
            detailScrollView.visibility = View.VISIBLE
        }
    }
}