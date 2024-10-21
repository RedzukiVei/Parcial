package com.example.parcial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.os.Handler
import android.widget.ImageView
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class AnswerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_answer, container, false)
        val resultText = view.findViewById<TextView>(R.id.resultText)
        val feedbackText = view.findViewById<TextView>(R.id.feedbackText)
        val answerImageView = view.findViewById<ImageView>(R.id.answerImage) // Cambiamos el ID a answerImage

        val isCorrect = arguments?.getBoolean("isCorrect") ?: false
        val feedback = arguments?.getString("feedback") ?: ""
        val answerImageResId = arguments?.getInt("answerImageResId") ?: R.drawable.default_image // Imagen para la respuesta

        resultText.text = if (isCorrect) "Correcto" else "Incorrecto"
        feedbackText.text = feedback
        answerImageView.setImageResource(answerImageResId) // Mostrar la imagen de la respuesta

        Handler(Looper.getMainLooper()).postDelayed({
            if (isAdded) {
                findNavController().navigate(R.id.action_answerFragment_to_questionFragment)
            }
        }, 5000)

        return view
    }
}

