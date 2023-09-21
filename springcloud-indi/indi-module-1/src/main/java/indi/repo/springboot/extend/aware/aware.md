## Aware 感知类拓展
***

| BeanNameAware                  | 获得到容器中Bean的名称                              |
|--------------------------------|-----------------------------------------------------|
| BeanFactoryAware               | 获得当前bean factory，这样可以调用容器的服务        |
| ApplicationContextAware        | 获得当前application context，这样可以调用容器的服务 |
| MessageSourceAware             | 获得message source这样可以获得文本信息              |
| ApplicationEventPublisherAware | 应用事件发布器，可以发布事件                        |
| ResourceLoaderAware            | 获得资源加载器，可以获得外部资源文件                |
| EnvironmentAware               | 能够获取配置文件中的配置项                          |
| BeanNameAware                  | 获取到bean名称                                      |