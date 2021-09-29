package io.github.mohamedisoliman.pixapay.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.github.mohamedisoliman.pixapay.R
import io.github.mohamedisoliman.pixapay.ui.image_details.ImageDetailsScreen
import io.github.mohamedisoliman.pixapay.ui.search.SearchImagesViewModel
import io.github.mohamedisoliman.pixapay.ui.search.SearchScreen


@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Search.route,
        modifier = modifier
    ) {

        composable(Screen.Search.route) {

            val viewModel: SearchImagesViewModel = viewModel()
            SearchScreen(viewModel) {
                navController.navigate("${Screen.ImageDetails.route}/${it}")
            }
        }

        composable(
            route = "${Screen.ImageDetails.route}/{imageId}",
            arguments = listOf(navArgument("imageId") { type = NavType.LongType })
        ) { entry ->
            val id = entry.arguments?.getLong("imageId")
            val viewModel: SearchImagesViewModel = viewModel()
            val image = id?.let { viewModel.findImage(it) }
            if (image != null) {
                ImageDetailsScreen(image)
            }
        }
    }

}


sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Search : Screen("search_screen", R.string.search_screen)
    object ImageDetails : Screen("image_details_screen", R.string.image_details_screen)
}
