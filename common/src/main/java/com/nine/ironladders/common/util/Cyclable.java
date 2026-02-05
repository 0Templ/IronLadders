package com.nine.ironladders.common.util;

public interface Cyclable<T extends Enum<T>> {
	
	@SuppressWarnings("unchecked")
	default T cycle(boolean forward) {
		T self = (T) this;
		T[] values = self.getDeclaringClass().getEnumConstants();
		
		int total = values.length;
		int delta = forward ? 1 : -1;
		
		int nextIndex = (self.ordinal() + delta + total) % total;
		
		return values[nextIndex];
	}
}