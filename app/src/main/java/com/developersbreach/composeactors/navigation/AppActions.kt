package com.developersbreach.composeactors.navigation

import androidx.navigation.NavHostController


class AppActions(
    navController: NavHostController,
    routes: AppDestinations
) {

    val selectedActor: (Int) -> Unit = { actorId: Int ->
        navController.navigate("${routes.ACTOR_DETAIL_ROUTE}/$actorId")
    }

    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}