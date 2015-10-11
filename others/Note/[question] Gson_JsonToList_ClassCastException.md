#Gson:
##将json转成list<MessageBean>时遇到问题

#####如果调用方法将json转成list<MessageBean>,将会抛出com.google.gson.internal.StringMap cannot be cast to com.example.lzchat.bean.MessageBean异常

	public static <T> List<T> JsonToList(String json, Class<T> cls) {
		Gson gson = new Gson();
		List<T> list = gson.fromJson(json, new TypeToken<List<T>>(){}.getType());
		return list;
	}

#####而直接使用,则正常
	Gson gson = new Gson();
	List<MessageBean> retList = gson.fromJson(listToJson,new TypeToken<List<MessageBean>>(){}.getType());

#####问题解析:
**初步分析:** new TypeToken<List<T>>(){}.getType().在运行时泛型已经被擦除，T已经是Object类。