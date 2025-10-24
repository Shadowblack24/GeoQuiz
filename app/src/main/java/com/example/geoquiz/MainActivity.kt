package com.example.geoquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { GeoQuizApp() }
    }
}

@Composable
fun GeoQuizApp() {
    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {
            GeoQuizScreen()
        }
    }
}

private data class Question(val text: String, val answerTrue: Boolean)

@Composable
fun GeoQuizScreen() {
    // Массив вопросов
    val questions: Array<Question> = remember {
        arrayOf(
            Question("Canberra is the capital of Australia.", true),
            Question("The Pacific Ocean is larger than the Atlantic Ocean.", true),
            Question("The Suez Canal connects the Red Sea and the Indian Ocean.", false),
            Question("The source of the Nile River is in Egypt.", false),
            Question("The Amazon River is the longest river in the Americas.", true),
            Question("Lake Baikal is the world's oldest and deepest freshwater lake.", true),
        )
    }

    var index by rememberSaveable { mutableIntStateOf(0) }         // номер текущего вопроса
    var answered by rememberSaveable { mutableStateOf(false) }     // ответ дан?
    var correctCount by rememberSaveable { mutableIntStateOf(0) }  // счётчик правильных ответов
    var showResult by rememberSaveable { mutableStateOf(false) }   // показывать результат

    val total = questions.size
    val currentQuestion = if (index < total) questions[index] else questions[total - 1]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Добавлено расстояние между шапкой и названием приложения
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "GeoQuiz",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 24.dp)
        )

        Text(
            text = currentQuestion.text,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        )

        // Кнопки TRUE/FALSE
        if (!answered && !showResult) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        answered = true
                        if (currentQuestion.answerTrue) correctCount++
                        if (index == total - 1) {
                            showResult = true
                        }
                    },
                    modifier = Modifier.widthIn(min = 120.dp)
                ) { Text("TRUE") }

                Button(
                    onClick = {
                        answered = true
                        if (!currentQuestion.answerTrue) correctCount++
                        if (index == total - 1) {
                            showResult = true
                        }
                    },
                    modifier = Modifier.widthIn(min = 120.dp)
                ) { Text("FALSE") }
            }
        } else {
            // Если ответ дан, добавляем пустое пространство
            Spacer(Modifier.height(56.dp))
        }

        Spacer(Modifier.weight(1f))

        // Кнопка NEXT для перехода к следующему вопросу
        if (index < total - 1 && !showResult) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        index++
                        answered = false
                    },
                    enabled = answered
                ) { Text("NEXT") }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GeoQuizPreview() {
    GeoQuizApp()
}