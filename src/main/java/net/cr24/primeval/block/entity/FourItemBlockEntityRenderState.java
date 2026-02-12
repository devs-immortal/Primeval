package net.cr24.primeval.block.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;

import java.util.Collections;
import java.util.List;

public class FourItemBlockEntityRenderState extends BlockEntityRenderState {
    public List<ItemRenderState> itemStates = ImmutableList.of(new ItemRenderState(), new ItemRenderState(), new ItemRenderState(), new ItemRenderState());

    public FourItemBlockEntityRenderState() {
    }
}
