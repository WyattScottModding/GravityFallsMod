package entity;

import java.util.ArrayList;

import net.minecraft.entity.EntityLivingBase;

public class EntityList extends ArrayList<EntityLivingBase>{

	
	public boolean containsEntity(EntityLivingBase entity) {

		for(int i = 0; i < this.size(); i++) {
			String string = this.get(i).toString();
			String entityString = entity.toString();

			if(string.substring(0, string.indexOf("[")).equals(entityString.substring(0, entityString.indexOf("[")))) {
				return true;
			}
		}
		
		return false;
	}
}