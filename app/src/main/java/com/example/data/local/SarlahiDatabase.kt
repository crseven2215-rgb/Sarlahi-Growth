package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserEntity::class,
        JobEntity::class,
        JobApplicationEntity::class,
        CollegeEntity::class,
        RoomEntity::class,
        ScholarshipEntity::class,
        MentorEntity::class,
        MentorshipRequestEntity::class,
        CommunityPostEntity::class,
        MarketplaceItemEntity::class,
        SarlahiResourceEntity::class,
        NotificationEntity::class,
        BookmarkEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SarlahiDatabase : RoomDatabase() {

    abstract fun sarlahiDao(): SarlahiDao

    companion object {
        @Volatile
        private var INSTANCE: SarlahiDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): SarlahiDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SarlahiDatabase::class.java,
                    "sarlahi_growth_database"
                )
                .addCallback(SarlahiDatabaseCallback(scope))
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class SarlahiDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateInitialData(database.sarlahiDao())
                }
            }
        }

        suspend fun populateInitialData(dao: SarlahiDao) {
            dao.insertUser(SeedData.defaultUser)
            dao.insertJobs(SeedData.seedJobs)
            SeedData.seedApplications.forEach { dao.insertApplication(it) }
            dao.insertColleges(SeedData.seedColleges)
            dao.insertRooms(SeedData.seedRooms)
            dao.insertScholarships(SeedData.seedScholarships)
            dao.insertMentors(SeedData.seedMentors)
            SeedData.seedPosts.forEach { dao.insertPost(it) }
            SeedData.seedMarketplace.forEach { dao.insertMarketplaceItem(it) }
            dao.insertSarlahiResources(SeedData.seedSarlahiResources)
            SeedData.seedNotifications.forEach { dao.insertNotification(it) }
        }
    }
}
