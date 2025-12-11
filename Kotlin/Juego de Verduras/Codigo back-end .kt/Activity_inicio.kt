package dmm.pmdm.vegetables_punch

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.net.toUri

class Activity_inicio : AppCompatActivity() {

    private lateinit var nombre : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pantalla)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //--Otros Juegos
        val botonOtrosJuego = findViewById<Button>(R.id.otrosJuegos)

        botonOtrosJuego.setOnClickListener {

            val url = "https://en.wikipedia.org/wiki/Fruit_Ninja#:~:text=Fruit%20Ninja%20is%20a%20video,and%20must%20not%20slice%20bombs."

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = url.toUri()

            startActivity(intent)
        }
        //Otros Juegos--

        //--Punch
        val selectPunch = findViewById<Button>(R.id.punch)

        val punoSelect = intent.getIntExtra("punoSelect", 0)
        val eligio = intent.getBooleanExtra("eligio", false)

        if (eligio) {
            when (punoSelect) {
                Punos.PUNO_ROJO -> selectPunch.text = getString(R.string.puno_rojo)
                Punos.PUNO_AZUL -> selectPunch.text = getString(R.string.puno_azul)
                Punos.PUNO_AMARILLO -> selectPunch.text = getString(R.string.puno_amarillo)
                Punos.PUNO_VERDE -> selectPunch.text = getString(R.string.puno_verde)
                Punos.PUNO_MORADO -> selectPunch.text = getString(R.string.puno_morado)
                Punos.PUNO_GRIS -> selectPunch.text = getString(R.string.puno_gris)
            }
        }

        //Guardar el nombre para que siga estando cuando vuelva
        nombre = findViewById<EditText>(R.id.nombre)

        selectPunch.setOnClickListener {
            val intent = Intent(this, Activity_punch::class.java)
            intent.putExtra("nombre", nombre.text.toString())
            startActivity(intent)
        }

        nombre.setText(intent.getStringExtra("nombre"))
        //Punch--

        //--Jugar
        val jugar = findViewById<Button>(R.id.jugar)
        jugar.setOnClickListener {

            if (eligio && nombre.text.isNotEmpty()) {
                val intent = Intent(this, Activity_juego::class.java)

                intent.putExtra("punoSelect", punoSelect)
                intent.putExtra("nombre", nombre.text.toString())

                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.toast), Toast.LENGTH_SHORT).show()
            }
        }
        //Jugar--
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("nombreGuardado", nombre.text.toString())
    }
}