package com.sancov.di.data

import com.sancov.data.repository.OrderRepositoryImpl
import com.sancov.data.repository.order.OrderLocalStoreImpl
import com.sancov.data.repository.order.OrderStore
import com.sancov.domain.repository.OrderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OrderDataModule {
    @Binds
    abstract fun bindStore(impl: OrderLocalStoreImpl): OrderStore

    @Binds
    @Singleton
    abstract fun bindRepository(impl: OrderRepositoryImpl): OrderRepository
}