package gdavid.phi.spell.selector;

import gdavid.phi.entity.MarkerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import vazkii.psi.api.internal.MathHelper;
import vazkii.psi.api.internal.Vector3;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellParam;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.param.ParamNumber;
import vazkii.psi.api.spell.param.ParamVector;
import vazkii.psi.api.spell.piece.PieceSelector;
import vazkii.psi.api.spell.wrapper.EntityListWrapper;

public class NearbyMarkersSelector extends PieceSelector {
	
	SpellParam<Vector3> position;
	SpellParam<Number> radius;
	
	public NearbyMarkersSelector(Spell spell) {
		super(spell);
	}
	
	@Override
	public void initParams() {
		addParam(position = new ParamVector(SpellParam.GENERIC_NAME_POSITION, SpellParam.BLUE, true, false));
		addParam(radius = new ParamNumber(SpellParam.GENERIC_NAME_RADIUS, SpellParam.GREEN, true, false));
	}
	
	@Override
	public Object execute(SpellContext context) throws SpellRuntimeException {
		Vector3 positionVal = getParamValueOrDefault(context, position,
				Vector3.fromVec3d(context.focalPoint.getPositionVec()));
		double radiusVal = getParamValueOrDefault(context, radius, SpellContext.MAX_DISTANCE * 2).doubleValue();
		if (radiusVal <= 0) {
			throw new SpellRuntimeException(SpellRuntimeException.NON_POSITIVE_VALUE);
		}
		if (MathHelper.pointDistanceSpace(positionVal.x, positionVal.y, positionVal.z, context.focalPoint.getPosX(),
				context.focalPoint.getPosY(), context.focalPoint.getPosZ()) > SpellContext.MAX_DISTANCE * 2) {
			throw new SpellRuntimeException(SpellRuntimeException.OUTSIDE_RADIUS);
		}
		AxisAlignedBB boundingBox = AxisAlignedBB.withSizeAtOrigin(radiusVal, radiusVal, radiusVal)
				.offset(positionVal.toVec3D());
		boundingBox = boundingBox
				.intersect(AxisAlignedBB.withSizeAtOrigin(SpellContext.MAX_DISTANCE * 2, SpellContext.MAX_DISTANCE * 2,
						SpellContext.MAX_DISTANCE * 2).offset(context.focalPoint.getPositionVec()));
		return EntityListWrapper
				.make(context.focalPoint.getEntityWorld().getEntitiesWithinAABB(MarkerEntity.class, boundingBox));
	}
	
	@Override
	public Class<?> getEvaluationType() {
		return EntityListWrapper.class;
	}
	
}
