import zlib
import pymysql

conn = pymysql.connect(host="127.0.0.1", user="****",password="****",database="****",charset="utf8")
cursor = conn.cursor()
for i in range(1,700000000):
    s = zlib.crc32(str(i).encode('utf8'))
    first = hex(s)[2]
    if(len(hex(s))<10):
        first = "0"
    try:
        sql = "insert into BilibiliCrc32b"+first+"(id,crc32b) values('{}','{}')"
        cursor.execute(sql.format(i,s))
        conn.commit()
    except:
      sql = "update BilibiliCrc32b"+first+" set id = concat(id,'{}') where crc32b = '{}'"
      cursor.execute(sql.format(','+str(i), s))
      conn.commit()

