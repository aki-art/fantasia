package io.github.akiart.fantasia.client.renderer.tileentity;

import io.github.akiart.fantasia.common.tileentity.CrystalLensTileEntity;
import io.github.akiart.fantasia.common.tileentity.model.CrystalLensModel;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class CrystalLensTileEntityRenderer extends GeoBlockRenderer<CrystalLensTileEntity> {

	public CrystalLensTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn, new CrystalLensModel());
	}
}
