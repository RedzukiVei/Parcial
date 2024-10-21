package com.example.parcial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)

        val startButton = view.findViewById<Button>(R.id.startButton)

        (activity as AppCompatActivity).supportActionBar?.title = "Inicio"


        startButton.setOnClickListener {
            // Navegar a la primera pregunta cuando se hace clic en "Iniciar"
            findNavController().navigate(R.id.action_welcomeFragment_to_questionFragment)
        }

        return view
    }
}