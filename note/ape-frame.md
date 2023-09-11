## mybatis-plus模块

### po，dto区别

po只和数据层进行交互，dto可以实现多个po聚合，进行数据体的转换

### 元数据处理

在插入数据的时候会有一些create_by，create_time等一些数据，手动进行填充会使代码冗余，且没有实际意义，所以需要简化自己的处理方式。

当处理相同逻辑的数据库字段的插入和更新时候，可以继承MetaObjectHandler类，实现insert，update两个方法，

```java
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createBy", String.class, "jingdianjichi");
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictInsertFill(metaObject, "deleteFlag", Integer.class, 0);
        this.strictInsertFill(metaObject, "version", Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateBy", String.class, "jingdianjichi");
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }
}
```

附加：version字段为后续的乐观锁做准备

### 集成druid监控

```yml
spring:
	datasource:
	 druid:
      initial-size: 20
      min-idle: 20
      max-active: 100
      max-wait: 60000
      stat-view-servlet:
        enabled: true
        url-pattern: /druid/*
        login-username: admin
        login-password: 123456
      filter:
        stat:
          enabled: true
          log-slow-sql: true
          slow-sql-millis: 2000 #设置超过2秒为慢查询
        wall: 
          enabled: true #启动sql防火墙          
```

可以通过8081/druid/login.html进行登录监控系统查看sql执行情况

![image-20230908111812630](ape-frame.assets/image-20230908111812630.png)

### 集成mp插件

集成公用拦截器插件

