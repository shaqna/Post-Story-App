package com.ngedev.postcat.data.source.service

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ngedev.postcat.data.source.response.ApiResponse
import com.ngedev.postcat.domain.model.LoginRequest
import com.ngedev.postcat.domain.model.RegisterRequest
import com.ngedev.postcat.utils.CoroutinesTestRule
import com.ngedev.postcat.utils.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class AuthRemoteTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTesRule = CoroutinesTestRule()

    @Mock
    private lateinit var authService: ApiService
    private lateinit var authRemote: AuthRemote

    @Mock
    private lateinit var loginRequest: LoginRequest

    @Mock
    private lateinit var registerRequest: RegisterRequest

    @Before
    fun setup() {
        authRemote = AuthRemote(authService)
    }


    @Test
    fun `is login successfully - result success`() = runTest {
        val expectedSuccessResponse = DataDummy.generateDummyLoginResponse()

        Mockito.`when`(authService.login(loginRequest)).thenReturn(expectedSuccessResponse)

        authRemote.login(loginRequest).collect {
            when(it) {
                is ApiResponse.Success -> {
                    assertTrue(!it.data.error)
                    assertNotNull(it.data)
                    assertEquals(expectedSuccessResponse, it.data)
                }
                else -> {}
            }
        }
    }

    @Test
    fun `is register successfully - result success`() = runTest {
        val expectedSuccessResponse = DataDummy.generateDummyRegisterResponse()

        Mockito.`when`(authService.createUser(registerRequest)).thenReturn(expectedSuccessResponse)

        authRemote.createUser(registerRequest).collect {
            when(it) {
                is ApiResponse.Success -> {
                    assertTrue(!it.data.error)
                    assertNotNull(it.data)
                    assertEquals(expectedSuccessResponse, it.data)
                }
                else -> {}
            }
        }
    }
}