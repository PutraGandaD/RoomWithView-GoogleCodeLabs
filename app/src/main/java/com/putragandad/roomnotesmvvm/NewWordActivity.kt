package com.putragandad.roomnotesmvvm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.putragandad.roomnotesmvvm.databinding.ActivityNewWordBinding

class NewWordActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNewWordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val editTextWord = binding.etWordInput
        val btnSaveWord = binding.btnSave

        btnSaveWord.setOnClickListener {
            val replyIntent = Intent()
            if(TextUtils.isEmpty(editTextWord.editText?.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = editTextWord.editText?.text.toString()
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