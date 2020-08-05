package com.arif.daggerhilt.ui.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.arif.daggerhilt.MyApplication
import com.arif.daggerhilt.R
import com.arif.daggerhilt.ui.detail.DetailActivity
import com.arif.daggerhilt.ui.adapter.MoviePagedAdapter
import com.arif.daggerhilt.data.entity.MovieModel
import com.arif.daggerhilt.util.ItemClickSupport
import com.arif.daggerhilt.di.ViewModelFactory
import com.arif.daggerhilt.data.repository.Resource
import kotlinx.android.synthetic.main.fragment_main_tab.*
import javax.inject.Inject


class MovieFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val movieViewModel: MovieViewModel by viewModels {
        factory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_main_tab, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Before Using Dagger
//        val factory = ViewModelFactory.getInstance(requireActivity())
//        movieViewModel = ViewModelProviders.of(requireActivity(), factory).get(MovieViewModel::class.java)

        recycleMovie.setHasFixedSize(true)
        recycleMovie.layoutManager = GridLayoutManager(activity, 2)

        getDataMovieOnline()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MyApplication).appComponent.inject(this)
    }

    private fun getDataMovieOnline() {
            val adapter = MoviePagedAdapter()
            recycleMovie.adapter = adapter
            recycleMovie.hasFixedSize()
            movieViewModel.getDataMovie(1).observe(viewLifecycleOwner, Observer { movie ->
                if (movie != null) {
                    when (movie) {
                        is Resource.Loading -> progressDialog.show()
                        is Resource.Success -> {
                            progressDialog.dismissWithAnimation()
                            showMovieList(movie.data as PagedList<MovieModel>, adapter)
                        }
                        is Resource.Error -> {
                            progressDialog.dismissWithAnimation()
                        }
                    }
                }
            })
    }

    private fun showMovieList(
        listMovie: PagedList<MovieModel>,
        adapter: MoviePagedAdapter
    ) {
        adapter.submitList(listMovie)
        adapter.notifyDataSetChanged()
        ItemClickSupport.addTo(recycleMovie).setOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
            override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra("data", listMovie[position])
                startActivity(intent)            }
        })
    }

    private val progressDialog: SweetAlertDialog by lazy {
        SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText(getString(R.string.loading))
            .setContentText(getString(R.string.waiting_text))
    }


}