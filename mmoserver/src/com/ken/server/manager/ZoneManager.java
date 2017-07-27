package com.ken.server.manager;

import java.util.HashMap;
import java.util.Map;

import com.ken.server.domain.Zone;

import static com.ken.server.Config.ZONES;

public class ZoneManager {

	public static ZoneManager instance = new  ZoneManager();

	private final Map<String,Zone> zones;
	
	private ZoneManager(){
		zones = new HashMap<String, Zone>();
	}
	
	public Zone getZone(String name){
		return zones.get(name);
	}
	
	public void setUpZone(){
		for(String zoneName:ZONES){
			Zone zone = new Zone(zoneName);
			zones.put(zoneName, zone);
		}
	}
}
