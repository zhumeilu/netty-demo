package com.ken.server.manager;

import java.util.HashMap;
import java.util.Map;

import com.ken.server.extension.ChatExtension;
import com.ken.server.extension.Extension;
import com.ken.server.extension.SystemExtension;

public class ExtensionManager {

	private final Map<String,Extension> extensions;

	public static final ExtensionManager instance = new ExtensionManager();
	
	private ExtensionManager(){
		extensions = new HashMap<String,Extension>();
		extensions.put("sys",new SystemExtension());
		extensions.put("chat",new ChatExtension());
	}

	public Extension getExtension(String name){
		return extensions.get(name);
	}
}
