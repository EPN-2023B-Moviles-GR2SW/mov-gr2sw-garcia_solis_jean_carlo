package com.example.amazonprime.utils

import com.example.amazonprime.model.ChildModel
import com.example.amazonprime.model.ParentModel

object SampleData {

    private val movieModel= listOf(
        ChildModel(Images.imageUrl0),
        ChildModel(Images.imageUrl1),
        ChildModel(Images.imageUrl2),
        ChildModel(Images.imageUrl3),
        ChildModel(Images.imageUrl4),
        ChildModel(Images.imageUrl5),
        ChildModel(Images.imageUrl6),
        ChildModel(Images.imageUrl7),
        ChildModel(Images.imageUrl8),
        ChildModel(Images.imageUrl9)
    )

    val collections = listOf(
        ParentModel("MOST RECOMMENDED" , movieModel),
        ParentModel("AMAZON ORIGINAL>" , movieModel.reversed()),
        ParentModel("COMING SOON >" , movieModel.shuffled()),
        ParentModel("WATCH IN YOUR LANGUAGE" , movieModel)

    )
}