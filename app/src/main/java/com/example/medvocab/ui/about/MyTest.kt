package com.example.medvocab.ui.about

import com.example.medvocab.FbaseData

//class MyTest {
    //: Fragment() {
//
//    private var _binding : FragmentTestBinding? = null
//    private val binding get() = _binding!!
//
//    private val viewModel: MainViewModel by activityViewModels()
//
//    private val TAG = "AboutFragment"
//
//
//    //TODO: del


    //    var listcw1 = listOf<FbaseData>(
//        FbaseData(0, "cardiology"),
//        FbaseData(1, "bronchoscope"),
//        FbaseData(2, "acetaminophen"),
//        FbaseData(3, "acupressure"),
//        FbaseData(4, "angiotensin"),
//        FbaseData(5, "echocardiography")
//    )
    var listcw1 = listOf<FbaseData>(
        FbaseData(0, "cardiology"),
        FbaseData(1, "bronchoscope"),
        FbaseData(2, "acetaminophen"),
        FbaseData(3, "acupressure"),
        FbaseData(4, "angiotensin"),
        FbaseData(5, "echocardiography"),
        FbaseData(6, "fatty acids"),
        FbaseData(7, "doctor"),
        FbaseData(8, "benign"),
        FbaseData(9, "biopsy"),
        FbaseData(10, "chronic"),
        FbaseData(11, "epidermis"),
        FbaseData(12, "fracture"),
        FbaseData(13, "gland"),
        FbaseData(14, "hypertension"),
        FbaseData(15, "inpatient"),
        FbaseData(16, "intravenous"),
        FbaseData(17, "malignant"),
        FbaseData(18, "outpatient"),
        FbaseData(19, "prognosis"),
        FbaseData(20, "relapse")
    )

    var listcw2 = listOf<FbaseData>(
        FbaseData(0, "zoonosis"),
        FbaseData(1, "vaccine"),
        FbaseData(2, "suture"),
        FbaseData(3, "transplant"),
        FbaseData(4, "urologist"),
        FbaseData(5, "triage"),
        FbaseData(6, "topical"),
        FbaseData(7, "subcutaneous"),
        FbaseData(8, "sepsis"),
        FbaseData(9, "physiology"),
        FbaseData(10, "pharmacology"),
        FbaseData(11, "pathology"),
        FbaseData(12, "neurology"),
        FbaseData(13, "idiopathic"),
        FbaseData(14, "hematology"),
        FbaseData(15, "genetics"),
        FbaseData(16, "etiology"),
        FbaseData(17, "analgesia"),
        FbaseData(18, "abatement"),
        FbaseData(19, "abrasion"),
        FbaseData(20, "anatomy"),
    )

    var listbasic1 = listOf<FbaseData>(
        FbaseData(0, "keratin"),
        FbaseData(1, "ketone"),
        FbaseData(2, "insulin"),
        FbaseData(3, "iris"),
        FbaseData(4, "ischemic stroke"),
        FbaseData(5, "hemoglobin"),
        FbaseData(6, "hemorrhage"),
        FbaseData(7, "histamine"),
        FbaseData(8, "hippocampus"),
        FbaseData(9, "hormone"),
        FbaseData(10, "hyaluronic acid"),
        FbaseData(11, "fluoroscope"),
        FbaseData(12, "frontal lobe"),
        FbaseData(13, "electrocardiogram"),
        FbaseData(14, "ellagic acid"),
        FbaseData(15, "endorphin"),
        FbaseData(16, "eosinophil"),
        FbaseData(17, "defibrillator"),
        FbaseData(18, "delta wave"),
        FbaseData(19, "detoxify"),
        FbaseData(20, "diabetes")
    )

    var listbasic2 = listOf<FbaseData>(
        FbaseData(0, "DNA"),
        FbaseData(1, "RNA"),
        FbaseData(2, "dry eye"),
        FbaseData(3, "calcify"),
        FbaseData(4, "cardiac arrest"),
        FbaseData(5, "cardiovascular"),
        FbaseData(6, "cavity"),
        FbaseData(7, "cerebellum"),
        FbaseData(8, "chiropractor"),
        FbaseData(9, "scurvy"),
        FbaseData(10, "serotonin"),
        FbaseData(11, "somnambulism"),
        FbaseData(12, "spondylitis"),
        FbaseData(13, "thrombosis"),
        FbaseData(14, "Parkinson's disease"),
        FbaseData(15, "asthma"),
        FbaseData(16, "migraine"),
        FbaseData(17, "angioplasty"),
        FbaseData(18, "angiogram"),
        FbaseData(19, "fatty liver"),
        FbaseData(20, "cerebral cortex:"),
    )

    var listadv1 = listOf<FbaseData>(
        FbaseData(0, "myocardial infarction"),
        FbaseData(1, "nebulize"),
        FbaseData(2, "ophthalmoscope"),
        FbaseData(3, "spirometer"),
        FbaseData(4, "amniocentesis"),
        FbaseData(5, "antihistamine"),
        FbaseData(6, "bradycardia"),
        FbaseData(7, "catheterization"),
        FbaseData(8, "cervical smear"),
        FbaseData(9, "gingivectomy"),
        FbaseData(10, "leishmaniasis"),
        FbaseData(11, "retinoblastoma"),
        FbaseData(12, "paronychia"),
        FbaseData(13, "roseola infantum"),
        FbaseData(14, "nystagmus"),
        FbaseData(15, "anesthesiologist"),
        FbaseData(16, "balloon angioplasty"),
        FbaseData(17, "osteomyelitis"),
        FbaseData(18, "pink eye"),
        FbaseData(19, "CT scan"),
        FbaseData(20, "diverticulitis"),
    )

    var listadv2 = listOf<FbaseData>(
        FbaseData(0, "endarterectomy"),
        FbaseData(1, "rheumatoid arthritis"),
        FbaseData(2, "kyphosis"),
        FbaseData(3, "hypochondriasis"),
        FbaseData(4, "laryngitis"),
        FbaseData(5, "hemangioma"),
        FbaseData(6, "lithotripsy"),
        FbaseData(7, "Hodgkin's disease"),
        FbaseData(8, "metabolite"),
        FbaseData(9, "mononucleosis"),
        FbaseData(10, "neurofibromatosis"),
        FbaseData(11, "heart"),
        FbaseData(12, "oncogene"),
        FbaseData(13, "ophthalmologist"),
        FbaseData(14, "ossification"),
        FbaseData(15, "gamma globulin"),
        FbaseData(16, "lymphadenopathy"),
        FbaseData(17, "pancreatitis"),
        FbaseData(18, "hyperlipidemia"),
        FbaseData(19, "pericarditis"),
        FbaseData(20, "psychosomatic"),
    )

     var getAllListsNewUser = mutableMapOf<String, List<FbaseData>>(
        "commonWordsOne" to listcw1,
        "commonWordsTwo" to listcw2,
        "basicWordsOne" to listbasic1,
        "basicWordsTwo" to listbasic2,
        "advancedWordsOne" to listadv1,
        "advancedWordsTwo" to listadv2,
    )

