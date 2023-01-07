package com.example.medvocab

import android.graphics.Color



object TypesList {

    var typesList: List<TypesOfSets>

    data class TypesOfSets(val tag : String, val type: String, val totalWords : Int, var masteredWords : Int)

    // These are all "static" methods because this is an object, not a class
    fun getAll() : List<TypesOfSets> { return typesList }
    fun size() : Int { return typesList.size }

    init {
        typesList = listOf(
            TypesOfSets("commonWordsOne","Common words I", 21, 0),
            TypesOfSets("commonWordsTwo","Common words II", 21, 0),
            TypesOfSets("basicWordsOne","Basic words I", 21, 0),
            TypesOfSets("basicWordsTwo","Basic words II", 21, 0),
            TypesOfSets("advancedWordsOne","Advanced words I", 21, 0),
            TypesOfSets("advancedWordsTwo","Advanced words II", 21, 0)
        )
    }
}

data class FbaseData(val index : Int, val word: String)

//var listcw1 = listOf<FbaseData>(
//    FbaseData(0, "cardiology"),
//    FbaseData(1, "bronchoscope"),
//    FbaseData(2, "acetaminophen"),
//    FbaseData(3, "acupressure"),
//    FbaseData(4, "angiotensin"),
//    FbaseData(5, "echocardiography")
//)

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