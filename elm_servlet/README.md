# Servlet
(DispatcherServlet)重写了一些方法，如 doGet() 或 doPost()，来处理客户端请求。在这些方法中，你可以读取请求参数、从数据库中获取数据、处理业务逻辑等  
后端调用顺序：Servlet——Controller——Service——Dao  

主要看Controller的需求
记得关注对于po类的输出，看是否需要重写toString
本人的几个问题：
1. 为什么要多此一举的分成Service 和 Dao 两个层，直接让Controller调用一个Dao层岂不是更加简洁？
2. 