package com.suraj.nasagallery.data

import org.mockito.Mockito

class FakeImageRepository(private var shouldReturnError: Boolean = false) : ImageRepository {

    override suspend fun loadImageModelsFromJson(): ArrayList<ImageModel> {
        return if (shouldReturnError)
            arrayListOf()
        else {
            val result = arrayListOf<ImageModel>()
            for (i in 0..10){
                result.add(Mockito.mock(ImageModel::class.java))
            }
            result
        }
    }
}