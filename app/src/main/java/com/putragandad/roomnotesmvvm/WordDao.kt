package com.putragandad.roomnotesmvvm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

// Dao = Data Access Object
@Dao
interface WordDao {
    // Getting all words ordered alphabetically
    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): Flow<List<Word>> // Use Flow to produces values one at a time

    // Inserting a word
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    // Deleting all words
    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

    @Update
    suspend fun update(word: Word)
}

