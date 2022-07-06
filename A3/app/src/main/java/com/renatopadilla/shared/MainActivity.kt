package com.renatopadilla.shared

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
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
        editor1.putBoolean("clave14",123)
        editor1.apply();//o commit()

        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        sharedPref.edit()
            .putString("clave11", "valor11")
            .putString("clave12", "valor12")
            .putInt("clave13",123)
            .putBoolean("clave14",123)
            .apply();//o commit()

        val textViewSaludo = findViewById<TextView>(R.id.textViewSaludo)
        textViewSaludo.text = sharedPref.getString("clave13",-1).toString()
*/
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sharedPreferences = EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        val editor3 = sharedPreferences.edit();
        editor3.putString("clave11", "valor11")
        editor3.putString("clave12", "valor12")
        editor3.putInt("clave13",1234567)
        editor3.putBoolean("clave14",true)
        editor3.apply();//o commit()
        val textViewSaludo = findViewById<TextView>(R.id.textViewSaludo)
        textViewSaludo.text = sharedPreferences.getInt("clave13",-1).toString()
/*
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
        }*/
    }

}