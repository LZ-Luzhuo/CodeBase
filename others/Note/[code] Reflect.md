# 反射
---


 - 获得类的泛型

	 	public abstract class ObjectCallback<T> {
			private final Class<T> clazz;
			
			@SuppressWarnings("unchecked")
			public ObjectCallback(){
				//通过泛型,获得当前类的类型
				ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
				clazz = (Class<T>) type.getActualTypeArguments()[0];
			}
		}