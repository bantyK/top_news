package com.banty.topnews.repository.local.files

import android.content.Context
import android.os.Parcel
import android.support.annotation.Nullable
import android.util.Log
import com.banty.topnews.datamodels.Article
import com.banty.topnews.datamodels.TopHeadlinesResponse
import java.io.*

/**
 * Created by Banty on 10/03/19.
 * Performs file input/output operation
 */
class FileManager constructor(private val applicationContext: Context) {
    private val logTag = "FileManager"

    // name of the file on which the news data will be saved in the internal storage of the device
    private val filePath = "news.vlp"

    /*
    * Performs the file write operation
    * Saves the list of articles in the file
    *
    * */
    fun saveArticlesToFile(articles: List<Article>) {
        try {
            // create a serializable object to write into the file
            val dataToWrite = TopHeadlinesResponse("ok", articles.size, articles)
            val fout = FileOutputStream("${applicationContext.filesDir}/$filePath")
            val oos = ObjectOutputStream(fout)
            oos.writeObject(dataToWrite)
            oos.close()
            Log.d(logTag, "file stored: $filePath")
        } catch (ex: IOException) {
            Log.d(logTag, "unable to store file: $filePath :  ${ex.message}")
        }
    }

    /*
    * Performs file read operation
    * Returns the list of articles from file.
    * Returns empty list if case of any exception
    * */
    @Nullable
    fun getArticlesFromFile(): List<Article>? {
        Log.d(logTag, "read object:  $filePath")
        var data: TopHeadlinesResponse?
        try {
            val fin = FileInputStream("${applicationContext.filesDir}/$filePath")
            val ois = ObjectInputStream(fin)
            data = ois.readObject() as TopHeadlinesResponse
            ois.close()
            Log.d(logTag, "object read from file successfully, file: $filePath")
        } catch (ex: Exception) {
            data = TopHeadlinesResponse.createFromParcel(Parcel.obtain()) // empty
            ex.printStackTrace()
        }

        return data?.articles
    }

}