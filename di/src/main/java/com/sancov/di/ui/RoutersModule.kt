package com.sancov.di.ui

import com.sancov.order.OrderRouterImpl
import com.sancov.routers.OrderRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RoutersModule {
    @Binds
    abstract fun bindOrdersRouter(impl: OrderRouterImpl): OrderRouter
}