#Mysql
##���ݿ�ʹ��ʱ�������Ӳ�����

#####���Ӳ����� Data source rejected establishment of connection,  message from server: "Too many connections"

#####�������:
**��������:** �趨�Ĳ���������̫�� / ���insert,update����û�йر�session / �趨�Ŀ���ʱ��̫��

**�������:** 

- **1.ֹͣmysql** (�ܾ�����:����Ա�������cmd)

		net stop mysql		//ֹͣ

- **2.��my.ini�ļ�,�� `[mysqld]` ���������������**
- 
  	# max_connections�޸����������Ϊ1000(ע�����޸�)
	max_connections=1000
	# max_user_connections����ÿ�û����������Ϊ500 
	max_user_connections=500
	# wait_timeout��ʾ200��󽫹رտ��У�IDLE�������ӣ����Ƕ����ڹ��������Ӳ�Ӱ�졣
	wait_timeout=200
 
- **3.����mysql**

	net start mysql		//����

- **4.�鿴�Ƿ��޸ĳɹ�**
- 
	mysqladmin -uroot -p variables
	Password:

	//���Կ���������˵���޸ĳɹ�
	| max_connections                 | 1000
	| max_user_connections            | 500
	| wait_timeout                    | 200