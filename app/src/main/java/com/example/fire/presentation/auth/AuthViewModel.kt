package com.example.fire.presentation.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fire.data.NoteData
import com.example.fire.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    var userData = mutableStateOf<UserData?>(null)
    var isLoading = mutableStateOf(false)
    var signedIn = mutableStateOf(false)
    val notes = mutableStateOf<List<NoteData>>(listOf())

    init {
//        signOut()
        signedIn.value = auth.currentUser != null
        auth.currentUser?.uid?.let { uid ->
            getData(uid)
        }

    }

    fun signUp(username: String, email: String, password: String) {
        isLoading.value = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    signedIn.value = true
                    createOrUpdateUser(username = username, email, password)
                }
                isLoading.value = false

            }
            .addOnFailureListener {
                isLoading.value = false
            }
            .addOnFailureListener {

            }
    }

    fun signIn(email: String, password: String) {
        isLoading.value = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                auth.currentUser?.uid?.let { uid ->
                    getData(uid)
                }
                signedIn.value = true
                isLoading.value = false
            }
            .addOnFailureListener {
                isLoading.value = false
            }
    }

    fun signOut() {
        auth.signOut()
        signedIn.value = false
        userData.value = null
        notes.value = listOf()
    }

    fun deleteUser() {
        auth.currentUser?.delete()
        signedIn.value = false
        userData.value = null
        notes.value = listOf()
    }

    private fun createOrUpdateUser(
        username: String? = null,
        email: String? = null,
        password: String? = null,
        image: String? = null,
        bio: String? = null
    ) {
        val userData = UserData(
            userId = auth.currentUser?.uid,
            username = username ?: userData.value?.username,
            email = email ?: userData.value?.email,
            password = password ?: userData.value?.password,
            image = image ?: userData.value?.image,
            bio = bio ?: userData.value?.bio
        )
        auth.currentUser?.uid?.let { uid ->
            isLoading.value = true
            firestore.collection("user").document(uid).get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        it.reference.update(userData.toMap())
                            .addOnSuccessListener {
                                this.userData.value = userData
                                isLoading.value = false
                            }
                            .addOnFailureListener {
                                isLoading.value = false
                            }
                    } else {
                        firestore.collection("user").document(uid).set(userData)
                        getData(uid)
                        isLoading.value = false
                    }
                }
        }
    }

    fun updateProfileData(username: String, bio: String) {
        createOrUpdateUser(username = username, bio = bio)
    }

    //Get data of current user from FireStore
    fun getData(uid: String) {
        isLoading.value = true
        firestore.collection("user").document(uid).get()
            .addOnSuccessListener {
                val user = it.toObject<UserData>()
                userData.value = user
                isLoading.value = false
                getNotes()
            }
            .addOnFailureListener { }
    }

    fun createNote(
        title: String,
        description: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        isLoading.value = true
        if (auth.currentUser?.uid != null) {
            val noteUUID = UUID.randomUUID().toString()
            val note = NoteData(
                userId = auth.currentUser?.uid,
                title = title,
                description = description,
                noteId = noteUUID,
                time = System.currentTimeMillis()
            )
            firestore.collection("post").document(noteUUID).set(note)

                .addOnSuccessListener {
                    isLoading.value = false
                    getNotes()
                    onSuccess.invoke()
                }
                .addOnFailureListener {
                    onFailure.invoke()
                    isLoading.value = false
                    getNotes()
                }
        }
    }


/////

private fun getNotes() {
    if (auth.currentUser?.uid != null) {
        firestore.collection("post").whereEqualTo("userId", auth.currentUser?.uid).get()
            .addOnSuccessListener { documents ->
                convertNotes(documents, notes)
            }
            .addOnFailureListener {
            }
    } else {
        signOut()
    }
}


//Converting Firebase data to my Data Class format.
private fun convertNotes(documents: QuerySnapshot, outState: MutableState<List<NoteData>>) {
    val newPosts = mutableListOf<NoteData>()
    documents.forEach { doc ->
        val post = doc.toObject<NoteData>()
        newPosts.add(post)
    }
    val sortedPosts = newPosts.sortedByDescending { it.time }
    outState.value = sortedPosts
}
}