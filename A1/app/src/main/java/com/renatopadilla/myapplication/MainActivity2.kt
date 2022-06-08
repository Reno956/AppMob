package com.renatopadilla.myapplication


import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity



class MainActivity2 : AppCompatActivity() {
    lateinit var buttonDo: Button
    lateinit var button2: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        initButton()
        doButton()
        /*buttonDo = findViewById(R.id.buttonDo)
        buttonDo.setOnClickListener {
            Toast.makeText(this, "Saludo", Toast.LENGTH_LONG).show()
            it.setBackgroundColor(Color.GREEN)
            setTitle(R.string.txtActionBar)
        }
        val button = findViewById<View>(R.id.buttonDo) as Button
        button.setOnClickListener {
            setTitle(R.string.txtActionBar)
        }*/
        longOperationAsync(
            longOperation = { Thread.sleep(1000L) },
            callback = { println("After one second") } //Prints after one second
        )
        println("Now")
        EjemploListaMutableEnteros()
        println(isValidEmail("renato_bga@live"))
    }

    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun longOperationAsync(longOperation: ()->Unit, callback: ()->Unit) {
        Thread({
            longOperation()
            callback()
        }).start()
    }

    fun EjemploListaMutableEnteros(){
        fun MutableList<Int>.swap(index1: Int, index2: Int) {
            val tmp = this[index1]
            this[index1] = this[index2]
            this[index2] = tmp
        }
        var myListInt : MutableList<Int> = mutableListOf(1,2)
        println("$myListInt")
        myListInt.swap(1,0)
        println("$myListInt")
    }

    fun initButton(){
        buttonDo = findViewById(R.id.buttonDo)
        button2 = findViewById(R.id.button2)
    }
    fun doButton(){
        buttonDo.setOnClickListener {
            Toast.makeText(this, "Saludo", Toast.LENGTH_LONG).show()
            it.setBackgroundColor(Color.GREEN)
            setTitle(R.string.txtActionBar)
        }
        button2.setOnClickListener {
            Toast.makeText(this, "Wellcome", Toast.LENGTH_LONG).show()
            it.setBackgroundColor(Color.RED)
            setTitle(R.string.txtActionBar1)
        }
    }
    fun onClickSaludo(view: View){
        setTitle(R.string.txtActionBar)

    }
}