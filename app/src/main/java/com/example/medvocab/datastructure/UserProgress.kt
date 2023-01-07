package com.example.medvocab.datastructure

data class UserProgress(val word: String, var newWord : Boolean,
                        var mastered: Boolean, var reviewing : Boolean, var learning : Boolean,
                        var reviewCount : Long, var noOfTimesMarkedDontKnow : Long)

