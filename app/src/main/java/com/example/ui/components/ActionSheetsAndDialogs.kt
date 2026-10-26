package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.local.CollegeEntity
import com.example.data.local.JobEntity
import com.example.data.local.MentorEntity
import com.example.data.local.UserEntity
import com.example.data.model.JobType
import com.example.data.model.RoomType
import com.example.ui.theme.ForestGreenPrimary
import com.example.ui.theme.AmberSecondary

@Composable
fun ApplyJobDialog(
    job: JobEntity?,
    user: UserEntity?,
    onSubmit: (String) -> Unit,
    onDismiss: () -> Unit
) {
    if (job == null) return
    var coverNote by remember { mutableStateOf("Namaste. I am a student from Sarlahi with practical skills matching your requirements. I am eager to contribute and learn under your team's leadership.") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(vertical = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Apply for Position",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${job.title} • ${job.companyName}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Applicant card summary
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "Applicant Profile (Auto-Attached):",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Name: ${user?.fullName ?: "Sarlahi Applicant"}", fontSize = 12.sp)
                        Text(text = "Email: ${user?.email ?: "student@sarlahi.edu.np"}", fontSize = 12.sp)
                        Text(text = "Education: ${user?.education ?: "Higher Education"}", fontSize = 12.sp)
                        Text(text = "Origin: ${user?.sarlahiOrigin ?: "Sarlahi District"}", fontSize = 12.sp)
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                            Icon(Icons.Default.Verified, contentDescription = null, tint = ForestGreenPrimary, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Verified Sarlahi Growth Connect ID Attached", fontSize = 11.sp, color = ForestGreenPrimary, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = coverNote,
                    onValueChange = { coverNote = it },
                    label = { Text("Cover Note / Why you're a good fit") },
                    minLines = 3,
                    maxLines = 5,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onSubmit(coverNote) },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("submit_application_btn")
                ) {
                    Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Submit Application Now")
                }
            }
        }
    }
}

@Composable
fun PostJobDialog(
    user: UserEntity?,
    onSubmit: (String, String, String, String, JobType, String, String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var company by remember { mutableStateOf(user?.fullName ?: "") }
    var location by remember { mutableStateOf("Kathmandu / Sarlahi") }
    var salary by remember { mutableStateOf("NPR 30,000 - 45,000 / month") }
    var selectedType by remember { mutableStateOf(JobType.FULL_TIME) }
    var category by remember { mutableStateOf("Technology & IT") }
    var requirements by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxHeight(0.85f)
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Post New Job Opportunity", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = "Close") }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Job Title (e.g. Junior Accountant, Web Dev)") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = company,
                        onValueChange = { company = it },
                        label = { Text("Company or Organization Name") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text("Job Location (e.g. Kathmandu, Lalbandi Sarlahi, Dubai)") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = salary,
                        onValueChange = { salary = it },
                        label = { Text("Salary / Stipend Range") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text("Job Type:", style = MaterialTheme.typography.labelMedium)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf(JobType.FULL_TIME, JobType.INTERNSHIP, JobType.PART_TIME, JobType.ABROAD_GULF).forEach { type ->
                            FilterChip(
                                selected = selectedType == type,
                                onClick = { selectedType = type },
                                label = { Text(type.name.replace("_", " ").lowercase().capitalize(), fontSize = 11.sp) }
                            )
                        }
                    }

                    OutlinedTextField(
                        value = requirements,
                        onValueChange = { requirements = it },
                        label = { Text("Requirements & Qualifications") },
                        minLines = 2,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Job Description & Responsibilities") },
                        minLines = 3,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (title.isNotBlank()) {
                            onSubmit(title, company, location, salary, selectedType, category, requirements, description)
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Publish, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Publish Job Listing")
                }
            }
        }
    }
}

