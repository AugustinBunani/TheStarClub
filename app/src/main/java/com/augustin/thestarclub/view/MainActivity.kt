package com.augustin.thestarclub.view

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.augustin.thestarclub.repository.UserBenefitsRepository
import com.augustin.thestarclub.repository.UserDataRepository
import com.augustin.thestarclub.ui.theme.VolleyRequestTheme
import com.augustin.thestarclub.ui.theme.darkBlue
import com.augustin.thestarclub.ui.theme.darkerBlue
import com.augustin.thestarclub.ui.theme.lightBlue
import com.augustin.thestarclub.utilities.Resource
import com.augustin.thestarclub.viewmodel.UserBenefitsViewModel
import com.augustin.thestarclub.viewmodel.UserDataViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VolleyRequestTheme {
                Surface(color = MaterialTheme.colors.background) {

                    VolleyRequestApi()

                }
            }
        }
    }
}

@Composable
fun VolleyRequestApi() {
    val viewModel: UserBenefitsViewModel = viewModel(
        factory = UserBenefitsViewModelFactory(userBenefitsRepository = UserBenefitsRepository())
    )


    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val getAllUserData = viewModel.getBenefitsData.observeAsState()

    Surface(
        modifier = Modifier.fillMaxSize()

    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            backgroundColor = darkerBlue
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray.copy(alpha = 0.2f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    darkBlue,
                                    lightBlue
                                )
                            )
                        )
                        .padding(15.dp)
                ) {
                    Text(
                        text = "THE STAR CLUB",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                scope.launch {
                    val result = viewModel.getBenefitsData(context)

                    if (result is Resource.Success) {
                        Toast.makeText(context, "Fetching data Success", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                    }

                }

//
                if (!viewModel.isLoading.value) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }

                if (viewModel.isLoading.value) {
                    if (viewModel.getBenefitsData.value != null) {
                        LazyColumn(
                            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)
                        ) {
                        }

                        Toast.makeText(context, "Fetching data Success ${getAllUserData.value.toString()}", Toast.LENGTH_SHORT).show()


//                        HandleUserDataTier(getAllUserData.value!!.tier, context)
//                        handleUserData(getAllUserData.value!!.name)
//                        handleUserData(getAllUserData.value!!.tierPoints)
//                        handleUserData(getAllUserData.value!!.casinoDollars)

                    }
                }
            }
        }
    }
}

@Composable
fun HandleUserDataTier(name: Any, context: Context) {

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(140.dp)
            .clickable(
                interactionSource = CreateMutableInteractionSource(),
                indication = CreateIndication(),
                onClick = {
                    Toast
                        .makeText(context, "Benefits Idiot : ", Toast.LENGTH_SHORT)
                        .show()
                }
            ),
        elevation = 2.dp,
        backgroundColor = lightBlue,
        shape = RoundedCornerShape(corner = CornerSize(10.dp))
    ) {
        Row {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = name.toString(), style = typography.h6)
            }
        }
    }
}

@Composable
private fun CreateMutableInteractionSource(): MutableInteractionSource = remember {
    MutableInteractionSource()
}

@Composable
private fun CreateIndication(bounded: Boolean = true, color: Color = Color.Red) =
    rememberRipple(bounded = bounded, color = color)


@Composable
fun handleUserData(name: Any) {

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = lightBlue,
        shape = RoundedCornerShape(corner = CornerSize(10.dp))
    ) {
        Row {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = name.toString(), style = typography.h6)
            }
        }
    }
}


class UserDataViewModelFactory(
    private val userDataRepository: UserDataRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDataViewModel::class.java)) {
            return UserDataViewModel(userDataRepository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}

class UserBenefitsViewModelFactory(
    private val userBenefitsRepository: UserBenefitsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserBenefitsViewModel::class.java)) {
            return UserBenefitsViewModel(userBenefitsRepository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}
