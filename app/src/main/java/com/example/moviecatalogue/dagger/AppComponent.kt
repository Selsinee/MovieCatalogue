package com.example.moviecatalogue.dagger

import dagger.Component
import javax.inject.Singleton

/**
 * Created by Seline on 24/01/2022 17:46
 */
@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
}