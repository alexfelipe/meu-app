package br.com.alexf.meuapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alexf.meuapp.ui.theme.MeuAppTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import java.util.Locale

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class, FormatStringsInDatetimeFormats::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeuAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        var openDatePicker by remember {
                            mutableStateOf(false)
                        }
                        var date by remember {
                            mutableStateOf("")
                        }
                        TextField(
                            date, onValueChange = {},
                            Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            label = {
                                Text("Data")
                            },
                            interactionSource = remember {
                                MutableInteractionSource()
                            }.also {
                                LaunchedEffect(it) {
                                    it.interactions.collectLatest { interaction ->
                                        if (interaction is PressInteraction.Release) {
                                            openDatePicker = true
                                        }
                                    }
                                }
                            },
                            readOnly = true
                        )

                        val state = rememberDatePickerState()
                        AnimatedVisibility(openDatePicker) {
                            DatePickerDialog(
                                onDismissRequest = {
                                    openDatePicker = false
                                }, confirmButton = {
                                    Button(onClick = {
                                        state.selectedDateMillis?.let { millis ->
                                            date = Instant.fromEpochMilliseconds(millis)
                                                .toLocalDateTime(TimeZone.UTC)
                                                .date.format(LocalDate.Format {
                                                    byUnicodePattern("dd/MM/yyyy")
                                                })
                                        }
                                        openDatePicker = false
                                    }) {
                                        Text("Selecionar")
                                    }
                                }
                            ) {
                                DatePicker(state)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyButton(
    text: @Composable () -> Unit,
    likes: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Red,
) {
    Row(
        modifier = modifier
            .background(backgroundColor)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        text()
        Spacer(
            modifier = Modifier.padding(8.dp)
        )
        likes()
    }
}

@Preview
@Composable
private fun MyButtonPreview() {
    MeuAppTheme {
        MyButton(text = {
            Text(
                text = "super like personalizado", Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(8.dp)
            )
        }, likes = {
            Text(text = "100", fontWeight = FontWeight.Bold)
        })
    }
}