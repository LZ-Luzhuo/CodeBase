#Gson:
##��jsonת��list<MessageBean>ʱ��������

#####������÷�����jsonת��list<MessageBean>,�����׳�com.google.gson.internal.StringMap cannot be cast to com.example.lzchat.bean.MessageBean�쳣

	public static <T> List<T> JsonToList(String json, Class<T> cls) {
		Gson gson = new Gson();
		List<T> list = gson.fromJson(json, new TypeToken<List<T>>(){}.getType());
		return list;
	}

#####��ֱ��ʹ��,������
	Gson gson = new Gson();
	List<MessageBean> retList = gson.fromJson(listToJson,new TypeToken<List<MessageBean>>(){}.getType());

#####�������:
**��������:** new TypeToken<List<T>>(){}.getType().������ʱ�����Ѿ���������T�Ѿ���Object�ࡣ