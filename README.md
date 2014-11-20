hbase-fs-rest
=============

[hbase-fs](http://leeychee.github.io/hbase-fs) is a InputStream/OutputStream lib for reading & writing file 
on [HBase](http://hbase.apache.org). It's not very convenient for end-user. So, we build a HTTP-REST Service to 
simplify the operation.

Basically, we use HEAD, GET, PUT, POST, DELETE to manipulate files. The request like this:
> curl -X HEAD http://${host}:${port}/${version}/fs/${identifier}?${paras}

 OP     | PARAS          | DESC                              |
--------|----------------|-----------------------------------|
HEAD    |                | Test if the file exists, 404 or 200|
GET     |                | Get the file                      |
GET     | details=true   | Get json of the file details      |
PUT     | desc="blabla"  | add new file desc                 |
POST    | desc="blabla"  | add new file with desc            |
DELETE  |                | delete the file                   |


根据REST风格的规范我们如下设计URL
对应URL一栏前缀例如:http://serverhost:8080/v1

HTTP动词  |  对应URL | 描述  |
--------|----------------|-----------------------------------|
HEAD    | files/{fileID}  | 查看文件是否存在,返回状态: 404 或者 200|
GET     | files/{fileID} | 获得指定文件(Fiel MD5)                    |
GET     | files/{fileID}?details=true   | 获得指定文件的元数据信息   |
PUT     | files/{fileID}  | 修改文件描述                 |
POST    | files/          | 新增文件            |
DELETE  | files/{fileID}  |  删除指定文件(Fiel MD5)       |
