package com.suraj.nasagallery.data

import android.media.Image
import android.widget.ImageView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.suraj.nasagallery.ui.ImagesUiModel
import com.suraj.nasagallery.ui.ImagesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito


@ExperimentalCoroutinesApi
class ImageViewModelTest {


    @get:Rule
    val rule = InstantTaskExecutorRule()

    val testDispatcher = TestCoroutineDispatcher()

    private lateinit var fakeImageRepository: FakeImageRepository
    private lateinit var imagesViewModel: ImagesViewModel

    @Before
    fun setup() {

        Dispatchers.setMain(testDispatcher)
    }

    @Test
     fun `Verify error scenario for image list`() {
        runBlockingTest {
            fakeImageRepository = FakeImageRepository(true)
            imagesViewModel = ImagesViewModel(fakeImageRepository)
            val imagesList = imagesViewModel.getImages()

            assertThat(imagesList.size).isEqualTo(0)
        }
    }

    @Test
    fun `verify success scenario for image list`() {
        var imageList: ArrayList<ImageModel> = arrayListOf()
        runBlockingTest {
            fakeImageRepository = FakeImageRepository(false)
            imagesViewModel = ImagesViewModel(fakeImageRepository)
            imageList = imagesViewModel.getImages()
            assertThat(imageList.size).isNotEqualTo(0)
        }


    }
}