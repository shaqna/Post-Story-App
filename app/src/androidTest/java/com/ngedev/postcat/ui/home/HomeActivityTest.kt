package com.ngedev.postcat.ui.home


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.MediumTest
import com.ngedev.postcat.R
import com.ngedev.postcat.ui.detail.DetailActivity
import com.ngedev.postcat.ui.maps.MapsActivity
import com.ngedev.postcat.ui.post.PostStoryActivity
import com.ngedev.postcat.utils.Constants.BASE_URL_MOCK
import com.ngedev.postcat.utils.EspressoIdlingResource
import com.ngedev.postcat.utils.JsonConverter
import com.ngedev.postcat.utils.di.PrefsModule.appPrefsModule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

@KoinExperimentalAPI
@MediumTest
@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    val activity = ActivityScenarioRule(HomeActivity::class.java)

    private val mockWebServer = MockWebServer()

    @Before
    fun setUp() {

        loadKoinModules(appPrefsModule)
        mockWebServer.start(8080)
        BASE_URL_MOCK = "http://127.0.0.1:8080/"
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun teardown() {
        unloadKoinModules(appPrefsModule)
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun launchHomeActivity_Success() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("success_response.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.btnPost)).check(matches(isDisplayed()))
        onView(withId(R.id.mapsStories)).check(matches(isDisplayed()))
        onView(withId(R.id.btnSettings)).check(matches(isDisplayed()))
        onView(withId(R.id.rvStories)).check(matches(isDisplayed()))
        onView(withId(R.id.progressBar_home)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.rvStories)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                10
            )
        )
    }

    @Test
    fun launchDetailStory_Success() {
        Intents.init()
        onView(withId(R.id.rvStories)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        intended(hasComponent(DetailActivity::class.java.name))
        onView(withId(R.id.tv_story_username)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_story_description)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_story_date)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_story_image)).check(matches(isDisplayed()))
        onView(withId(R.id.btnBack)).perform(click())
        pressBack()
        Intents.release()
    }

    @Test
    fun launchMapsActivity_Success() {
        Intents.init()
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("success_response.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.mapsStories)).perform(click())
        intended(hasComponent(MapsActivity::class.java.name))
        onView(withId(R.id.map)).check(matches(isDisplayed()))
        Intents.release()
    }

    @Test
    fun launchCreatePost_Success() {
        Intents.init()
        onView(withId(R.id.btnPost)).perform(click())
        intended(hasComponent(PostStoryActivity::class.java.name))
        onView(withId(R.id.photoLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_add_story)).check(matches(isDisplayed()))
        onView(withId(R.id.pickFormGallery)).check(matches(isDisplayed()))
        onView(withId(R.id.takePhoto)).check(matches(isDisplayed()))
        onView(withId(R.id.til_description)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_upload_form)).check(matches(isDisplayed()))
        onView(withId(R.id.btnBack)).check(matches(isDisplayed()))
        pressBack()
        Intents.release()
    }


}