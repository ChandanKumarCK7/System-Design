//
//
//
//
//
//
//
//
//tell how to just convert monolith to a microservice or things to consider
//    1- there has to be single source of truth that means that a DB cant be accessed directly by multiple microservices
//
//only one microservice can access DB directly, if another microservice wants to access some table in that db
//then that microservice should make call to the owner(microservice) of that particular db
//
//    there cant be a microservice created if only one other microservice will make calls to that,
//    a microservice can only be created if multiple other services wants to communicate else, integrate whatever that
//you intend to be creating a separte micrservice just as a part of existing microservice
//
//    small teams can only use monolith
//
//    always make sure that when u change function definition of some function in a microservice then also change the way
//    that will be caled in other microservice.
//
//tell the real challenges or exactly key things to considr designing instagram
//    1- related to the posts, who likes that and who comments that
//
//    just create separte tables such as Like, Post, Comments, Activity
//    and then make sure that they are related to each other with foreign keys
//
//    then microservices would be userFeed Service, follow service, Posts service
//    userFeed - we will have feed computed before user opens application to reduce loading time though cache cant store
//        each and every user data so what can be done is that store userFeed of users based on LRU(users) so that
//        whoever will be loggining in frequenctly will hve their feed computed already else feed will compute then and there that
//        can be slow sometimes.
//
//    Posts - that will have the DBs and request to this microservice will be made by userFeed
//    so posts microservice will return all recent posts made by all the people i follow
//
//    follow - that microservice will return only people i follow and people who are following me, that will have table of its own
//
//    in order to reduce load on load balancer just have a snapshor of instances running for Posts microservice so that
//    gateway can directly call that instance from snapshot, make sure to update snapshot every 10 seconds.
//

//
//
//
//
//
//
//
//    so how does that netflix usually seamlessly provide content without buffer
//    1- routing the request sent from client
//
//
//    lets say that the user will search for netflix.com then what happens is that ISP will route request to some cache that is between
//    that ISP and netflix server
//
//    usually these cahces are helpful, there are multiple ISPs in worls so for each country they probably provide a cache so that
//    when request will be made from india then the cache in india will be hit instead of cache in the america to improve latency
//
//    2- so how they provide seamless video after routing request to cache?
//    so as part of that cache they configure to send frames based on categorizing movie, so netflix usually categorizes movie as sparse/dense
//
//    if the movie is sparse - people will probably just skip in between so netflix sends few frames at a time
//    if the movie will be dense - people will be continously watched so netflix sends many frames that will be continusly there
//


//Factory Method -
//    create class such as vehicle as either abstract or as interface
//    then provide multiple implementations for that such as car, bus.class
//
//Singleton -
//    create class as singleton if you want single instance of that class to exist in runtime
//    perfect example would be database connection pool because if anyone wants to connect to db, the singleton class will be source
//    contact and that provides a connection
//
//Observer -
//    pattern can be used to notify user for changes
//    maybe that can be said like in zomato we will have order tracking that can be perfect example
//    just create an interface for that and implement for different functionalities
//    maybe just like think of OrderStatus that can be of noticaion panel view, in app view so create two classes
//    that implement OrderStatus interface.
//
//Decorator -
//    pattern can be used to just add topup features on top of concrete classes
//    such as discounts, offers in the amazon

//    Adapter
//if u want one entity to behave like another entity we use a mediator in between adapter
//    for ex - you want a cable of usb-c to work like hdmi cable then what can be done is use a HDMI adapter in between
//
//    or maybe there is a bird interface and a toyduck interface so bird can makr sound and touduck can make squaks
//    but if u forcefully want toyDuck to make sounds like Bird we have to use birdAdapter
//
//    refer - https://www.geeksforgeeks.org/adapter-pattern/
//    BirdAdapter implements ToyDuck{
//        Bird bird;
//        BirdAdapter(Bird bird){
//            this.bird = bird;
//        }
//
//        @override public void squeak(){
//            bird.makeSound();
//        }
//    }

