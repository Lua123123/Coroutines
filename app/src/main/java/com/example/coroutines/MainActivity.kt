package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val RESULT_1 = "result #1"
    lateinit var txtResult: TextView
    var btnClick: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnClick?.setOnClickListener {
            initView()
            //IO, Main, Thread
            CoroutineScope(IO).launch {
                fakeApiRequest()
            }

        }
    }

    private fun initView() {
        txtResult = findViewById<TextView>(R.id.txtResult)
        btnClick = findViewById<Button>(R.id.btnClick)
    }

    private fun setNewText(input: String) {
        val newText = txtResult.text.toString() + "\n$input"
        txtResult.text = newText
    }

    private suspend fun setTextOnMainThread(input: String) {
        withContext(Main) {
            setNewText(input)
        }
    }

    private suspend fun fakeApiRequest(){
        val result1 = getResult1FromApi()
        println("debug: $result1")
        setTextOnMainThread(result1)
        Log.d("iii", result1)
    }

    private suspend fun getResult1FromApi(): String {
        logThread("getResult1FromApi")
        delay(1000)
        return RESULT_1
    }

    private fun logThread(methodName: String) {
        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }
}
