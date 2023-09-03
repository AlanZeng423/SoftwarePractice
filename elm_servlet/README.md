# Servlet
(DispatcherServlet)重写了一些方法，如 doGet() 或 doPost()，来处理客户端请求。在这些方法中，你可以读取请求参数、从数据库中获取数据、处理业务逻辑等  
后端调用顺序：Servlet——Controller——Service——Dao  
代码的执行逻辑和JDBC项目是相同的，理解一下Serclet就行了

## 积分系统_后端
1. 更改数据库，在user的部分添加一个属性——points(over)
2. 对应的添加User中的 变量和get set (over)
3. 新建user时记得给point赋值为0(over)
4. UserController实现对积分的加减(over)
5. OrdersController也要实现对于订单状态的修改
## 积分系统_前端
主要是两个页面，order页面 和 Payment页面
1. order页面 在费用的下面显示积分，同时给出一个按钮，决定是否使用积分，以及使用多少积分（每一百分减免1元）(over)
2. Payment页面 展开的时候给出一个积分的使用情况 (over)
3. Paying页面 支付后要有积分更新的操作 (over)