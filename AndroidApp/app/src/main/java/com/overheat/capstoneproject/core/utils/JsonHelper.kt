package com.overheat.capstoneproject.core.utils

import android.content.Context
import android.util.Log
import com.overheat.capstoneproject.core.data.source.remote.response.FaqResponse
import com.overheat.capstoneproject.core.data.source.remote.response.ListFaqResponse
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {

    private fun parsingFileToString(filename: String) : String? {
        return try {
            Log.d("onReading", "with file name $filename")
            val `is` = context.assets.open(filename)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            Log.d("onReading", "successfully read $filename")
            String(buffer)
        } catch (ex: IOException) {
            Log.e("onReading", "error on reading $filename")
            ex.printStackTrace()
            null
        }
    }

    fun readFaqDataset() : ListFaqResponse {
        val listFaqs = ArrayList<FaqResponse>()

        try {
            val responseObject = JSONObject(parsingFileToString("data_faqs.json").toString())
            val arrayFaqs = responseObject.getJSONArray("data")

            for (i in 0 until arrayFaqs.length()) {
                val objectFaq = arrayFaqs.getJSONObject(i)

                val id = objectFaq.getInt("id")
                val question = objectFaq.getString("question")
                val answer = objectFaq.getString("answer")

                val newFaq = FaqResponse(id, question, answer)
                listFaqs.add(newFaq)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return ListFaqResponse(
            data = listFaqs
        )
    }
}