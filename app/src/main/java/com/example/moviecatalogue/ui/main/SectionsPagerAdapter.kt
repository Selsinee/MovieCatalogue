package com.example.moviecatalogue.ui.main

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviecatalogue.ui.films.FilmFragment

/**
 * Created by Seline on 21/12/2021 20:36
 */
class SectionsPagerAdapter(activity: AppCompatActivity) :
    FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FilmFragment.newInstance(FilmFragment.TYPE_MOVIE)
            1 -> fragment = FilmFragment.newInstance(FilmFragment.TYPE_TVSHOW)
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }

}