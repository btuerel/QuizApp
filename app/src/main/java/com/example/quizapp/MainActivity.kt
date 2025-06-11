package com.example.quizapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

data class Question(
    val questionText: String,
    val answers: List<String>,
    val correctAnswerIndex: Int
)

class MainActivity : AppCompatActivity() {

    private val questions = listOf(
        Question("Was ist die Hauptstadt von Deutschland?", listOf("Berlin", "Paris", "Rom"), 0),
        Question("Wann wurde die Univerität zu Köln gegründet?", listOf("1412", "1388", "1955"), 1),
        Question("Wie viele Monde hat der Mars?", listOf("1", "2", "3"), 1),
        Question("Was ist die Hauptstadt von Togo?", listOf("Accra", "Kpalimé", "Lomé"), 2),
        Question("Wie viele Planeten hat unser Sonnensystem?", listOf("7", "8", "9"), 1),
        Question("Wie lang ist der Panamakanal?", listOf("82km", "77km", "112km"), 1),
        Question("Wann wurde die erste Moschee in Deutschland erbaut?", listOf("1915", "1930", "1946"), 0),
        Question("Wie heißt der höchste Berg der Welt?", listOf("Kilimandscharo", "Mount Everest", "Uludag"), 1),
        Question("Wie hieß der aller erste Kontinent?", listOf("Pangea", "Panama", "Paramount"), 0),
        Question("Auf welchem Kontinent ist der Nil?", listOf("Südamerika", "Asien", "Afrika"), 2),
        Question("Wie lang ist der Nil?", listOf("6.650km", "600km", "60.050km"), 0),
        Question("Wie viele Länder sind in der EU", listOf("28", "27", "29"), 1),
        Question("Was ist die am meist verbreitete Weltsprache", listOf("Englisch", "Mandarin-Chinesich", "Deutsch"), 0),
        Question("Wie viele Kontinente gibt es", listOf("3", "5", "7"), 2),
        Question("Wie viele Buchstaben hat das deutsche Alphabet", listOf("23", "29", "26"), 2)
    )


    private var currentQuestionIndex = 0
    private var score = 0

    private lateinit var scoreTextView: TextView
    private lateinit var questionTextView: TextView
    private lateinit var answerButtons: List<Button>
    private lateinit var restartButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scoreTextView = findViewById(R.id.scoreTextView)
        questionTextView = findViewById(R.id.questionTextView)
        answerButtons = listOf(
            findViewById(R.id.answerButton1),
            findViewById(R.id.answerButton2),
            findViewById(R.id.answerButton3)
        )
        restartButton = findViewById(R.id.restartButton)
        restartButton.setOnClickListener {
            restartQuiz()
        }


        showQuestion()
    }

    private fun showQuestion() {
        val question = questions[currentQuestionIndex]
        questionTextView.text = question.questionText
        scoreTextView.text = "Punkte: $score"

        for (i in answerButtons.indices) {
            answerButtons[i].text = question.answers[i]
            answerButtons[i].setBackgroundColor(getColor(android.R.color.holo_blue_light))
            answerButtons[i].setOnClickListener {
                checkAnswer(i)
            }
        }
    }

    private fun checkAnswer(selectedIndex: Int) {
        val correct = questions[currentQuestionIndex].correctAnswerIndex

        if (selectedIndex == correct) {
            score++
            answerButtons[selectedIndex].setBackgroundColor(getColor(android.R.color.holo_green_light))
        } else {
            answerButtons[selectedIndex].setBackgroundColor(getColor(android.R.color.holo_red_light))
            answerButtons[correct].setBackgroundColor(getColor(android.R.color.holo_green_light))
        }

        answerButtons.forEach { it.setOnClickListener(null) }

        questionTextView.postDelayed({
            currentQuestionIndex++
            if (currentQuestionIndex < questions.size) {
                showQuestion()
            } else {
                showSummary()
            }
        }, 1000)
    }

    private fun showSummary() {
        questionTextView.text = "Quiz beendet!\nDu hast $score von ${questions.size} richtig."
        scoreTextView.text = ""
        answerButtons.forEach { it.visibility = View.GONE }
        restartButton.visibility = View.VISIBLE

    }

    private fun restartQuiz() {
        score = 0
        currentQuestionIndex = 0
        restartButton.visibility = View.GONE
        answerButtons.forEach { it.visibility = View.VISIBLE }
        showQuestion()
    }
}
