package com.example.testjob.data.repository

import android.util.Log
import com.example.testjob.constants.Constants.TABLE_KEY
import com.example.testjob.constants.Constants.TAG_FOR_LOGS
import com.example.testjob.data.entity.Item
import com.google.firebase.database.*
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class Repository {

    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference(TABLE_KEY)
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    val listFlow = MutableSharedFlow<List<Item>>()
    val someItemFlow = MutableSharedFlow<Item>()

    fun readItems() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Item>()
                snapshot.children.forEach { element ->
                    val item = element.getValue(Item::class.java)
                    item?.key = element.key
                    item?.let { list.add(it) }
                }
                scope.launch {
                    listFlow.emit(list)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG_FOR_LOGS, error.toString())
            }
        })
    }

    fun getSomeItem(key: String) {
        database.child(key).get().addOnSuccessListener {
            scope.launch(Dispatchers.IO) {
                it.getValue(Item::class.java)?.let { it1 -> someItemFlow.emit(it1) }
            }
        }.addOnFailureListener {
            Log.d(TAG_FOR_LOGS, "onFailure get some item")
        }
    }
}

