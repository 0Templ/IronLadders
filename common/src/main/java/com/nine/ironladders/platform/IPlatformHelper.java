package com.nine.ironladders.platform;

import com.nine.ironladders.platform.util.LoaderTarget;

import java.io.File;

public interface IPlatformHelper {

	LoaderTarget currentLoader();
	
	String getModName(String modId);

	File getConfigPath(String id);

}
