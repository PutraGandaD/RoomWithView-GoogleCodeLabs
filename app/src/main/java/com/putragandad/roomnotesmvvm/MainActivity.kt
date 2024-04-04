package com.putragandad.roomnotesmvvm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.putragandad.roomnotesmvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private val newWordActivityRequestCode = 1
    private val updateWordActivityRequestCode = 2

    // Connect MainActivity with the data through ViewModel
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // define recyclerview
        val recyclerView = binding.recyclerview
        val adapter = WordListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // observer for allWords LiveData from WordViewModel
        wordViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.submitList(it) }
        })

        // set FAB to launch AddEditWordActivity
        val fab = binding.floatingActionButton
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getParcelableExtra<Word>(AddEditWordActivity.EXTRA_REPLY)?.let {
                val word = Word(it.id, it.word)
                wordViewModel.insert(word)
            }
        } else if(requestCode == updateWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getParcelableExtra<Word>(AddEditWordActivity.EXTRA_REPLY)?.let {
                val word = Word(it.id, it.word)
                wordViewModel.update(word)
            }
        } else {
            Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }
    }

    override fun onItemClicked(word: Word) {
        val intent = Intent(this@MainActivity, AddEditWordActivity::class.java)
        intent.putExtra(PUT_EXTRA_WORD, word)
        startActivityForResult(intent, updateWordActivityRequestCode)
        Toast.makeText(this, "${word.id}, ${word.word}", Toast.LENGTH_SHORT).show()
    }

    companion object {
        val PUT_EXTRA_WORD = "PUT_EXTRA_WORD"
    }
}