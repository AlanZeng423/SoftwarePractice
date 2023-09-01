# Springboot版本
相比于之前的版本，Springboot不需要在DBUnit中寻找连接数据库的方式  
后端程序的执行顺序变为：  
前端调用对应的 Controller - Service - Mapper  
修改属性的时候一定把mapper也修改了（特别是有xml的情况下）  
## mapper！！！
Mapper是一个Mybatis的接口，其中的函数用于直接操作数据库  
使用 ‘@’ + ‘增删改查’（表明操作种类）+（“对应的 SQL 语句”）  
对于比较复杂 or 要求比较多的 SQL 语句可以写在对应的XML文件中
### 非XML SQL 操作
###### 1. ‘@’ + ‘增删改查’
是 MyBatis 提供的注解之一，用于标记一个方法为对应的 SQL 操作。注解的参数是一个对应的 SQL 语句。
###### 2. 例子如下：  
@Select("select * from user where userId=#{userId} and password=#{password}")  
public User getUserByIdByPass(User user);  
除了上述提到的注解；SQL 语句中的#(userId) 和 #{password}是对应的占位符，将从下面函数的传参中获取对应的数值，所以说，传参是有一定要求的
### XML SQL 操作
###### 了解XML文件的结构（特指非公共部分）
1. 首先列出Mapper所对应的数据库的属性（一般来说一个po 会有一个相应的mapper）  
    mapper标签—— namespace 指定了与之关联的mapper接口  
    resultMap标签—— type为对应的Java类，id就是之后 SQL 语句执行后返回的标识对象  
    id & result标签—— column 属性指定数据库列的名称，而 property 属性指定 Java 类中对应的属性名称。其中id为主键列  
    association标签——属于是关联关系的映射，property 属性指定它在当前类中的属性名，javaType 属性指定关联对象的类，
select 属性用于查询关联表中对象的数据（该select在关联对象的mapper中是一个函数），column 是关联的列名（外键）  
    以resultMap开始，以/resultMap结束，且包裹id & result
2. 之后就是对应的一个个 SQL　语句
    其中id属性对应mapper中的函数，resultMap映射的结果  
    where 标签—— SQL 语句中的WHERE  
    if 标签—— WHERE的动态条件，如果if中的条件满足则加入 if  /if 中的语句