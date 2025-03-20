package com.hotdogcode.spendwise.presentation.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.hotdogcode.spendwise.R
import com.hotdogcode.spendwise.navigation.AppNavigation
import com.hotdogcode.spendwise.presentation.home.HomeActivity
import com.hotdogcode.spendwise.ui.theme.DarkForestGreenColor
import com.hotdogcode.spendwise.ui.theme.PBGFont
import com.hotdogcode.spendwise.ui.theme.PersonalFinanceTheme
import com.hotdogcode.spendwise.ui.theme.SoftPinkColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        enableEdgeToEdge()
        setContent {
            PersonalFinanceTheme {
                Scaffold {
                    innerPadding ->
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                painter = painterResource(R.drawable.logo_removebg),
                                contentDescription = "",
                                modifier = Modifier.size(50.dp)
                            )
                            Text(
                                text = "SpendWise",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = PBGFont,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }

                        Text(
                            text = "Your AI-powered saving companion.",
                            style = TextStyle(
                                fontSize = 20.sp,
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        DarkForestGreenColor, Color.Green, SoftPinkColor,
                                        DarkForestGreenColor
                                    )
                                )
                            ),
                            fontWeight = FontWeight.Normal,
                            fontFamily = PBGFont,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            textAlign = TextAlign.Center

                        )
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null){
            //logged in
            auth.currentUser?.reload()?.addOnCompleteListener {
                if (auth.currentUser == null) {
                    auth.signOut()
                    resultLauncher.launch(getGoogleSignInIntent())
                }else {
                    startActivity(Intent(this,HomeActivity::class.java))
                    finish()
                }
            }
        }else{
            auth.signOut()
            resultLauncher.launch(getGoogleSignInIntent())
        }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        if (result.resultCode == RESULT_OK) {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
    }

    fun getGoogleSignInIntent() : Intent{
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        return AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAlwaysShowSignInMethodScreen(true)
            .setAvailableProviders(providers)
            .build()
    }



}
















