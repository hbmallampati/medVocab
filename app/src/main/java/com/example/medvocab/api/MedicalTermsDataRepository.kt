package com.example.medvocab.api

class MedicalTermsDataRepository(private val api: WordDetailsFetchApi) {

    suspend fun fetchMedicalWordDetails(key : String, word : String) : List<MedicalTerm> {
        val result = api.getWordDetails(word, key)

        return result
    }

    fun getAudioField() {

    }

}