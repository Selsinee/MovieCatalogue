package com.example.moviecatalogue.utils

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher
import org.hamcrest.core.Is
import android.graphics.Bitmap
import android.graphics.Canvas

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import com.example.moviecatalogue.utils.TestUtils.DrawableMatcher

/**
 * Created by Seline on 31/12/2021 20:46
 */
class TestUtils {

    class RecyclerViewItemCountAssertion(private val matcher: Matcher<Int>) :
        ViewAssertion {

        override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }
            val recyclerView = view as RecyclerView
            val adapter = recyclerView.adapter
            ViewMatchers.assertThat(adapter?.itemCount, matcher)
        }

    }

    class DrawableMatcher internal constructor(private val expectedId: Int) :
        TypeSafeMatcher<View?>(View::class.java) {
        private var resourceName: String? = null

        override fun matchesSafely(item: View?): Boolean {
            if (item !is ImageView) {
                return false
            }
            val imageView: ImageView = item
            if (expectedId == EMPTY) {
                return imageView.drawable == null
            }
            if (expectedId == ANY) {
                return imageView.drawable != null
            }
            val resources: Resources = item.context.resources
            val expectedDrawable: Drawable? = AppCompatResources.getDrawable(item.context, expectedId)
            resourceName = resources.getResourceEntryName(expectedId)
            if (expectedDrawable == null) {
                return false
            }
            val bitmap = getBitmap(imageView.drawable)
            val otherBitmap = getBitmap(expectedDrawable)
            return bitmap.sameAs(otherBitmap)
        }

        private fun getBitmap(drawable: Drawable): Bitmap {
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }

        override fun describeTo(description: Description) {
            description.appendText("with drawable from resource id: ")
            description.appendValue(expectedId)
            if (resourceName != null) {
                description.appendText("[")
                description.appendText(resourceName)
                description.appendText("]")
            }
        }

        companion object {
            const val EMPTY = -1
            const val ANY = -2
        }
    }

}

fun withDrawable(resourceId: Int): Matcher<View?> {
    return DrawableMatcher(resourceId)
}

fun withItemCount(expectedCount: Int): TestUtils.RecyclerViewItemCountAssertion {
    return withItemCount(Is.`is`(expectedCount))
}

fun withItemCount(matcher: Matcher<Int>): TestUtils.RecyclerViewItemCountAssertion {
    return TestUtils.RecyclerViewItemCountAssertion(matcher)
}