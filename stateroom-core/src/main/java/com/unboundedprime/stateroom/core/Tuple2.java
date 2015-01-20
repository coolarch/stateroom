package com.unboundedprime.stateroom.core;

class Tuple2<T,U> {
	
	private final T _item0;
	
	private final U _item1;
	
	Tuple2(final T item0, final U item1) {
		_item0 = item0;
		_item1 = item1;
	}
	
	public T getItem0() {
		return _item0;
	}

	public U getItem1() {
		return _item1;
	}
}
