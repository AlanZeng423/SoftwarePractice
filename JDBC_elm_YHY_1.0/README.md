# JDBC—8.25更新
### 一、po层
出现位置为数据库数据，用来存储数据库提取的数据；只存储数据，不包含数据操作
1. 主要是对应数据库的各个表项的后端实现
2. 包括了get 和 set 两个主要函数
3. 必要时重写toString函数

### 二、dao层——数据访问层(未实现)
DAO层主要是做数据持久层的工作，用来对应数据库实体，负责与数据库进行联络的一些任务都封装在此  
1. 封装一些数据库的增删改查操作（有几个表dao层就有几个文件）
2. 正常来说Dao层，只负责接收最终的sql语句,具体到某一张表的增删查改。（此版本elm将service层也放进了dao层）
3. 实现函数定义与实现的分离

### 三、view层——视图层(未实现)
就本项目来说，view层就是实现一些功能，将面对数据库的传给dao层   
（view——dao）

### 四、JDBC工具类（Util类）
Util类，一般是无状态的，只包含静态方法。使用时无需创建类的实例
1. 获取Connection，实现和数据库的链接
2. 关闭资源，关闭和数据库的链接

### 附加1：针对数据库操作的几个工具
1. connection，数据库连接，主要用到函数
   1. Class.forName("com.mysql.jdbc.Driver")——注册驱动
   2. DriverManager.getConnection(数据库地址url,用户名,密码)——获取数据库连接
2. PreparedStatement，预编译类，主要用到函数
   1. PreparedStatement pst = connection.prepareStatement(sql)——给出一个可能缺少参数的sql语句进行预编译
   2. pst.setString(第几个参数, 添加的参数)——为预存的sql语句添加参数
   3. pst.executeQuery()——执行查询，每运行一次=完成一次查询
   4. pst.executeUpdate()——执行update/insert/delete，中的任意一个
3. ResultSet，结果集  
常和预编译类的pst一起使用  
ResultSet resultSet = pst.executeQuery()——将查询结果放入resultset中
4. 注意：上述三个工具在关闭数据库连接的时候也需要关闭

### 附加2：service层——业务层
决定对数据库进行怎样的操作（数据），具体的操作通过调用对应的dao层来实现

### 附加3：函数和实现的分离
对于dao层 和 service层这种比较复杂的层级，进行高度解耦合  
先写一个interface，这个接口以service/dao为后缀表示这是一个service/dao接口，在这个类里定义好我们需要的方法，然后写实现类去实现这个接口里的方法

### 附加4：一个事务中进行多个数据库的操作
需要执行connection.setAutoCommit(false);将数据库连接设置为手动提交模式  
使得如果操作时发生异常，通过connection.rollback();实现回滚到当前事务所有sql语句执行之前  
如果没有异常，需要connection.commit();完成提交
（如果不设置为手动提交，出错时系统会自动回滚到当前sql语句执行前的状态）
