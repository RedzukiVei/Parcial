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

        // Infla el layout y asigna la vista inflada a una variable
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        // Asocia los elementos del layout usando la vista inflada 'view'
        val questionText = view.findViewById<TextView>(R.id.questionText)
        val questionImageView = view.findViewById<ImageView>(R.id.questionImageView)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val progressText = view.findViewById<TextView>(R.id.progressText)
        val timerText = view.findViewById<TextView>(R.id.timerText)
        val questionNumberText = view.findViewById<TextView>(R.id.questionNumber)

        // Botones para las opciones
        val option1Button = view.findViewById<Button>(R.id.option1Button)
        val option2Button = view.findViewById<Button>(R.id.option2Button)
        val option3Button = view.findViewById<Button>(R.id.option3Button)
        val option4Button = view.findViewById<Button>(R.id.option4Button)

        // Cargar preguntas y mostrar la primera
        questions = loadQuestions()

        // Mostrar la primera pregunta
        showQuestion(questionText, questionImageView, timerText)

        // Añadir funcionalidad a los botones
        option1Button?.setOnClickListener {
            handleAnswer(option1Button.text.toString(), questionText, progressBar, progressText, timerText)
        }
        option2Button?.setOnClickListener {
            handleAnswer(option2Button.text.toString(), questionText, progressBar, progressText, timerText)
        }
        option3Button?.setOnClickListener {
            handleAnswer(option3Button.text.toString(), questionText, progressBar, progressText, timerText)
        }
        option4Button?.setOnClickListener {
            handleAnswer(option4Button.text.toString(), questionText, progressBar, progressText, timerText)
        }

        return view
    }

    private fun showQuestion(questionText: TextView, questionImageView: ImageView, timerText: TextView) {
        val currentQuestion = questions[currentQuestionIndex]
        questionText.text = currentQuestion.text
        questionImageView.setImageResource(currentQuestion.imageResId)

        // Iniciar el temporizador de 15 segundos
        startTimer(timerText)
    }

    private fun startTimer(timerText: TextView) {
        // Cancelar cualquier temporizador anterior
        timer?.cancel()

        // Crear un nuevo temporizador de 15 segundos
        timer = object : CountDownTimer(15000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Actualiza el texto del temporizador cada segundo
                timerText.text = "Tiempo: ${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {
                // Cuando el temporizador se acaba, procesar como si fuera una respuesta vacía
                handleAnswer("", questionText = TextView(context), progressBar = ProgressBar(context), progressText = TextView(context), timerText)
            }
        }.start()
    }

    private fun handleAnswer(selectedAnswer: String, questionText: TextView, progressBar: ProgressBar, progressText: TextView, timerText: TextView) {
        val currentQuestion = questions[currentQuestionIndex]
        val isCorrect = currentQuestion.correctAnswer == selectedAnswer

        // Cancela el temporizador cuando se selecciona una respuesta
        timer?.cancel()

        // Actualiza la barra de progreso
        progressBar.progress = (currentQuestionIndex + 1) * (100 / maxQuestions)
        progressText.text = "Progreso: ${currentQuestionIndex + 1} de $maxQuestions"

        // Navega al fragmento de respuesta con la información necesaria
        val bundle = Bundle().apply {
            putBoolean("isCorrect", isCorrect)
            putString("feedback", currentQuestion.feedback)
        }
        findNavController().navigate(R.id.action_questionFragment_to_answerFragment, bundle)

        // Avanzar a la siguiente pregunta o finalizar el juego
        currentQuestionIndex++
        if (currentQuestionIndex < maxQuestions) {
            showQuestion(questionText, view?.findViewById(R.id.questionImageView)!!, timerText)
        } else {
            findNavController().navigate(R.id.action_questionFragment_to_resultFragment)
        }
    }

    private fun loadQuestions(): List<Question> {
        // Aquí cargas las preguntas de tu lista
        return listOf(
            // Preguntas Faciles
            Question(
                text = "¿Cuál es el personaje principal de la serie de videojuegos 'The Legend of Zelda'?",
                options = listOf("Link", "Zelda", "Ganondorf", "Epona"),
                correctAnswer = "Link",
                feedback = "Link es el protagonista de la mayoría de los juegos de The Legend of Zelda. Aunque el título del juego lleva el nombre de la princesa Zelda, el jugador toma el rol de Link, un héroe que debe salvar Hyrule de las fuerzas del mal.",
                imageResId = R.drawable.p1
            ),
            Question(
                text = "¿En qué videojuego aparece el personaje de Mario por primera vez?",
                options = listOf("Super Mario Bros.", "Donkey Kong", "Mario Kart", "Mario Party"),
                correctAnswer = "Donkey Kong",
                feedback = "Mario apareció por primera vez en Donkey Kong en 1981 como 'Jumpman'. Este arcade fue diseñado por Shigeru Miyamoto, y marcó el inicio de la carrera de Mario como una de las figuras más icónicas en la historia de los videojuegos.",
                imageResId = R.drawable.p2
            ),
            Question(
                text = "¿Cuál es el nombre del hermano de Mario en los videojuegos?",
                options = listOf("Luigi", "Bowser", "Yoshi", "Wario"),
                correctAnswer = "Luigi",
                feedback = "Luigi es el hermano de Mario, conocido por ser más alto y tímido. Luigi hizo su debut en el juego arcade Mario Bros. en 1983, y aunque empezó como un personaje secundario, ha protagonizado varios juegos como Luigi's Mansion.",
                imageResId = R.drawable.p3
            ),
            Question(
                text = "¿Qué color es el personaje Sonic the Hedgehog?",
                options = listOf("Azul", "Rojo", "Verde", "Amarillo"),
                correctAnswer = "Azul",
                feedback = "Sonic, el veloz erizo azul, fue creado por SEGA para competir con Mario de Nintendo. Su primera aparición fue en 1991 en Sonic the Hedgehog para la consola Sega Genesis, y rápidamente se convirtió en la mascota de la compañía.",
                imageResId = R.drawable.p4
            ),
            Question(
                text = "¿En qué videojuego debes comer puntos y evitar fantasmas?",
                options = listOf("Pac-Man", "Space Invaders", "Pong", "Tetris"),
                correctAnswer = "Pac-Man",
                feedback = "Pac-Man, lanzado en 1980, es uno de los videojuegos más influyentes y reconocibles de todos los tiempos. Fue creado por Toru Iwatani para Namco y se convirtió en un ícono cultural de los años 80.",
                imageResId = R.drawable.p5
            ),
            Question(
                text = "¿Cómo se llama el videojuego, el cuál su protagonista se llama Red?",
                options = listOf("Digimon", "Pokémon", "Yu-Gi-Oh!", "Monster Hunter"),
                correctAnswer = "Pokémon",
                feedback = "Pokémon fue lanzado en 1996 por Nintendo, Game Freak, y Creatures. En el juego, los jugadores toman el rol de entrenadores Pokémon que deben capturar y entrenar criaturas para luchar. La franquicia sigue siendo una de las más grandes en la industria de los videojuegos.",
                imageResId = R.drawable.p6
            ),
            Question(
                text = "¿Cuál de estos juegos es un simulador de fútbol?",
                options = listOf("FIFA", "Call of Duty", "League of Legends", "Fortnite"),
                correctAnswer = "FIFA",
                feedback = "La serie FIFA, desarrollada por EA Sports, es el simulador de fútbol más popular del mundo. El primer juego de la serie fue lanzado en 1993 y sigue siendo un pilar en el género deportivo.",
                imageResId = R.drawable.p7
            ),

            // Preguntas Intermedias
            Question(
                text = "¿En qué año se lanzó el videojuego Minecraft?",
                options = listOf("2009", "2011", "2013", "2015"),
                correctAnswer = "2011",
                feedback = "Minecraft fue lanzado oficialmente en 2011 después de una versión beta en 2009. Desarrollado por Mojang y creado por Markus 'Notch' Persson, Minecraft es uno de los juegos más vendidos de la historia, conocido por su estilo gráfico de bloques y libertad creativa.",
                imageResId = R.drawable.p8
            ),
            Question(
                text = "¿Cómo se llama el personaje principal de la serie de videojuegos Halo?",
                options = listOf("Master Chief", "Cortana", "Arbiter", "Sergeant Johnson"),
                correctAnswer = "Master Chief",
                feedback = "Master Chief, también conocido como John-117, es el protagonista de la serie de juegos Halo de Microsoft. Su primera aparición fue en Halo: Combat Evolved en 2001, un juego que redefinió los shooters en primera persona.",
                imageResId = R.drawable.p9
            ),
            Question(
                text = "¿En qué videojuego utilizas una herramienta llamada 'Gravity Gun'?",
                options = listOf("Half-Life 2", "Portal", "Bioshock", "Doom"),
                correctAnswer = "Half-Life 2",
                feedback = "La Gravity Gun, una de las herramientas más icónicas de Half-Life 2, permite manipular objetos a distancia. Este juego, lanzado en 2004, fue desarrollado por Valve y es considerado uno de los mejores videojuegos de todos los tiempos.",
                imageResId = R.drawable.p10
            ),
            Question(
                text = "¿Qué juego popularizo el modo de juego 'Battle Royale'?",
                options = listOf("Fortnite", "Overwatch", "Apex Legends", "PUBG"),
                correctAnswer = "Fortnite",
                feedback = "Fortnite, desarrollado por Epic Games, se lanzó en 2017 y popularizó el modo Battle Royale. Aunque no fue el primer juego de este tipo, su éxito masivo lo convirtió en un fenómeno cultural.",
                imageResId = R.drawable.p11
            ),
            Question(
                text = "¿Cómo se llama el villano principal en la serie de videojuegos Final Fantasy VII?",
                options = listOf("Sephiroth", "Kefka", "Ultimecia", "Sin"),
                correctAnswer = "Sephiroth",
                feedback = "Sephiroth es el antagonista principal de Final Fantasy VII, uno de los RPG más aclamados de todos los tiempos. Con su icónica espada Masamune, es uno de los villanos más memorables en la historia de los videojuegos.",
                imageResId = R.drawable.p12
            ),
            Question(
                text = "¿Qué empresa desarrolló el videojuego Overwatch?",
                options = listOf("Blizzard", "Valve", "Ubisoft", "EA"),
                correctAnswer = "Blizzard",
                feedback = "Overwatch fue desarrollado por Blizzard Entertainment y lanzado en 2016. El juego es un shooter en primera persona por equipos que ha ganado numerosos premios por su diseño y jugabilidad.",
                imageResId = R.drawable.p13
            ),
            Question(
                text = "¿En qué juego se encuentra la ciudad ficticia de Los Santos?",
                options = listOf("GTA V", "Red Dead Redemption", "Mafia", "Watch Dogs"),
                correctAnswer = "GTA V",
                feedback = "Los Santos es la ciudad ficticia inspirada en Los Ángeles y es el escenario principal de Grand Theft Auto V. Este juego, lanzado en 2013, ha sido uno de los más vendidos de la historia.",
                imageResId = R.drawable.p14
            ),

            // Preguntas Difíciles
            Question(
                text = "¿Qué consola fue la primera en introducir los cartuchos de juego intercambiables?",
                options = listOf("Atari 2600", "NES", "Sega Genesis", "Magnavox Odyssey"),
                correctAnswer = "Atari 2600",
                feedback = "La Atari 2600, lanzada en 1977, fue la primera consola en popularizar los cartuchos intercambiables, lo que permitió a los jugadores comprar diferentes juegos para una sola máquina.",
                imageResId = R.drawable.p15
            ),
            Question(
                text = "¿Cuál es el nombre de Solid Snake en la serie Metal Gear?",
                options = listOf("David", "John", "Jack", "Sam"),
                correctAnswer = "David",
                feedback = "Solid Snake, cuyo verdadero nombre es David, es el protagonista de la serie Metal Gear, creada por Hideo Kojima. La franquicia es conocida por su enfoque en el sigilo y su narrativa compleja.",
                imageResId = R.drawable.p16
            ),
            Question(
                text = "¿Cuál fue el primer videojuego en incluir un 'modo Nueva Partida+'?",
                options = listOf("Chrono Trigger", "Final Fantasy", "The Legend of Zelda", "Metroid"),
                correctAnswer = "Chrono Trigger",
                feedback = "Chrono Trigger, lanzado en 1995, fue uno de los primeros juegos en incluir el modo 'Nueva Partida+', permitiendo a los jugadores volver a jugar con todos los objetos y habilidades obtenidas.",
                imageResId = R.drawable.p17
            ),
            Question(
                text = "¿En qué videojuego de la serie Zelda aparece por primera vez Sheik?",
                options = listOf("Ocarina of Time", "Majora's Mask", "Twilight Princess", "Wind Waker"),
                correctAnswer = "Ocarina of Time",
                feedback = "Sheik aparece por primera vez en The Legend of Zelda: Ocarina of Time. Aunque parece ser un nuevo personaje, Sheik es en realidad la princesa Zelda disfrazada.",
                imageResId = R.drawable.p18
            ),
            Question(
                text = "¿Cuál es el nombre del director de la serie de videojuegos Metal Gear?",
                options = listOf("Hideo Kojima", "Shigeru Miyamoto", "Yoko Taro", "Fumito Ueda"),
                correctAnswer = "Hideo Kojima",
                feedback = "Hideo Kojima es un aclamado director y diseñador de videojuegos, conocido por su trabajo en la serie Metal Gear. Es considerado uno de los autores más influyentes de la industria.",
                imageResId = R.drawable.p19
            ),
            Question(
                text = "¿Qué juego de rol (RPG) popularizó el uso del término 'grinding'?",
                options = listOf("Final Fantasy", "Dragon Quest", "Diablo", "World of Warcraft"),
                correctAnswer = "Diablo",
                feedback = "'Grinding' se refiere a la repetición de actividades en un videojuego para mejorar las habilidades del personaje o obtener objetos. Diablo, lanzado por Blizzard en 1996, popularizó este término en los RPG.",
                imageResId = R.drawable.p20
            )
        )
    }
}
