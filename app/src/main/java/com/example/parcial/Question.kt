package com.example.parcial

data class Question(
    val text: String,              // Texto de la pregunta
    val options: List<String>,     // Opciones de respuesta
    val correctAnswer: String,     // Respuesta correcta
    val feedback: String,          // Retroalimentación para mostrar en AnswerFragment
    val imageResId: Int,           // Imagen para la pregunta (QuestionFragment)
    val imageAnsId: Int            // Imagen para la respuesta (AnswerFragment)
)
