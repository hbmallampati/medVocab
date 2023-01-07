package com.example.medvocab

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medvocab.TypesList.typesList
import com.example.medvocab.datastructure.User
import com.example.medvocab.datastructure.UserProgress

import com.example.medvocab.ui.about.getAllListsNewUser
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    private val TAG = "MainViewModel"

    //Type selection handle
    private val selectedTypeIndex = MutableLiveData<Int>().apply {
        value = -1
    }

    //Reset decks status
    private var resetDone : MutableLiveData<Boolean> = MutableLiveData(false)

    //Firebase stuff
    private var createdNewUser : MutableLiveData<Boolean> = MutableLiveData(false)
    private var signedOut = MutableLiveData<Int>().apply {
        value = 0
    }

    private var typeWiseMasteredCount = MutableLiveData<Boolean>()
     var getNewList = mutableListOf<TypesList.TypesOfSets>()


    private var signInStatus = MutableLiveData<Int>().apply {
        value = 0
    }
    //0 - not signed in
    //1 - signed in

    //Type selection handle
    fun newTypeSelected(index : Int) {
        selectedTypeIndex.value = index
    }

    fun setSIgnInStatus(x : Int) {
        signInStatus.value = x

    }

    fun getSignInStatus() : LiveData<Int> {
        return signInStatus
    }

    fun getSignInStatus2() : Int {
        return signInStatus.value!!
    }

    fun observeTypeSelection() : LiveData<Int> {
        return selectedTypeIndex
    }


    fun resetAllDecks() = viewModelScope.launch(context = viewModelScope.coroutineContext + Dispatchers.IO) {
        createNewUser2()
        resetDone.postValue(true)
    }

    fun observeResetDone() : LiveData<Boolean> {
        return resetDone
    }

    fun resetResetDone() {
        resetDone.value = false
    }

    fun createNewUser2() = viewModelScope.launch(context = viewModelScope.coroutineContext + Dispatchers.IO) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser

        //Retrieve data -- working
//        Firebase.firestore.collection("users_new")
//            .document(firebaseUser!!.uid)
//            .collection("common_one")
//            .get()
//            .addOnSuccessListener {
//                Log.d(TAG, " finally")
//
//                Log.d(TAG, it.documents.size.toString())
//                //Log.d(TAG, it.documents[0].data.toString())
//
//                it.forEach { x ->
//                    Log.d(TAG, x.data.toString())
//
//                    Log.d(TAG, x.data["word"].toString())
//                    Log.d(TAG, x.data["reviewCount"].toString())
//                    Log.d(TAG, x.data["reviewing"].toString())
//                    Log.d(TAG, x.data["newWord"].toString())
//                }
//            }
//            .addOnFailureListener {
//                Log.d(TAG, "failed again")
//            }

        //Add data, update data -- working
//        val testvar = UserProgress("hospital2", newWord = false,
//            mastered = false, reviewing = false, learning = false,
//            reviewCount = 300, noOfTimesMarkedDontKnow = 0)
//
//                Firebase.firestore.collection("users_new")
//            .document(firebaseUser!!.uid)
//            .collection("common_one")
//                    .document("hospital2")
//                    .set(testvar)
//            .addOnSuccessListener {
//                Log.d(TAG, "added ")
//            }
//            .addOnFailureListener {
//                Log.d(TAG, "failed again")
//            }

//        val testvar = UserProgress("hospital2", newWord = false,
//            mastered = false, reviewing = false, learning = false,
//            reviewCount = 300, noOfTimesMarkedDontKnow = 0)

        val newUser = User(firebaseUser!!.displayName!!, firebaseUser.uid)
        Firebase.firestore.collection("users_new").document(firebaseUser.uid).set(newUser)

        getAllListsNewUser.forEach { entry ->
            entry.value.forEach {
                val testvar = UserProgress(it.word, newWord = true,
                    mastered = false, reviewing = false, learning = false,
                    reviewCount = 3, noOfTimesMarkedDontKnow = 0)

                Firebase.firestore.collection("users_new")
                    .document(firebaseUser!!.uid)
                    .collection(entry.key)
                    .document(it.word)
                    .set(testvar)
                    .addOnSuccessListener {
                        Log.d(TAG, "added ")
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "failed again")
                    }
            }
        }

        createdNewUser.postValue(true)
    }

    fun signOut(context : Context) {
        AuthUI.getInstance()
            .signOut(context)
            .addOnSuccessListener {
                Log.d(javaClass.simpleName, "Signed out successfully")
                signedOut.value = 1
            }
            .addOnFailureListener {
                Log.d(javaClass.simpleName, "Sign out failed")
                signedOut.value = 2
            }

    }

    fun observeSignOutOperationStatus() : LiveData<Int> {
        return signedOut
    }

    fun resetSignOutOperationStatus() {
        signedOut.value = 0
    }

    fun getMasteredTypeWise() : LiveData<Boolean> {

        val firebaseUser = FirebaseAuth.getInstance().currentUser
        typesList.forEach { entry ->

            Firebase.firestore.collection("users_new")
                .document(firebaseUser!!.uid)
                .collection(entry.tag)
                .get()
                .addOnSuccessListener { x ->

                    Log.d(TAG, x.documents.size.toString())
                    //Log.d(TAG, it.documents[0].data.toString())

                    val x1filter = x.filter {
                        it.data["mastered"] as Boolean
                    }

                    entry.masteredWords = x1filter.size
                    Log.d(TAG, "x1 : "+ x1filter.size.toString())

                    //Log.d(TAG, x_filter.toString())
                }
                .addOnFailureListener {
                    Log.d(TAG, "failed again")
                }
            }

            Log.d(javaClass.simpleName, "0: listtttt : "+ typesList.get(0).masteredWords)
            Log.d(javaClass.simpleName, "1: listtttt : "+ typesList.get(1).masteredWords)
            Log.d(javaClass.simpleName, "2: listtttt : "+ typesList.get(2).masteredWords)
            Log.d(javaClass.simpleName, "3: listtttt : "+ typesList.get(3).masteredWords)
            Log.d(javaClass.simpleName, "4: listtttt : "+ typesList.get(4).masteredWords)
            Log.d(javaClass.simpleName, "5: listtttt : "+ typesList.get(5).masteredWords)

            typeWiseMasteredCount.value = true

        return typeWiseMasteredCount

    }

}