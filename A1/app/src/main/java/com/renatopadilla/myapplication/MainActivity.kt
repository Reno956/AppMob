package com.renatopadilla.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setTitle("Texto")
        val actionBar: ActionBar? = supportActionBar
        //actionBar?.setTitle(R.string.txtActionBar)
        actionBar?.setTitle(getString(R.string.txtActionBar  ))
        //var titulo = (actionBar?.title ?:"unknow") + getString(R.string.txtActionBar)

    }
}