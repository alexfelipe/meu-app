package br.com.alexf.meuapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alexf.meuapp.ui.theme.MeuAppTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeuAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        var likesCounter by remember {
                            mutableIntStateOf(0)
                        }
                        val clock = Clock.System.now()
                        val localDatetime = clock.toLocalDateTime(TimeZone.currentSystemDefault())
                        MyButton(
                            text = { Text(text = "$localDatetime", color = Color.White) },
                            likes = {
                                Text(
                                    text = "$likesCounter",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            },
                            Modifier.clickable {
                                likesCounter++
                            }
                        )

                        MyButton(
                            text = {
                                Text(
                                    text = "super like", Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(Color.White)
                                        .padding(8.dp)
                                )
                            },
                            likes = {
                                Icon(Icons.Default.Check, contentDescription = null, tint = White)
                                Text(
                                    text = "$likesCounter",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 30.sp,
                                    color = Color.White
                                )
                            },
                            Modifier
                                .padding(8.dp)
                                .width(200.dp)
                                .clip(RoundedCornerShape(15))
                                .clickable {
                                    likesCounter++
                                }
                        )

                        Button(onClick = { /*TODO*/ }, content = {
                            MyButton(text = { }, likes = { })
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "like",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        })
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