@Composable
fun PostRoomDialog(
    user: UserEntity?,
    onSubmit: (String, String, Int, RoomType, String, String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("Kirtipur / Putalisadak") }
    var rentText by remember { mutableStateOf("6000") }
    var roomType by remember { mutableStateOf(RoomType.SINGLE_ROOM) }
    var facilities by remember { mutableStateOf("WiFi, 24hr Water, Kitchen, Inverter") }
    var phone by remember { mutableStateOf(user?.phone ?: "+977-9800000000") }
    var description by remember { mutableStateOf("Quiet environment for students. Near colleges.") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxHeight(0.85f)
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("List Room or Hostel", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = "Close") }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title (e.g. Sunny Single Room near TU)") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text("Exact Location (e.g. Naya Bazaar Kirtipur)") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = rentText,
                        onValueChange = { rentText = it },
                        label = { Text("Monthly Rent (NPR)") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text("Room Category:", style = MaterialTheme.typography.labelMedium)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                        listOf(RoomType.SINGLE_ROOM, RoomType.DOUBLE_ROOM, RoomType.FLAT, RoomType.BOYS_HOSTEL, RoomType.GIRLS_HOSTEL).take(3).forEach { type ->
                            FilterChip(
                                selected = roomType == type,
                                onClick = { roomType = type },
                                label = { Text(type.name.replace("_", " ").lowercase().capitalize(), fontSize = 11.sp) }
                            )
                        }
                    }

                    OutlinedTextField(
                        value = facilities,
                        onValueChange = { facilities = it },
                        label = { Text("Facilities (WiFi, Water, Kitchen, Inverter)") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Owner Phone Contact") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description & Notes for Students") },
                        minLines = 2,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        val rent = rentText.toIntOrNull() ?: 5000
                        if (title.isNotBlank()) {
                            onSubmit(title, location, rent, roomType, facilities, phone, description)
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreenPrimary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Home, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Post Room Listing")
                }
            }
        }
    }
}

