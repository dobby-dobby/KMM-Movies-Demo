package com.sopa.movieskmm.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ResultTest {
    
    @Test
    fun `Result Success should contain correct data`() {
        // Given
        val testData = "test data"
        
        // When
        val result = Result.Success(testData)
        
        // Then
        assertTrue(result is Result.Success)
        assertEquals(testData, result.data)
    }
    
    @Test
    fun `Result Failure should contain correct exception`() {
        // Given
        val testException = Exception("test error")
        
        // When
        val result = Result.Failure(testException)
        
        // Then
        assertTrue(result is Result.Failure)
        assertEquals(testException, result.exception)
    }
    
    @Test
    fun `Result Success should work with different data types`() {
        // Given & When
        val stringResult = Result.Success("string data")
        val intResult = Result.Success(42)
        val listResult = Result.Success(listOf(1, 2, 3))
        
        // Then
        assertTrue(stringResult is Result.Success)
        assertEquals("string data", stringResult.data)
        
        assertTrue(intResult is Result.Success)
        assertEquals(42, intResult.data)
        
        assertTrue(listResult is Result.Success)
        assertEquals(listOf(1, 2, 3), listResult.data)
    }
    
    @Test
    fun `Result Failure should work with different exception types`() {
        // Given & When
        val runtimeException = Result.Failure(RuntimeException("runtime error"))
        val illegalArgumentException = Result.Failure(IllegalArgumentException("illegal argument"))
        
        // Then
        assertTrue(runtimeException is Result.Failure)
        assertTrue(runtimeException.exception is RuntimeException)
        assertEquals("runtime error", runtimeException.exception.message)
        
        assertTrue(illegalArgumentException is Result.Failure)
        assertTrue(illegalArgumentException.exception is IllegalArgumentException)
        assertEquals("illegal argument", illegalArgumentException.exception.message)
    }
} 