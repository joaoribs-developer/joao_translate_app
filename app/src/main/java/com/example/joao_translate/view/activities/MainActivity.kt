package com.example.joao_translate.view.activities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.joao_translate.R
import com.example.joao_translate.model.PatternLanguage
import com.example.joao_translate.view.components.TransparentLoadScreen
import com.example.joao_translate.view.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {
    val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = Color(0xff454545).hashCode()
        super.onCreate(savedInstanceState)
        setContent {

            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xff454545)
            ) {
                MainContent()
            }
        }
    }


    @Composable
    fun MainContent() {
        Column(
            Modifier
                .fillMaxSize()
                .padding(10.dp),
            Arrangement.SpaceBetween,
            Alignment.CenterHorizontally
        ) {
            Text(text = "João Translate", fontSize = 30.sp, fontWeight = W700, color = Color.White)
            TextBox(
                text = mainViewModel.textForTranslate,
                enabled = true,
                onClickCopy = {
                    Toast.makeText(this@MainActivity, "Conteúdo copiado!", Toast.LENGTH_SHORT).show()
                    copyToClipBoard(mainViewModel.textForTranslate)
                },
                onClickBin = { mainViewModel.textForTranslate = ""}
            ){
                mainViewModel.textForTranslate = it
            }
            CenterComponente()
            TextBox(
                text = mainViewModel.textTranslated,
                enabled = false,
                onClickCopy = {
                    Toast.makeText(this@MainActivity, "Conteúdo copiado!", Toast.LENGTH_SHORT).show()
                    copyToClipBoard(mainViewModel.textTranslated)
                },
                onClickBin = { mainViewModel.textTranslated = ""}
            )
            Button(
                shape = RoundedCornerShape(12.dp),
                onClick = { mainViewModel.getTextTranslated() },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xfffe0f92)
                )
            ) {
                Text(text = "Traduzir")
            }
        }
        if (mainViewModel.isLoading)
            TransparentLoadScreen()
    }


    @Composable
    private fun TextBox(text: String ,enabled: Boolean = true, onClickBin:()->Unit,onClickCopy:()->Unit,onValueChange:(String)->Unit={}) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xff565656))
        ) {
            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(10.dp), Arrangement.SpaceBetween
            ) {
                val manager = LocalFocusManager.current
                TextField(
                    shape = RoundedCornerShape(12.dp),
                    enabled = enabled,
                    value = text,
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = W500
                    ),
                    onValueChange = { onValueChange(it) },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = Color.Transparent,
                        backgroundColor = Color(0xff565656),
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone= {
                        manager.clearFocus()
                        mainViewModel.getTextTranslated()
                    }),
                    placeholder = { if (enabled) Text(text = "Digite aqui seu texto") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.80f)
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { onClickBin()},
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .size(50.dp)
                            .background(Color(0xFFA38E09))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.bin_svg),
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    IconButton(
                        onClick = { onClickCopy()},
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .size(50.dp)
                            .background(Color(0xFFA38E09))
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.copy_document_svg),
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun CenterComponente() {
        Row {
            MyDropDownMenu(mainViewModel.firstField.name){
                mainViewModel.firstField = it
            }
            Spacer(modifier = Modifier.width(5.dp))
            IconButton(
                onClick = { mainViewModel.invertFields() },
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .size(55.dp)
                    .background(Color(0xfffe0f92))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.reload_svg),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(27.dp)
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            MyDropDownMenu(mainViewModel.secondField.name){
                mainViewModel.secondField = it
            }
        }
    }

    @Composable
    private fun MyDropDownMenu(item: String, onClick: (PatternLanguage) -> Unit) {
        var expanded by remember {
            mutableStateOf(false)
        }
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .width(150.dp)
                .height(55.dp)
                .clickable { expanded = true },
            backgroundColor = Color(0xff565656)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .width(150.dp)
                    .padding(7.dp)
            ) {
                Text(text = item, textAlign = TextAlign.Center)
                Icon(
                    painter = painterResource(id = R.drawable.chevron_down_svg),
                    contentDescription = "",
                    modifier = Modifier.size(25.dp)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = !expanded },
                modifier = Modifier
                    .width(150.dp)
                    .background(Color(0xffa9a9a9))
            ) {
                PatternLanguage.values().forEach { language ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onClick(language)
                        }
                    ) {
                        Text(text = language.name)
                    }
                }
            }
        }
    }
    private fun copyToClipBoard(content: String){
        val clipboard = this.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", content)
            clipboard.setPrimaryClip(clip)
    }
}
