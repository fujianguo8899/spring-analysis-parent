1. 工程说明
viewhigh-demo-eureka-server             Eureka服务器
viewhigh-demo-eureka-provider           微服务，提供服务接口，注册到Eureka服务器上
viewhigh-demo-eureka-consumer           微服务，通过feign调用provider提供的服务，同样需要注册到Eureka上
viewhigh-demo-eureka-zuul               路由服务，访问可以通过路由服务转发到相应的微服务上

2. 启动顺序
server ---> provider --> consumer --> zuul
注意： zuul也可以不用，根据项目架构要求定

3. 访问方式
1）通过路由（zuul）访问, 如：
http://localhost:8000/zuul-provider/user/1
http://localhost:8000/zuul-consumer/demo/1

2）直接通过consumer访问, 如：
http://localhost:8081/demo/1
http://localhost:8081/demo/error/1