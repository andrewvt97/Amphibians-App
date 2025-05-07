package com.example.amphibiansapp.data

import com.example.amphibiansapp.network.Amphibian
import com.example.amphibiansapp.network.AmphibiansApiService

interface AmphibianRepository {
    suspend fun getAmphibians(): List<Amphibian>
}

class NetworkAmphibianRepository (
    private val amphibiansApiService: AmphibiansApiService
) : AmphibianRepository{

    override suspend fun getAmphibians(): List<Amphibian> = amphibiansApiService.getAmphibians()
}