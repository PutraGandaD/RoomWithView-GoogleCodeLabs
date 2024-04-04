package com.putragandad.roomnotesmvvm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.putragandad.roomnotesmvvm.databinding.ActivityAddEditWordBinding

class AddEditWordActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddEditWordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val editTextWord = binding.etWordInput
        val btnSaveWord = binding.btnSave
        var id = 0 /// default id to 0

        val notes = intent.getParcelableExtra<Word>("word")
        if(notes != null) {
            editTextWord.editText?.setText(notes.word)
            id = notes.id
        }

        btnSaveWord.setOnClickListener {
            val replyIntent = Intent()
            if(TextUtils.isEmpty(editTextWord.editText?.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = Word(id, editTextWord.editText?.text.toString())
                replyIntent.putExtra(EXTRA_REPLY, word)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}