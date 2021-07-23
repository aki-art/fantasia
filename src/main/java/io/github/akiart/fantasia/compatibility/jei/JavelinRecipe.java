//package io.github.akiart.fantasia.compatibility.jei;
//
//import com.google.common.collect.ImmutableList;
//import net.minecraft.client.Minecraft;
//import net.minecraft.item.ItemStack;
//
//import java.util.Collections;
//import java.util.List;
//
//public class JavelinRecipe {
//    private final List<List<ItemStack>> inputs;
//    private final List<List<ItemStack>> outputs;
//
//    public JavelinRecipe(List<ItemStack> leftInput, List<ItemStack> rightInputs, List<ItemStack> outputs) {
//        this.inputs = ImmutableList.of(leftInput, rightInputs);
//        this.outputs = Collections.singletonList(outputs);
//
//    }
//
//    public List<List<ItemStack>> getInputs() {
//        return this.inputs;
//    }
//
//    public List<List<ItemStack>> getOutputs() {
//        return this.outputs;
//    }
//
//}
