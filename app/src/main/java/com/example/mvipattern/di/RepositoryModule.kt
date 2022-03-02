package com.example.mvipattern.di
import com.example.mvipattern.repository.MainRepository
import com.example.mvipattern.retrofit.BlogRetrofit
import com.example.mvipattern.retrofit.NetworkMapper
import com.example.mvipattern.room.BlogDao
import com.example.mvipattern.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        blogDao: BlogDao,
        retrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ): MainRepository {
        return MainRepository(blogDao, retrofit, cacheMapper, networkMapper)
    }
}