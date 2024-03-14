package org.thomasphillips.greatsilencehelpermod;

import github.pitbox46.hiddennames.data.Animations;
import github.pitbox46.hiddennames.data.NameData;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Events {
    // Hide player names automatically, config is bugged in hidden names mod and won't hide by default
    @SubscribeEvent
    public void playerJoins(PlayerEvent.PlayerLoggedInEvent event) {
        NameData data = NameData.DATA.get(event.getEntity().getUUID());
        data.setAnimation(Animations.HIDDEN);
        NameData.sendSyncData();
        //event.getEntity().sendSystemMessage(Component.literal("Working"));
    }

}
