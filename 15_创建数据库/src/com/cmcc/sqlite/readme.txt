1、自定义类MySqliteOpenHelper继承SqliteOpenHelper抽象类。注意要实现其中的含参数的额构造函数，因为其没有无参构造函数

2、通过自定义类MySqliteOpenHelper产生对象。该对象就获取了数据库的句柄，但此时并没有创建或者获取数据库

3、通过该对象的getWritableDatabase()方法或者getReadableDatabase()方法创建或者获取该数据库。
	这两个方法的区别是，当磁盘大小不够的时候，后者会返回一个只读的数据库

4、数据库创建成功后，会在/data/data/工程包/  下创建一个database文件夹，其中生成数据库文件和数据库缓存文件

5、类MySqliteOpenHelper中的onCreate()方法只在数据库第一次创建的时候调用，一般用来创建表

