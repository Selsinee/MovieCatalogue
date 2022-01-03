package com.example.moviecatalogue.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.moviecatalogue.R
import com.example.moviecatalogue.utils.DataDummy
import com.example.moviecatalogue.utils.withDrawable
import com.example.moviecatalogue.utils.withItemCount
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Seline on 31/12/2021 20:18
 */

@RunWith(MockitoJUnitRunner::class)
class MainActivityTest {

    @Mock
    private val dummyData = DataDummy

    private val dummyMovies = dummyData.generateDummyMovies()
    private val dummyTvshows = dummyData.generateDummyTVShows()

    private val dummyMovie = dummyMovies[0]
    private val dummyTvshow = dummyTvshows[0]

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_film)).check(
            matches(
                isDisplayed()
            )
        )
        onView(withId(R.id.rv_film))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovies.size))
        onView(withId(R.id.rv_film)).check(
            withItemCount(18)
        )
    }

    @Test
    fun loadTvShows() {
        onView(withText("TV Show")).perform(click())

        onView(withId(R.id.rv_film)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_film))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvshows.size))
        onView(withId(R.id.rv_film)).check(
            withItemCount(20)
        )
    }

    @Test
    fun loadMovieDetails() {
        onView(withId(R.id.rv_film)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.img_poster)).check(matches(withDrawable(dummyMovie.image)))

        onView(withId(R.id.tv_release)).perform(scrollTo())

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(withText(dummyMovie.title)))

        onView(withId(R.id.tv_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genre)).check(matches(withText("Genre: " + dummyMovie.genre)))

        onView(withId(R.id.prog_text)).check(matches(isDisplayed()))
        onView(withId(R.id.prog_text)).check(matches(withText(dummyMovie.rating)))

        onView(withId(R.id.prog_bar)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_desc)).check(matches(withText(dummyMovie.description)))

        onView(withId(R.id.tv_status)).check(matches(withText("Status: " + dummyMovie.status)))

        onView(withId(R.id.tv_release)).check(matches(withText("Release Date: " + dummyMovie.releaseDate)))
    }

    @Test
    fun loadTvShowDetails() {
        onView(withText("TV Show")).perform(click())
        onView(withId(R.id.rv_film)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.img_poster)).check(matches(withDrawable(dummyTvshow.image)))

        onView(withId(R.id.tv_release)).perform(scrollTo())

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(withText(dummyTvshow.title)))

        onView(withId(R.id.tv_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genre)).check(matches(withText("Genre: " + dummyTvshow.genre)))

        onView(withId(R.id.prog_text)).check(matches(isDisplayed()))
        onView(withId(R.id.prog_text)).check(matches(withText(dummyTvshow.rating)))

        onView(withId(R.id.prog_bar)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_desc)).check(matches(withText(dummyTvshow.description)))

        onView(withId(R.id.tv_status)).check(matches(withText("Status: " + dummyTvshow.status)))

        onView(withId(R.id.tv_release)).check(matches(withText("Release Date: " + dummyTvshow.releaseDate)))
    }
}