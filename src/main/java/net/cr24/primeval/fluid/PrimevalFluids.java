package net.cr24.primeval.fluid;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.cr24.primeval.PrimevalMain;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

public class PrimevalFluids {

    public static final Fluid MOLTEN_COPPER = registerFluid("molten_copper", new LavaFluid.Still());
    public static final Fluid MOLTEN_BOTCHED_ALLOY = registerFluid("molten_botched_alloy", new LavaFluid.Still());


    public static void init() {}

    private static Fluid registerFluid(String id, Fluid fluid) {
        return Registry.register(Registry.FLUID, PrimevalMain.getId(id), fluid);
    }

    public static Pair<Identifier, Integer> fluidFromJson(JsonObject json) {
        if (json.has("fluid")) {
            if (json.has("amount")) {
                String fluid = JsonHelper.getString(json, "fluid");
                int aOut = JsonHelper.getInt(json, "amount");
                return new Pair<>(new Identifier(fluid), aOut);
            } else {
                throw new JsonParseException("Can't have a fluid result with an unspecified amount!");
            }
        } else {
            throw new JsonParseException("Can't have a fluid result with no fluid!");
        }
    }
}
