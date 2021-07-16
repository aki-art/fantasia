package io.github.akiart.fantasia.compatibility.jei;

import com.google.common.collect.ImmutableList;
import io.github.akiart.fantasia.Fantasia;
import io.github.akiart.fantasia.common.fluid.FFluids;
import io.github.akiart.fantasia.common.item.FItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.RegistryObject;

@JeiPlugin
public class JEI implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Fantasia.ID, "jeiplugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        registration.addIngredientInfo(new FluidStack(FFluids.ACID_SOURCE.get(), 8), VanillaTypes.FLUID,
                "Singing acid flooding the caves of Fantasia.",
                "Muckfish can be caught in it with a fishing rod.",
                "Unlike lava, acid only destroys organic matter, such as food or plants.");

        registration.addIngredientInfo(ImmutableList.of(
                new ItemStack(FItems.WOODEN_JAVELIN.get()),
                new ItemStack(FItems.STONE_JAVELIN.get()),
                new ItemStack(FItems.GOLD_JAVELIN.get()),
                new ItemStack(FItems.IRON_JAVELIN.get()),
                new ItemStack(FItems.IRON_JAVELIN.get()),
                new ItemStack(FItems.DIAMOND_JAVELIN.get()),
                new ItemStack(FItems.NETHERITE_JAVELIN.get()),
                new ItemStack(FItems.WOLFRAMITE_JAVELIN.get()),
                new ItemStack(FItems.FROSTWORK_BOLT.get()),
                new ItemStack(FItems.GHASTLY_JAVELIN.get())),
                VanillaTypes.ITEM,
                "Charge and throw this weapon.",
                "Accepts generic weapon enchantments, Loyalty, and Piercing.");

        addItemDescription(registration, FItems.FROSTWORK_PICKAXE,
                "Powerful pickaxe that can be charged to release ranged explosions.",
                "The explosions will break blocks and hurt nearby entities, but not the holder of the pickaxe.",
                "Most enchantments such as Fortune will apply to the explosions. The Gentle Explosions enchantment will prevent environment damage.");

        addItemDescription(registration, FItems.ICICLE,
                "Can be thrown as a projectile for 3 damage dealt.",
                "Has a 2/3 chance of breaking on impact.");

        addItemDescription(registration, FItems.HIEMSITE.lens,
                "These lenses will freeze anything beneath when light is shone through.");

        addItemDescription(registration, FItems.GEHENNITE.lens,
                "These lenses will heat up anything under them when light is shone through.",
                "Sets blocks on fire.");

        registration.addIngredientInfo(ImmutableList.of(
                new ItemStack(FItems.GEHENNITE.waxedLens.get()),
                new ItemStack(FItems.HIEMSITE.waxedLens.get())),
                VanillaTypes.ITEM,
                "No effect, decorational use only.");
    }

    private void addItemDescription(IRecipeRegistration registration, RegistryObject<? extends Item> item, String... description) {
        registration.addIngredientInfo(new ItemStack(item.get()), VanillaTypes.ITEM, description);
    }
}
