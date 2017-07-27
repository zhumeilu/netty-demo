package com.ken.server.manager;

import org.jboss.netty.channel.Channel;

import com.ken.server.domain.Room;
import com.ken.server.domain.User;
import com.ken.server.domain.Zone;

public class UserManager {

	public static UserManager instance = new UserManager();

	public User joinZone(String userName,String zoneName,Channel channel){
		Zone zone = ZoneManager.instance.getZone(zoneName);
		User user = new User(userName,channel,zone);
		zone.addUser(user);
		return user;
	}

	public boolean joinRoom(User user,String roomName){
		Room room = user.getZone().getRoom(roomName);
		room.addUser(user);
		user.addRoom(room);
		return true;
	}

	public void lostCon(User user){
		user.getRoom().removeUser(user);
		user.getZone().removeUser(user);
		ChannelManager.instance.remove(user.getChannel());
	}
}
