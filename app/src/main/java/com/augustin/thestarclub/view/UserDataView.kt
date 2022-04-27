package com.augustin.thestarclub.view

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.augustin.thestarclub.R
import com.augustin.thestarclub.Screen
import com.augustin.thestarclub.repository.UserDataRepository
import com.augustin.thestarclub.ui.theme.*
import com.augustin.thestarclub.utilities.Resource
import com.augustin.thestarclub.viewmodel.UserDataViewModel
import kotlinx.coroutines.launch

@Composable
fun UserDataView(navController: NavController) {
    val viewModel: UserDataViewModel = viewModel(
        factory = UserDataViewModelFactory(userDataRepository = UserDataRepository())
    )

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberScrollState()
    val getAllUserData = viewModel.getUserData.observeAsState()

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
                    val result = viewModel.getUserData(context)

                    if (result is Resource.Success) {
                        Toast.makeText(context, "Fetching data Success", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                    }

                }

                if (!viewModel.isLoading.value) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }

                if (viewModel.isLoading.value) {
                    if (viewModel.getUserData.value != null) {
                        LazyColumn(
                            modifier = Modifier
                                .padding(top = 10.dp, start = 10.dp, end = 10.dp)

                        ) {

                        }
                        handleUserDataName(getAllUserData.value!!.name)
                        handleUserTier(getAllUserData.value!!.tier, navController)
                        HandleUserTierData(getAllUserData.value!!.tierPoints)
                        handleUserStarGifts(navController)
                        handleUserCasinoDollars(getAllUserData.value!!.casinoDollars, navController)

                    }
                }
            }
        }
    }
}

@Composable
fun handleUserDataName(name: String) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp),
        backgroundColor = midnightBlue,
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        elevation = 8.dp
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Welcome Back",
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = name,
                    style = MaterialTheme.typography.h4
                )
            }
            IconCard(icon = R.drawable.profile, padding = 0.dp)

        }

    }

}

@Composable
fun IconCard(icon: Int, padding: Dp) {

    Card(
        modifier = Modifier
            .height(80.dp)
            .width(80.dp),
        shape = CircleShape,
        border = BorderStroke(1.dp, midnightBlue)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun handleUserTier(name: Any, navController: NavController) {

    Card(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .height(100.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = CreateMutableInteractionSource(),
                indication = CreateIndication(),
                onClick = {
                    navController.navigate(Screen.DetailScreen.withArgs(name.toString()))
                }
            ),
        elevation = 2.dp,
        backgroundColor = lightBlue,
        shape = RoundedCornerShape(corner = CornerSize(10.dp))

    ) {


        Box(
            contentAlignment = Alignment.CenterEnd
        ) {
            Image(
                painter = painterResource(R.drawable.ic_baseline_arrow_forward_ios_24),
                contentDescription = null,
                modifier = Modifier.padding(1.dp),

                )

        }

    }
}

@Composable
fun HandleUserTierData(tierPoints: Int) {

    Card(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = lightBlue,
        shape = RoundedCornerShape(corner = CornerSize(10.dp))
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(100.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 8.dp),
                    verticalArrangement = Arrangement.aligned(Alignment.CenterVertically)
                ) {
                    Text("TIER POINTS", fontWeight = FontWeight.Bold)
                    Text(
                        text = "CURRENT BALANCE",
                        style = MaterialTheme.typography.body2
                    )
                    Text(
                        textAlign = TextAlign.End,
                        text = tierPoints.toString(),
                        style = MaterialTheme.typography.h6
                    )
                }

            }
            Spacer(
                modifier = Modifier
                    .width(5.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(100.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 8.dp),
                    verticalArrangement = Arrangement.aligned(Alignment.CenterVertically)
                ) {
                    Text("TIER POINTS TO SILVER", fontWeight = FontWeight.Bold)


                    Text(
                        textAlign = TextAlign.End,
                        text = "0",
                        style = MaterialTheme.typography.h6

                    )
                }
            }

            Image(
                painter = painterResource(R.drawable.ic_baseline_arrow_forward_ios_24),
                contentDescription = null,
                modifier = Modifier.padding(1.dp)
            )
        }
    }
}

@Composable
fun handleUserStarGifts(navController: NavController) {

    Card(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .height(80.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = CreateMutableInteractionSource(),
                indication = CreateIndication(),
                onClick = {
                    navController.navigate(Screen.DetailScreen.withArgs("screen"))
                }
            ),
        elevation = 2.dp,
        backgroundColor = lightBlue,
        shape = RoundedCornerShape(corner = CornerSize(10.dp))

    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.aligned(Alignment.CenterVertically)
        ) {
            Text("STAR GIFTS", fontWeight = FontWeight.Bold)

            Text(
                textAlign = TextAlign.End,
                text = "EXPLORE GIFTS YOU CAN EARN AT SILVER",
                style = MaterialTheme.typography.body2

            )
        }


        Spacer(
            modifier = Modifier
                .width(5.dp)
        )

        Box(
            contentAlignment = Alignment.CenterEnd
        ) {
            Image(
                painter = painterResource(R.drawable.ic_baseline_arrow_forward_ios_24),
                contentDescription = null,
                modifier = Modifier.padding(1.dp),

                )

        }

    }
}

@Composable
fun handleUserCasinoDollars(casinoDollars: Int, navController: NavController) {

    Card(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .height(80.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = CreateMutableInteractionSource(),
                indication = CreateIndication(),
                onClick = {
                    navController.navigate(Screen.DetailScreen.withArgs("screen"))
                }
            ),
        elevation = 2.dp,
        backgroundColor = lightBlue,
        shape = RoundedCornerShape(corner = CornerSize(10.dp))

    ) {


        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.aligned(Alignment.CenterVertically)
        ) {
            Text("CASINO DOLLARS BALANCE", fontWeight = FontWeight.Bold)

            Text(
                textAlign = TextAlign.End,
                text = "$ ${casinoDollars}",
                style = MaterialTheme.typography.h5

            )
        }


        Spacer(
            modifier = Modifier
                .width(5.dp)
        )

        Box(
            contentAlignment = Alignment.CenterEnd
        ) {
            Image(
                painter = painterResource(R.drawable.ic_baseline_arrow_forward_ios_24),
                contentDescription = null,
                modifier = Modifier.padding(1.dp),

                )

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


class UserDataViewModelFactory(
    private val userDataRepository: UserDataRepository
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDataViewModel::class.java)) {
            return UserDataViewModel(userDataRepository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}