package dmm.pmdm.vegetables_punch

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Activity_final : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_final)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nombreTexto = findViewById<TextView>(R.id.nombreResultados)
        val puntosTexto = findViewById<TextView>(R.id.puntosFinales)

        val nombre = intent.getStringExtra("nombre")
        val puntos = intent.getIntExtra("puntos", 0)

        nombreTexto.text = nombre
        puntosTexto.text = puntos.toString()

    }
}