@Composable
fun AICareerAdvisorDialog(
    user: UserEntity?,
    onDismiss: () -> Unit
) {
    var customQuery by remember { mutableStateOf("") }
    var conversationHistory by remember {
        mutableStateOf(
            listOf(
                Pair(
                    "AI Career Advisor",
                    "Namaste ${user?.fullName ?: "Sathi"}! I am your Sarlahi Growth Career Advisor. How can I guide you today? You can choose a quick topic below or ask any question about studying in Kathmandu, finding rooms, scholarships, or building a career!"
                )
            )
        )
    }

    val quickQuestions = listOf(
        "What jobs are suitable for my profile?",
        "How can Sarlahi students prepare for Lok Sewa?",
        "Tips for finding affordable rooms in Kathmandu?",
        "What are the best scholarships for Terai students?",
        "Safe checklist for foreign & Gulf employment?"
    )

    fun answerQuestion(q: String) {
        val answer = when {
            q.contains("suitable", ignoreCase = true) || q.contains("job", ignoreCase = true) ->
                "Based on your profile in ${user?.education ?: "Higher Studies"}, high-growth pathways in Kathmandu include Junior Mobile/Web Development, Banking Relationship Assistants, and NGO Project Coordination. Start with a solid portfolio or Lok Sewa foundation and connect with Sarlahi mentors!"

            q.contains("Lok Sewa", ignoreCase = true) ->
                "For Lok Sewa (Section Officer/Nayab Subba): 1) Focus on Paper 1 GK & IQ daily for 2 hours; 2) Read national dailies like Gorkhapatra and Kantipur; 3) Form a study group with fellow Sarlahi students in Bagbazar libraries; 4) Request mentorship from Pradeep Yadav in our Mentors tab!"

            q.contains("room", ignoreCase = true) || q.contains("Kathmandu", ignoreCase = true) ->
                "For Sarlahi students in Kathmandu: 1) Kirtipur is best for TU Central Campus; 2) Putalisadak/Bagbazar is ideal for Shanker Dev, ASCOL, and consultancies; 3) Always verify 24/7 water supply and inverter electricity; 4) Check our 'Rooms' tab for verified Sarlahi alumni landlords offering student discounts!"

            q.contains("scholarship", ignoreCase = true) ->
                "Top scholarships to apply for right now: 1) Sarlahi Municipal Youth Meritorious Grant (NPR 60,000/yr); 2) Indian Embassy Golden Jubilee Scholarship (NPR 4,000/mo); 3) UGC Higher Education Reform Grant. Make sure your Sarlahi citizenship and SEE/+2 transcripts are notarized."

            q.contains("Gulf", ignoreCase = true) || q.contains("Abroad", ignoreCase = true) ->
                "Crucial Safety Checklist for Foreign Jobs: 1) Verify the recruitment agency's Nepal Government DoFE License; 2) Never pay unauthorized advance cash without official receipt; 3) Check pre-approval LT number on DoFE portal; 4) Ensure free visa & free ticket policy is followed!"

            else ->
                "Great question! For Sarlahi students, balancing college fees and living costs in Kathmandu requires practical digital skills, community networking through Sarlahi Student Welfare Association, and applying for timely semester scholarships. Check out our Mentorship tab to talk 1-on-1 with an experienced alumnus!"
        }

        conversationHistory = conversationHistory + Pair("You", q) + Pair("AI Career Advisor", answer)
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxHeight(0.85f)
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = AmberSecondary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("AI Career & Growth Assistant", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = "Close") }
                }

                Text(
                    text = "Personalized guidance for Sarlahi students & job seekers",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Chat stream
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(conversationHistory) { (sender, msg) ->
                        val isUser = sender == "You"
                        Row(
                            horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Surface(
                                shape = RoundedCornerShape(
                                    topStart = 14.dp,
                                    topEnd = 14.dp,
                                    bottomStart = if (isUser) 14.dp else 2.dp,
                                    bottomEnd = if (isUser) 2.dp else 14.dp
                                ),
                                color = if (isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.widthIn(max = 280.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = sender,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isUser) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = msg,
                                        fontSize = 12.sp,
                                        color = if (isUser) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }

                // Quick prompts
                Text("Suggested Questions:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    quickQuestions.take(2).forEach { q ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                            modifier = Modifier.clickable { answerQuestion(q) }
                        ) {
                            Text(
                                text = q,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Input bar
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = customQuery,
                        onValueChange = { customQuery = it },
                        placeholder = { Text("Ask anything about careers, rooms, colleges...") },
                        singleLine = true,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            if (customQuery.isNotBlank()) {
                                answerQuestion(customQuery)
                                customQuery = ""
                            }
                        },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = ForestGreenPrimary, contentColor = Color.White)
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Send")
                    }
                }
            }
        }
    }
}

@Composable
fun CollegeComparisonSheet(
    colleges: List<CollegeEntity>,
    onClear: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.85f)
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CompareArrows, contentDescription = null, tint = ForestGreenPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("College Comparison Tool", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                    IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = "Close") }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (colleges.isEmpty()) {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text("Select 2 or 3 colleges to compare side-by-side.")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                colleges.forEach { col ->
                                    Card(
                                        shape = RoundedCornerShape(12.dp),
                                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Column(modifier = Modifier.padding(10.dp)) {
                                            Text(text = col.name, fontWeight = FontWeight.Bold, fontSize = 12.sp, maxLines = 2)
                                            Text(text = col.affiliation, fontSize = 10.sp, color = ForestGreenPrimary, fontWeight = FontWeight.SemiBold)
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Text(text = "Fee: ${col.feeRange}", fontSize = 11.sp)
                                            Text(text = "Hostel: ${if (col.hostelAvailable) "Yes" else "No"}", fontSize = 11.sp)
                                            Text(text = "Scholarship: ${if (col.scholarshipAvailable) "Yes" else "No"}", fontSize = 11.sp)
                                            Text(text = "Rating: ⭐ ${col.rating}/5.0", fontSize = 11.sp)
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Text(text = "Programs:\n${col.programs}", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Button(
                    onClick = onClear,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.outline),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Clear Comparison")
                }
            }
        }
    }
}
