package com.cos30049.corethree

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var clubAdapter: ClubAdapter
    private lateinit var recyclerView: RecyclerView
    private var clubList = mutableListOf<Club>()
    private lateinit var filterButton: Button
    private var isFilterApplied = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView
        clubAdapter = ClubAdapter(clubList)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = clubAdapter
        }

        filterButton = findViewById(R.id.filterButton)
        filterButton.setOnClickListener {
            isFilterApplied = !isFilterApplied
            updateList()
        }

        // Load data from CSV file
        loadDataFromCSV()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadDataFromCSV() {
        val inputStream: InputStream = resources.openRawResource(R.raw.groups)
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String?
        var lineNumber = 0
        try {
            while (reader.readLine().also { line = it } != null) {
                // Skip header line
                if (lineNumber == 0) {
                    lineNumber++
                    continue
                }
                // Split CSV line and create Club object
                val parts = line!!.split(",")
                val club = Club(parts[0], parts[1].toBoolean(), parts[2], parts[3])
                clubList.add(club)
            }
            reader.close()
            clubList.sortBy { it.dateTime }
            clubAdapter.notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun updateList() {
        val filteredList = if (isFilterApplied) {
            clubList.filter { it.title == "Xsports" }
        } else {
            clubList
        }
        clubAdapter.updateData(filteredList)
    }

}
