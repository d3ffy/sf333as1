package com.example.guessgame03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guessgame03.ui.theme.GuessGame03Theme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuessGame03Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    guessGame()
                }
            }
        }
    }
}

@Composable
fun guessGame() {
    var guessInput by remember {
        mutableStateOf("")
    }
    var message by remember {
        mutableStateOf("")
    }
    var isGameOver by remember {
        mutableStateOf(false)
    }
    val randomNumber = remember {
        mutableStateOf(Random.nextInt(1, 1001))
    }
    var count by remember{
        mutableStateOf(0)
    }
    val guess = guessInput.toIntOrNull()

    Column(
        modifier = Modifier.padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "Guess a number between 1 and 1000!",
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.CenterHorizontally),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
        EditNumberScreen(
            value = guessInput,
            onValueChanged = { guessInput = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )
        Button(
            onClick = {
                when{
                    guess == null -> {
                        guessInput = ""
                        return@Button
                    }

                    guess < 1 || guess > 1000 -> {
                        guessInput = ""
                        message = "Please Input Number between 0 - 1000!"
                        return@Button
                    }

                    guess < randomNumber.value -> {
                        guessInput = ""
                        message = "Too low!"
                        count += 1
                    }

                    guess > randomNumber.value -> {
                        guessInput = ""
                        message = "Too high!"
                        count += 1
                    }

                    guess == randomNumber.value -> {
                        guessInput = ""
                        count += 1
                        message = "You guessed it! \n" +
                                "You guessed $count times!"
                        isGameOver = true
                    }
                }
            },
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .height(50.dp)
            ) {
            Text(text = "Guess",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Text(text = "$message",
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.CenterHorizontally),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (isGameOver){
            AlertDialog(
                onDismissRequest = {
                    isGameOver = false
                    guessInput = ""
                    message = ""
                    count = 0
                    randomNumber.value = Random.nextInt(1, 1001)
                                   },
                title = { Text(text = "Game Over") },
                text = { Text(text = message) },
                confirmButton = {
                    Button(
                        onClick = {
                            isGameOver = false
                            guessInput = ""
                            message = ""
                            count = 0
                            randomNumber.value = Random.nextInt(1, 1001)
                        },
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .size(width = 200.dp, height = 60.dp)
                    ) {
                        Text(text = "Play Again",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                })
        }
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "Made by: ",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = "Chaibancha Rangklang 6410742230",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = "Tanasit Vanachayangkul 6410742396",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNumberScreen(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier
){
    TextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = modifier,
        label = { Text(
            text = "Enter a number: ",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        ) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GuessGame03Theme {
        guessGame()
    }
}