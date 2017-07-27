package com.ken.server.extension;

import java.util.Set;

import com.ken.server.codec.ActionScriptObject;
import com.ken.server.domain.User;

public abstract class Extension {
	public void init(){};
	public void destroy(){};
	
	public abstract void request(String cmd,ActionScriptObject ao,User sender,String fromRoom);

	public void sendResponse(ActionScriptObject ao,Set<User> recipients){
		for(User user:recipients){
			if(user.getChannel().isConnected()){
				user.getChannel().write(ao.getObject());
			}
		}
	}
	
	public void sendResponse(ActionScriptObject ao,User recipient){
		recipient.getChannel().write(ao.getObject());
	}
	
	protected ActionScriptObject createAsObject(String cmd){
		ActionScriptObject ao = new ActionScriptObject();
		ao.putString("cmd", cmd);
		return ao;
	}
}
