package com.example.amazonprime.model

data class ParentModel (
    val title: String,
    val childModels: List<ChildModel>
)