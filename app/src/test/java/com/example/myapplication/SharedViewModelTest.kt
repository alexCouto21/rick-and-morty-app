package com.example.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.myapplication.data.GetCharacterByIdResponse
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SharedViewModelTest {

    private lateinit var viewModel: SharedViewModel
    private lateinit var mockRepository: SharedRepository
    private lateinit var characterObserver: Observer<GetCharacterByIdResponse?>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        mockRepository = mockk(relaxed = true)
        characterObserver = mockk(relaxed = true)
        viewModel = SharedViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `refreshCharacter given character Id`() = runTest {
        //Given
        val characterId = 20
        val mockResponse = mockk<GetCharacterByIdResponse>(relaxed = true)
        val liveDataUnderTest = viewModel.characterByIdLiveData

        coEvery { mockRepository.getCharacter(characterId) } returns mockResponse

        //When
        viewModel.refreshCharacter(characterId)

        //Then
        coVerify { mockRepository.getCharacter(characterId) }
        liveDataUnderTest.observeForever(characterObserver)
        verify { characterObserver.onChanged(mockResponse) }
        liveDataUnderTest.removeObserver(characterObserver)
    }

}