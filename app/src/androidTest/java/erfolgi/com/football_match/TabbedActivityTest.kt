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
        //Last Match:  add favorite
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withText("Man United")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("Man United")).perform(ViewActions.click())
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(add_to_favorite))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())
        Thread.sleep(1000)
        Espresso.pressBack()
        Thread.sleep(1000)


        //Next Match: buka match
        Espresso.onView(ViewMatchers.withId(bottom_navigation))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("NEXT MATCH")).perform(ViewActions.click())
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withText("Tottenham")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("Tottenham")).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.pressBack()
        Thread.sleep(1000)
        ////////////// TEAM: add favorite
        Espresso.onView(ViewMatchers.withId(bottom_navigation))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(team_)).perform(ViewActions.click())
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withText("Chelsea")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("Chelsea")).perform(ViewActions.click())
        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(add_to_favorite))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withText("TEAM PLAYER")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("TEAM PLAYER")).perform(ViewActions.click())
        Thread.sleep(1000)
        ////////////// Player: add favorite
        Espresso.onView(ViewMatchers.withText("Antonio Rudiger")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("Antonio Rudiger")).perform(ViewActions.click())
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(add_to_favorite))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())
        Thread.sleep(1000)
        Espresso.pressBack()
        Espresso.pressBack()
        Thread.sleep(1000)

        /////////Favorite: remove favorite
        Espresso.onView(ViewMatchers.withId(bottom_navigation))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(favorites)).perform(ViewActions.click())
        Thread.sleep(1000)

        Espresso.onView(ViewMatchers.withText("Man United")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("Man United")).perform(ViewActions.click())
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withId(add_to_favorite))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())
        Thread.sleep(1000)
        Espresso.pressBack()
        Thread.sleep(1000)
        Espresso.onView(ViewMatchers.withText("TEAM")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("TEAM")).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withText("Chelsea")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("Chelsea")).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(add_to_favorite))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())
        Thread.sleep(1000)
        Espresso.pressBack()
        Espresso.onView(ViewMatchers.withText("PLAYER")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("PLAYER")).perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withText("Antonio Rudiger")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("Antonio Rudiger")).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(add_to_favorite))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())
        Thread.sleep(1000)
        Espresso.pressBack()
    }

}