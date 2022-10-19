package me.emafire003.dev.foxglow.mixin;

import me.emafire003.dev.foxglow.FoxGlow;
import me.emafire003.dev.foxglow.compat.ColoredGlowLibCompat;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import static me.emafire003.dev.foxglow.FoxGlow.*;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }


    @Inject(method = "eat", at = @At("HEAD"))
    protected void injectInEatFoodMethod(Level world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        //checks if the world is actually the server world, so it can apply stuff properly, like gamerules
        if(world instanceof ServerLevel){
            ServerLevel sworld = (ServerLevel) world;

            //Checks if the item eaten is the glowberry/a glowfood item & if the playerglow is active
            if(FoxGlow.getGlowFoodsList().contains(ForgeRegistries.ITEMS.getKey(stack.getItem())) && PLAYERGLOW){
                this.addEffect(new MobEffectInstance(MobEffects.GLOWING, FOXGLOW_DURATION*20, 0, true, false));

                //checks if custom color glow is enabled
                if(FoxGlow.getCGL()) {
                    ColoredGlowLibCompat.doColoredGlowLibStuff(((Player)(Object)this).getCommandSenderWorld(), ((Player)(Object)this));

                }
                if(FoxGlow.getAP1()){
                    this.addEffect(new MobEffectInstance(MobEffects.LEVITATION, FOXGLOW_DURATION*20, 1, true, false));
                    this.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, FOXGLOW_DURATION*20*2, 200, true, false));
                }

            }
        }
    }
}

