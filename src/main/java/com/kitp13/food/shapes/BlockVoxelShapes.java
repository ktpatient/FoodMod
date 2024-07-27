package com.kitp13.food.shapes;

import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockVoxelShapes {
    public static final VoxelShape[] PIZZA_SHAPES = new VoxelShape[] {
        makePizzaShape1(), makePizzaShape2(), makePizzaShape3(), makePizzaShape4(), makePizzaShape5(), makePizzaShape6()
    };
    public static final VoxelShape POT_BLOCK_SHAPE = makePotShape();
    public static final VoxelShape CHOPPING_BOARD_SHAPE = makeChoppingBoardShape();
    public static final VoxelShape BERRY_BUSH_SHAPE = makeBerryShape();
    public static final VoxelShape PEDESTAL_SHAPE = makePedestal();

    private static VoxelShape makeBerryShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 1, 0.9375), BooleanOp.OR);
        return shape;
    }
    private static VoxelShape makeChoppingBoardShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.125, 0.875, 0.125, 0.875), BooleanOp.OR);
        return shape;
    }
    private static VoxelShape makePotShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.125, 0, 1, 0.375, 0.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.125, 0.125, 0.125, 0.375, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.875, 0.125, 0.125, 1, 0.375, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.125, 0.875, 1, 0.375, 1), BooleanOp.OR);
        return shape;
    }
    private static VoxelShape makePizzaShape6(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.5, 1, 0.25, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.25, 0.5), BooleanOp.OR);

        return shape;
    }
    private static VoxelShape makePizzaShape5(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.5, 0.6875, 0.25, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.25, 0.5), BooleanOp.OR);

        return shape;
    }
    private static VoxelShape makePizzaShape4(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.5, 0.3125, 0.25, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.25, 0.5), BooleanOp.OR);

        return shape;
    }
    private static VoxelShape makePizzaShape3(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.25, 0.5), BooleanOp.OR);

        return shape;
    }
    private static VoxelShape makePizzaShape2(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.6875, 0.25, 0.5), BooleanOp.OR);

        return shape;
    }
    private static VoxelShape makePizzaShape1(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.3125, 0.25, 0.5), BooleanOp.OR);

        return shape;
    }
    private static VoxelShape makePedestal(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.9375, 0.9375), BooleanOp.OR);

        return shape;
    }
}
