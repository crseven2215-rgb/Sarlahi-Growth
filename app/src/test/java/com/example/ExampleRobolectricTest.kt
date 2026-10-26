package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.SeedData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Sarlahi Connect", appName)
  }

  @Test
  fun `verify seed data content is available`() {
    assertTrue(SeedData.seedJobs.isNotEmpty())
    assertTrue(SeedData.seedColleges.isNotEmpty())
    assertTrue(SeedData.seedRooms.isNotEmpty())
    assertTrue(SeedData.seedScholarships.isNotEmpty())
    assertTrue(SeedData.seedMentors.isNotEmpty())
    assertEquals("Aayush Kushwaha", SeedData.defaultUser.fullName)
  }
}
