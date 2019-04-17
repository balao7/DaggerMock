package it.cosenonjaviste.daggermock.builder

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class MyService {
    fun get(): String = "AAA"
}

class MyService2 {
    fun get(): String = "AAA"
}

class MainService @Inject constructor(val myService: MyService) {

    @Inject
    lateinit var myService2: MyService2

    fun get2(): String = myService.get() + myService2.get()

    fun get(): String = myService.get()
}

@Component(modules = [MyModule::class], dependencies = [MyComponent2::class])
interface MyComponent {
    fun mainService(): MainService

    @Component.Builder
    interface Builder {
        fun myComponent2(component2: MyComponent2): Builder

        fun myModule(module: MyModule): Builder

        fun build(): MyComponent
    }
}

@Component(modules = [MyModule2::class])
interface MyComponent2 {
    fun myService2(): MyService2

    @Component.Builder
    interface Builder {
        fun myModule2(module: MyModule2): Builder

        fun build(): MyComponent2
    }
}

@Module
class MyModule {
    @Provides
    fun provideMyService(): MyService = MyService()
}

@Module
class MyModule2 {
    @Provides
    fun provideMyService2(): MyService2 = MyService2()
}