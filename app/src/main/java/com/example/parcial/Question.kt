package com.example.parcial

data class Question(
    val text: String,              // El texto de la pregunta
    val options: List<String>,     // Las opciones de respuesta (lista de strings)
    val correctAnswer: String,     // La respuesta correcta
    val feedback: String,          // Retroalimentación que se muestra en el AnswerFragment
    val imageResId: Int            // ID del recurso de la imagen de la pregunta (R.drawable.image_x)
)