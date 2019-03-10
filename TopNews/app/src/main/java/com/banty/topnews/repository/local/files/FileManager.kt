package com.banty.topnews.repository.local.files

import android.content.Context
import android.os.Parcel
import android.support.annotation.Nullable
import android.util.Log
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
    * Saves the object in the file
    * */
    fun saveObjectToFile(dataToWrite: TopHeadlinesResponse?) {
        try {
            val fout = FileOutputStream("${applicationContext.filesDir}/$filePath")
            val oos = ObjectOutputStream(fout)
            oos.writeObject(dataToWrite)
            oos.close()
            Log.d(logTag, "file stored: $filePath")
        } catch (ex: IOException) {
            Log.d(logTag, "unable to store file: $filePath")
        }
    }

    /*
    * Returns the object from file.
    * returns Empty class if file is not present or could not read
    * */
    @Nullable
    fun getObjectFromFile(): TopHeadlinesResponse? {
        Log.d(logTag, "read object:  $filePath")
        var data: TopHeadlinesResponse
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

        return data
    }

}