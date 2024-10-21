package com.example.parcial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class ResultFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_result, container, false)

        val resultText = view.findViewById<TextView>(R.id.resultText)
        val resultImage = view.findViewById<ProgressBar>(R.id.resultImage)
        val resultProgressBar = view.findViewById<ProgressBar>(R.id.resultProgressBar)
        val resultPercentage = view.findViewById<TextView>(R.id.resultPercentage)
        val wrongQuestions = view.findViewById<TextView>(R.id.wrongQuestions)
        val rightQuestions = view.findViewById<TextView>(R.id.rightQuestions)
        val retryButton = view.findViewById<Button>(R.id.retryButton)
        val chooseCategoryButton = view.findViewById<Button>(R.id.chooseCategoryButton)

        // Obtener los argumentos pasados (número de preguntas correctas, incorrectas, etc.)
        val totalCorrect = arguments?.getInt("correctAnswers") ?: 0
        val totalQuestions = arguments?.getInt("totalQuestions") ?: 10
        val totalWrong = totalQuestions - totalCorrect
        val percentage = (totalCorrect * 100) / totalQuestions

        // Configura los textos e imágenes
        resultText.text = if (percentage >= 80) "¡Excelente!" else "Buen intento"
        resultPercentage.text = "$percentage%"
        rightQuestions.text = "Preguntas correctas: $totalCorrect"
        wrongQuestions.text = "Preguntas incorrectas: $totalWrong"
        resultProgressBar.progress = percentage

        retryButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_questionFragment)
        }

        chooseCategoryButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_welcomeFragment)
        }

        return view
    }
}
