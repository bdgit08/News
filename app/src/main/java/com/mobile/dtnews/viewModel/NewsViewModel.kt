package com.mobile.dtnews.viewModel

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.mobile.dtnews.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {
    private var articleResult: Flow<PagingData<Article>>? = null
    private val _keyword = MutableLiveData<Event<String>>()
    val keyword: LiveData<Event<String>> = _keyword

    private val _source = MutableLiveData<SourceResult>()
    val source: LiveData<SourceResult> = _source

    var sources = ""

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Event<Throwable>>()
    val error : LiveData<Event<Throwable>> = _error

    private var category = ""

    fun getSource(category: String){
        this.category = category
        _loading.value = true
        viewModelScope.launch {
            val result = repository.getSource(category)
            compareSource(result)
        }
    }

    fun tryAgain(){
        getSource(category)
    }

    private fun compareSource(result: Result<SourceResult>){
        if (result is Result.Success){
            _source.value = result.data
        }else {
            val t = result as Result.Error
            _error.value = Event(t.exception)
        }
        _loading.value = false
    }

    fun getArticle(query : MutableMap<String,Any>): Flow<PagingData<Article>> {
        val lastResult = articleResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<Article>> = repository.getArticle(query)
        articleResult = newResult
        return newResult
    }

    fun onSearch(query : String){
        _keyword.value = Event(query)
    }
}

@Suppress("UNCHECKED_CAST")
class DefaultViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val api = ServiceApi.instanceApi()
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(DefaultNewsRepository(api)) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}