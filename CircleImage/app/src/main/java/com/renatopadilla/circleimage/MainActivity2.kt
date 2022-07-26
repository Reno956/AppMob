package com.renatopadilla.circleimage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.Picasso

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val imageViewPicasso = findViewById<ImageView>(R.id.imageViewPicasso)
        Picasso.get().load("https://i.imgur.com/DvpvklR.png").into(imageViewPicasso)
        //Picasso.get().load(R.drawable.img_1).into(imageViewPicasso)
    }
}