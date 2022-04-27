package com.augustin.thestarclub.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
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
import com.augustin.thestarclub.model.BenefitX
import com.augustin.thestarclub.repository.UserBenefitsRepository
import com.augustin.thestarclub.ui.theme.darkBlue
import com.augustin.thestarclub.ui.theme.darkerBlue
import com.augustin.thestarclub.ui.theme.lightBlue
import com.augustin.thestarclub.ui.theme.red
import com.augustin.thestarclub.utilities.Resource
import com.augustin.thestarclub.viewmodel.UserBenefitsViewModel
import kotlinx.coroutines.launch


@Composable
fun UserBenefitsView() {
    val viewModel: UserBenefitsViewModel = viewModel(
        factory = UserBenefitsViewModelFactory(userBenefitsRepository = UserBenefitsRepository())
    )
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val getAllBenefitsData = viewModel.getBenefitsData.observeAsState()

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
                        text = "Benefits",
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
                    if (viewModel.getBenefitsData.value!!.benefits.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)
                        ) {

                        }

                        LazyColumn(
                            modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)
                        ) {
                            items(getAllBenefitsData.value!!.benefits.size) { index ->
                                HandleUserBenefitsData(
                                    getAllBenefitsData.value!!.benefits[index], index
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HandleUserBenefitsData(benefitX: BenefitX, index: Int) {

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .padding(5.dp),
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
                    .height(60.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 8.dp),
                    verticalArrangement = Arrangement.aligned(Alignment.CenterVertically)
                ) {

                    Text(
                        textAlign = TextAlign.Start,
                        text = benefitX.name,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold

                    )
                }

            }
            Spacer(
                modifier = Modifier
                    .width(20.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(60.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 8.dp),
                    verticalArrangement = Arrangement.aligned(Alignment.CenterVertically)
                ) {
                    Text("Expire Date", fontWeight = FontWeight.Bold, color = red)

                    Text(
                        textAlign = TextAlign.End,
                        text = benefitX.expireDate,
                        style = MaterialTheme.typography.subtitle1

                    )
                }
            }
        }
    }
}


class UserBenefitsViewModelFactory(
    private val userBenefitsRepository: UserBenefitsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserBenefitsViewModel::class.java)) {
            return UserBenefitsViewModel(userBenefitsRepository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}