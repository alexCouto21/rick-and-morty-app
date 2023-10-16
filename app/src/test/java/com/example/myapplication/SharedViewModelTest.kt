package com.example.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.data.GetCharacterByIdResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class SharedViewModelTest {

    private lateinit var viewModel: SharedViewModel
    private lateinit var mockRepository: SharedRepository
    private lateinit var characterObserver: Observer<GetCharacterByIdResponse?>

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        mockRepository = mockk(relaxed = true) {
            coEvery { getCharacter(any()) } returns mockk()
        }
        characterObserver = mockk()
        viewModel = SharedViewModel()
        viewModel.characterByIdLiveData.observeForever(characterObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `refreshCharacter given character Id`() = runTest {
        //Given
        val characterId = 12
        val mockResponse = mockk<GetCharacterByIdResponse>()
        coEvery { mockRepository.getCharacter(characterId) } returns mockResponse
        //When
        viewModel.refreshCharacter(characterId)
        dispatcher.scheduler.advanceUntilIdle()

        //Then
        coVerify(exactly = 0) { mockRepository.getCharacter(characterId) }
    }

}