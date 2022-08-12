package com.padillarenato.cazarpatos

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var textViewUsuario: TextView
    lateinit var textViewContador: TextView
    lateinit var textViewTiempo: TextView
    lateinit var imageViewPato: ImageView
    private var mediaPlayer: MediaPlayer? = null
    lateinit var  mAdView: AdView
    private var interstitialAd: InterstitialAd? = null
    private final var TAG = "MainActivity"
    var contador = 0
    var anchoPantalla = 0
    var alturaPantalla = 0
    var gameOver = false
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Inicialización de variables
        textViewUsuario = findViewById(R.id.textViewUsuario)
        textViewContador = findViewById(R.id.textViewContador)
        textViewTiempo = findViewById(R.id.textViewTiempo)
        imageViewPato = findViewById(R.id.imageViewPato)
        mediaPlayer = MediaPlayer.create(this, R.raw.gunshot)
        database = Firebase.database.reference
        MobileAds.initialize(this){}

        //Ads Inicializacion
        mAdView = findViewById(R.id.adView)
        //mAdView.adUnitId = "ca-app-pub-5659945283466171/3492656630"
        //mAdView.adSize = "BANNER"
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        loadInterstitialAd()

        //Obtener el usuario de pantalla login
        val extras = intent.extras ?: return
        val usuario = extras.getString(EXTRA_LOGIN) ?: "Unknown"
        //usuario = usuario.substringBefore("@")
        val ind = subUser(usuario)
        val newUser = usuario.subSequence(0,ind)
        textViewUsuario.setText(newUser)

        //Determina el ancho y largo de pantalla
        inicializarPantalla()

        //Cuenta regresiva del juego
        inicializarCuentaRegresiva()

        //Evento clic sobre la imagen del pato
        imageViewPato.setOnClickListener {
            if (gameOver) return@setOnClickListener
            contador++
            if (! mediaPlayer!!.isPlaying){
                mediaPlayer?.start()
            }
            textViewContador.setText(contador.toString())
            imageViewPato.setImageResource(R.drawable.duck_clicked)
            //Evento que se ejecuta luego de 500 milisegundos
            Handler().postDelayed(Runnable {
                imageViewPato.setImageResource(R.drawable.duck)
                moverPato()
                mediaPlayer?.pause()
                mediaPlayer?.seekTo(0)
            }, 600)

        }
    }

    fun procesarPuntajePatosCazados(nombreJugador:String, patosCazados:Int){
        val jugador = Jugador(nombreJugador,patosCazados)
        //Trata de obtener id del documento del ranking específico,
        // si lo obtiene lo actualiza, caso contrario lo crea
        val db = Firebase.firestore
        db.collection("Ranking")
            .whereEqualTo("usuario", jugador.usuario)
            .get()
            .addOnSuccessListener { documents ->
                if(documents!= null &&
                    documents.documents != null &&
                    documents.documents.count()>0
                ){
                    val idDocumento = documents.documents.get(0).id
                    val jugadorLeido = documents.documents.get(0).toObject(Jugador::class.java)
                    if(jugadorLeido!!.patosCazados < patosCazados){
                        Log.w(EXTRA_LOGIN, "Actualizado, puntaje mayor")
                        actualizarPuntajeJugador(idDocumento, jugador)
                    }else{
                        Log.w(EXTRA_LOGIN, "No actualizado, puntaje menor")
                    }
                }
                else{
                    ingresarPuntajeJugador(jugador)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(EXTRA_LOGIN, "Error getting documents", exception)
                Toast.makeText(this, "Error al obtener datos de jugador", Toast.LENGTH_LONG).show()
            }
    }

    fun procesarPuntajePatosCazadosRTDB(nombreJugador: String, patosCazados: Int){
        val idJugador = nombreJugador.replace('.', '_')
        val jugador = Jugador(nombreJugador,patosCazados)
        database.child("Ranking").child(idJugador).setValue(jugador)
    }

    fun ingresarPuntajeJugador(jugador:Jugador){
        val db = Firebase.firestore
        db.collection("Ranking")
            .add(jugador)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(this,"Puntaje usuario ingresado exitosamente", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { exception ->
                Log.w(EXTRA_LOGIN, "Error adding document", exception)
                Toast.makeText(this,"Error al ingresar el puntaje", Toast.LENGTH_LONG).show()
            }
    }

    fun actualizarPuntajeJugador(idDocumento:String, jugador:Jugador){
        val db = Firebase.firestore
        db.collection("Ranking")
            .document(idDocumento)
            //.update(contactoHashMap)
            .set(jugador) //otra forma de actualizar
            .addOnSuccessListener {
                Toast.makeText(this,"Puntaje de usuario actualizado exitosamente", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { exception ->
                Log.w(EXTRA_LOGIN, "Error updating document", exception)
                Toast.makeText(this,"Error al actualizar el puntaje" , Toast.LENGTH_LONG).show()
            }
    }

    fun eliminarPuntajeJugador(idDocumentoSeleccionado:String){
        val db = Firebase.firestore
        db.collection("Ranking")
            .document(idDocumentoSeleccionado)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this,"Puntaje de usuario eliminado exitosamente", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { exception ->
                Log.w(EXTRA_LOGIN, "Error deleting document", exception)
                Toast.makeText(this,"Error al eliminar el puntaje" , Toast.LENGTH_LONG).show()
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings1 -> {
                reiniciarJuego()
                return true
            }
            R.id.action_settings2 -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://duckhuntjs.com/")))
                return true
            }
            R.id.action_settings3 -> {
                val intent = Intent(this, RankingActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun subUser(email: String): Int {
        val lastIndex = email.length - 1
        var lastNumber = 0;
        for (i in 0..lastIndex){
            if(email.elementAt(i)=='@'){
                lastNumber = i
            }
        }
        return lastNumber
    }

    private fun inicializarPantalla() {
        // 1. Obtenemos el tamaño de la pantalla del dispositivo
        val display = this.resources.displayMetrics
        anchoPantalla = display.widthPixels
        alturaPantalla = display.heightPixels
    }

    private fun moverPato() {
        val min = imageViewPato.getWidth()/2
        val maximoX = anchoPantalla - imageViewPato.getWidth()
        val maximoY = alturaPantalla - imageViewPato.getHeight()
        // Generamos 2 números aleatorios, para la coordenadas x , y
        val randomX = Random().nextInt(maximoX - min ) + 1
        val randomY = Random().nextInt(maximoY - min ) + 1

        // Utilizamos los números aleatorios para mover el pato a esa nueva posición
        imageViewPato.setX(randomX.toFloat())
        imageViewPato.setY(randomY.toFloat())
    }

    var contadorTiempo = object : CountDownTimer(10000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val segundosRestantes = millisUntilFinished / 1000
            textViewTiempo.setText("${segundosRestantes}s")
        }
        override fun onFinish() {
            textViewTiempo.setText("0s")
            gameOver = true
            mostrarDialogoGameOver()
            val nombreJugador = textViewUsuario.text.toString()
            val patosCazados = textViewContador.text.toString()
            procesarPuntajePatosCazados(nombreJugador, patosCazados.toInt())//Firestore
            //procesarPuntajePatosCazadosRTDB(nombreJugador, patosCazados.toInt()) //Realtime Database

        }
    }

    private fun inicializarCuentaRegresiva() {
        contadorTiempo.start()
    }

    private fun mostrarDialogoGameOver() {
        showInterstitial()
        val builder = AlertDialog.Builder(this)
        builder
            .setMessage("Felicidades!!\nHas conseguido cazar $contador patos")
            .setTitle("Fin del juego")
            .setIcon(R.drawable.duck)
            .setPositiveButton("Reiniciar",
                { _, _ ->
                    reiniciarJuego()
                })
            .setNegativeButton("Cerrar",
                { _, _ ->
                    //dialog.dismiss()
                    //finish()
                    val intencion = Intent(this, RankingActivity::class.java)
                    startActivity(intencion)
                })
        builder.create().show()
    }

    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    // The interstitialAd reference will be null until
                    // an ad is loaded.
                    interstitialAd = ad
                    /*Toast.makeText(this@MainActivity, "onAdLoaded()", Toast.LENGTH_SHORT)
                        .show()*/
                    ad.setFullScreenContentCallback(
                        object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                // Called when fullscreen content is dismissed.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                interstitialAd = null
                                Log.d(TAG, "The ad was dismissed.")
                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                // Called when fullscreen content failed to show.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                interstitialAd = null
                                Log.d(TAG, "The ad failed to show.")
                            }

                            override fun onAdShowedFullScreenContent() {
                                // Called when fullscreen content is shown.
                                Log.d(TAG, "The ad was shown.")
                            }
                        })
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    Log.i(TAG, loadAdError.message)
                    interstitialAd = null
                    val error: String = String.format(
                        Locale.ENGLISH,
                        "domain: %s, code: %d, message: %s",
                        loadAdError.domain,
                        loadAdError.code,
                        loadAdError.message
                    )
                    Toast.makeText(
                        this@MainActivity,
                        "onAdFailedToLoad() with error: $error", Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })
    }

    private fun showInterstitial() {
        // Show the ad if it"s ready. Otherwise toast and reload the ad.
        if (interstitialAd != null) {
            interstitialAd!!.show(this)
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show()
        }
    }

    fun reiniciarJuego(){
        contador = 0
        gameOver = false
        contadorTiempo.cancel()
        textViewContador.setText(contador.toString())
        moverPato()
        inicializarCuentaRegresiva()
    }

    override fun onStop() {
        Log.w(EXTRA_LOGIN, "Play canceled")
        contadorTiempo.cancel()
        textViewTiempo.text = "0s"
        gameOver = true
        mediaPlayer?.stop()
        super.onStop()
    }
    
    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }

}