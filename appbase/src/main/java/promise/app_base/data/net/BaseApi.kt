package promise.app_base.data.net

import dagger.Module
import dagger.Provides
import promise.app_base.scopes.AppScope
import promise.data.net.converters.GsonConverterFactory
import promise.data.net.net.FastParserEngine

@Module
class BaseApi {

  @Provides
  @AppScope
  fun provideFastParserEngine(): FastParserEngine = FastParserEngine.Builder()
      .baseUrl("https://jsonplaceholder.typicode.com")
      .addConverterFactory(GsonConverterFactory.create())
      .build()

  @Provides
  @AppScope
  fun provideAuthApi(fastParserEngine: FastParserEngine): AuthApi =
      fastParserEngine.create(AuthApi::class.java)
}