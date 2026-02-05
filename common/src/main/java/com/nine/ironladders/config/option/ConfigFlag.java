package com.nine.ironladders.config.option;

import com.nine.ironladders.config.ConfigSpec;

public enum ConfigFlag implements ConfigOption {
	
	SYNC {
		
		@Override
		public void apply(ConfigSpec spec) {
			spec.shouldSync = true;
		}
	},
	HIDE_CONSTRAINTS {
		
		@Override
		public void apply(ConfigSpec spec) {
			spec.hideConstraints = true;
			
		}
	}
	
	;
	

}
