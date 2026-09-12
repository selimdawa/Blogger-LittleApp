package com.littleapp.blogger.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.littleapp.blogger.model.Page
import com.littleapp.blogger.repository.BloggerRepository

class PagesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = BloggerRepository(application)

    private val _pages = MutableLiveData<List<Page>>()
    val pages: LiveData<List<Page>> = _pages

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadPages() {
        _loading.value = true
        repository.fetchPages({ pagesList ->
            _pages.value = pagesList
            _loading.value = false
        }, { errorMsg ->
            _error.value = errorMsg
            _loading.value = false
        })
    }
}