package erfolgi.com.football_match

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import erfolgi.com.football_match.R.drawable.ic_fav_no
import erfolgi.com.football_match.R.id.*
import erfolgi.com.football_match.activities.TabbedActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TabbedActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(TabbedActivity::class.java)
    @Test
    fun testAppBehaviour() {
        //Last Match: Buka match Everton, add favorite
        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withText("Everton")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("Everton")).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(add_to_favorite))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.pressBack()
        Thread.sleep(2000)


        //Next Match: buka match Fulham
        Espresso.onView(ViewMatchers.withId(bottom_navigation))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(team_)).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withText("Fulham")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("Fulham")).perform(ViewActions.click())
        Thread.sleep(3000)
        Espresso.pressBack()
        Thread.sleep(2000)

        //Favorite: Buka match Everton, remove favorite
        Espresso.onView(ViewMatchers.withId(bottom_navigation))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(favorites)).perform(ViewActions.click())
        Thread.sleep(2000)

        Espresso.onView(ViewMatchers.withText("Everton")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("Everton")).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(add_to_favorite))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.pressBack()
        Thread.sleep(2000)
    }

}