package com.example.parcial

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.parcial.Question

class QuestionFragment : Fragment() {
    private lateinit var questions: List<Question>
    private var currentQuestionIndex = 0
    private var correctAnswers = 0
    private val maxQuestions = 10
    private var timer: CountDownTimer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)
        val questionText = view.findViewById<TextView>(R.id.questionText)
        val questionImageView = view.findViewById<ImageView>(R.id.questionImageView)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val timerText = view.findViewById<TextView>(R.id.timerText)
        questions = loadQuestions()
        showQuestion(questionText, questionImageView)
        startTimer(timerText)
        view.findViewById<Button>(R.id.option1Button).setOnClickListener {
            handleAnswer(it as Button, questionText, progressBar)
        }
        view.findViewById<Button>(R.id.option2Button).setOnClickListener {
            handleAnswer(it as Button, questionText, progressBar)
        }
        view.findViewById<Button>(R.id.option3Button).setOnClickListener {
            handleAnswer(it as Button, questionText, progressBar)
        }
        view.findViewById<Button>(R.id.option4Button).setOnClickListener {
            handleAnswer(it as Button, questionText, progressBar)
        }
    }
}
