## Mybatis Sample in Spring Project

### Features

#### Nested class

Something like:

```java
public class User {
    private String id;
    private String name;
    private Group group;
}

public class Group {
    private String id;
    private String name;
}
```

Then set result map to get:

```xml
<resultMap id="user" type="User">
    <result column="group_id" property="group.id"/>
    <result column="group_name" property="group.name"/>
</resultMap>
```

#### Enum class

Like Level class in User class, some information save number type in database.

Define TypeHandler for the enum class, and specify that in mybatis config file.

```xml
<typeHandlers>
    <typeHandler handler="org.weixing.spring.mybatis.LevelTypeHandler"/>
</typeHandlers>
```
