package promise.app_base.data.net

import dagger.Module
import dagger.Provides
import promise.app_base.scopes.AppScope
import promise.data.net.converters.GsonConverterFactory
import promise.data.net.net.FastParserEngine

@Module
object BaseApi {

  @JvmStatic
  @Provides
  @AppScope
  fun provideFastParserEngine(): FastParserEngine = FastParserEngine.Builder()
      .baseUrl("https://jsonplaceholder.typicode.com")
      .addConverterFactory(GsonConverterFactory.create())
      .build()

  @JvmStatic
  @Provides
  @AppScope
  fun provideAuthApi(fastParserEngine: FastParserEngine): AuthApi =
      fastParserEngine.create(AuthApi::class.java)

  @JvmStatic
  @Provides
  @AppScope
  fun provideTodoApi(fastParserEngine: FastParserEngine): TodoApi =
      fastParserEngine.create(TodoApi::class.java)
}