//}

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentTestBinding.inflate(inflater, container, false)
//
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        //binding.textHome.text = "Yes about"
//
//        //TODO : API test - to del
//        binding.apiTest.setOnClickListener {
//            //viewModel.netRefresh()
//        }
//
//        //TODO : Firebase setup to del
//        var temp_uuid = String()
//        binding.clickMe.setOnClickListener {
//            val db = Firebase.firestore
//
//            db.collection("userProgress")
//                .get()
//                .addOnSuccessListener {  result ->
////                    for (document in result) {
////                        Log.d(TAG, "${document.id} => ${document.data}")
////                    }
//                    temp_uuid = result.first().data["uuid"] as String
//                    Log.d(TAG, "uuid first instance : "+ temp_uuid)
//
//                    var wordWiseProgress = mutableListOf<UserProgress>()
//                    result.forEach {
//                        wordWiseProgress.add(
//                            UserProgress(
//                                it.data["word"] as String, it.data["newWord"] as Boolean,
//                                it.data["mastered"] as Boolean, it.data["reviewing"] as Boolean,
//                                it.data["learning"] as Boolean, it.data["reviewCount"] as Long,
//                                it.data["noOfTimesMarkedDontKnow"] as Long)
//                            //it.data["uuid"] as String)
//                        )
//                    }
//                    Log.d(TAG, "list : "+ wordWiseProgress.toString())
//                    Log.d(TAG, "list size : "+ wordWiseProgress.size.toString())
//                }
//                .addOnFailureListener { e ->
//                    Log.w(TAG, "Error adding document", e)
//                }
//        }
//
//        binding.dontClickMe.setOnClickListener {
//            val db = Firebase.firestore
//
//            var temp_userProgressObj = UserProgress(word= "spinal stenosis", newWord = false,
//                mastered=false, reviewing=false, learning=false, reviewCount=3,
//                noOfTimesMarkedDontKnow=0)
//            Log.d(TAG, "user progress doc uuid : "+ temp_uuid)
//
//            db.collection("userProgress").document(temp_uuid).set(temp_userProgressObj)
//        }
//
//        binding.dontClickMe2.setOnClickListener {
//            val db = Firebase.firestore
//
//            listcw1.forEach {
//                var temp_userProgressObj = UserProgress(word= it.word, newWord = true,
//                    mastered=false, reviewing=false, learning=false, reviewCount=3,
//                    noOfTimesMarkedDontKnow=0)
//
//                db.collection("commonWordsOne").add(temp_userProgressObj)
//            }
//
//        }
//
//    }
//
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
//
////TODO: Del after data population
////data class FbaseData(val index : Int, val word: String)