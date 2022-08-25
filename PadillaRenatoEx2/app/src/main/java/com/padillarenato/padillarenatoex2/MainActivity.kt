package com.padillarenato.padillarenatoex2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.padillarenato.padillarenatoex2.ui.theme.PadillaRenatoEx2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PadillaRenatoEx2Theme {
                GameApp()
            }
        }
    }
}

@Composable
fun GameApp() {
    var currentStep by remember { mutableStateOf(rand()) }
    var oddStep = 0
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        when (currentStep) {
            1 -> {
                TextAndImage(
                    textLabelResourceId = R.string.question_1,
                    drawableResourceId = R.drawable.a1,
                    contentDescriptionResourceId = R.string.question_1_content,
                    onImageClick = {
                        currentStep = 2
                    }
                )
            }
            2 -> {
                TextAndImage(
                    textLabelResourceId = R.string.question_1,
                    drawableResourceId = R.drawable.a2,
                    contentDescriptionResourceId = R.string.question_1_content,
                    onImageClick = {
                        currentStep = 1
                    }
                )
            }
            3 -> {
                TextAndImage(
                    textLabelResourceId = R.string.question_2,
                    drawableResourceId = R.drawable.b1,
                    contentDescriptionResourceId = R.string.question_2_content,
                    onImageClick = {
                        currentStep = 4
                    }
                )
            }
            4 -> {
                TextAndImage(
                    textLabelResourceId = R.string.question_2,
                    drawableResourceId = R.drawable.b2,
                    contentDescriptionResourceId = R.string.question_2_content,
                    onImageClick = {
                        currentStep = 3
                    }
                )
            }
            5 -> {
                TextAndImage(
                    textLabelResourceId = R.string.question_3,
                    drawableResourceId = R.drawable.c1,
                    contentDescriptionResourceId = R.string.question_3_content,
                    onImageClick = {
                        currentStep = 6
                    }
                )
            }
            6 -> {
                TextAndImage(
                    textLabelResourceId = R.string.question_3,
                    drawableResourceId = R.drawable.c2,
                    contentDescriptionResourceId = R.string.question_3_content,
                    onImageClick = {
                        currentStep = 5
                    }
                )
            }
            7 -> {
                TextAndImage(
                    textLabelResourceId = R.string.question_4,
                    drawableResourceId = R.drawable.d1,
                    contentDescriptionResourceId = R.string.question_4_content,
                    onImageClick = {
                        currentStep = 8
                    }
                )
            }
            8 -> {
                TextAndImage(
                    textLabelResourceId = R.string.question_4,
                    drawableResourceId = R.drawable.d2,
                    contentDescriptionResourceId = R.string.question_4_content,
                    onImageClick = {
                        currentStep = 7
                    }
                )
            }
            9 -> {
                TextAndImage(
                    textLabelResourceId = R.string.question_5,
                    drawableResourceId = R.drawable.e1,
                    contentDescriptionResourceId = R.string.question_5_content,
                    onImageClick = {
                        currentStep = 10
                    }
                )
            }
            10 -> {
                TextAndImage(
                    textLabelResourceId = R.string.question_5,
                    drawableResourceId = R.drawable.e2,
                    contentDescriptionResourceId = R.string.question_5_content,
                    onImageClick = {
                        currentStep = 9
                    }
                )
            }
            11 -> {
                TextAndImage(
                    textLabelResourceId = R.string.question_6,
                    drawableResourceId = R.drawable.f1,
                    contentDescriptionResourceId = R.string.question_6_content,
                    onImageClick = {
                        currentStep = 12
                    }
                )
            }
            12 -> {
                TextAndImage(
                    textLabelResourceId = R.string.question_6,
                    drawableResourceId = R.drawable.f2,
                    contentDescriptionResourceId = R.string.question_6_content,
                    onImageClick = {
                        currentStep = 11
                    }
                )
            }
            13 -> {
                TextAndImage(
                    textLabelResourceId = R.string.question_7,
                    drawableResourceId = R.drawable.g1,
                    contentDescriptionResourceId = R.string.question_7_content,
                    onImageClick = {
                        currentStep = 14
                    }
                )
            }
            14 -> {
                TextAndImage(
                    textLabelResourceId = R.string.question_7,
                    drawableResourceId = R.drawable.g2,
                    contentDescriptionResourceId = R.string.question_7_content,
                    onImageClick = {
                        currentStep = 13
                    }
                )
            }
            15 -> {
                TextAndImage(
                    textLabelResourceId = R.string.question_8,
                    drawableResourceId = R.drawable.h1,
                    contentDescriptionResourceId = R.string.question_8_content,
                    onImageClick = {
                        currentStep = 16
                    }
                )
            }
            16 -> {
                TextAndImage(
                    textLabelResourceId = R.string.question_8,
                    drawableResourceId = R.drawable.h2,
                    contentDescriptionResourceId = R.string.question_8_content,
                    onImageClick = {
                        currentStep = 15
                    }
                )
            }
        }

        Row(Modifier.wrapContentSize(Alignment.BottomCenter)) {
            Button(onClick = { currentStep = oddStep}) {
                Text(text = stringResource(R.string.button_back), fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.width(32.dp))
            Button(onClick = { currentStep = rand()
                oddStep = currentStep  }) {
                Text(text = stringResource(R.string.button_next), fontSize = 16.sp)
            }
        }
        println(oddStep)
    }
}

@Composable
fun TextAndImage(
    textLabelResourceId: Int,
    drawableResourceId: Int,
    contentDescriptionResourceId: Int,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(textLabelResourceId),
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(drawableResourceId),
            contentDescription = stringResource(contentDescriptionResourceId),
            modifier = Modifier
                .wrapContentSize()
                .clickable(
                    onClick = onImageClick
                )
                .border(
                    BorderStroke(2.dp, Color(105, 205, 216)),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.response),
            fontSize = 12.sp
        )
    }
}

fun rand(): Int{
    val aux = (2..16).random()
    return if(aux%2 == 0){
        aux-1
    }else aux
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PadillaRenatoEx2Theme {
        GameApp()
    }
}