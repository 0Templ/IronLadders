package com.nine.ironladders.compat.top;

import com.nine.ironladders.config.ILConfig;
import mcjty.theoneprobe.api.ITheOneProbe;

import java.util.function.Function;

public class TheOneProbeSetup implements Function<ITheOneProbe, Void> {
	
	@Override
	public Void apply(ITheOneProbe top) {
		top.registerProvider(new TOPBlockComponentProvider());
		
		return null;
	}
	
}