```java
package com.jingdianjichi.inteceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.defaults.DefaultSqlSession.StrictMap;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.*;

@Intercepts(value = {
        @Signature(args = {Statement.class, ResultHandler.class}, method = "query", type = StatementHandler.class),
        @Signature(args = {Statement.class}, method = "update", type = StatementHandler.class),
        @Signature(args = {Statement.class}, method = "batch", type = StatementHandler.class)})
public class SqlBeautyInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        long startTime = System.currentTimeMillis();
        StatementHandler statementHandler = (StatementHandler) target;
        try {
            return invocation.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long sqlCost = endTime - startTime;
            BoundSql boundSql = statementHandler.getBoundSql();
            String sql = boundSql.getSql();
            Object parameterObject = boundSql.getParameterObject();
            List<ParameterMapping> parameterMappingList = boundSql.getParameterMappings();
            sql = formatSql(sql, parameterObject, parameterMappingList);
            System.out.println("SQL： [ " + sql + " ]执行耗时[ " + sqlCost + "ms ]");
        }
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private String formatSql(String sql, Object parameterObject, List<ParameterMapping> parameterMappingList) {
        if (sql == "" || sql.length() == 0) {
            return "";
        }
        sql = beautifySql(sql);
        if (parameterObject == null || parameterMappingList == null || parameterMappingList.size() == 0) {
            return sql;
        }
        String sqlWithoutReplacePlaceholder = sql;
        try {
            if (parameterMappingList != null) {
                Class<?> parameterObjectClass = parameterObject.getClass();
                if (isStrictMap(parameterObjectClass)) {
                    StrictMap<Collection<?>> strictMap = (StrictMap<Collection<?>>) parameterObject;
                    if (isList(strictMap.get("list").getClass())) {
                        sql = handleListParameter(sql, strictMap.get("list"));
                    }
                } else if (isMap(parameterObjectClass)) {
                    Map<?, ?> paramMap = (Map<?, ?>) parameterObject;
                    sql = handleMapParameter(sql, paramMap, parameterMappingList);
                } else {
                    sql = handleCommonParameter(sql, parameterMappingList, parameterObjectClass, parameterObject);
                }
            }
        } catch (Exception e) {
            return sqlWithoutReplacePlaceholder;
        }
        return sql;
    }


    private String handleCommonParameter(String sql, List<ParameterMapping> parameterMappingList,
                                         Class<?> parameterObjectClass, Object parameterObject) throws Exception {
        Class<?> originalParameterObjectClass = parameterObjectClass;
        List<Field> allFieldList = new ArrayList<>();
        while (parameterObjectClass != null) {
            allFieldList.addAll(new ArrayList<>(Arrays.asList(parameterObjectClass.getDeclaredFields())));
            parameterObjectClass = parameterObjectClass.getSuperclass();
        }
        Field[] fields = new Field[allFieldList.size()];
        fields = allFieldList.toArray(fields);
        parameterObjectClass = originalParameterObjectClass;
        for (ParameterMapping parameterMapping : parameterMappingList) {
            String propertyValue = null;
            if (isPrimitiveOrPrimitiveWrapper(parameterObjectClass)) {
                propertyValue = parameterObject.toString();
            } else {
                String propertyName = parameterMapping.getProperty();
                Field field = null;
                for (Field everyField : fields) {
                    if (everyField.getName().equals(propertyName)) {
                        field = everyField;
                    }
                }
                field.setAccessible(true);
                propertyValue = String.valueOf(field.get(parameterObject));
                if (parameterMapping.getJavaType().isAssignableFrom(String.class)) {
                    propertyValue = "\"" + propertyValue + "\"";
                }
            }
            sql = sql.replaceFirst("\\?", propertyValue);
        }
        return sql;
    }

    private String handleMapParameter(String sql, Map<?, ?> paramMap, List<ParameterMapping> parameterMappingList) {
        for (ParameterMapping parameterMapping : parameterMappingList) {
            Object propertyName = parameterMapping.getProperty();
            Object propertyValue = paramMap.get(propertyName);
            if (propertyValue != null) {
                if (propertyValue.getClass().isAssignableFrom(String.class)) {
                    propertyValue = "\"" + propertyValue + "\"";
                }

                sql = sql.replaceFirst("\\?", propertyValue.toString());
            }
        }
        return sql;
    }

    private String handleListParameter(String sql, Collection<?> col) {
        if (col != null && col.size() != 0) {
            for (Object obj : col) {
                String value = null;
                Class<?> objClass = obj.getClass();
                if (isPrimitiveOrPrimitiveWrapper(objClass)) {
                    value = obj.toString();
                } else if (objClass.isAssignableFrom(String.class)) {
                    value = "\"" + obj.toString() + "\"";
                }

                sql = sql.replaceFirst("\\?", value);
            }
        }
        return sql;
    }

    private String beautifySql(String sql) {
        sql = sql.replaceAll("[\\s\n ]+", " ");
        return sql;
    }

    private boolean isPrimitiveOrPrimitiveWrapper(Class<?> parameterObjectClass) {
        return parameterObjectClass.isPrimitive() || (parameterObjectClass.isAssignableFrom(Byte.class)
                || parameterObjectClass.isAssignableFrom(Short.class)
                || parameterObjectClass.isAssignableFrom(Integer.class)
                || parameterObjectClass.isAssignableFrom(Long.class)
                || parameterObjectClass.isAssignableFrom(Double.class)
                || parameterObjectClass.isAssignableFrom(Float.class)
                || parameterObjectClass.isAssignableFrom(Character.class)
                || parameterObjectClass.isAssignableFrom(Boolean.class));
    }

    /**
     * 是否DefaultSqlSession的内部类StrictMap
     */
    private boolean isStrictMap(Class<?> parameterObjectClass) {
        return parameterObjectClass.isAssignableFrom(StrictMap.class);
    }

    /**
     * 是否List的实现类
     */
    private boolean isList(Class<?> clazz) {
        Class<?>[] interfaceClasses = clazz.getInterfaces();
        for (Class<?> interfaceClass : interfaceClasses) {
            if (interfaceClass.isAssignableFrom(List.class)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 是否Map的实现类
     */
    private boolean isMap(Class<?> parameterObjectClass) {
        Class<?>[] interfaceClasses = parameterObjectClass.getInterfaces();
        for (Class<?> interfaceClass : interfaceClasses) {
            if (interfaceClass.isAssignableFrom(Map.class)) {
                return true;
            }
        }
        return false;
    }

}
```

在配置类中将拦截器插件导入进来

```java
 	@Bean
    public SqlBeautyInterceptor sqlBeautyInterceptor() {
        return new SqlBeautyInterceptor();
    }

```

#### 优化

使用Conditional注解

在一定情况下，对于一些业务方可能不需要一些插件，可以使用@Conditional系列注解进行指定特定情况下开启

```java
  	@Bean
    @ConditionalOnProperty(name = {"sql.beauty.show"}, havingValue = "true", matchIfMissing = true)
    public SqlBeautyInterceptor sqlBeautyInterceptor() {
        return new SqlBeautyInterceptor();
    }
```

### 共有实体抽取

需要实现序列化接口

```java
package com.jingdianjichi.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Integer deleteFlag;

    @TableField(fill = FieldFill.INSERT)
    private Integer version;

}
```

 	

### pageresult

