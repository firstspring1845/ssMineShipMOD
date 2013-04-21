package net.minecraft.ssMineShipMOD;

import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;

public class worldevent
{
	@ForgeSubscribe
	public void entityupdate(EntityJoinWorldEvent e)
	{
		if(ssMineShipMOD.インスタンス.エンティティをスポーンさせない)
			e.setCanceled(true);

	}
/*
	@ForgeSubscribe
	public void entityupdate(EntityEvent.CanUpdate e)
	{
		if(e.entity.worldObj.provider.dimensionId == ssMineShipMOD.インスタンス.データ用ディメンジョン)
			e.canUpdate = false;

	}
*/
	@ForgeSubscribe
	public void worldunload(WorldEvent.Save l)//データ用ワールドがアンロードされたら、入れ替える
	{
		if(l.world != null&&l.world instanceof WorldServer&&l.world instanceof mineshipWorld&&l.world.provider.dimensionId == ssMineShipMOD.インスタンス.データ用ディメンジョン)
		{
			DimensionManager.setWorld(l.world.provider.dimensionId,null);
			WorldServer w = ((mineshipWorld)l.world).データ用ワールド;
			DimensionManager.setWorld(l.world.provider.dimensionId,w);
			MinecraftForge.EVENT_BUS.post(new WorldEvent.Save(w));
		}
	}
	
	@ForgeSubscribe
	public void worldload(WorldEvent.Load l)//データ用ワールドがロードされたら、入れ替える
	{
		if(l.world != null&&l.world instanceof WorldServer&&!(l.world instanceof mineshipWorld)&&l.world.provider.dimensionId == ssMineShipMOD.インスタンス.データ用ディメンジョン)
		{
			DimensionManager.setWorld(l.world.provider.dimensionId,null);
			WorldServer w = new mineshipWorld((WorldServer)l.world);
			MinecraftForge.EVENT_BUS.post(new WorldEvent.Load(w));
		}
	}
}
