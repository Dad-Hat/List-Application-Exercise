package com.example.listapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.listapplication.model.Item
import com.example.listapplication.model.ItemGroup
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /*
    * Grab the data from the url - https://hiring.fetch.com/hiring.json and then sort for
    * the adapter and pass it into a parser. Its an array of Json objects.
    * */
    private fun fetchData(){
        lifecycleScope.launch{
            val items = withContext(Dispatchers.IO){
                val url = URL("https://hiring.fetch.com/hiring.json")
                val connection = url.openConnection() as HttpURLConnection
                val response = connection.inputStream.bufferedReader().use { it.readText() }


                val items = parseItems(JSONArray(response))
                    .sortedWith(compareBy<Item> { it.listId }.thenBy { it.name })
                    .groupBy { it.listId }
                    .map { ItemGroup(it.key, it.value) }
            }
        }
    }

    /*
    * With the JsonArray go through each object and filter out each item with a blank or null name.
    * */
    private fun parseItems(jsonArray: JSONArray): List<Item>{
        val list = mutableListOf<Item>()
        for (i in 0 until jsonArray.length()){
            val obj = jsonArray.getJSONObject(i)
            val name = obj.optString("name", "")
            if(name.isBlank()) continue
            list.add(
                Item(
                    id = obj.getInt("id"),
                    listId = obj.getInt("listId"),
                    name = name
                )
            )
        }
        return list
    }
}