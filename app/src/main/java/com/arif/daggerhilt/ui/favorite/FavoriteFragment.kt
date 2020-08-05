package com.arif.daggerhilt.ui.favorite


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
import com.arif.daggerhilt.data.repository.Resource
import com.arif.daggerhilt.ui.detail.DetailActivity
import com.arif.daggerhilt.ui.adapter.MoviePagedAdapter
import com.arif.daggerhilt.data.entity.MovieModel
import com.arif.daggerhilt.util.ItemClickSupport
import com.arif.daggerhilt.util.gone
import com.arif.daggerhilt.util.visible
import com.arif.daggerhilt.di.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_favorite.*
import javax.inject.Inject

class FavoriteFragment : Fragment() {
    private lateinit var progressDialog: SweetAlertDialog

    @Inject
    lateinit var factory: ViewModelFactory

    private val movieViewModel: FavoriteViewModel by viewModels {
        factory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Before Using Dagger
//        val factory = ViewModelFactory.getInstance(requireActivity())
//        movieViewModel = ViewModelProviders.of(requireActivity(), factory).get(FavoriteViewModel::class.java)

        recycleMovie.setHasFixedSize(true)
        recycleMovie.layoutManager = GridLayoutManager(activity, 2)

        createDialog()

        getDataMovieOnline()
    }

    private fun getDataMovieOnline() {
            val adapter = MoviePagedAdapter()
            recycleMovie.adapter = adapter
            movieViewModel.getDataMovie().observe(viewLifecycleOwner, Observer { movie ->
                if (movie != null) {
                    when (movie) {
                        is Resource.Loading -> progressDialog.show()
                        is Resource.Success -> {
                            progressDialog.dismissWithAnimation()
                            showMovieList(movie.data as PagedList<MovieModel>, adapter)
                        }
                        is Resource.Error -> {
                            progressDialog.dismissWithAnimation()
                            showErrorDialog()
                        }
                    }
                }
            })
    }

    private fun showErrorDialog() {
        val errorDialog = SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
            .setTitleText(getString(R.string.error))
            .setContentText(getString(R.string.something_wrong))
        errorDialog.show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as MyApplication).appComponent.inject(this)
    }

    private fun showMovieList(
        listMovie: PagedList<MovieModel>,
        adapter: MoviePagedAdapter
    ) {
        if (listMovie.size > 0) {
            recycleMovie.visible()
            adapter.submitList(listMovie)
            adapter.notifyDataSetChanged()
            ItemClickSupport.addTo(recycleMovie).setOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
                override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                    val intent = Intent(requireActivity(), DetailActivity::class.java)
                    intent.putExtra("data", listMovie[position])
                    startActivity(intent)                }
            })
        } else {
            recycleMovie.gone()
        }
    }

    private fun createDialog() {
        progressDialog = SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText(getString(R.string.loading))
            .setContentText(getString(R.string.waiting_text))
    }
}
