# TestArchitectureComponents

Testing ground for various implementations of clean architecture in Android. Retrofit is used as a web backend. The `ViewModel` from the android architecture components library is used for a life-cycle observant view model. A data repository layer is accessed by the view model to access data independently of it being sourced from a web server or local cache. 

## Local Persistance
Primarily this testing ground was created to experiment with 
- `Realm` is the reference implementation - a NoSQL database implementation. It includes dynamic query building, flexible relationship modelling, and built-in data change observers. Drawbacks include issues related to multi-threaded access and needing to deep-copy objects in order to modify them in UI classes which are unaware of Realm (due to seperation of concerns). 
- `Room` is a google-authored SQLite ORM which makes use of compile-time checking SQL statements to map queries to objects. Ideal for simple queries and live data observation, it may be more difficult to work with complex queries or relationship modelling. Directly works with lifecycle aware livedata objects
- `DbFlow` is another SQLite ORM with dynamic query building and fast performance. 
- `ObjectBox` is a NoSQL database implementation. Includes dynamic query building and relationships, as well as some support for change observers. Biggest drawback at this time is relatively new product so less support / community assistance available. Must use `long` data type for ID fields for performance reasons, unfortunate for UUID-based client applications. 
