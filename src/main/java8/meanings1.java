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
