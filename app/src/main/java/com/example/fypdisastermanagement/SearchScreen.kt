package com.example.myprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fypdisastermanagement.R

@Composable
fun SearchScreen(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        )
    {
        Surface(
            elevation = 3.dp,
        )
        {
              Box(
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth()
                        .background(Color(0XFFD34040))
                )
                {
                    Text(
                        text = "Search",
                        modifier = Modifier
                            .padding(top = 30.dp, start = 120.dp),
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.robotomedium)),
                            fontSize = 32.sp,
                            color = Color.White
                        )
                    )
                    Box(
                        modifier = Modifier.padding(top = 100.dp)
                    ) {
                        SearchField()
                    }
                }
            }

        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(){
    var search by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = search,
        onValueChange = { search = it },
        placeholder = {
            Text(
                text = "I want to help",
                fontFamily = FontFamily(Font(R.font.poppinsregular)),
                fontSize = 18.sp,
                color = Color.White.copy(alpha= 0.5f)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(10),
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .background(
                color = Color(0xFFC84747)
            ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
          //  textColor = Color.White,
            cursorColor = Color.White,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
        ),
        trailingIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White.copy(alpha = 0.5f),
                    modifier = Modifier
                        .size(32.dp)
                )
            }
        }
    )
}