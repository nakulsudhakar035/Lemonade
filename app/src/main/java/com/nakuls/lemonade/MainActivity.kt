package com.nakuls.lemonade

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nakuls.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                Lemonade(1, Modifier)
            }
        }
    }
}

@Composable
fun Lemonade(step: Int, modifier: Modifier = Modifier){
    var state by remember {
        mutableStateOf(1)
    }
    val squeezesRequired = (2..4).random()
    var squeezeState by remember {
        mutableStateOf(0)
    }
    val stateImageResource = when (state) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }
    val stateTextResource = when (state) {
        1 -> R.string.lemon_tree
        2 -> R.string.lemon
        3 -> R.string.lemonade_glass
        else -> R.string.lemon_tree
    }
    val stateMessageResource = when (state) {
        1 -> R.string.tap_tree
        2 -> R.string.tap_lemon
        3 -> R.string.tap_lemonade
        else -> R.string.tap_empty_glass
    }
    Column (
        modifier = modifier
            .fillMaxSize(),
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFEB3B)) // Yellow bar
                .padding(
                    top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                    bottom = 16.dp
                )
        ) {
            Text(
                text = stringResource(R.string.app_name),
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 20.sp
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center), // This does the centering
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFFD0F0C0)) // Light greenish background
                    .padding(18.dp)
            ) {
                Image(
                    painter = painterResource(stateImageResource),
                    contentDescription = stringResource(stateTextResource),
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            if(state == 2 && squeezeState != squeezesRequired) {

                                Log.i("status","$state:$squeezeState:$squeezesRequired")
                                squeezeState++
                            } else if(state < 4){
                                state++
                            } else {
                                squeezeState = 0
                                state = 1
                            }
                        }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(stateMessageResource),
                color = Color.DarkGray,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadePreview() {
    Lemonade(
        1,
        modifier = Modifier
            .fillMaxSize()
    )
}