```java
@Data
public class PageResult<T> implements Serializable {

    private Long total;

    private Long size;

    private Long current;

    private Long pages;

    private List<T> records = Collections.emptyList();

    public void loadData(IPage<T> pageData){
        this.setCurrent(pageData.getCurrent());
        this.setPages(pageData.getPages());
        this.setSize(pageData.getSize());
        this.setTotal(pageData.getTotal());
        this.setRecords(pageData.getRecords());
    }

}

```

### Param注解

![image-20230908170548501](ape-frame.assets/image-20230908170548501.png)

当我们打上了@Param注解，需要对应在mappering文件中加上前缀

![image-20230908192141258](ape-frame.assets/image-20230908192141258.png)

### 抽取公共page类

#### 分页pageRequest

抽取分页算法所需要的两个参数为公共类

```java
@Setter
public class PageRequest {

    private Long pageNo = 1L;

    private Long pageSize = 10L;

    //进行参数判断
    public Long getPageNo(){
        if(pageNo == null || pageNo < 1){
            return 1L;
        }
        return pageNo;
    }

    public Long getPageSize(){
        if(pageSize == null || pageSize < 1 || pageSize > Integer.MAX_VALUE){
            return 10L;
        }
        return pageSize;
    }

}

```

### 集成mapstruct

beansUtils.copyProperties效率比较低 ，所以我们引入mapStruct

创建convert，定义一个接口

```java
//Mapper属于org.mapstruct
@Mapper
public interface SysUserConverter {
    SysUserConverter INSTANCE = Mappers.getMapper(SysUserConverter.class);
    //如果属性名不一样的情况可以使用Mapping注解来进行字段映射
    @Mapping(source="age", target="age1")
    SysUser convertReqToSysUser(SysUserReq sysUserReq);
}
//使用,将req对象映射到user对象 	 
SysUser  sysUser = SysUserConverter。INSTANCE.convertReqToSysUser(sysUserReq);

```





## web模块

### 全局异常处理

#### 异常处理注解

@[RestControllerAdvice](https://so.csdn.net/so/search?q=RestControllerAdvice&spm=1001.2101.3001.7020)主要用精简客户端返回异常，它可以捕获各种异常



处理步骤

配置异常拦截器

```java
@RestControllerAdvice
public class ExceptionAdaptController {

    @ExceptionHandler({RuntimeException.class})
    public Result runTimeException(RuntimeException exception){
        exception.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler({Exception.class})
    public Result exception(Exception exception){
        exception.printStackTrace();
        return Result.fail();
    }
}
 	
```



#### 处理前后对比

处理前

```java
int i = 1/0;
```

对于前端来说，返回的信息，无法识别

![image-20230908114333462](ape-frame.assets/image-20230908114333462.png)

处理后

![image-20230908115045001](ape-frame.assets/image-20230908115045001.png)

## 集成Swagger模块

swagger具有代码侵入性，小公司会使用

优化：通过配置文件的形式来对Swagger进行定制。



```java
@Configuration
//swagger开关
@EnableSwagger2
public class SwaggerConfig {
//自动注入
    @Autowired
    private SwaggerInfo swaggerInfo;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerInfo.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerInfo.getTitle())
                .contact(new Contact(swaggerInfo.getContactName(),
                        swaggerInfo.getContactUrl(),
                        swaggerInfo.getEmail()))
                .version(swaggerInfo.getVersion())
                .description(swaggerInfo.getDescription())
                .build();
    }

}
```

```java
package com.jingdianjichi.swagger.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
//通过配置的方式填充属性
@ConfigurationProperties(prefix = "swagger")
public class SwaggerInfo {

    private String basePackage;

    private String title;

    private String contactName;

    private String contactUrl;

    private String email;

    private String version;

    private String description;

    public String getContactUrl() {
        return contactUrl;
    }

    public void setContactUrl(String contactUrl) {
        this.contactUrl = contactUrl;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

```

## 集成redis

新增配置：

```xml
spring:
 redis:
    host: 117.78.51.210
    port: 6379
    database: 0
#配置连接池
    lettuce:
      pool:
        max-active: 20
        max-idle: 8
        max-wait: -1
        min-idle: 0
    password: jingdianjichi
```

配置redis序列化器以及RedisTemplate

```java
@Configuration
public class RedisConfig {

    //RedisTemplate
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer());
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(pair).entryTtl(Duration.ofSeconds(10));
        return new RedisCacheManager(redisCacheWriter,defaultCacheConfig);
    }
//设置序列化器
    private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer(){
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jsonRedisSerializer.setObjectMapper(objectMapper);
        return jsonRedisSerializer;
    }

}
```

