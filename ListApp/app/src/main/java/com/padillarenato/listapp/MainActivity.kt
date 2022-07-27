package com.padillarenato.listapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    lateinit var listViewSitiosWeb: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listaSitiosWeb = arrayOf(
            "https://www.epn.edu.ec",
            "https://www.fis.epn.edu.ec",
            "https://www.google.com",
            "https://www.microsoft.com"
        )
        listViewSitiosWeb = findViewById(R.id.listViewSitiosWeb)
        //listViewSitiosWeb.adapter = ArrayAdapter(this,R.layout.simple_list_item_p,resources.getStringArray(R.array.array_websites))
        listViewSitiosWeb.adapter = SitiosWebAdaptador(resources.getStringArray(R.array.array_websites))
        listViewSitiosWeb.setOnItemClickListener { parent, view, position, id ->
            val intencion = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
            startActivity(intencion)
        }
    }
}