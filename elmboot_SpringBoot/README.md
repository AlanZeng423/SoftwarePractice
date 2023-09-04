# Springboot版本
## 更新记录：
#### 8.31 & 9.1
1. 在User 的系列文件中新增了  
    updatePoint ——实现支付成功后对数据库中积分的更新  
    getPointById ——实现Orders页面对于积分的实时获取（因为只有在Login时会获取一次 DB 中User的数据，而point是需要实时更新的）
2. 在Orders 的系列文件中新增了  
    updateOrder ——实现支付成功后对属性orderState的更新
3. 对xml文件中部分 SQL 操作进行更新(于9.2来看，似乎没有什么必要)  
    CartMapper中更新listCart的查询
    OrdersMapper中更新getOrdersById的查询
#### 9.2
1. 在Orders 的系列文件中新增了  
   updateOrders ——解决从订单列表中跳转到支付界面时，因为没有再次创建订单，而导致的使用积分但是没有保存到orderTotal中的问题
#### 9.3
截至目前来说，所有的需求（除了新技术应用）都已经实现  
接下来要实现关于首页商家推荐的需求，下面是需要完成的事情：  
1. 数据库Business表中添加一个views——访问量，orderQuantity——订单量(over)
2. index页面  
    调用一个查询all商家的函数，根据不同的标准——点击量、下单量、两者加权，进行排序  
    推荐商家要有一个指向BusinessInfo的路由
3. BusinessInfo页面  
    更新views
4. Payment页面/Paying页面  
    更新orderQuantity
#### 9.4
完成了首页推荐部分的后端部分函数  
listBusinessSortByView——访客最多  
listBusinessSortByOrderQuantity——销量最多  
listBusinessSortByAverage——综合排序（各50%）  
updateViews——更新访问量  
updateOrderQuantity——更新销量  



# Springboot的理解
相比于之前的版本，Springboot不需要在DBUnit中寻找连接数据库的方式  
后端程序的执行顺序变为：  
前端调用对应的 Controller - Service - Mapper  
修改属性的时候一定把mapper也修改了（特别是有xml的情况下）  
## mapper！！！
Mapper是一个Mybatis的接口，其中的函数用于直接操作数据库  
使用 ‘@’ + ‘增删改查’（表明操作种类）+（“对应的 SQL 语句”）  
对于比较复杂 or 要求比较多的 SQL 语句可以写在对应的XML文件中
### 非XML SQL 操作
##### 1. ‘@’ + ‘增删改查’
是 MyBatis 提供的注解之一，用于标记一个方法为对应的 SQL 操作。注解的参数是一个对应的 SQL 语句。
##### 2. 例子如下：  
@Select("select * from user where userId=#{userId} and password=#{password}")  
public User getUserByIdByPass(User user);  
除了上述提到的注解；SQL 语句中的#(userId) 和 #{password}是对应的占位符，将从下面函数的传参中获取对应的数值，所以说，传参是有一定要求的
### XML SQL 操作
##### 了解XML文件的结构（特指非公共部分）
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