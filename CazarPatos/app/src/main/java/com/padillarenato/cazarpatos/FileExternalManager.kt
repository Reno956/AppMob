package com.padillarenato.cazarpatos

import android.app.Activity
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class FileExternalManager (val actividad: Activity): FileHandler{
    override fun SaveInformation(datosAGrabar:Pair<String,String>){
        if (isExternalStorageWritable()) {
            FileOutputStream(
                File(
                    actividad.getExternalFilesDir(null),
                    "mySharedInformation.dat"
                )
            ).bufferedWriter().use { outputStream ->
                outputStream.write(LOGIN_KEY)
                outputStream.write(System.lineSeparator())
                outputStream.write(PASSWORD_KEY)
            }
        }
    }
    override fun ReadInformation():Pair<String,String>{
        var email = ""
        var clave = ""
        if (isExternalStorageReadable()) {
            FileInputStream(
                File(
                    actividad.getExternalFilesDir(null),
                    "mySharedInformation.dat"
                )
            ).bufferedReader().use {
                val datoLeido = it.readText()
                val textArray = datoLeido.split(System.lineSeparator())
                email = textArray[0]
                clave = textArray[1]
                println(textArray)
            }
        }
        return (email to clave)
    }
    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }
    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }
}