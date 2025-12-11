package dmm.pmdm.vegetables_punch

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class Fragment_arriba : Fragment() {


    private var puntos = 0
    private lateinit var textoPuntos: TextView

    private var vidas = 3
    private lateinit var textoVidas: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_arriba, container, false)

        val textoNombre = view.findViewById<TextView>(R.id.nombreFragment)
        textoPuntos = view.findViewById(R.id.puntosFragment)
        textoVidas = view.findViewById(R.id.vidas)

        val nombre = arguments?.getString("nombre")
        textoNombre.text = nombre
        puntos = 0
        textoPuntos.text = puntos.toString()

        actualizarVidas()

        return view
    }


    fun sumarPuntos(cantidad: Int) {
        puntos += cantidad
        textoPuntos.text = puntos.toString()
    }

    fun perderVida() {
        if (vidas > 1) {
            vidas--
            actualizarVidas()
        } else {
            val nombre = arguments?.getString("nombre")
            val intent = Intent(requireActivity(), Activity_final::class.java)
            intent.putExtra("puntos", puntos)
            intent.putExtra("nombre", nombre)

            startActivity(intent)
            requireActivity().finish()
        }



    }

    private fun actualizarVidas() {
        val vidaString = when (vidas) {
            3 -> getString(R.string.vidas_3)
            2 -> getString(R.string.vidas_2)
            1 -> getString(R.string.vidas_1)
            else -> getString(R.string.vidas_0)
        }
        textoVidas.text = vidaString
    }
}
