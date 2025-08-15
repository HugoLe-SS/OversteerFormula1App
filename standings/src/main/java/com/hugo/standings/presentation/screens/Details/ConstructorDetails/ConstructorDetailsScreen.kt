//package com.hugo.standings.presentation.screens.Details.ConstructorDetails
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.hugo.design.components.AppToolbar
//import com.hugo.design.ui.theme.AppTheme
//
//@Composable
//fun ConstructorDetailsScreen(
//    constructorId: String?= null,
//    backButtonClicked : () -> Unit = {},
//    viewModel: ConstructorDetailsViewModel = hiltViewModel(),
//){
//    val state by viewModel.state.collectAsState()
//
//    LaunchedEffect(key1 = constructorId) {
//        constructorId?.let {
//            viewModel.fetchConstructorDetails(season = "current", constructorId = it)
//        }
//    }
//
//    Scaffold (
//        topBar = {
//            AppToolbar(
//                isBackButtonVisible = true,
//                backButtonClicked = backButtonClicked
//            )
//        },
//    )
//    {padding->
//        when{
//            state.isLoading -> {
//                CircularProgressIndicator(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        //.padding(innerPadding)
//                        .size(24.dp),
//                    color = AppTheme.colorScheme.onSecondary
//                )
//            }
//            state.error != null -> {
//                Text(text = "Error: ${state.error}")
//            }
//            else -> {
//                LazyColumn (
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(AppTheme.colorScheme.background)
//                        .padding(padding)
//                ){
//                    item{
//                        Text(
//                            text = "Constructor Details Screen",
//                            style = AppTheme.typography.titleLarge,
//                            color = AppTheme.colorScheme.onSecondary
//                        )
//                    }
//
//                }
//            }
//        }
//
//    }
//
//}
