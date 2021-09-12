package gdavid.phi.util;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public final class TooltipHelper {

    @OnlyIn(Dist.CLIENT)
    public static void tooltipIfShift(List<ITextComponent> tooltip, Runnable r) {
        if (Screen.hasShiftDown()) {
            r.run();
        } else {
            tooltip.add(new TranslationTextComponent("misc.phi.shift_for_info"));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void tooltipIfAlt(List<ITextComponent> tooltip, Runnable r) {
        if (Screen.hasAltDown()) {
            r.run();
        } else {
            tooltip.add(new TranslationTextComponent("misc.phi.alt_for_info"));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void tooltipIfCtrl(List<ITextComponent> tooltip, Runnable r) {
        if (Screen.hasControlDown()) {
            r.run();
        } else {
            tooltip.add(new TranslationTextComponent("misc.phi.ctrl_for_info"));
        }
    }

}