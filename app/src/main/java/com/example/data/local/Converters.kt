package com.example.data.local

import androidx.room.TypeConverter
import com.example.data.model.ApplicationStatus
import com.example.data.model.JobType
import com.example.data.model.MentorshipStatus
import com.example.data.model.RoomType
import com.example.data.model.UserRole

class Converters {
    @TypeConverter
    fun fromUserRole(value: UserRole): String = value.name

    @TypeConverter
    fun toUserRole(value: String): UserRole = runCatching { UserRole.valueOf(value) }.getOrDefault(UserRole.STUDENT)

    @TypeConverter
    fun fromJobType(value: JobType): String = value.name

    @TypeConverter
    fun toJobType(value: String): JobType = runCatching { JobType.valueOf(value) }.getOrDefault(JobType.FULL_TIME)

    @TypeConverter
    fun fromRoomType(value: RoomType): String = value.name

    @TypeConverter
    fun toRoomType(value: String): RoomType = runCatching { RoomType.valueOf(value) }.getOrDefault(RoomType.SINGLE_ROOM)

    @TypeConverter
    fun fromApplicationStatus(value: ApplicationStatus): String = value.name

    @TypeConverter
    fun toApplicationStatus(value: String): ApplicationStatus = runCatching { ApplicationStatus.valueOf(value) }.getOrDefault(ApplicationStatus.APPLIED)

    @TypeConverter
    fun fromMentorshipStatus(value: MentorshipStatus): String = value.name

    @TypeConverter
    fun toMentorshipStatus(value: String): MentorshipStatus = runCatching { MentorshipStatus.valueOf(value) }.getOrDefault(MentorshipStatus.PENDING)
}
