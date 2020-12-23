package de.maltesermailo.impl.minigame;

import java.lang.reflect.Type;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class LocationJSONSerializer implements JsonSerializer<Location>, JsonDeserializer<Location> {

	@Override
	public JsonElement serialize(Location loc, Type typeOfLoc, JsonSerializationContext context) {
		JsonObject object = new JsonObject();
		
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		
		float yaw = loc.getYaw();
		float pitch = loc.getPitch();
		
		String world = loc.getWorld().getName();
		
		object.addProperty("x", x);
		object.addProperty("y", y);
		object.addProperty("z", z);
		object.addProperty("yaw", yaw);
		object.addProperty("pitch", pitch);
		object.addProperty("world", world);
		
		return object;
	}

	@Override
	public Location deserialize(JsonElement obj, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		final JsonObject object = obj.getAsJsonObject();
		
		double x = object.get("x").getAsDouble();
		double y = object.get("y").getAsDouble();
		double z = object.get("z").getAsDouble();
		
		float yaw = object.get("yaw").getAsFloat();
		float pitch = object.get("pitch").getAsFloat();
		
		String worldName = object.get("world").getAsString();
		
		World world = Bukkit.getWorld(worldName);
		
		if(world == null) {
			throw new NullPointerException();
		}
		
		Location locObj = new Location(world, x, y, z, yaw, pitch);
		
		return locObj;
	}

}
