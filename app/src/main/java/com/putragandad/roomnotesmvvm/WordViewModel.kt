package com.putragandad.roomnotesmvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class WordViewModel(private val repository: WordRepository): ViewModel() {
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   UI when the data actually changes
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    /*
        Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }

    fun update(word: Word) = viewModelScope.launch {
        repository.update(word)
    }

    fun delete(word: Word) = viewModelScope.launch {
        repository.delete(word)
    }
}

// This class below created the ViewModel above
class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}