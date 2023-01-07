package com.example.medvocab

import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medvocab.api.*
import com.example.medvocab.datastructure.UserProgress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class VocabBuildViewModel : ViewModel() {

    private val TAG = "VocabBuildViewModel"
    private var _word = "cardiology"
    private val key = "bc6eb0ce-9151-4492-be3b-2dc815ad797a"
    private var type = MutableLiveData<String>()

    //Network handle
    private val medVocabApi = WordDetailsFetchApi.create()
    private val medVocabRepository = MedicalTermsDataRepository(medVocabApi)
    private val medicalTermResponse = MutableLiveData<List<MedicalTerm>>()
    var fetchDone : MutableLiveData<Boolean> = MutableLiveData(false)

    var mediaPlayer = MediaPlayer()
    var fetchAudioFile =  MutableLiveData<String>()

    var stateVar = 0

    //Firestore handle
    private var wordsDetailsWithProgress = MutableLiveData<List<UserProgress>>()
    private var wordsDetailsWithProgressBckp = MutableLiveData<List<UserProgress>>()

    enum class LayoutState {
        WORD_ONLY_STATE,
        WORD_WITH_DETAILS
    }
    private var layoutState = MutableLiveData<LayoutState>().apply {
        value = LayoutState.WORD_ONLY_STATE
    }
    private var currentWordIndex = MutableLiveData<Int>().apply {
        value = -1
    }

    enum class NewWordState {
        NEW,
        LEARNING,
        REVIEWING
    }

    private val newWordStateText = hashMapOf<NewWordState, String>(
        NewWordState.NEW to "new word",
        NewWordState.LEARNING to "learning",
        NewWordState.REVIEWING to "reviewing"
    )

    private val newWordStateColor = hashMapOf<NewWordState, Int>(
        NewWordState.NEW to R.color.doc_bng,
        NewWordState.LEARNING to R.color.teal_200,
        NewWordState.REVIEWING to R.color.white
    )

    private var newWordBackgroundColor = R.color.purple_200


    //Network handle
    fun netRefresh() = viewModelScope.launch(context = viewModelScope.coroutineContext + Dispatchers.IO) {

        medicalTermResponse.postValue(medVocabRepository.fetchMedicalWordDetails(
            key, wordsDetailsWithProgress.value?.get(currentWordIndex.value!!)?.word.toString()))

//        val store = medVocabRepository.fetchMedicalWordDetails(
//            key, wordsDetailsWithProgress.value?.get(currentWordIndex.value!!)?.word.toString() )
//
//
//        medicalTermResponse.postValue(store)
        fetchDone.postValue(true)

    }


     fun getAudioFile()  {

        val audio = medicalTermResponse.value?.get(0)?.hwi?.prs_v?.get(0)?.sound?.audio

        val subDirectory = if ("bix" == audio?.substring(0, 3))
            "bix"
        else if("gg" == audio?.substring(0,2))
            "gg"
        else if(audio?.substring(0,1)?.onlyLetters() != true)
            "number"
        else
            audio?.substring(0,1)

        val baseUrl = "https://media.merriam-webster.com/audio/prons/en/us/mp3/${subDirectory}/${audio}.mp3"
        fetchAudioFile.postValue(baseUrl)
    }

    fun observeFetchAudioFile() : LiveData<String> {
        return fetchAudioFile
    }


    fun String.onlyLetters() = all { it.isLetter() }

    fun observeMedicalTermResponse(): LiveData<List<MedicalTerm>> {
        return medicalTermResponse
    }

    //Firestore handle
    fun setType(typeInd : Int) {
        when(typeInd) {
            0 -> type.value = TypesList.getAll()[0].tag
            1 -> type.value = TypesList.getAll()[1].tag
            2 -> type.value = TypesList.getAll()[2].tag
            3 -> type.value = TypesList.getAll()[3].tag
            4 -> type.value = TypesList.getAll()[4].tag
            5 -> type.value = TypesList.getAll()[5].tag
        }
        type.value?.let { fetchWordsList(it) }
    }

    fun getType() : String {
        return type.value!!
    }

//    fun fetchWordsList(type : String) = viewModelScope.launch(context = viewModelScope.coroutineContext + Dispatchers.IO) {
//
//        Log.d(TAG, "type : $type")
//
//        val db = Firebase.firestore
//
//        db.collection(type)
//            .get()
//            .addOnSuccessListener {  result ->
//
//                val wordWiseProgress = mutableListOf<UserProgress>()
//                result.forEach {
//                    wordWiseProgress.add(
//                        UserProgress(
//                            it.data["word"] as String, it.data["newWord"] as Boolean,
//                            it.data["mastered"] as Boolean, it.data["reviewing"] as Boolean,
//                            it.data["learning"] as Boolean, it.data["reviewCount"] as Long,
//                            it.data["noOfTimesMarkedDontKnow"] as Long)
//                    )
//                }
//                wordsDetailsWithProgress.postValue(wordWiseProgress)
//                wordsDetailsWithProgressBckp.postValue(wordWiseProgress)
//
//                Log.d(TAG, "list : ${wordWiseProgress}")
//                Log.d(TAG, "list size : "+ wordWiseProgress.size.toString())
//
//            }
//            .addOnFailureListener { e ->
//                Log.w(TAG, "Error fetching document", e)
//
//            }
//    }

    fun fetchWordsList(type : String) = viewModelScope.launch(context = viewModelScope.coroutineContext + Dispatchers.IO) {

        val db = Firebase.firestore
        val firebaseUser = FirebaseAuth.getInstance().currentUser

        Firebase.firestore.collection("users_new")
            .document(firebaseUser!!.uid)
            .collection(type)
            .get()
            .addOnSuccessListener {
                ///Log.d(TAG, " finally")

                Log.d(TAG, it.documents.size.toString())
                //Log.d(TAG, it.documents[0].data.toString())
                var wordWiseProgress = mutableListOf<UserProgress>()

                it.forEach { x ->

//                    Log.d(TAG, x.data.toString())
//                    Log.d(TAG, x.data["word"].toString())
//                    Log.d(TAG, x.data["reviewCount"].toString())
//                    Log.d(TAG, x.data["reviewing"].toString())
//                    Log.d(TAG, x.data["newWord"].toString())

                    if(x.data["mastered"] as Boolean)
                    {

                    }
                    else
                    {
                        wordWiseProgress.add(
                            UserProgress(
                                x.data["word"] as String, x.data["newWord"] as Boolean,
                                x.data["mastered"] as Boolean, x.data["reviewing"] as Boolean,
                                x.data["learning"] as Boolean, x.data["reviewCount"] as Long,
                                x.data["noOfTimesMarkedDontKnow"] as Long )
                        )
                    }
                    //Log.d(TAG, "list : "+ wordWiseProgress.toString())
                    //Log.d(TAG, "list size : "+ wordWiseProgress.size.toString())
                }
                wordsDetailsWithProgress.postValue(wordWiseProgress)
                wordsDetailsWithProgressBckp.postValue(wordWiseProgress)
            }
            .addOnFailureListener {
                Log.d(TAG, "failed again")
            }
    }

    fun observeFetchWordsList(): LiveData<List<UserProgress>> {
        return wordsDetailsWithProgressBckp
    }

    fun getWordsListBckp(): LiveData<List<UserProgress>> {
        return wordsDetailsWithProgressBckp
    }


    //Learning logic
    fun setLayoutState(x : LayoutState) {
        layoutState.value = x
    }

    fun observeLayoutState() : LiveData<LayoutState> {
        return layoutState
    }

    fun incrementCurrentIndexParam(temp : Int) {

//        if(wordsDetailsWithProgress.value!!.size <= 3)
//        {
//            wordsDetailsWithProgressBckp.value?.sortedByDescending { it.noOfTimesMarkedDontKnow }
//
//        }

        if(wordsDetailsWithProgress.value!!.isNotEmpty())
        {
            if(temp > 0)
                currentWordIndex.value = (currentWordIndex.value!!.inc()) % (wordsDetailsWithProgress.value!!.size)
            else
                currentWordIndex.value = (currentWordIndex.value!!) % (wordsDetailsWithProgress.value!!.size)

            //Log.d(TAG, "Current index param inc : ${currentWordIndex.value!!}")
        }
    }

    fun observeCurrentIndexParam() : LiveData<Int> {
        return currentWordIndex
    }

    fun getSizewordsDetailsWithProgress() : Int {
        return wordsDetailsWithProgress.value?.size ?: 0
    }

    //Mastered : get count
    fun getMasteredWordsCount() : Int {

        return wordsDetailsWithProgressBckp.value!!.filter {
            if(it.mastered)
                return@filter true
            else
                false
        }.size
    }

    fun getMasteredVarState() : Boolean {
        return wordsDetailsWithProgress.value!![currentWordIndex.value!!].mastered
    }

    //Mastered : set true
    fun setMasteredTrue() {
        wordsDetailsWithProgress.value!![currentWordIndex.value!!].mastered = true
        wordsDetailsWithProgressBckp.value!!.map {
            if(it.word == wordsDetailsWithProgress.value!![currentWordIndex.value!!].word)
                it.mastered = true
        }
    }

    //Mastered : set true
    fun setMasteredFalse() {
        wordsDetailsWithProgress.value!![currentWordIndex.value!!].mastered = false
        wordsDetailsWithProgressBckp.value!!.map {
            if(it.word == wordsDetailsWithProgress.value!![currentWordIndex.value!!].word)
                it.mastered = false
        }
    }

    //Learning : get count
    fun getLearningWordsCount() : Int {
        return wordsDetailsWithProgressBckp.value!!.filter {
            if(it.learning)
                return@filter true
            else
                false
        }.size
    }

    fun getLearningVarState() : Boolean {
        return wordsDetailsWithProgress.value!![currentWordIndex.value!!].learning
    }

    fun setLearningTrue() {
        wordsDetailsWithProgress.value!![currentWordIndex.value!!].learning = true
        wordsDetailsWithProgressBckp.value!!.map {
            if(it.word == wordsDetailsWithProgress.value!![currentWordIndex.value!!].word)
                it.learning = true
        }
    }

    fun setLearningFalse() {
        wordsDetailsWithProgress.value!![currentWordIndex.value!!].learning = false
        wordsDetailsWithProgressBckp.value!!.map {
            if(it.word == wordsDetailsWithProgress.value!![currentWordIndex.value!!].word)
                it.learning = false
        }
    }


    fun getReviewingWordsCount() : Int {
        return wordsDetailsWithProgressBckp.value!!.filter {
            if(it.reviewing)
                return@filter true
            else
                false
        }.size
    }

    fun getReviewingVarState() : Boolean {
        return wordsDetailsWithProgress.value!![currentWordIndex.value!!].reviewing
    }

    fun setReviewingTrue() {
        wordsDetailsWithProgress.value!![currentWordIndex.value!!].reviewing = true
        wordsDetailsWithProgressBckp.value!!.map {
            if(it.word == wordsDetailsWithProgress.value!![currentWordIndex.value!!].word)
                it.reviewing = true
        }
    }

    fun setReviewingFalse() {
        wordsDetailsWithProgress.value!![currentWordIndex.value!!].reviewing = false
        wordsDetailsWithProgressBckp.value!!.map {
            if(it.word == wordsDetailsWithProgress.value!![currentWordIndex.value!!].word)
                it.reviewing = false
        }
    }

    fun getNewWordStateText() : String? {
//        Log.d(TAG, "new learning reviewing")
//        Log.d(TAG, " word : "+ (wordsDetailsWithProgress.value!![currentWordIndex.value!!].word))
//        Log.d(TAG, "newWord : "+ (wordsDetailsWithProgress.value!![currentWordIndex.value!!].newWord).toString())
//        Log.d(TAG, "learning : "+(wordsDetailsWithProgress.value!![currentWordIndex.value!!].learning).toString())
//        Log.d(TAG, "mastered : "+(wordsDetailsWithProgress.value!![currentWordIndex.value!!].mastered).toString())
//        Log.d(TAG, "reviewing : "+(wordsDetailsWithProgress.value!![currentWordIndex.value!!].reviewing).toString())
//        Log.d(TAG, "rc : "+(wordsDetailsWithProgress.value!![currentWordIndex.value!!].reviewCount).toString())

        if(wordsDetailsWithProgress.value!![currentWordIndex.value!!].newWord)
        {
            //Log.d(TAG, "newWordState : new word true")
            newWordBackgroundColor = newWordStateColor[NewWordState.NEW]!!
            return newWordStateText[NewWordState.NEW]
        }
        else if(wordsDetailsWithProgress.value!![currentWordIndex.value!!].learning)
        {
            //Log.d(TAG, "newWordState : learning")
            newWordBackgroundColor = newWordStateColor[NewWordState.LEARNING]!!
            return newWordStateText[NewWordState.LEARNING]
        }
        else if(wordsDetailsWithProgress.value!![currentWordIndex.value!!].reviewing)
        {
            //Log.d(TAG, "newWordState : reviewing")
            newWordBackgroundColor = newWordStateColor[NewWordState.REVIEWING]!!
            return newWordStateText[NewWordState.REVIEWING]
        }
        else
        {
            //Log.d(TAG, "newWordState : none ")
            return "DON'T KNOW STATE"
        }
    }

    fun getNewWordBackgroundColor() : Int{
        return newWordBackgroundColor
    }

    fun setNewWordFalse(temp : Int) {
        wordsDetailsWithProgress.value!![currentWordIndex.value!!].newWord = false
        wordsDetailsWithProgressBckp.value!!.map {
            if(it.word == wordsDetailsWithProgress.value!![currentWordIndex.value!!].word)
                it.newWord = false
        }
    }

    fun getVarCountNoOfTimesMarkedDontKnow() : Long {
        return wordsDetailsWithProgress.value!![currentWordIndex.value!!].noOfTimesMarkedDontKnow
    }

    fun increaseVarCountNoOfTimesMarkedDontKnow() {
        wordsDetailsWithProgress.value!![currentWordIndex.value!!].noOfTimesMarkedDontKnow++
        wordsDetailsWithProgressBckp.value!!.map {
            if(it.word == wordsDetailsWithProgress.value!![currentWordIndex.value!!].word)
                it.noOfTimesMarkedDontKnow++
        }
    }

    fun getReviewCountOfWord() : Int {
        return wordsDetailsWithProgress.value!![currentWordIndex.value!!].reviewCount.toInt()
    }

    fun setReviewCountOfWord(rc : Int) {
        wordsDetailsWithProgress.value!![currentWordIndex.value!!].reviewCount = rc.toLong()
    }

    fun getWord() : String {
        return return wordsDetailsWithProgress.value!![currentWordIndex.value!!].word
    }

    fun removeWordFromMainList() {

        wordsDetailsWithProgress.value = wordsDetailsWithProgress.value!!.filter {
            it != wordsDetailsWithProgress.value!![currentWordIndex.value!!]
        }
    }

}


