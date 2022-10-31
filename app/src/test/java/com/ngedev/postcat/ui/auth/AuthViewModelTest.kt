package com.ngedev.postcat.ui.auth


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.ngedev.postcat.data.source.response.LoginResponse
import com.ngedev.postcat.data.source.response.RegisterResponse
import com.ngedev.postcat.domain.model.LoginRequest
import com.ngedev.postcat.domain.model.RegisterRequest
import com.ngedev.postcat.domain.state.Resource
import com.ngedev.postcat.domain.usecase.auth.AuthInteractor
import com.ngedev.postcat.domain.usecase.auth.AuthUseCase
import com.ngedev.postcat.utils.CoroutinesTestRule
import com.ngedev.postcat.utils.DataDummy
import com.ngedev.postcat.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import kotlin.math.exp

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class AuthViewModelTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var authUseCase: AuthUseCase
    private lateinit var viewModel: AuthViewModel

    @Mock
    private lateinit var loginRequest: LoginRequest

    @Mock
    private lateinit var registerRequest: RegisterRequest

    @Before
    fun setup() {
        viewModel = AuthViewModel(authUseCase)
    }

    @Test
    fun `is login failed - result failure with error message`() = runTest {
        val expectedFailedResponse: Flow<Resource<LoginResponse>> =
            flowOf(Resource.Error("login failed"))

        `when`(authUseCase.login(loginRequest)).thenReturn(expectedFailedResponse)

        assertEquals("login failed", viewModel.login(loginRequest).getOrAwaitValue().message)
        Mockito.verify(authUseCase).login(loginRequest)


    }

    @Test
    fun `is login successfully - result success`() {
        val expectedSuccessResponse: Flow<Resource<LoginResponse>> =
            flowOf(Resource.Success(DataDummy.generateDummyLoginResponse()))

        `when`(authUseCase.login(loginRequest)).thenReturn(expectedSuccessResponse)

        assertEquals(
            DataDummy.generateDummyLoginResponse(),
            viewModel.login(loginRequest).getOrAwaitValue().data
        )

        Mockito.verify(authUseCase).login(loginRequest)
    }

    @Test
    fun `is register failed - result failure with error message`() {
        val expectedFailedResponse: Flow<Resource<RegisterResponse>> =
            flowOf(Resource.Error("register failed"))

        `when`(authUseCase.createUser(registerRequest)).thenReturn(expectedFailedResponse)

        assertEquals(
            "register failed",
            viewModel.createUser(registerRequest).getOrAwaitValue().message
        )

        Mockito.verify(authUseCase).createUser(registerRequest)
    }

    @Test
    fun `is register successfully - result success`() {
        val expectedSuccessResponse: Flow<Resource<RegisterResponse>> =
            flowOf(Resource.Success(DataDummy.generateDummyRegisterResponse()))

        `when`(authUseCase.createUser(registerRequest)).thenReturn(expectedSuccessResponse)

        assertEquals(
            DataDummy.generateDummyRegisterResponse(),
            viewModel.createUser(registerRequest).getOrAwaitValue().data
        )

        Mockito.verify(authUseCase).createUser(registerRequest)
    }


}