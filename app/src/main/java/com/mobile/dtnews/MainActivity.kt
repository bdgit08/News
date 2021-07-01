package com.mobile.dtnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.mobile.dtnews.databinding.ActivityMainBinding
import com.mobile.dtnews.view.SourceAdapter
import com.mobile.dtnews.viewModel.DefaultViewModelFactory
import com.mobile.dtnews.viewModel.NewsViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel : NewsViewModel
    private val adapter = SourceAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel = ViewModelProvider(this, DefaultViewModelFactory()).get(NewsViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val category = chip.text
            adapter.list.clear()
            adapter.notifyDataSetChanged()
            viewModel.getSource(category.toString())
        }
        binding.chipGroup.check(R.id.chipBusiness)
        setRecyclerView()
        viewModel.source.observe(this){
            adapter.list = it.sources.toMutableList()
            adapter.notifyDataSetChanged()
        }
        viewModel.error.observe(this){
            it.getContentIfNotHandled()?.let { error ->
                Utils.handleErrorMessage(this,error){ errorStr ->
                    Snackbar.make(binding.root,errorStr, Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.try_again)){
                        viewModel.tryAgain()
                    }.show()
                }
            }
        }
    }

    private fun setRecyclerView(){
        binding.rvSource.adapter = adapter
    }
}