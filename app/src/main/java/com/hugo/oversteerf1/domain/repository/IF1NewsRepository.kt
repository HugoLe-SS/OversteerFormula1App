package com.hugo.oversteerf1.domain.repository

import com.hugo.datasource.local.entity.F1News
import com.hugo.utilities.Resource
import kotlinx.coroutines.flow.Flow

interface IF1NewsRepository {

    fun getF1News(): Flow<Resource<List<F1News>>>

}