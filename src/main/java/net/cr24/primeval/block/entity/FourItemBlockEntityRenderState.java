package net.cr24.primeval.block.entity;

import com.google.common.collect.ImmutableList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class FourItemBlockEntityRenderState extends BlockEntityRenderState {
    public List<ItemStackRenderState> itemStates = ImmutableList.of(new ItemStackRenderState(), new ItemStackRenderState(), new ItemStackRenderState(), new ItemStackRenderState());

    public FourItemBlockEntityRenderState() {
    }
}
