package com.example.parcial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import androidx.fragment.app.Fragment

class AnswerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_answer, container, false)
        val resultText = view.findViewById<TextView>(R.id.resultText)
        val feedbackText = view.findViewById<TextView>(R.id.feedbackText)

        val isCorrect = arguments?.getBoolean("isCorrect") ?: false
        val feedback = arguments?.getString("feedback") ?: ""

        resultText.text = if (isCorrect) "Correcto" else "Incorrecto"
        feedbackText.text = feedback

        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_answerFragment_to_questionFragment)
        }, 5000)

        return view
    }
}
