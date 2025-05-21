package com.codewithhiren.foodorderapplatest.di


import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.codewithhiren.foodorderapplatest.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped


@Module
@InstallIn(FragmentComponent::class)
object NavControllerFragmentModule {

    @FragmentScoped
    @Provides
    fun getNavController(fragment: Fragment): NavController {
        return ((fragment.requireActivity().supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView)) as NavHostFragment).navController
    }
}










