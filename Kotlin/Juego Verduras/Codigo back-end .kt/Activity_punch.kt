package dmm.pmdm.vegetables_punch

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Activity_punch : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_punch)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pantalla)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nombre = intent.getStringExtra("nombre")

        val rojo = findViewById<ImageView>(R.id.rojo)
        val azul = findViewById<ImageView>(R.id.azul)
        val amarillo = findViewById<ImageView>(R.id.amarillo)
        val verde = findViewById<ImageView>(R.id.verde)
        val morado = findViewById<ImageView>(R.id.morado)
        val gris = findViewById<ImageView>(R.id.gris)

        val opciones = listOf(rojo, azul, amarillo, verde, morado, gris)
        var opcionMarcada : Int? = null
        var imgSeleccionada : ImageView? = null


        //--Seleccion de punch visual
        for ((indice, img) in opciones.withIndex()) {
            img.setOnClickListener {
                imgSeleccionada?.setBackgroundResource(android.R.color.transparent)

                img.setBackgroundResource(R.drawable.fondo_marco_negro)

                opcionMarcada = indice

                imgSeleccionada = img

            }
        }
        //Seleccion de punch visual--

        val confirmar = findViewById<Button>(R.id.confirmar)
        confirmar.setOnClickListener {

            val intent = Intent(this, Activity_inicio::class.java)
            intent.putExtra("punoSelect", opcionMarcada)
            intent.putExtra("nombre", nombre)

            if (opcionMarcada != null) {
                intent.putExtra("eligio", true)
            }

            startActivity(intent)
        }

    }
}