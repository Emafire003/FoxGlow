package me.emafire003.dev.foxglow.mixin;

import me.emafire003.dev.foxglow.FoxGlow;
import me.emafire003.dev.foxglow.compat.CGLColorCompat;
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
import static me.emafire003.dev.foxglow.compat.ColoredGlowLibCompat.foxcolor;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    private Player player = ((Player)(Object)this);

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }


    @Inject(method = "eat", at = @At("HEAD"))
    protected void injectInEatFoodMethod(Level world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        //checks if the world is actually the server world, so it can apply stuff properly, like gamerules
        if(world instanceof ServerLevel){
            ServerLevel sworld = (ServerLevel) world;

            //Checks if the item eaten is the glowberry/a glowfood item & if the playerglow is active
            if(FoxGlow.getGlowFoodsList().contains(ForgeRegistries.ITEMS.getKey(stack.getItem())) && sworld.getGameRules().getBoolean(PLAYERGLOW.getRule())){
                this.addEffect(new MobEffectInstance(MobEffects.GLOWING, this.level.getGameRules().getInt(FOXGLOW_DURATION.getRule())*20, 0, true, false));

                //checks if custom color glow is enabled
                if(sworld.getGameRules().getBoolean(CUSTOM_COLOR_GLOW.getRule()) && FoxGlow.getCGL()) {

                    //Checks if it should apply a random color
                    if (sworld.getGameRules().getBoolean(RANDOM_COLOR_GLOW.getRule())) {
                        ColoredGlowLibCompat.getLib().setColorToEntity(player, CGLColorCompat.randomColor(random));
                    }else if(!ColoredGlowLibCompat.getLib().hasEntityColor(player)){
                        ColoredGlowLibCompat.getLib().setColorToEntity(player, foxcolor);
                    }
                    if(FoxGlow.getAP1()){
                        this.addEffect(new MobEffectInstance(MobEffects.LEVITATION, this.level.getGameRules().getInt(FOXGLOW_DURATION.getRule())*20, 1, true, false));
                        this.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, this.level.getGameRules().getInt(FOXGLOW_DURATION.getRule())*20*2, 200, true, false));
                    }

                }else if(!ColoredGlowLibCompat.getLib().getEntityTypeColor(this.getType()).equals(CGLColorCompat.getWhiteColor())){
                    //This is done because the entity would still have another color selected otherwise.
                    ColoredGlowLibCompat.getLib().setColorToEntity(player, CGLColorCompat.getWhiteColor());
                }

            }
        }
    }
}

