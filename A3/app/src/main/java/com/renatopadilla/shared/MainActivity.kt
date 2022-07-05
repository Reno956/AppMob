package com.renatopadilla.shared

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        val sharedPrefWithName = getSharedPreferences("custom_shared_prefs", Context.MODE_PRIVATE)
        val editor1 = sharedPrefWithName.edit()
        editor1.putString("clave11", "valor11")
        editor1.putString("clave12", "valor12")
        editor1.putInt("clave13",123)
        editor1.apply();//o commit()
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        sharedPref.edit()
        .putString("clave11", "valor11")
            .putString("clave12", "valor12")
            .apply();//o commit()
        val textViewSaludo = findViewById<TextView>(R.id.textViewSaludo)
        textViewSaludo.text = sharedPref.getString("clave13","").toString()
    */
        val fichero = "fichero.txt"
        val texto = "texto\nalmacenado"
        val fos: FileOutputStream
        try {
            fos = openFileOutput(fichero, Context.MODE_PRIVATE)
            fos.write(texto.toByteArray())
            fos.close()
        } catch (e: FileNotFoundException) {
            Log.e("Mi Aplicación", e.message, e)
        } catch (e: IOException) {
            Log.e("Mi Aplicación", e.message, e)
        }
        openFileInput("fichero.txt").bufferedReader().use {
            val datoLeido = it.readText()
            val textArray = datoLeido.split("\n")
            val texto1 = textArray[0]
            val texto2 = textArray[1]
            val textViewSaludo = findViewById<TextView>(R.id.textViewSaludo)
            textViewSaludo.text = "$texto1 $texto2"
        }
    }

}