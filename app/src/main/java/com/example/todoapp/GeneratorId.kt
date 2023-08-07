package com.example.todoapp

import kotlin.random.Random

object GeneratorId {
    fun generate()= String(Random.Default.nextBytes(10))
}