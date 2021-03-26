# MovieApp
### in thes project i am using 

"kotlin" as the preferred language for android.

"Coroutine" framework in Kotlin to make asynchronous calls in a more readable fashion.

"Koin" dependency injection library that is very easy to use as compare to dagger or hilt.

"ViewModel" A jetpack component to architect app with MVVM.

"Live Data" It's a life cycle aware observable data holder.

"Retrofit" It's the most famous web service calling library which we will use in the app to fetch data from web API.

"Navigation component" A jetpack component to implement navigation in a simple way

### using MVVM Architecteur 

Model-View-ViewModel (ie MVVM) is a template of a client application architecture, is an alternative to MVC and MVP patterns. Its concept is to separate data presentation logic from business logic by moving it into particular class for a clear distinction. You can also check MVP

Why Promoting MVVM VS MVP:

ViewModel has Built in LifeCycleOwerness, on the other hand Presenter not, and you have to take this responsiblty in your side.
ViewModel doesn't have a reference for View, on the other hand Presenter still hold a reference for view, even if you made it as weakreference.
ViewModel survive configuration changes, while it is your own responsiblities to survive the configuration changes in case of Presenter. (Saving and restoring the UI state)

### Repository pattern

The Repository pattern is used to decouple the business logic and the data access layers in your application
