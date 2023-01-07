package com.example.medvocab

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.lifecycle.Observer
import com.example.medvocab.databinding.ActivityVocanBuilderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class VocabBuildActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVocanBuilderBinding
    private val viewModel: VocabBuildViewModel by viewModels()

    companion object IntentStrings {
        const val startLearningActivity = "startLearningActivity"
        const val suffixText = " of 21 words "
        const val mastered = "mastered"
        const val learning = "learning"
        const val reviewing = "reviewing"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVocanBuilderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activityThatCalled = intent
        val callingBundle = activityThatCalled.extras
        val position = callingBundle?.getInt(MainActivity.callingActivityKey)
        Log.d(javaClass.simpleName, "position : $position")

        if (position != null) {
            viewModel.setType(position)
        }
        else
            Log.d(javaClass.simpleName, "Error occurred")

        binding.endLayout.root.visibility = View.GONE

        //Fetch words list and start learning
        viewModel.observeFetchWordsList().observe(this, Observer {
            viewModel.setLayoutState(VocabBuildViewModel.LayoutState.WORD_ONLY_STATE)
            viewModel.stateVar = 0
            viewModel.incrementCurrentIndexParam(1)
        })

        //Set values to display widgets
        viewModel.observeCurrentIndexParam().observe(this, Observer {

            if(it != -1) //word only state
            {
                binding.part1.newWordTv1.text = viewModel.getNewWordStateText()
                binding.part1.newWordTv1.setBackgroundColor(
                    resources.getColor(viewModel.getNewWordBackgroundColor(), resources.newTheme()))

                binding.part1.wordTv1.text = viewModel.getWord()

                binding.masteredTv.text = String.format("%d %s %s", viewModel.getMasteredWordsCount(), suffixText, mastered)
                binding.learningTv.text = String.format("%d %s %s", viewModel.getLearningWordsCount(), suffixText, learning)
                binding.reviewingTv.text = String.format("%d %s %s", viewModel.getReviewingWordsCount(), suffixText, reviewing)

                binding.masteredProgressBar.progress = viewModel.getMasteredWordsCount()
                binding.learningProgressBar.progress = viewModel.getLearningWordsCount()
                binding.reviewingProgressBar.progress = viewModel.getReviewingWordsCount()

            }
        })

        //Tap to see meaning button click event
        binding.part1.tapToSeeMeaningBt.setOnClickListener {

            viewModel.netRefresh()
            viewModel.setLayoutState(VocabBuildViewModel.LayoutState.WORD_WITH_DETAILS)
            viewModel.stateVar = 1
        }

        viewModel.observeMedicalTermResponse().observe(this, Observer {

            binding.wordDetailslayout.newWordTv2.text = viewModel.getNewWordStateText()
            binding.wordDetailslayout.newWordTv2.setBackgroundColor(
                resources.getColor(viewModel.getNewWordBackgroundColor(), resources.newTheme()))

            binding.wordDetailslayout.wordTv2.text = viewModel.getWord()
            binding.wordDetailslayout.partsOfSpeechTv.text = "Parts of speech : "+it[0].f1

            //get only if phonetics is present
            var temp = it[0]?.hwi?.prs_v?.get(0)?.mw?.toString()
            if(!temp.isNullOrEmpty())
            {
                binding.wordDetailslayout.phoneticsTv.text = "Pronunciation : "+ it[0].hwi.prs_v[0].mw.toString()
            }

            var shortdefs = it[0].shortdef.toString().replace("[", "")
            shortdefs = shortdefs.replace("]","")
            binding.wordDetailslayout.meaningsTv.text = "Meaning : " + shortdefs


            viewModel.getAudioFile()
        })

        binding.wordDetailslayout.root.visibility = View.GONE

        viewModel.observeFetchAudioFile().observe(this, Observer { s ->
            if(s.isNotEmpty() && !s.contains("null")) {

                binding.wordDetailslayout.audioBt.visibility = View.VISIBLE
                binding.wordDetailslayout.audioBt.setOnClickListener {

                    //val url = viewModel.fetchAudioFile.toString()
                    val mediaPlayer = MediaPlayer().apply {
                        setAudioAttributes(
                            AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build()
                        )
                        setDataSource(s.toString())
                        prepare()
                        start()
                    }
                }
            }})


        viewModel.observeLayoutState().observe(this, Observer {

            if(it == VocabBuildViewModel.LayoutState.WORD_ONLY_STATE)
            {
                flipCard(this, binding.part1.root, binding.wordDetailslayout.root)
                binding.wordDetailslayout.root.visibility = View.GONE
                binding.part1.root.visibility = View.VISIBLE

            }
            else
            {
                flipCard(this, binding.wordDetailslayout.root, binding.part1.root)
                binding.part1.root.visibility = View.GONE
                binding.wordDetailslayout.root.visibility = View.VISIBLE

                binding.wordDetailslayout.audioBt.visibility = View.GONE

                Log.d(javaClass.simpleName, "word details layout")

                binding.wordDetailslayout.iKnewThisWordBt.setOnClickListener {

                    //Update review count
                    //viewModel.updateReviewCount(-1)

                    Log.d(javaClass.simpleName, "i knew this bt")

                    //Set mastered = true
                    if(viewModel.getReviewingVarState())
                    {
                        Log.d(javaClass.simpleName, "rc : "+ viewModel.getReviewCountOfWord())
                        viewModel.setReviewCountOfWord(viewModel.getReviewCountOfWord() - 1)

                        if(viewModel.getReviewCountOfWord() == 0)
                        {
                            viewModel.setReviewingFalse()
                            viewModel.setMasteredTrue()

                            //Set newWord = false
                            viewModel.setNewWordFalse(0)

                            //Remove word from list : wordsDetailsWithProgress
                            viewModel.removeWordFromMainList()

                            //Increment currentIndex var
                            viewModel.incrementCurrentIndexParam(0)
                        }
                        else
                        {
                            //Set newWord = false
                            viewModel.setNewWordFalse(0)

                            viewModel.incrementCurrentIndexParam(1)
                        }
                    }
                    else if(viewModel.getLearningVarState())
                    {
                        Log.d(javaClass.simpleName, "i knew this bt: learn")
                        viewModel.setReviewCountOfWord(viewModel.getReviewCountOfWord() - 1)
                        viewModel.setLearningFalse()
                        viewModel.setReviewingTrue()

                        //Set newWord = false
                        viewModel.setNewWordFalse(0)

                        //Increment currentIndex var
                        viewModel.incrementCurrentIndexParam(1)
                    }
                    else
                    {
                        Log.d(javaClass.simpleName, "i knew this bt: none")
                        viewModel.setMasteredTrue()

                        //Set newWord = false
                        viewModel.setNewWordFalse(0)

                        //Remove word from list : wordsDetailsWithProgress
                        viewModel.removeWordFromMainList()

                        //Increment currentIndex var
                        viewModel.incrementCurrentIndexParam(0)
                    }

                    //Check if any words left or not
                    if(viewModel.getSizewordsDetailsWithProgress() > 0)
                    {
                        //Switch to word only layout
                        viewModel.setLayoutState(VocabBuildViewModel.LayoutState.WORD_ONLY_STATE)
                        viewModel.stateVar = 0
                    }
                    else
                    {
                        binding.wordDetailslayout.root.visibility = View.GONE
                        binding.part1.root.visibility = View.GONE
                        binding.endLayout.root.visibility = View.VISIBLE
                        binding.bottomPart.visibility = View.GONE
                    }

                }

                binding.wordDetailslayout.iDidntKnewThisWordBt.setOnClickListener {

                    //Update review count
                    //viewModel.updateReviewCount(1)

                    //Set learning = true
                    if(viewModel.getLearningVarState())
                    {

                    }
                    else if(viewModel.getReviewingVarState())
                    {
                        //rc++
                        viewModel.setReviewCountOfWord(viewModel.getReviewCountOfWord()+1)

                        //if rc == 3 --> change state to learning state
                        if(viewModel.getReviewCountOfWord() == 3)
                        {
                            viewModel.setLearningTrue()
                            viewModel.setReviewingFalse()
                        }
                    }
                    else
                    {
                        viewModel.setLearningTrue()
                    }

                    //Increase count of var noOfTimesmarkedDontKnow
                    viewModel.increaseVarCountNoOfTimesMarkedDontKnow()

                    //Set newWord false
                    viewModel.setNewWordFalse(1)

                    //Increment currentIndex var
                    viewModel.incrementCurrentIndexParam(1)

                    //Switch to word only layout
                    viewModel.setLayoutState(VocabBuildViewModel.LayoutState.WORD_ONLY_STATE)
                    viewModel.stateVar = 0
                }
            }
        })
    }

    fun flipCard(context: Context, visibleView: View, inVisibleView: View) {
        try {
            visibleView.visibility = View.VISIBLE

            val scale = context.resources.displayMetrics.density
            val cameraDist = 8000 * scale

            visibleView.cameraDistance = cameraDist
            inVisibleView.cameraDistance = cameraDist

            val flipOutAnimatorSet =
                AnimatorInflater.loadAnimator(
                    context,
                    R.animator.flip_out
                ) as AnimatorSet
            flipOutAnimatorSet.setTarget(inVisibleView)

            val flipInAnimatorSet =
                AnimatorInflater.loadAnimator(
                    context,
                    R.animator.flip_in
                ) as AnimatorSet
            flipInAnimatorSet.setTarget(visibleView)

            flipOutAnimatorSet.start()
            flipInAnimatorSet.start()

            flipInAnimatorSet.doOnEnd {
                inVisibleView.visibility = View.GONE
            }
        }
        catch (e: Exception) {
            Log.d(javaClass.simpleName, e.message.toString())
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        //Upload changes to cloud
        val firebaseUser = FirebaseAuth.getInstance().currentUser

        viewModel.getWordsListBckp().value?.forEach {

            Firebase.firestore.collection("users_new")
                .document(firebaseUser!!.uid)
                .collection(viewModel.getType())
                .document(it.word)
                .set(
                    hashMapOf(
                        "word" to it.word,
                        "learning" to it.learning,
                        "mastered" to it.mastered,
                        "reviewing" to it.reviewing,
                        "newWord" to it.newWord,
                        "reviewCount" to it.reviewCount,
                        "noOfTimesMarkedDontKnow" to it.noOfTimesMarkedDontKnow
                    )
                )
                .addOnSuccessListener {
                    Log.d("onstop", "updated  data")
                }
                .addOnFailureListener {
                    Log.d("onstop", "failed again")
                }
        }
    }

}















