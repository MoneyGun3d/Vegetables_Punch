package dmm.pmdm.vegetables_punch

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool

class Activity_juego : AppCompatActivity() {

    private lateinit var pantalla : ConstraintLayout
    private lateinit var mediaPlayer : MediaPlayer
    private lateinit var soundPoolEspachurrao: SoundPool
    private lateinit var soundPoolOof: SoundPool
    private var sonidoEspachurrao = 0
    private var sonidoOof = 0
    private var sonidoEspachurraoCargado = false

    val verduras = listOf(
        Verduras.BROCOLI,
        Verduras.COLIFLOR,
        Verduras.LECHUGA,
        Verduras.NABO,
        Verduras.ZANAHORIA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_juego)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pantalla)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //--Pantalla
        pantalla = findViewById(R.id.pantalla)

        soundPoolOof = SoundPool.Builder()
            .setMaxStreams(5)
            .build()

        sonidoOof = soundPoolOof.load(this, R.raw.oof_fallo, 1)

        pantalla.setOnClickListener {
            soundPoolOof.play(sonidoOof, 1f, 1f, 1, 0, 1f)
            val fragmento = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
            if (fragmento is Fragment_arriba) {
                fragmento.perderVida()
            }
        }

        //NECESARIO PARA QUE DETECTE LOS BORDES
        pantalla.post {
            iniciarSpawner()
        }
        //Pantalla--

        //--Sonido de Fondo
        mediaPlayer = MediaPlayer.create(this, R.raw.musica_fondo)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
        //Sonido de Fondo--


        //--Sonido Verduras
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPoolEspachurrao = SoundPool.Builder()
            .setMaxStreams(15)
            .setAudioAttributes(audioAttributes)
            .build()

        sonidoEspachurrao = soundPoolEspachurrao.load(this, R.raw.espachurrao, 1)
        soundPoolEspachurrao.setOnLoadCompleteListener { sp, sampleId, status ->
            if (status == 0 && sampleId == sonidoEspachurrao) {
                sonidoEspachurraoCargado = true
            }
        }
        //Sonido Verduras--

        val nombre = intent.getStringExtra("nombre")
        val puno = intent.getIntExtra("punoSelect", 0)

        //--Fragment
        val fragmento = Fragment_arriba()

        val args = Bundle().apply {
            putString("nombre", nombre)
            putInt("punoSelect", puno)
        }

        fragmento.arguments = args

        supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView, fragmento).commit()
        //Fragment--

    }

    private fun iniciarSpawner() {
        val spawner = Handler(Looper.getMainLooper())
        spawner.postDelayed(object : Runnable {
            override fun run() {
                generarVerdura()
                spawner.postDelayed(this, 500)
            }
        }, 1000)
    }

    private fun generarVerdura() {

        val puno = intent.getIntExtra("punoSelect", 0)
        var punoElegido = 0
        when(puno){
            Punos.PUNO_ROJO -> punoElegido = Manchas.MANCHA_ROJA
            Punos.PUNO_AZUL -> punoElegido = Manchas.MANCHA_AZUL
            Punos.PUNO_AMARILLO -> punoElegido = Manchas.MANCHA_AMARILLO
            Punos.PUNO_VERDE -> punoElegido = Manchas.MANCHA_VERDE
            Punos.PUNO_MORADO -> punoElegido = Manchas.MANCHA_MORADO
            Punos.PUNO_GRIS -> punoElegido = Manchas.MANCHA_GRIS
        }

        //--Parametros de verduras y pantalla
        val size = 150
        val verdura = ImageView(this)
        verdura.setImageResource(verduras.random())

        val params = ConstraintLayout.LayoutParams(size, size)
        params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
        params.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID

        val anchoPantalla = pantalla.width
        val altoPantalla = pantalla.height

        pantalla.addView(verdura)
        verdura.layoutParams = params
        //Parametros de verduras y pantalla--

        //--Donde se generan y hacia donde van
        val direccion = Random.nextInt(4) // 0=arriba,1=abajo,2=izq,3=der

        when (direccion) {
            0 -> { // ARRIBA a ABAJO
                params.leftMargin = Random.nextInt(0, anchoPantalla - size)
                params.topMargin = -size + 20
                verdura.translationY = 0f
                verdura.animate()
                    .translationY((altoPantalla + size).toFloat())
                    .rotationBy(360f)
                    .setDuration(6000)
                    .withEndAction { pantalla.removeView(verdura) }
                    .start()
            }
            1 -> { // ABAJO a ARRIBA
                params.leftMargin = Random.nextInt(0, anchoPantalla - size)
                params.topMargin = altoPantalla - 20
                verdura.translationY = 0f
                verdura.animate()
                    .translationY(-(altoPantalla + size).toFloat())
                    .rotationBy(360f)
                    .setDuration(6000)
                    .withEndAction { pantalla.removeView(verdura) }
                    .start()
            }
            2 -> { // IZQUIERDA a DERECHA
                params.leftMargin = -size + 20
                params.topMargin = Random.nextInt(0, altoPantalla - size - 200)
                verdura.translationX = 0f
                verdura.animate()
                    .translationX((anchoPantalla + size).toFloat())
                    .rotationBy(360f)
                    .setDuration(10000)
                    .withEndAction { pantalla.removeView(verdura) }
                    .start()
            }
            3 -> { // DERECHA a IZQUIERDA
                params.leftMargin = anchoPantalla - 20
                params.topMargin = Random.nextInt(0, altoPantalla - size - 200)
                verdura.translationX = 0f
                verdura.animate()
                    .translationX(-(anchoPantalla + size).toFloat())
                    .rotationBy(360f)
                    .setDuration(10000)
                    .withEndAction { pantalla.removeView(verdura) }
                    .start()
            }
        }
        //Donde se generan y hacia donde van--

        // Se cambia a la mancha del color elegido
        verdura.setOnClickListener {
            verdura.animate().cancel()

            if (sonidoEspachurraoCargado){
                soundPoolEspachurrao.play(sonidoEspachurrao, 0.5f, 0.5f, 1, 0, 1f)
            }

            verdura.setImageResource(punoElegido)

            verdura.animate()
                .alpha(0f)
                .setDuration(800)
                .withEndAction { pantalla.removeView(verdura) }
                .start()

            //Actualizar puntos
            val fragmento = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
            if (fragmento is Fragment_arriba) {
                fragmento.sumarPuntos(1)
            }

            verdura.isClickable = false
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        soundPoolEspachurrao.release()
    }
}