package com.example.medvocab.api

import com.google.gson.annotations.SerializedName

data class MedicalTerm(
    @SerializedName("meta")
    val meta : Meta,

    @SerializedName("hwi")
    val hwi : Hwi,

    @SerializedName("shortdef")
    val shortdef : List<String>,

    @SerializedName("fl")
    val f1 : String
)

data class Meta(
    @SerializedName("id")
    val id: String,

    @SerializedName("uuid")
    val uuid : String)


data class Hwi(
    @SerializedName("prs")
    val prs_v : List<Prs>
    )


data class Prs(@SerializedName("mw")
               val mw : String,

               @SerializedName("sound")
               val sound: SoundObj
               )

data class SoundObj(
    @SerializedName("audio")
    val audio : String
)

