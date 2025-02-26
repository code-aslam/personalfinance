package com.hotdogcode.spendwise.presentation.smartpurchase.components

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hotdogcode.spendwise.presentation.smartpurchase.SmartPurchaseViewModel
import com.hotdogcode.spendwise.ui.Dropdown
import com.hotdogcode.spendwise.ui.theme.MainColor
import com.hotdogcode.spendwise.ui.theme.PBGFont
import com.hotdogcode.spendwise.ui.theme.SecondaryColor
import com.hotdogcode.spendwise.ui.theme.SharpMainColor
import com.hotdogcode.spendwise.ui.theme.SoftPinkColor
import com.hotdogcode.spendwise_ml.MLTask
import com.hotdogcode.spendwise_ml.SpendWiseML

@Composable
fun SmartPurchaseScreen(
) {
    Scaffold()
    { innerPadding ->
        SmartPurchase(innerPadding)
    }
}

@Composable
fun SmartPurchase(
    paddingValues: PaddingValues
) {
    val context = LocalContext.current
    val smartPurchaseViewModel: SmartPurchaseViewModel = hiltViewModel()
    val expanseCategories by smartPurchaseViewModel.expanseCategoryList.collectAsState()
    val accountList by smartPurchaseViewModel.accountList.collectAsState()
    var itemNameValue by remember { mutableStateOf("") }
    var itemAmountValue by remember { mutableStateOf("") }
    val purchaseSuggestion by smartPurchaseViewModel.purchaseSuggestion.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor)
            .padding(
                PaddingValues(
                    bottom = paddingValues.calculateBottomPadding() + 10.dp,
                    top = paddingValues.calculateTopPadding() + 10.dp,
                    start = paddingValues.calculateLeftPadding(LayoutDirection.Ltr) + 10.dp,
                    end = paddingValues.calculateRightPadding(LayoutDirection.Ltr) + 10.dp
                )
            )

    ) {
        Text(
            text = "Smart Purchase Adviser",
            fontFamily = PBGFont,
            fontWeight = FontWeight.Bold,
            color = SecondaryColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )


        Column(
            modifier = Modifier.padding(top = 10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Item Name", color = SecondaryColor)
            OutlinedTextField(
                value = itemNameValue,
                onValueChange = { itemNameValue = it },
                placeholder = {Text("Optional", color = Color.LightGray)},
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SecondaryColor, RoundedCornerShape(5.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = SharpMainColor,
                    unfocusedContainerColor = SharpMainColor
                ),
            )
        }
        Column(
            modifier = Modifier.padding(top = 10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Category", color = SecondaryColor)
            Dropdown(options = expanseCategories.map { it.title }){
                selectedOption -> smartPurchaseViewModel.getCategorySpendingAcg(selectedOption)
            }
        }

        Column(
            modifier = Modifier.padding(top = 10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Amount", color = SecondaryColor)
            OutlinedTextField(
                value = itemAmountValue,
                onValueChange = { itemAmountValue = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SecondaryColor, RoundedCornerShape(5.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = SharpMainColor,
                    unfocusedContainerColor = SharpMainColor
                ),
                keyboardOptions = KeyboardOptions().copy(keyboardType = KeyboardType.Number)
            )
        }

        Column(
            modifier = Modifier.padding(top = 10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Account", color = SecondaryColor)
            Dropdown(options = accountList.map { it.name }){
                    selectedOption -> smartPurchaseViewModel.getAccountBalance(selectedOption)
            }
        }

        Row(
            modifier = Modifier.padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Slider(){
                value ->
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .border(1.dp, SecondaryColor, RoundedCornerShape(5.dp))
                    .clickable { (context as? Activity)?.finish() }
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(text = "Cancel", color = SoftPinkColor)
            }
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .border(1.dp, SecondaryColor, RoundedCornerShape(5.dp))
                    .clickable {
                        smartPurchaseViewModel.buildPurchaseTransaction(itemAmountValue.toInt())
                    }
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(text = "Suggest", color = SecondaryColor)
            }
        }

        Text(text = purchaseSuggestion.reason)


    }
}





@Composable
fun Slider(
    onSliderChange : (Float) -> Unit
) {
    var sliderPosition by remember { mutableFloatStateOf(1f) }
    var sliderText by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(getImportancyLevel(sliderPosition.toInt())) // Convert Float to Int

        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it
                onSliderChange(sliderPosition)
                            },
            valueRange = 1f..5f, // Set range from 1 to 10
            steps = 3, // 10 values → 9 steps (since first & last are fixed)
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = SecondaryColor,
                activeTrackColor = SecondaryColor,   // Change active track color
                inactiveTrackColor = SharpMainColor  // Change inactive track color
            )
        )

        Text("How important this item for you ?")
    }
}

fun getImportancyLevel(level: Int): String {
    return when (level) {
        1 -> "It is Luxury for me"
        2 -> "I can live without it"
        3 -> "It is a Basic need"
        4 -> "It is important to me"
        5 -> "I have to buy this"
        else -> "Unknown"
    }
}
